package com.iznaroth.manicmechanics.entity.custom;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.item.MMItems;
import com.iznaroth.manicmechanics.util.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ambient.AmbientCreature;
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.animal.Turtle;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Random;

import static software.bernie.geckolib3.util.GeckoLibUtil.createFactory;

public class PinchEntity extends PathfinderMob implements IAnimatable {

    private AnimationFactory factory = createFactory(this);

    public PinchEntity(EntityType<? extends PathfinderMob> type, Level worldIn) {
        super(type, worldIn);
    }


    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.pinch.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    public static AttributeSupplier setCustomAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0D)
                .add(Attributes.ATTACK_DAMAGE, 350.0D)
                .add(Attributes.FOLLOW_RANGE, 50.0D)
                .add(Attributes.SPAWN_REINFORCEMENTS_CHANCE, 0.0D).build();

    }

    public static boolean checkSpawnRules(EntityType<PinchEntity> pinchEntityEntityType, ServerLevelAccessor serverLevelAccessor, MobSpawnType mobSpawnType, BlockPos blockPos, RandomSource randomSource) {
        return blockPos.getY() > 100 && checkMobSpawnRules(pinchEntityEntityType, serverLevelAccessor, mobSpawnType, blockPos, randomSource);
    }


    @Override
    public boolean hurt(DamageSource p_70097_1_, float p_70097_2_) {
        if(p_70097_1_.getEntity() instanceof Player && (((Player) p_70097_1_.getEntity()).getMainHandItem().getItem().equals(MMItems.REINFORCED_ARM.get()) || (((Player) p_70097_1_.getEntity()).getOffhandItem().getItem().equals(MMItems.REINFORCED_ARM.get())))) {
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
        this.goalSelector.addGoal(7, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, AbstractVillager.class, false));
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
                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200,3));
                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200));
                ((LivingEntity)entityIn).addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200));
            }
            return true;
        }
    }

}