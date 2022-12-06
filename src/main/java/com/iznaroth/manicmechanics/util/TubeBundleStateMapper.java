package com.iznaroth.manicmechanics.util;

import com.iznaroth.manicmechanics.blockentity.TubeBundleBE;
import net.minecraft.core.BlockPos;

import java.util.Arrays;
import java.util.HashMap;

public class TubeBundleStateMapper {
    //This is a helper class which acts as an intermediary between a Tube that is destroying itself
    //and a newly-created tube bundle.

    public static HashMap<BlockPos, TubeBundleBE> replacable = new HashMap<>();

    public static void mapDestroyedTile(BlockPos from, TubeBundleBE what){
        replacable.put(from, what);
        System.out.println("MAPPED FOR" + from);
        System.out.println(Arrays.toString(what.getTubesInBlock()));
    }

    public static TubeBundleBE checkAndPurge(BlockPos from){
        System.out.println("Signalled to check for " + from);

        if(replacable.containsKey(from)){
            TubeBundleBE result =  replacable.get(from);
            replacable.remove(from);
            return result;
        }

        return null;
    }
}
