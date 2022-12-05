package com.iznaroth.manicmechanics.entity.custom;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.util.ModSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.TurtleEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;

import java.util.Random;

public class PinchEntity extends CreatureEntity {
    public PinchEntity(EntityType<? extends CreatureEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute setCustomAttributes() {
        return MobEntity.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0D)
                .add(Attributes.ATTACK_DAMAGE, 350.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D);

    }

    public static boolean checkSpawnRules(EntityType<? extends CreatureEntity> p_223325_0_, IServerWorld p_223325_1_, SpawnReason p_223325_2_, BlockPos pos, Random p_223325_4_) {
        return pos.getY() > 100 && checkMobSpawnRules(p_223325_0_, p_223325_1_, p_223325_2_, pos, p_223325_4_);
    }

    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if(p_70097_1_.getEntity() instanceof PlayerEntity && (((PlayerEntity) p_70097_1_.getEntity()).getMainHandItem().getItem().equals(MMItems.REINFORCED_ARM.get()) || (((PlayerEntity) p_70097_1_.getEntity()).getOffhandItem().getItem().equals(MMItems.REINFORCED_ARM.get())))) {
            return super.hurt(p_70097_1_, p_70097_2_); //If it's a player with proper equipment, do damage as normal.
        } else if(p_70097_1_.getEntity() instanceof LivingEntity) {
            if (this.level.isClientSide) {
                return false;
            }

            DamageSource PINCH = new DamageSource(ManicMechanics.MOD_ID + "_pinched");
            ((LivingEntity) p_70097_1_.getEntity()).hurt(PINCH, Float.MAX_VALUE); //Pinch instantly kills any unequipped damage source.
            return false;
        }

        return false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(7, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillagerEntity.class, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, IronGolemEntity.class, true));
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, TurtleEntity.class, 10, true, false, TurtleEntity.BABY_ON_LAND_SELECTOR));
    }

    @Override
    protected int getExperienceReward(PlayerEntity player)
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
        return ModSoundEvents.PINCH_1.get();
    }


    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.IRON_GOLEM_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.IRON_GOLEM_HURT;
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
                ((LivingEntity)entityIn).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 200,3));
                ((LivingEntity)entityIn).addEffect(new EffectInstance(Effects.WEAKNESS, 200));
                ((LivingEntity)entityIn).addEffect(new EffectInstance(Effects.CONFUSION, 200));
            }
            return true;
        }
    }
}