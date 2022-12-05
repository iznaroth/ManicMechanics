package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.util.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class DyspersiumOreBlock extends Block {
    public DyspersiumOreBlock(Properties p_i48440_1_) {
        super(p_i48440_1_.randomTicks());
    }

    Random r = new Random();
    int r_timer = r.nextInt(25) + 5; //at least every 5 random ticks and at most every 25, a random Dysp. ore block will be allowed to play a sound at you.


    public void randomTick(BlockState state, ServerWorld level, BlockPos pos, Random rand) {

        if(r_timer != 0){
            r_timer--;
            return;
        }

        SoundEvent e = getAppropriateSE(r.nextInt(1));

        if (e != null) {
            System.out.println("Got random tick. Playing: " + e);
            level.playSound((PlayerEntity) null, pos.getX(), pos.getY(), pos.getZ(), e, SoundCategory.AMBIENT, 1.0F, 1.0F); //LAST TWO PROPERTIES ARE VOL AND PITCH
        }

        r_timer = r.nextInt(25) + 5;
    }

    public SoundEvent getAppropriateSE(int which){
        switch(which){
            case 0:
                return ModSoundEvents.DYSPERSIUM_0.get();
            case 1:
                return ModSoundEvents.DYSPERSIUM_1.get();
        }


        return null;
    }
}
