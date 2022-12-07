package com.iznaroth.manicmechanics.client;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;
import java.util.List;

public class ClientInfo {
    private ClientInfo(){}

    public static CompoundTag persistentData = new CompoundTag();
    public static int ticksInGame = 0;
    public static List<BlockPos> scryingPositions = new ArrayList<>();
}
