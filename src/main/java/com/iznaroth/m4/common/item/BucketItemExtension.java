package com.iznaroth.m4.common.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;

import javax.annotation.Nullable;
import java.util.Optional;

public class BucketItemExtension extends BucketItem {
    public BucketItemExtension(Fluid content, Properties properties) {
        super(content, properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        System.out.println("Polled use on Bucket: " + this.content);
        ItemStack itemstack = player.getItemInHand(hand);
        BlockHitResult blockhitresult = getPlayerPOVHitResult(level, player, this.content == Fluids.EMPTY ? net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY : net.minecraft.world.level.ClipContext.Fluid.NONE);
        if (blockhitresult.getType() == HitResult.Type.MISS) {
            System.out.println("MISS recorded");
            return InteractionResultHolder.pass(itemstack);
        } else if (blockhitresult.getType() != HitResult.Type.BLOCK) {
            System.out.println("NOT A BLOCKHIT: " +  blockhitresult.getType());
            return InteractionResultHolder.pass(itemstack);
        } else {
            BlockPos blockpos = blockhitresult.getBlockPos();
            Direction direction = blockhitresult.getDirection();
            BlockPos blockpos1 = blockpos.relative(direction);
            if (level.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack)) {
                System.out.println("Player can use item.");
                if (this.content == Fluids.EMPTY) {
                    System.out.println("Is this bucket empty?");
                    BlockState blockstate1 = level.getBlockState(blockpos);
                    Block var15 = blockstate1.getBlock();
                    if (var15 instanceof BucketPickup) {
                        BucketPickup bucketpickup = (BucketPickup)var15;
                        ItemStack itemstack3 = bucketpickup.pickupBlock(player, level, blockpos, blockstate1);
                        if (!itemstack3.isEmpty()) {
                            player.awardStat(Stats.ITEM_USED.get(this));
                            bucketpickup.getPickupSound(blockstate1).ifPresent((p_150709_) -> player.playSound(p_150709_, 1.0F, 1.0F));
                            level.gameEvent(player, GameEvent.FLUID_PICKUP, blockpos);
                            ItemStack itemstack2 = ItemUtils.createFilledResult(itemstack, player, itemstack3);
                            if (!level.isClientSide) {
                                CriteriaTriggers.FILLED_BUCKET.trigger((ServerPlayer)player, itemstack3);
                            }

                            return InteractionResultHolder.sidedSuccess(itemstack2, level.isClientSide());
                        }
                    }

                    return InteractionResultHolder.fail(itemstack);
                } else {
                    System.out.println("Try empty contents: " + this.content);
                    BlockState blockstate = level.getBlockState(blockpos);
                    BlockPos blockpos2 = this.canBlockContainFluid(player, level, blockpos, blockstate) ? blockpos : blockpos1;
                    if (this.emptyContents(player, level, blockpos2, blockhitresult, itemstack)) {
                        System.out.println("MPT into block.");
                        this.checkExtraContent(player, level, itemstack, blockpos2);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer)player, blockpos2, itemstack);
                        }

                        player.awardStat(Stats.ITEM_USED.get(this));
                        ItemStack itemstack1 = ItemUtils.createFilledResult(itemstack, player, getEmptySuccessItem(itemstack, player));
                        return InteractionResultHolder.sidedSuccess(itemstack1, level.isClientSide());
                    } else {
                        return InteractionResultHolder.fail(itemstack);
                    }
                }
            } else {
                return InteractionResultHolder.fail(itemstack);
            }
        }
    }

    @Override
    public boolean emptyContents(@Nullable Player player, Level level, BlockPos pos, @Nullable BlockHitResult result, @Nullable ItemStack container) {
        Fluid $$7 = this.content;
        BlockState blockstate;
        blockstate = level.getBlockState(pos);
        Block var17 = blockstate.getBlock();

        if (!($$7 instanceof FlowingFluid flowingfluid)) {
            System.out.println("FlowingFluid?");
            return false;
        } else {
            boolean $$8;

            boolean flag2;
            label78: {
                label77: {

                    $$8 = blockstate.canBeReplaced(this.content);
                    if (!blockstate.isAir() && !$$8) { //not air and can't be replaced
                        if (!(var17 instanceof LiquidBlockContainer)) {
                            System.out.println("Placing on nonsolid?");
                            break label77;
                        }

                        LiquidBlockContainer liquidblockcontainer = (LiquidBlockContainer)var17;
                        if (!liquidblockcontainer.canPlaceLiquid(player, level, pos, blockstate, this.content)) {
                            System.out.println("Can't place off liquidblockcontainer?");
                            break label77;
                        }
                    }

                    //passed!
                    flag2 = true;
                    break label78;
                }

                //Failed!
                flag2 = false;
            }

            Optional<FluidStack> containedFluidStack = Optional.ofNullable(container).flatMap(FluidUtil::getFluidContained);
            if (!flag2) { //if we set this, we failed to place so we recall wit h a dead hit result? weird
                return result != null && this.emptyContents(player, level, result.getBlockPos().relative(result.getDirection()), (BlockHitResult)null, container);
            } else if (containedFluidStack.isPresent() && this.content.getFluidType().isVaporizedOnPlacement(level, pos, (FluidStack)containedFluidStack.get())) {
                // i do not know what containedFluidStackIs -> but it appears to check for legal placement for a vaporized fluid
                System.out.println("vaporize?");
                this.content.getFluidType().onVaporize(player, level, pos, (FluidStack)containedFluidStack.get());
                return true;
            } else if (level.dimensionType().ultraWarm() && this.content.is(FluidTags.WATER)) {
                System.out.println("ultrawarm?");
                int l = pos.getX();
                int i = pos.getY();
                int j = pos.getZ();
                level.playSound(player, pos, SoundEvents.FIRE_EXTINGUISH, SoundSource.BLOCKS, 0.5F, 2.6F + (level.random.nextFloat() - level.random.nextFloat()) * 0.8F);

                for(int k = 0; k < 8; ++k) {
                    level.addParticle(ParticleTypes.LARGE_SMOKE, (double)l + Math.random(), (double)i + Math.random(), (double)j + Math.random(), (double)0.0F, (double)0.0F, (double)0.0F);
                }

                return true;
            } else {
                System.out.println("pass dead-place types?");
                if (var17 instanceof LiquidBlockContainer) {
                    System.out.println("placement target is a LiquidBlockCOntainer?");
                    LiquidBlockContainer liquidblockcontainer1 = (LiquidBlockContainer)var17;
                    if (liquidblockcontainer1.canPlaceLiquid(player, level, pos, blockstate, this.content)) {
                        System.out.println("container valid for placement?");
                        liquidblockcontainer1.placeLiquid(level, pos, blockstate, flowingfluid.getSource(false));
                        this.playEmptySound(player, level, pos);
                        return true;
                    }
                }

                if (!level.isClientSide && $$8 && !blockstate.liquid()) {
                    System.out.println("not clientside, replacable & not liquid?");
                    level.destroyBlock(pos, true);
                }

                //setblock likely tries to actually put it into the world
                System.out.println("placing.");
                System.out.println(this.content);
                System.out.println(this.content.defaultFluidState());
                System.out.println(this.content.defaultFluidState().createLegacyBlock());
                System.out.println(blockstate.getFluidState());
                if (!level.setBlock(pos, this.content.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
                    System.out.println("setBlock failed for unknown reason && blockstate is not a source block");
                    return false;
                } else {
                    System.out.println("worked.");
                    this.playEmptySound(player, level, pos);
                    return true;
                }
            }
        }
    }
}
