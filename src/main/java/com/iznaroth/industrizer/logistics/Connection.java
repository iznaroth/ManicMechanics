package com.iznaroth.industrizer.logistics;

import com.iznaroth.industrizer.tile.TubeBundleTile;
import com.iznaroth.industrizer.util.LogisticJob;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Connection {
    //An array of Connections is attached to every logistic tube. It is populated as connections to valid machines are made.
    //This connection is signaled by its parent to track what kind of jobs it will schedule, and has a passthrough function for
    //each of the tube types which it will try to schedule against

    public Connection(TubeBundleTile parent, Direction facing){

    }

    private TubeBundleTile parent;

    //These reference the last-remembered operation to repath if the newly-found job is a mismatch.
    private ItemStack prev_item;
    private Fluid prev_fluid;
    //private Gas prev_gas;

    private TileEntity attached; //This should never be wrong, but we'll check against it anyways.

    int cd; //set by parent tube tier
    int counter = cd;

    boolean sleep;

    //public void onAttachmentOperationComplete(OperationCompleteModEvent event){ //This is called
    //    TickEvent.ClientTickEvent
    //}


    private static void checkAndCreateJobs(){

    }
}
