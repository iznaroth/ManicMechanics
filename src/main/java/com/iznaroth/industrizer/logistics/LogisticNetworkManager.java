package com.iznaroth.industrizer.logistics;

import com.iznaroth.industrizer.logistics.job.FluidJob;
import com.iznaroth.industrizer.logistics.job.GasJob;
import com.iznaroth.industrizer.logistics.job.LogisticJob;
import com.iznaroth.industrizer.logistics.job.PowerJob;
import com.iznaroth.industrizer.tile.TubeBundleTile;
import com.sun.xml.internal.ws.api.pipe.Tube;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jline.utils.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
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

    private ArrayList<TubeBundleTile> all_nodes = new ArrayList<TubeBundleTile>();


    //Job traversal helper lists. Pulled from connections on wakeup.
            //Must be 2D due to bundles allowing a full network with many orphan subnetworks.
    private ArrayList<ArrayList<Connection>> storage_subnetworks;
    private ArrayList<ArrayList<Connection>> power_subnetworks;
    private ArrayList<ArrayList<Connection>> fluid_subnetworks;
    private ArrayList<ArrayList<Connection>> gas_subnetworks;

    /**
     * Job caches do not all work the same - particularly power. Each network only has one open power job which seeks to maximally saturate the network whenever any power is available.
     * For all others, the Cache is a list of lists of jobs, where each list is the set of legal jobs a given connection is allowed to perform so long as it is using the same operation.
     */
    private ArrayList<ArrayList<LogisticJob>> item_cache;
    private ArrayList<PowerJob> open_power_rq;
    private ArrayList<ArrayList<FluidJob>> fluid_cache;
    private ArrayList<ArrayList<GasJob>> gas_cache;



    public LogisticNetworkManager(){

    }

    public LogisticNetworkManager mergeWithProvided(LogisticNetworkManager other){
        List<BlockPos> combined_connections = null;
        List<BlockPos> combined_tubes = null;

        //we ignore cache because it is invalidated by the change anyways

        LogisticNetworkManager combined = new LogisticNetworkManager();

        return combined;
    }

    public int startSearchRecursion(Connection from, int type){

        if(type < 0 || type > 3){
            throw new IllegalArgumentException("Type must be a valid tube value");
        }

        //Ideally, these three functions should recursively search a particular network type and back-store it to this manager as a subnetwork.
        //Jobs call this if they don't have cached jobs or a parent network to search for them in.
        TubeBundleTile startingPoint = from.getParentTile();
        Direction startingVector = from.getContactDirection();

        ArrayList<Connection> toStore = new ArrayList<Connection>();
        toStore = searchAndStoreNetwork(startingPoint, type, toStore, startingVector);

        cleanupTileNodes(); //Unmark all dirty nodes post-traversal.

        switch(type){
            case 0:
                this.storage_subnetworks.add(toStore);
                return storage_subnetworks.size() - 1;
            case 1:
                this.power_subnetworks.add(toStore);
                return storage_subnetworks.size() - 1;
            case 2:
                this.fluid_subnetworks.add(toStore);
                return storage_subnetworks.size() - 1;
            case 3:
                this.gas_subnetworks.add(toStore);
                return storage_subnetworks.size() - 1;
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



    public ArrayList<Connection> searchAndStoreNetwork(TubeBundleTile from, int type, ArrayList<Connection> unfinished_network, Direction previous){ //The recursive investigation method, for now.

        from.setTraverseDirty(); //Prevent normal-repeat cycling

        for(Direction direction : Direction.values()){

            if(direction == previous){
                System.out.println("Pass."); //Prevent back-and-forth cycling
            } else {
                TubeBundleTile next = from.hasTubeNeighbor(direction);
                if (next != null && !next.isTraverseDirty() && next.hasTube(type)) {

                    unfinished_network = checkAndAddValidCon(unfinished_network, next.getConnections(), type);

                    searchAndStoreNetwork(next, type, unfinished_network, direction);
                }
            }
        }

        //This is the point where recursion is unwinding, e.g. a dead end.

        return unfinished_network;
    }

    public ArrayList<Connection> checkAndAddValidCon(ArrayList<Connection> unfinished_network, Connection[] tile_connections, int type){
        for(Connection con : tile_connections){
            if(con.isTypeActive(type)){
                unfinished_network.add(con);
            }
        }

        return unfinished_network;
    }



    public int buildLogisticJobCache(Connection cxn, ItemStack toInsert){
        int parent = findParentSubnetworkFor(cxn, 0);

        if(parent == -1){ //This is a FULLY-ORPHANED connection - it has no subnetwork yet.
            parent = startSearchRecursion(cxn, 0);
        }

        ArrayList<Connection> subnetwork = rankByManhattanDist(cxn, storage_subnetworks.get(parent)); //to search
        ArrayList<LogisticJob> cxnCache = new ArrayList<LogisticJob>(); //what we're building

        for(Connection to : subnetwork){
            int slot = to.getFirstLegalInsertSlot(toInsert);
            if(slot != -1){ //Indicates that the item we tested is valid to insert somewhere in the connected inventory
                LogisticJob newJob = new LogisticJob(toInsert, cxn, to, cxn.getSlotFrom(), slot); //A new job is cached!
                cxnCache.add(newJob);
            }
        }
        this.item_cache.add(cxnCache); //All legal jobs for the given circumstances are queued.
        return this.item_cache.size() - 1; //This is the index the connection will check so long as it is trying for the same job.
    }

    //
    public boolean executeCacheEntry(int idx, int type){ //NOTE - wrap job types as AbstractJob children, make caches public, let the cxns pass them to these functions to eliminate switchers
        //This function either passes each job to the executor, or returns false if the cache is empty.
        //NOTE - Another one! Cache numbering means INVALIDATION CANNOT BE DELETION! You need to leave the cache entry populated in a way identifiable as an invalidated entry so this can do cleanup.

        return false;
    }

    public ArrayList<Connection> rankByManhattanDist(Connection cxn, ArrayList<Connection> toRank){
        //This is the very, very stupid implementation of how the cache priority is ordered - pure manhattan distance. It does not consider network distance yet - give me a little bit longer.
        TreeMap<Integer, Connection> sortmap = new TreeMap<>();

        for(Connection to : toRank){
            int distance = cxn.getParentTile().getBlockPos().distManhattan(to.getParentTile().getBlockPos());
            sortmap.put(distance, to);
        }

        return (ArrayList<Connection>) sortmap.values(); //is this a weird cast? my degree is useless man
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

    public void addTileToGrid(TubeBundleTile tile){
        all_nodes.add(tile);
    }

    public void cleanupTileNodes(){
        for(TubeBundleTile tile : all_nodes){
            tile.setTraverseClean();
        }
    }
}
