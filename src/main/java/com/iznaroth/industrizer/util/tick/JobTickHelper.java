package com.iznaroth.industrizer.util.tick;

import com.iznaroth.industrizer.logistics.LogisticNetworkManager;
import com.iznaroth.industrizer.util.LogisticJob;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;


public class JobTickHelper {

    public static ArrayList<LogisticJob> queue = new ArrayList<LogisticJob>();

    //Jobs are passed to this utility class and stalled the required amount of time before attempting completion.
    public void enqueueJob(LogisticJob to_queue){
        queue.add(to_queue);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        //a tick happened
        //increment counter
        System.out.println("On Tick");
        for (LogisticJob job : queue ) {
            job.distance--;

            if(job.distance == 0){
                job.parent.completeJob(job);
                queue.remove(job); //This is almost certainly wrong!
                System.out.println("A job was marked completed! Committing final change!");
            }
        }
    }


}
