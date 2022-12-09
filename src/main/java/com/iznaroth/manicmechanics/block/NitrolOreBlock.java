package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;


public class NitrolOreBlock extends MMBlockWrapper {
    public NitrolOreBlock(Properties p_i48440_1_) {
        super(p_i48440_1_.randomTicks());
    }

    @Override
    public void onBlockExploded(BlockState state, Level world, BlockPos pos, Explosion explosion) {
        super.onBlockExploded(state, world, pos, explosion);


        world.explode((Entity) null, new DamageSource(ManicMechanics.MOD_ID + "_nitrol_busted"), (ExplosionDamageCalculator) null, pos.getX(), pos.getY(),  pos.getZ(), 5.0F, true, Explosion.BlockInteraction.DESTROY);

    }
}
