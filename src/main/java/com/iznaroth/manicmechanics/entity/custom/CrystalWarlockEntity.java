package com.iznaroth.manicmechanics.entity.custom;

import net.minecraft.client.renderer.entity.LightningBoltRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class CrystalWarlockEntity extends Monster implements IAnimatable, RangedAttackMob {
    private AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public CrystalWarlockEntity(EntityType<? extends Monster> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier setAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0f)
                .add(Attributes.ATTACK_SPEED, 2.0f)
                .add(Attributes.MOVEMENT_SPEED, 0.05f).build();
    }

    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(2, new RangedAttackGoal(this, 1.2D, 1, 1.2F));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.25D));

        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }


    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wizard.idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;


    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController(this, "controller",
                0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, 0.15F, 1.0F);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_BASALT_DELTAS_MOOD;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ALLAY_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.PILLAGER_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }

    public boolean canBeLeashed(Player player) {
        return false;
    }


    /* VARIANTS */
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor p_146746_, DifficultyInstance p_146747_,
                                        MobSpawnType p_146748_, @Nullable SpawnGroupData p_146749_,
                                        @Nullable CompoundTag p_146750_) {

        return super.finalizeSpawn(p_146746_, p_146747_, p_146748_, p_146749_, p_146750_);
    }

    @Override
    public void performRangedAttack(LivingEntity p_33317_, float p_33318_) {
        double d0 = p_33317_.getX() - this.getX();
        double d1 = p_33317_.getY(0.3333333333333333D);
        double d2 = p_33317_.getZ() - this.getZ();
        double d3 = Math.sqrt(d0 * d0 + d2 * d2); //Fuzzed coords for target

        this.playSound(SoundEvents.LIGHTNING_BOLT_THUNDER, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        //TODO - custom sideways lightning bolt renderer.
        p_33317_.hurt(DamageSource.LIGHTNING_BOLT, 3.0F);
    }
}
