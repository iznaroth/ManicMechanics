package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class M4Fluids {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, M4.MODID);

    //Fluids!
    //Note - This is a segment head.
    //Fluids are relatively unorganized because they are less shorthandy and less common than the above entries.
    //We'd be better off building the helper independently and disguising this here, a.la the Mekanism implementation.
    //...because you need to register the base, the flowing, the bucket, the block.

    public static final DeferredHolder<FluidType, FluidType> WHISPERING_WATER_TYPE = DeferredHolder.create(NeoForgeRegistries.Keys.FLUID_TYPES, ResourceLocation.withDefaultNamespace("milk"));
    public static final DeferredHolder<Fluid, Fluid> WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("milk"));
    public static final DeferredHolder<Fluid, Fluid> FLOWING_WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.withDefaultNamespace("flowing_milk"));

}
