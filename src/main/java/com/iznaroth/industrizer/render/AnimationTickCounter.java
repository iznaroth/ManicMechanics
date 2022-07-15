package com.iznaroth.industrizer.render;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class AnimationTickCounter {
    public static long getTotalElapsedTicksInGame() {
        return totalElapsedTicksInGame;
    }

    @SubscribeEvent
    public static void clientTickEnd(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            if (!Minecraft.getInstance().isPaused()) {
                totalElapsedTicksInGame++;
            }
        }
    }

    private static long totalElapsedTicksInGame = 0;
}
