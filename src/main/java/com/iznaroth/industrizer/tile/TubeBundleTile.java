package com.iznaroth.industrizer.tile;

import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.block.tube.TransportTubeBlock;
import com.iznaroth.industrizer.block.tube.TubeBundleBlock;
import com.iznaroth.industrizer.logistics.Connection;
import com.iznaroth.industrizer.logistics.LogisticNetworkManager;
import com.iznaroth.industrizer.util.TubeBundleStateMapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class TubeBundleTile extends TileEntity  {

    private boolean[] tubesInBlock = new boolean[]{false, false, false, false}; //LOGISTIC / ENERGY / FLUID / GAS
    private Connection[] connections = new Connection[]{null, null, null, null, null, null}; //IF NULL PRESUME NO CONTACT



    private LogisticNetworkManager manager; //reference to current network for logical ops

    /**
     * The TubeBundleTile has a misleading name - it represents the tile attached to any logistic network tube - power, items, liquid or gas.
     * It primarily acts as a storage for references to "what is in the block itself" and "what, if anything, we are connected to".
     * The actual work on-network is done through any stored Connection objects (which obviously must be deserialized into NBT on unload, so they aren't very heavy either)
     * which reference their parent tile to see which kind of jobs they need to try and do.
     *
     * This class is mostly to provide that information, keep a reference to its parent network, and update the block visuals.
     *
     * It may be unpacked if it proves too architecturally janky to use, instead utilizing individual TEs for each conduit type.
     */


    public TubeBundleTile() {
        super(IndustrizerTileEntities.TUBE_BUNDLE_TILE.get());
    }


    @Override
    public void onLoad(){
        TubeBundleTile old = TubeBundleStateMapper.checkAndPurge(this.getBlockPos());

        if(old != null){
            System.out.println("Mapping parameters!");
            this.copyInto(old); //Horrible method for saving state when a tube is converted into a full bundle. Sorry!
        }

        this.setupInitialConnections();

        Block parent = this.getBlockState().getBlock();
        if(parent instanceof TubeBundleBlock){ //TBB has unique comparison conditions on spawn that require a stall before the shape can be fully-constructed.
            refreshModelConnections();
        }
    }

    @Override
    public CompoundNBT save(CompoundNBT tag){
        CompoundNBT result = new CompoundNBT();
        //Need a way to decompose & rebuild connections.
        result.putBoolean("has_logistic", tubesInBlock[0]);
        result.putBoolean("has_power", tubesInBlock[1]);
        result.putBoolean("has_fluid", tubesInBlock[2]);
        result.putBoolean("has_gas", tubesInBlock[3]);

        return result;
    }

    @Override
    public void load(BlockState state, CompoundNBT tag){
        tubesInBlock[0] = tag.getBoolean("has_logistic");
        tubesInBlock[1] = tag.getBoolean("has_power");
        tubesInBlock[2] = tag.getBoolean("has_fluid");
        tubesInBlock[3] = tag.getBoolean("has_gas");

    }

    public TileEntity getNeighborTile(BlockPos thisPos, Direction where){
        return this.level.getBlockEntity(thisPos.relative(where));
    }

    public void setupInitialConnections(){
        int i = 0;

        for(Direction dir : Direction.values()){


            BlockPos toCheck = this.worldPosition.relative(dir);
            //System.out.println(toCheck);

            if(this.level.getBlockState(toCheck) != null) {

                if (this.level.getBlockState(toCheck).getBlock() instanceof TransportTubeBlock || this.level.getBlockState(toCheck).getBlock().equals(IndustrizerBlocks.TUBE_BUNDLE.get())) {
                    //activeConnections[i] = true;

                    inheritUpdateLogisticNetwork((TubeBundleTile) this.level.getBlockEntity(toCheck)); //WARNING - This is probably an unsafe cast!
                }
            }

            if(this.manager == null){
                createLogisticNetwork(); //this tube has no connections, so we make a new manager.
            }
            i++;
        }
    }

    public void inheritUpdateLogisticNetwork(TubeBundleTile from){
        //This is called when a newly-placed tube is connected to an existing network. It inherits the network and marks it as dirty (flush route cache)

        if(this.manager != null && !this.manager.equals(from.getNetworkManager())){
            //This is a second, unique network manager - we are linking to multiple previously-separate networks and need to commit a merge first.

            this.manager = mergeCopyNetwork(from.getNetworkManager()); //This is redundant - the Network Manager will call updateParentNetwork on every contained tube-bundle.

            return;
        }
    }

    public void createLogisticNetwork(){
        LogisticNetworkManager nw_manager = new LogisticNetworkManager();
        this.manager = nw_manager;
    }

    public LogisticNetworkManager mergeCopyNetwork(LogisticNetworkManager other){
        //The "first-referred network" is the parent, and the newly-found is merged in.
        return this.manager.mergeWithProvided(other);
    }

    public void addTube(int which) {
        if(which > 3){
            throw new IllegalArgumentException("Only 4 types of tubes!");
        }

        tubesInBlock[which] = true;
    }

    public void downgradeBlockToTube(int type){
        //Whenever a bundle has only one tube type, we have to downgrade it back to its remaining block.
    }

    public void removeTube(int which) throws Exception {
        if(which > 3){
            throw new IllegalArgumentException("Only 4 types of tubes!");
        }

        tubesInBlock[which] = false;

        if(getStuffedTubeCount() == 1){
            downgradeBlockToTube(-1); //PLACEHOLDER!
        } else if(getStuffedTubeCount() == 0){
            throw new Exception("A TubeBundle's stuffed count has reached zero! This should never happen.");
        }
    }

    public int getStuffedTubeCount(){
        int total = 0;
        for(boolean each : tubesInBlock){
            if(each){
                total++;
            }
        }
        return total;
    }

    public boolean[] getTubesInBlock(){
        return this.tubesInBlock;
    }

    public boolean hasTube(int which){
        return tubesInBlock[which];
    }


    public LogisticNetworkManager getNetworkManager(){
        return this.manager;
    }

    public void setNetworkManager(LogisticNetworkManager manager){
        this.manager = manager;
    }

    public Connection[] getConnections(){
        return this.connections;
    }

    public void copyInto(TubeBundleTile other){
        this.tubesInBlock = other.getTubesInBlock();
        this.manager = other.getNetworkManager();
        this.connections = other.getConnections(); //Should be everything relevant.
    }

    public boolean anyMatch(TubeBundleTile other){
        boolean[] compare = other.getTubesInBlock();
        for(int i = 0; i < 4; i++){
            if(this.tubesInBlock[i] == compare[i]){
                return true;
            }
        }

        return false;
    }

    public void refreshModelConnections(){ //Called whenever something happens that would require a hard reevaluation of connections (namely addition and removal of conduits)
        System.out.println("From inside this tile, my array looks like this: " + this.getTubesInBlock());

        BlockState current = this.getBlockState();

        for(Direction dir : Direction.values()){
            //Safe recall of updateShape on every direction. Should not recieve a null tile this time.

            current = current.getBlock().updateShape(current, dir, this.getLevel().getBlockState(this.worldPosition.relative(dir)), this.getLevel(), this.worldPosition, this.worldPosition.relative(dir));
        }

        this.getLevel().setBlockAndUpdate(this.getBlockPos(), current); //WARNING - I am unsure if setBlock will kill the TileEntity here.

        System.out.println(this.getLevel());

    }


}
