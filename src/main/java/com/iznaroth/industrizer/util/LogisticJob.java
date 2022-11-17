package com.iznaroth.industrizer.util;

import com.iznaroth.industrizer.logistics.LogisticNetworkManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class LogisticJob {

    //simple data structure for cached jobs
    public int distance; //this is the number of TICKS required to complete the route (as found by pathfinder)

    public LogisticNetworkManager parent;

    int slotDestination; //For ease of automation, the job stores which slot it is going to.

    public ItemStack to_transfer;
    public ItemStackHandler from;
    public ItemStackHandler to;

    public LogisticJob (int dist, ItemStack tr, ItemStackHandler from, ItemStackHandler to, LogisticNetworkManager parent){
        this.distance = dist;
        this.to_transfer = tr;
        this.from = from;
        this.to = to;

        this.parent = parent;
    }

    public void testJobValidity(){
        //Test maximum possible amount to be extracted and deposited (it can be less than the throughput normally requests, but not more)
        //Used to decide legality of ongoing jobs.

    }

}
