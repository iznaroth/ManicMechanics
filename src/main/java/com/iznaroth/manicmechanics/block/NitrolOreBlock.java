package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.util.ModSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class NitrolOreBlock extends Block {
    public NitrolOreBlock(Properties p_i48440_1_) {
        super(p_i48440_1_.randomTicks());
    }

    @Override
    public void onBlockExploded(BlockState state, World world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);


        world.explode((Entity) null, new DamageSource(ManicMechanics.MOD_ID + "_nitrol_busted"), (ExplosionContext) null, pos.getX(), pos.getY(),  pos.getZ(), 5.0F, true, Explosion.Mode.DESTROY);

    }
}
