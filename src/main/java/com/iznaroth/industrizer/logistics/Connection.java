package com.iznaroth.industrizer.logistics;

import com.iznaroth.industrizer.tile.TubeBundleTile;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;


public class Connection {
    //An array of Connections is attached to every logistic tube. It is populated as connections to valid machines are made.
    //This connection is signaled by its parent to track what kind of jobs it will schedule, and has a passthrough function for
    //each of the tube types which it will try to schedule against

    public Connection(TubeBundleTile parent, TileEntity attached, Direction from){
        this.parent = parent;

        if(attached == null){
            throw new IllegalArgumentException("Connection init caught a null tile entity!"); //NOTE - catch this if it is a persistent issue and just retry build logic.
        }

        this.attached = attached;

        this.contactPoint = from;
    }

    private TubeBundleTile parent;

    private Direction contactPoint;

    private boolean[] active_connections = {false, false, false, false};

    //These reference the last-remembered operation to repath if the newly-found job is a mismatch.
    private ItemStack activeItem;
    private int slot_from; //Secondary invalidation condition - pulling from a different slot is potentially volatile, even if the items match.

    private Fluid prev_fluid;
    //private Gas prev_gas;

    private TileEntity attached; //This should never be wrong, but we'll check against it anyways.


    int item_cache = -1;
    int power_cache = -1;
    int fluid_cache = -1;
    int gas_cache;

    int item_counter;
    int fluid_counter;
    int gas_counter;

    int connection_mode = 0; //0 = NONE, 1 = EXTRACT, 2 = INTAKE

    boolean sleep;

    //public void onAttachmentOperationComplete(OperationCompleteModEvent event){ //This is called
    //    TickEvent.ClientTickEvent
    //}

    public void addActiveType(int which){ //NO REMOVE - a machine is not likely to "change types", and changing the machine destroys and rebuilds the connection anyways.
        active_connections[which] = true;
    }

    public void tick(){ //If this connection is in the queue, this will be called every tick for it.
        item_counter--;
        fluid_counter--;
        gas_counter--;

        if (this.parent.hasTube(0) && item_counter <= 0 && connection_mode == 1) { //ITEMS
            System.out.println("Try to enqueue job for ITEM TRANSFER");

            this.executeLogisticJob();

            item_counter = 10; //NOTE - These values are going to pull from server properties.
        }

        if (this.parent.hasTube(1)) { //POWER - no cooldown as cables always transfer constantly. Also ignores MODE because cables just automatically maximize for saturation.
            System.out.println("Try to enqueue job for POWER TRANSFER");
        }

        if (this.parent.hasTube(2) && fluid_counter <= 0 && connection_mode == 1) { //FLUID

            fluid_counter = 10;
        }

        if (this.parent.hasTube(3) && gas_counter <= 0 && connection_mode == 1) { //GAS

            gas_counter = 10;
        }


    }

    public void executeLogisticJob(){
        //Check if this is a novel job. If it is, build a new cache.
        ItemStack compare = this.getNewItemOutput();

        if(compare == ItemStack.EMPTY){ //There is no valid job to do right now.
            return;
        }

        //Check our network for a cache hit.
        if(item_cache == -1 || compare != this.activeItem){ //Either an uninitialized network or a changed job.
            System.out.println("Building cache.");
            this.item_cache = this.parent.getNetworkManager().buildLogisticJobCache(this, compare);
            this.activeItem = compare;
        }

        //We need to check if the idx we have still corresponds to this connection (check if the job property still lists this as the FROM contact). If not, we need to find where it went.
        item_cache = this.parent.getNetworkManager().useOrFindMovedCacheEntry(this, this.item_cache);

        if(item_cache == -1){ //No cache hit!

        }

        boolean complete = this.parent.getNetworkManager().executeLogisticCacheEntry(this.item_cache); //Regardless of if this is newly-initialized, lookup and try to execute from our existing cache reference.
    }

    public ItemStack getNewItemOutput(){
        IItemHandler handler = this.attached.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);

        if(handler == null){
            throw new NullPointerException("Connection's handler did not return the expected capability.");
        }

        int allSlots = handler.getSlots();
        int targetSlot = -1;
        ItemStack what = ItemStack.EMPTY;

        for(int i = 0; i < allSlots; i++){
            what = handler.extractItem(i, 1, true);
            if(what != ItemStack.EMPTY){
                targetSlot = i;
                break;
            }
        }

        if(targetSlot == -1){
            //We found no non-empty extractables. Logic will eventually tell the cxn to sleep here until awoken by an event, but for now, we just keep trying.
            System.out.println("Connection reads an empty inventory.");
            return ItemStack.EMPTY;
        }

        this.slot_from = targetSlot; //meh
        return what;
    }



    public void dequeueSelfIfActive(){
        if (ActiveConnectionQueue.queue.contains(this)){
            ActiveConnectionQueue.dequeueInactive(this);
        } else {
            System.out.println("ERROR: Tried to dequeue inactive connection.");
        }
    }

    public TubeBundleTile getParentTile(){
        return this.parent;
    }

    public boolean isTypeActive(int which){
        return active_connections[which];
    }

    public Direction getContactDirection(){
        return this.contactPoint;
    }

    public TileEntity getAttached(){
        return this.attached;
    }

    public int getSlotFrom(){
        return this.slot_from;
    }

    public int getFirstLegalInsertSlot(ItemStack toInsert){
        IItemHandler handler = this.getAttached().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        if(handler == null){
            throw new NullPointerException("Subnetwork contact does not have expected capability.");
        }

        int slots = handler.getSlots();

        for(int i = 0; i < slots; i++){
            if(handler.isItemValid(i, toInsert)){
                return i;
            }
        }

        return -1;
    }

    public void cycleMode(){
        if(this.connection_mode == 0) { //From NONE to EXTRACT
            this.connection_mode++;
            System.out.println("Cycled connection mode to EXTRACT for " + this.contactPoint);

            if(!ActiveConnectionQueue.queue.contains(this)){
                ActiveConnectionQueue.enqueueActive(this); //Connection will now be ticked and try to perform jobs.
            }

        }else if(this.connection_mode == 1) { //From EXTRACT to INTAKE
            this.connection_mode++;
            System.out.println("Cycled connection mode to INTAKE for " + this.contactPoint);

            if(ActiveConnectionQueue.queue.contains(this)){
                ActiveConnectionQueue.dequeueInactive(this); //Connection is purged. This performs a dirty cache invalidation that will need to be corrected.
            }
        }else if(this.connection_mode == 2) { //From INTAKE to NONE
            this.connection_mode = 0;
            System.out.println("Cycled connection mode to NONE for " + this.contactPoint);
        }
    }

}
