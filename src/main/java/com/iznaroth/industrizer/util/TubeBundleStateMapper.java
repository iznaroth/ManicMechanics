package com.iznaroth.industrizer.util;

import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.tile.TubeBundleTile;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.HashMap;

public class TubeBundleStateMapper {
    //This is a helper class which acts as an intermediary between a Tube that is destroying itself
    //and a newly-created tube bundle.

    public static HashMap<BlockPos, TubeBundleTile> replacable = new HashMap<>();

    public static void mapDestroyedTile(BlockPos from, TubeBundleTile what){
        replacable.put(from, what);
        System.out.println("MAPPED FOR" + from);
        System.out.println(Arrays.toString(what.getTubesInBlock()));
    }

    public static TubeBundleTile checkAndPurge(BlockPos from){
        System.out.println("Signalled to check for " + from);

        if(replacable.containsKey(from)){
            TubeBundleTile result =  replacable.get(from);
            replacable.remove(from);
            return result;
        }

        return null;
    }
}
