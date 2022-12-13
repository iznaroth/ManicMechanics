package com.iznaroth.manicmechanics.blockentity;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.block.tube.AbstractTubeBlock;
import com.iznaroth.manicmechanics.logistics.Connection;
import com.iznaroth.manicmechanics.logistics.LogisticNetworkManager;
import com.iznaroth.manicmechanics.util.TubeBundleStateMapper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public class TubeBundleBE extends BlockEntity {

    private boolean[] tubesInBlock = new boolean[]{false, false, false, false}; //LOGISTIC / ENERGY / FLUID / GAS
    private Connection[] connections = new Connection[]{null, null, null, null, null, null}; //IF NULL PRESUME NO CONTACT

    private TubeBundleBE[] validNeighbors = {null, null, null, null, null, null};

    private LogisticNetworkManager manager; //reference to current network for logical ops. Is not serializable (networks are rebuilt on world load)

    private boolean traverse_dirty = false; //used by network manager during traversal to track what tiles have been visited before.

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


    public TubeBundleBE(BlockPos pos, BlockState state) {
        super(MMBlockEntities.TUBE_BUNDLE_TILE.get(), pos, state);
    }


    @Override
    public void onLoad() {
        if (this.getLevel().isClientSide()) {
            return;
        }

        TubeBundleBE old = TubeBundleStateMapper.checkAndPurge(this.getBlockPos());

        if (old != null) {
            System.out.println("Tile created from existing tube at pos: " + this.worldPosition + "Mapping parameters!");
            this.copyInto(old); //Horrible method for saving state when a tube is converted into a full bundle. Sorry!

            this.setChanged();
        }
    }
/**
        refreshModelConnections(); //We usually need to access the tile to build the right initial shape, so we wait.

        this.setChanged();
        System.out.println("IF YOU SEE THIS - THE TILE LOADED - FREEZE IS ATTRIBUTED TO SOMETHING ELSE.");
    }
 **/

    @Override
    public void saveAdditional(CompoundTag tag){
        //Need a way to decompose & rebuild connections.
        super.saveAdditional(tag);
        tag.putBoolean("has_logistic", tubesInBlock[0]);
        tag.putBoolean("has_power", tubesInBlock[1]);
        tag.putBoolean("has_fluid", tubesInBlock[2]);
        tag.putBoolean("has_gas", tubesInBlock[3]);


        System.out.println("Saving state for tileentity at " + this.worldPosition);
        System.out.println("Saving this: " + tag.toString());

    }

    @Override
    public void load(CompoundTag tag){

        super.load(tag);

        //System.out.println("Loading state for tileentity at " + this.worldPosition);
        //System.out.println("Loading this: " + tag.toString());

        tubesInBlock[0] = tag.getBoolean("has_logistic");
        tubesInBlock[1] = tag.getBoolean("has_power");
        tubesInBlock[2] = tag.getBoolean("has_fluid");
        tubesInBlock[3] = tag.getBoolean("has_gas");

    }

    private final int INVALID_VALUE = -1;
    private int ticksLeftTillDisappear = INVALID_VALUE;  // the time (in ticks) left until the block disappears

    // set by the block upon creation
    public void setTicksLeftTillDisappear(int ticks)
    {
        ticksLeftTillDisappear = ticks;
    }

    /**
    // When the world loads from disk, the server needs to send the TileEntity information to the client
    //  it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this:
    //  getUpdatePacket() and onDataPacket() are used for one-at-a-time TileEntity updates
    //  getUpdateTag() and handleUpdateTag() are used by vanilla to collate together into a single chunk update packet
    //  Not really required for this example since we only use the timer on the client, but included anyway for illustration
    @Override
    @Nullable
    public SUpdateTileEntityPacket getUpdatePacket()
    {
        System.out.println("getUpdatePacket is trying to save now.");
        CompoundNBT nbtTagCompound = new CompoundNBT();
        save(nbtTagCompound);
        int tileEntityType = 42;  // arbitrary number; only used for vanilla TileEntities.  You can use it, or not, as you want.
        return new SUpdateTileEntityPacket(this.worldPosition, tileEntityType, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        BlockState blockState = this.getLevel().getBlockState(this.worldPosition);
        load(blockState, pkt.getTag());   // read from the nbt in the packet
    }

    /* Creates a tag containing all of the TileEntity information, used by vanilla to transmit from server to client
     */

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag result = super.getUpdateTag();
        System.out.println("getUpdateTag is trying to save now.");
        saveAdditional(result); //NOTE - this logic was based on the old return paradigm, and so saving and loading may break due to this.
        return result;
    }

    /* Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client
    */
    @Override
    public void handleUpdateTag(CompoundTag parentNBTTagCompound)
    {
        this.load(parentNBTTagCompound);
        //this.refreshModelConnections();
    }

    public BlockEntity getNeighborTile(BlockPos thisPos, Direction where){
        return this.level.getBlockEntity(thisPos.relative(where));
    }

    public void setupNetwork(){
        int i = 0;

        for(Direction dir : Direction.values()){


            BlockPos toCheck = this.worldPosition.relative(dir);
            //System.out.println(toCheck);

            if(this.level.getBlockState(toCheck) != null) {

                if (this.level.getBlockState(toCheck).getBlock() instanceof AbstractTubeBlock || this.level.getBlockState(toCheck).getBlock().equals(MMBlocks.TUBE_BUNDLE.get())) {
                    //activeConnections[i] = true;

                    System.out.println("Inheriting neighbor network! This tube's manager should always be null: " + this.manager);

                    TubeBundleBE tile = (TubeBundleBE) this.level.getBlockEntity(toCheck);

                    if(tile != null) {
                        inheritUpdateLogisticNetwork(tile); //WARNING - This is probably an unsafe cast!
                    }
                }
            }


            i++;
        }

        if(this.manager == null){
            createLogisticNetwork(); //this tube has no connections, so we make a new manager.
        }

        this.setChanged();
    }

    public void inheritUpdateLogisticNetwork(TubeBundleBE from){
        //This is called when a newly-placed tube is connected to an existing network. It inherits the network and marks it as dirty (flush route cache)
        System.out.println("Did we get into neighbor checker?");

        if(this.manager != null && from.getNetworkManager() != null && !this.manager.equals(from.getNetworkManager())){
            //This is a second, unique network manager - we are linking to multiple previously-separate networks and need to commit a merge first.

            System.out.println("Merging two networks together.");
            this.manager = mergeCopyNetwork(from.getNetworkManager()); //This is redundant - the Network Manager will call updateParentNetwork on every contained tube-bundle.

            this.setChanged();

            return;
        } else if(from.getNetworkManager() != null){
            System.out.println("Copying neighbor.");
            this.manager = from.getNetworkManager();
            this.manager.addTileToGrid(this);
            return;
        } else {
            return; //Pass, tube will create its own.
        }
    }

    public void createLogisticNetwork(){
        this.manager = new LogisticNetworkManager();
        this.manager.addTileToGrid(this);
        this.setChanged();
    }

    public LogisticNetworkManager mergeCopyNetwork(LogisticNetworkManager other){
        //The "first-referred network" is the parent, and the newly-found is merged in.
        return this.manager.mergeWithProvided(other);
    }

    public TubeBundleBE hasTubeNeighbor(Direction direction){

       // System.out.println(this.getLevel().getBlockState(this.worldPosition.relative(direction)).getBlock());
       // System.out.println(this.getLevel().getBlockEntity(this.worldPosition.relative(direction)));

       // TileEntity neighbor = this.getLevel().getBlockEntity(this.worldPosition.relative(direction));

       // if(neighbor instanceof TubeBundleTile){
       //     return (TubeBundleTile) neighbor;
       // }

        if(validNeighbors[direction.ordinal()] != null){
            return validNeighbors[direction.ordinal()];
        }

        return null;
    }

    public void addTube(int which) {
        //System.out.println("ADDTUBE HIT - VOLATILE");

        if(which > 3){
            throw new IllegalArgumentException("Only 4 types of tubes!");
        }

        tubesInBlock[which] = true;

        this.setChanged();
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

        this.setChanged();
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

    public boolean hasTube(int which){ //This doubles as a lazy self-evaluator. Once something needs to know what tubes are in this tile, we double-check if we have any orphaned state to recover.

        //TubeBundleTile old = TubeBundleStateMapper.checkAndPurge(this.getBlockPos());

        //if(old != null){
        //    System.out.println("Mapping parameters!");
        //    this.copyInto(old); //Horrible method for saving state when a tube is converted into a full bundle. Sorry!
        //}

        return tubesInBlock[which];
    }


    public LogisticNetworkManager getNetworkManager(){

        if(this.manager == null){
            this.createLogisticNetwork();
        }

        return this.manager;
    }

    public void setNetworkManager(LogisticNetworkManager manager){
        this.manager = manager;
    }

    public Connection[] getConnections(){
        return this.connections;
    }

    public void copyInto(TubeBundleBE other){
        this.tubesInBlock = other.getTubesInBlock();
        this.manager = other.getNetworkManager();
        this.connections = other.getConnections(); //Should be everything relevant.

        System.out.println(Arrays.toString(this.tubesInBlock));
    }

    public boolean anyMatch(TubeBundleBE other){
        boolean[] compare = other.getTubesInBlock();
        for(int i = 0; i < 4; i++){
            if(this.tubesInBlock[i] == true && this.tubesInBlock[i] == compare[i]){
                return true;
            }
        }

        return false;
    }

    public void refreshModelConnections(){ //Called whenever something happens that would require a hard reevaluation of connections (namely addition and removal of conduits)

        //System.out.println("Refreshing model con");

        BlockState current = this.getBlockState();

        for(Direction dir : Direction.values()){
            //Safe recall of updateShape on every direction. Should not recieve a null tile this time.

            current = current.getBlock().updateShape(current, dir, this.getLevel().getBlockState(this.worldPosition.relative(dir)), this.getLevel(), this.worldPosition, this.worldPosition.relative(dir));
            //System.out.println("CHECK IF FAIL AT read and update shape");
        }

        this.getLevel().setBlockAndUpdate(this.getBlockPos(), current); //WARNING - I am unsure if setBlock will kill the TileEntity here.
        //System.out.println("CHECK IF FAIL AT setBlock");

    }

    public void buildOrUpdateConnection(int type, Direction face){
        if(connections[face.ordinal()] == null){
            connections[face.ordinal()] = new Connection(this, this.getLevel().getBlockEntity(this.worldPosition.relative(face)), face);
        }
        connections[face.ordinal()].addActiveType(type);
    }

    public void setTraverseDirty(){
        this.traverse_dirty = true;
    }

    public void setTraverseClean(){
        this.traverse_dirty = false;
    }

    public boolean isTraverseDirty(){
        return this.traverse_dirty;
    }

    public void addTileNeighbor(TubeBundleBE tile, int ordinalDir){
        validNeighbors[ordinalDir] = tile;

    }

    public void clearAll(){
        //Called on remove
        for(int i = 0; i < connections.length; i++){
            connections[i].dequeueSelfIfActive();
            connections[i] = null;
        }
    }

}
