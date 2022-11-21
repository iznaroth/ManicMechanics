package com.iznaroth.industrizer.logistics;

import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;

public class ActiveConnectionQueue {

    public static ArrayList<Connection> queue = new ArrayList<Connection>();

    //Jobs are passed to this utility class and stalled the required amount of time before attempting completion.
    public static void enqueueActive(Connection to_queue){
        queue.add(to_queue);
    }

    public static void dequeueInactive(Connection to_remove){ queue.remove(to_remove); }

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        //a tick happened
        //increment counter
        //System.out.println("On Tick");
        for (Connection con : queue ) {
            con.tick();
        }
    }
}
