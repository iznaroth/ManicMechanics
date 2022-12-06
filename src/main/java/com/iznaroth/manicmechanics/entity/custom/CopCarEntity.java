package com.iznaroth.manicmechanics.entity.custom;

import com.iznaroth.manicmechanics.util.ModSoundEvents;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.BlockState;

public class CopCarEntity extends Zombie {
    public CopCarEntity(EntityType<? extends Zombie> type, Level worldIn) {
        super(type, worldIn);
    }

    public static AttributeSupplier setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 2.33D)
                .add(Attributes.ATTACK_DAMAGE, 13.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 50.0D).build();

    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal( 1, new NearestAttackableTargetGoal<>( this, Player.class, true ) );
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Villager.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolem.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, Turtle.class, 10, true, false, Turtle.BABY_ON_LAND_SELECTOR));
    }

    @Override
    public int getExperienceReward()
    {
        return 3 + this.level.random.nextInt(5);
    }

    @Override
    public int getAmbientSoundInterval() {
        return 540;
    }

    @Override
    protected SoundEvent getAmbientSound()
    {
        return ModSoundEvents.COP_SIREN.get();
    }


    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.PIG_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.PIG_HURT;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn)
    {
        this.playSound(SoundEvents.BLAZE_HURT, 0.20F, 0.5F);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (!super.doHurtTarget(entityIn)) {
            return false;
        } else {
            if (entityIn instanceof LivingEntity) {
                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200,3));
                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200));
                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
            }
            return true;
        }
    }
}