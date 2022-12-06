package com.iznaroth.manicmechanics.logistics;

import com.iznaroth.manicmechanics.logistics.job.FluidJob;
import com.iznaroth.manicmechanics.logistics.job.GasJob;
import com.iznaroth.manicmechanics.logistics.job.LogisticJob;
import com.iznaroth.manicmechanics.logistics.job.PowerJob;
import com.iznaroth.manicmechanics.blockentity.TubeBundleBE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class LogisticNetworkManager {

    //The head of a logistic network. A network is a reference utility for any set of contiguous TubeBundleTiles, providing tools for navigating the sub-networks within -
    //it is primarily used to perform full-depth searches of a specific tube type and provide all valid connections that exist for it.
    // The network is always marked dirty for re-evaluation of stored Connections if an existing tube is broken (could be smarter).
    //To minimize re-evaluation, when an attached block is changed or removed the Connection is responsible for informing the network of its recent status change.
    //NOTE - Some smarter re-evaluation optimizations you can add:
    /**
     *      - Do not reevaluate network the moment it is marked dirty - wait until a job comes in. That way you can avoid re-treading every time a tube is added.
     *      - Add a "modifying network" timer that freezes ops while changes happen for a minimum duration.
     *      - When a TubeBundleTile is destroyed, intelligently check for orphaned nodes - no need to re-evaluate if no connections died.
     */

    private ArrayList<TubeBundleBE> all_nodes = new ArrayList<TubeBundleBE>();


    //Job traversal helper lists. Pulled from connections on wakeup.
            //Must be 2D due to bundles allowing a full network with many orphan subnetworks.
    private ArrayList<ArrayList<Connection>> storage_subnetworks = new ArrayList<>();
    private ArrayList<ArrayList<Connection>> power_subnetworks = new ArrayList<>();
    private ArrayList<ArrayList<Connection>> fluid_subnetworks = new ArrayList<>();
    private ArrayList<ArrayList<Connection>> gas_subnetworks = new ArrayList<>();

    /**
     * Job caches do not all work the same - particularly power. Each network only has one open power job which seeks to maximally saturate the network whenever any power is available.
     * For all others, the Cache is a list of lists of jobs, where each list is the set of legal jobs a given connection is allowed to perform so long as it is using the same operation.
     */
    private ArrayList<ArrayList<LogisticJob>> item_cache = new ArrayList<>();
    private ArrayList<PowerJob> open_power_rq = new ArrayList<>();
    private ArrayList<ArrayList<FluidJob>> fluid_cache = new ArrayList<>();
    private ArrayList<ArrayList<GasJob>> gas_cache = new ArrayList<>();



    public LogisticNetworkManager(){

    }

    public LogisticNetworkManager mergeWithProvided(LogisticNetworkManager other){
        List<BlockPos> combined_connections = null;
        List<BlockPos> combined_tubes = null;

        //we ignore cache because it is invalidated by the change anyways

        return new LogisticNetworkManager();
    }

    public int startSearchRecursion(Connection from, int type){

        if(type < 0 || type > 3){
            throw new IllegalArgumentException("Type must be a valid tube value");
        }

        //Ideally, these three functions should recursively search a particular network type and back-store it to this manager as a subnetwork.
        //Jobs call this if they don't have cached jobs or a parent network to search for them in.
        TubeBundleBE startingPoint = from.getParentTile();
        Direction startingVector = from.getContactDirection();

        ArrayList<Connection> toStore = new ArrayList<Connection>();

        toStore = checkAndAddValidCon(toStore, from.getParentTile().getConnections(), type); //The subnetwork contains this cxn's parent's connection inventory as well.
        System.out.println("This should have AT LEAST ONE CONNECTION no matter what: " + toStore.toString());


        System.out.println("Start search from: " + from.getParentTile().getBlockPos());
        toStore = searchAndStoreNetwork(startingPoint, type, toStore, startingVector);

        cleanupTileNodes(); //Unmark all dirty nodes post-traversal.

        System.out.println("BUILT A NEW SUBNETWORK FOR " + type + "! -- CONTENTS: " + toStore.toString());

        switch(type){
            case 0:
                this.storage_subnetworks.add(toStore);
                return storage_subnetworks.size() - 1;
            case 1:
                this.power_subnetworks.add(toStore);
                return power_subnetworks.size() - 1;
            case 2:
                this.fluid_subnetworks.add(toStore);
                return fluid_subnetworks.size() - 1;
            case 3:
                this.gas_subnetworks.add(toStore);
                return gas_subnetworks.size() - 1;
        }

        return -1; //The compiler can't always be right ig

    }



    public int findParentSubnetworkFor(Connection cxn, int type){
        switch(type){
            case 0:
                return searchConnectionMatchForType(storage_subnetworks, cxn);
            case 1:
                return searchConnectionMatchForType(power_subnetworks, cxn);
            case 2:
                return searchConnectionMatchForType(fluid_subnetworks, cxn);
            case 3:
                return searchConnectionMatchForType(gas_subnetworks, cxn);
        }

        throw new IllegalArgumentException("Illegal tube types passed!");
    }

    private int searchConnectionMatchForType(ArrayList<ArrayList<Connection>> in, Connection cxn){
        for(int i = 0; i < in.size(); i++){
             if(in.get(i).contains(cxn)){
                 return i;
            }
        }

         return -1; //This connection is not in a subnetwork, but is in this parent network. Return -1 to implicatively start a new subnetwork search.
    }



    public ArrayList<Connection> searchAndStoreNetwork(TubeBundleBE from, int type, ArrayList<Connection> unfinished_network, Direction previous){ //The recursive investigation method, for now.

        from.setTraverseDirty(); //Prevent normal-repeat cycling

        for(Direction direction : Direction.values()){

            if(direction == previous){
                System.out.println("Pass for " + direction); //Prevent back-and-forth cycling
            } else {
                TubeBundleBE next = from.hasTubeNeighbor(direction);
                System.out.println("Direction: " + direction + " , Contents: " + next);

                if(next != null){
                    System.out.println("Is it dirty?: " + next.isTraverseDirty());
                }

                if (next != null && !next.isTraverseDirty() && next.hasTube(type)) {

                    System.out.println("Tube neighbor found " + direction);

                    unfinished_network = checkAndAddValidCon(unfinished_network, next.getConnections(), type);

                    searchAndStoreNetwork(next, type, unfinished_network, direction.getOpposite());
                }
            }
        }

        //This is the point where recursion is unwinding, e.g. a dead end.

        return unfinished_network;
    }

    public ArrayList<Connection> checkAndAddValidCon(ArrayList<Connection> unfinished_network, Connection[] tile_connections, int type){

        System.out.println("CHECKING TILE CONNECTION LIST FOR VALIDITY: " + Arrays.toString(tile_connections));

        for(Connection con : tile_connections){
            if(con != null){
                System.out.println(Arrays.toString(con.getActiveConnections()));
            }
            if(con != null && con.isTypeActive(type)){
                unfinished_network.add(con);
            }
        }

        return unfinished_network;
    }



    public int buildLogisticJobCache(Connection cxn, ItemStack toInsert){
        int parent = findParentSubnetworkFor(cxn, 0);

        System.out.println("Building a new job cache for ItemStack: " + toInsert.toString());

        if(parent == -1){ //This is a FULLY-ORPHANED connection - it has no subnetwork yet.
            System.out.println("MAJOR PROBLEM ---------- Failed to find subnetwork!");
            parent = startSearchRecursion(cxn, 0);
        }

        ArrayList<Connection> subnetwork = rankByManhattanDist(cxn, storage_subnetworks.get(parent)); //to search
        ArrayList<LogisticJob> cxnCache = new ArrayList<LogisticJob>(); //what we're building

        for(Connection to : subnetwork){

            if(to == cxn){
                System.out.println("Skip self in caching jobs");
            } else {
                int slot = to.getFirstLegalInsertSlot(toInsert);
                if (slot != -1) { //Indicates that the item we tested is valid to insert somewhere in the connected inventory
                    LogisticJob newJob = new LogisticJob(toInsert, cxn, to, cxn.getSlotFrom(), slot); //A new job is cached!
                    cxnCache.add(newJob);
                }
            }
        }

        if(cxn.item_cache != -1){  //This is not the first time this connection has built a cache.
            this.item_cache.set(cxn.item_cache, cxnCache);
            return cxn.item_cache; //replace and reuse old slot!
        } else { //This is a first-time build. Set the connection to ACTIVE and assign it.
            if(!ActiveConnectionQueue.queue.contains(cxn)){
                ActiveConnectionQueue.enqueueActive(cxn); //Connection will now be ticked and try to perform jobs. NOTE - This may be moved to post initial-exec-success
            }
            this.item_cache.add(cxnCache); //All legal jobs for the given circumstances are queued.
            System.out.println("Setting new cache IDX to " + (this.item_cache.size() - 1));
            return this.item_cache.size() - 1; //This is the index the connection will check so long as it is trying for the same job.
        }

    }

    public int useOrFindMovedCacheEntry(Connection cxn, int old){ //This just does Logistic right now. Oops

        if(old == -1){
            return -1; //We never found a cache match. Assume failure and rebuild.
        }

        if(item_cache.get(old).size() == 0){
            System.out.println(" ----------- Empty JobCache Created. There is likely a pathfinding issue. -----------");
            return old;
        }

        if(this.item_cache.get(old).get(0).from != cxn){ //This cache isn't the correct one? Search backwards from this index until we find it. Since caches only move on remove, they can only move down in index.
            return useOrFindMovedCacheEntry(cxn, old - 1); //navigate downwards until we find a match, then return it up the hierarchy.
        }

        //This is only reached when OLD matches the cxn, for the recursion
        return old;
    }

    //
    public boolean executeLogisticCacheEntry(int idx){
        //This function either passes each job to the executor, or returns false if the cache is empty.
        //NOTE - Another one! Cache numbering means INVALIDATION CANNOT BE DELETION! You need to leave the cache entry populated in a way identifiable as an invalidated entry so this can do cleanup.

        ArrayList<LogisticJob> toExecute = this.item_cache.get(idx);

        for(LogisticJob job : toExecute){
            boolean flag = job.tryAndExecute();
            if(flag)
                return true; //do first legal job by distance.
        }

        //No jobs were executed legally? something went wrong.
        return false;
    }

    public ArrayList<Connection> rankByManhattanDist(Connection cxn, ArrayList<Connection> toRank){
        //This is the very, very stupid implementation of how the cache priority is ordered - pure manhattan distance. It does not consider network distance yet - give me a little bit longer.
        TreeMap<Integer, Connection> sortmap = new TreeMap<>();

        for(Connection to : toRank){
            int distance = cxn.getParentTile().getBlockPos().distManhattan(to.getParentTile().getBlockPos());
            sortmap.put(distance, to);
        }

        return new ArrayList<>(sortmap.values());
    }

    public int buildPowerJobForProvider(Connection cxn){
        //It is implied that the passed connection is attached to a provider. We need to find or traverse our subnetwork first.

        int parent = findParentSubnetworkFor(cxn, 1);

        System.out.println("Building a new job cache for Power Provider " + cxn.getAttached());

        if(parent == -1){ //This is a FULLY-ORPHANED connection - it has no subnetwork yet.
            System.out.println("Building a new subnetwork for POWER. This should only happen once.");
            parent = startSearchRecursion(cxn, 1);
        }

        ArrayList<Connection> subnetwork = power_subnetworks.get(parent); //No need to manhattan this - power transfer is universally instantaneous.
        PowerJob jobCxns = new PowerJob((IEnergyStorage) cxn.getAttached()); //what we're building

        //With that done, we can check those connections for any that can ACCEPT, adding them to our job list.

        for(Connection to : subnetwork){

            if(to == cxn){
                System.out.println("Skip self in caching jobs");
            } else {
                if(to.getAttached().getCapability(ForgeCapabilities.ENERGY).isPresent() && ((IEnergyStorage) to.getAttached().getCapability(ForgeCapabilities.ENERGY).orElse(null)).canReceive()){
                    jobCxns.addReciever((IEnergyStorage) to.getAttached().getCapability(ForgeCapabilities.ENERGY).orElse(null));
                }
            }
        }

        //We cache it and return the int, so it will be continuously executed. That's all!

        this.open_power_rq.add(jobCxns);
        return this.open_power_rq.size() - 1;
    }

    public boolean executePowerProviderJob(int cached){
        //Check the 1D power cache for the hit, panic if necessary, and distribute power to connections.
        //Power is prone to race-conditions to some degree, so we kinda just refund and move on if the job is illegal in-practice.
        //TODO - notification for bad power job types so-as to sleep connection.

        PowerJob toExecute = open_power_rq.get(cached);
        toExecute.tryAndExecute(); //TODO - This is completely unsafe and crash-worthy. Correct it!

        return false;
    }

    public boolean invalidateJobList(int idx){
        //Invalidates an entry in the job list, destroying the objects.

        return false;
    }

    public boolean dirtyCacheInvalidate(){
        //Called when the network recieves a dirty change (for now, any deletion - there are smarter ways of checking this)
        //and there is a possibility of non-possible cached jobs, so we purge it and notify all connections. Connections then
        //remove their IDX ref so that they need to do a full locateAndTest for any new request.

        return false;
    }

    public void addTileToGrid(TubeBundleBE tile){
        all_nodes.add(tile);
        System.out.println("Grid updated. Contents: " + this.all_nodes.toString() + " size check " + this.all_nodes.size());
    }

    public void cleanupTileNodes(){
        for(TubeBundleBE tile : all_nodes){
            tile.setTraverseClean();
        }
    }
}
