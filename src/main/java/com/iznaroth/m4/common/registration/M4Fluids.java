package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.PointedDripstoneBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.Nullable;

public class M4Fluids {

    private static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, "m4");

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, M4.MODID);

    //Fluids!
    //Note - This is a segment head.
    //Fluids are relatively unorganized because they are less shorthandy and less common than the above entries.
    //We'd be better off building the helper independently and disguising this here, a.la the Mekanism implementation.
    //...because you need to register the base, the flowing, the bucket, the block.
    public static final DeferredHolder<Fluid, Fluid> WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("whispering_water"));
    public static final DeferredHolder<Fluid, Fluid> FLOWING_WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("flowing_whispering_water"));


    private static <T extends Fluid> T register(String key, T fluid) {
        return (T)(Registry.register(BuiltInRegistries.FLUID, key, fluid));
    }

    public static final Holder<FluidType> WHISPERING_WATER_TYPE = FLUID_TYPES.register("whispering_water", () -> new FluidType(FluidType.Properties.create()
            .descriptionId("block.m4.whispering_water")
            .fallDistanceModifier(0F)
            .canExtinguish(true)
            .canConvertToSource(true)
            .supportsBoating(true)
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
            .canHydrate(true)
            .addDripstoneDripping(PointedDripstoneBlock.WATER_TRANSFER_PROBABILITY_PER_RANDOM_TICK, ParticleTypes.DRIPPING_DRIPSTONE_WATER, Blocks.WATER_CAULDRON, SoundEvents.POINTED_DRIPSTONE_DRIP_WATER_INTO_CAULDRON)) {
        @Override
        public boolean canConvertToSource(FluidState state, LevelReader reader, BlockPos pos) {
            if (reader instanceof Level level) {
                return level.getGameRules().getBoolean(GameRules.RULE_WATER_SOURCE_CONVERSION);
            }
            //Best guess fallback to default (true)
            return super.canConvertToSource(state, reader, pos);
        }

        @Override
        public @Nullable PathType getBlockPathType(FluidState state, BlockGetter level, BlockPos pos, @Nullable Mob mob, boolean canFluidLog) {
            return canFluidLog ? super.getBlockPathType(state, level, pos, mob, true) : null;
        }
    });

    public static void registerFluids(RegisterEvent event) {

        // register fluid type
        event.register(NeoForgeRegistries.Keys.FLUID_TYPES, helper -> helper.register(WHISPERING_WATER_TYPE.unwrapKey().orElseThrow(), new FluidType(
                FluidType.Properties.create().density(1024).viscosity(1024))));

        // register fluids
        event.register(Registries.FLUID, helper -> {
            // set up properties
            BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(WHISPERING_WATER_TYPE::value, WHISPERING_WATER::value, FLOWING_WHISPERING_WATER::value).bucket(M4Items.WHISPERING_WATER_BUCKET);

            helper.register(WHISPERING_WATER.getId(), new BaseFlowingFluid.Source(properties));
            helper.register(FLOWING_WHISPERING_WATER.getId(), new BaseFlowingFluid.Flowing(properties));
        });
    }
}

