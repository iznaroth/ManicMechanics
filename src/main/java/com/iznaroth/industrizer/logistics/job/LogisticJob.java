package com.iznaroth.industrizer.logistics.job;

import com.iznaroth.industrizer.logistics.Connection;
import com.iznaroth.industrizer.logistics.LogisticNetworkManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class LogisticJob {

    //simple data structure for cached item transfer jobs
    public LogisticNetworkManager parent;

    int slotDestination; //For ease of automation, the job stores which slot it is going to.

    public ItemStack to_transfer;
    public Connection from;
    public int from_slot;
    public Connection to;
    public int to_slot;

    public LogisticJob (ItemStack tr, Connection from, Connection to, int from_slot, int to_slot){
        this.to_transfer = tr;
        this.from = from;
        this.to = to;
        this.from_slot = from_slot;
        this.to_slot = to_slot;
    }

    public int testJobValidity(){
        //Test maximum possible amount to be extracted and deposited (it can be less than the throughput normally requests, but not more)
        //Used to decide legality of ongoing jobs. Returns the maximum allowed number, or 0 if the job is illegal due to inventory limit.
        //Returns -1 if the item clashes with the inventory for some reason (uncaught network reevaluation)
        return -1;
    }



    public boolean tryAndExecute(){
        int valid = 1;

        IItemHandler fromHandler = from.getAttached().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);
        IItemHandler toHandler = to.getAttached().getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).orElse(null);

        if(valid > 0){
            fromHandler.extractItem(from_slot, 4, false); //Valid is supposed to be given a server_properties value for batch size - not yet tho! :)

            ItemStack remainder = toHandler.insertItem(to_slot, to_transfer, false);

            for(int i = to_slot + 1; i < toHandler.getSlots(); i++) { //Attempt to overfill to any other valid inventories.
                remainder = toHandler.insertItem(i, remainder, false);

            }

            if (remainder.getCount() > 0) {
                fromHandler.insertItem(from_slot, remainder, false);
            }

            if(remainder.getCount() == 4){
                return false; //This job failed due to perceived insufficient space.
            }

            return true;
        } else {
            return false; //Job is illegal.
        }
    }


}
