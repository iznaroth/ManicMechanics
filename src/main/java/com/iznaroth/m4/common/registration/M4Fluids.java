package com.iznaroth.m4.common.registration;

import com.iznaroth.m4.common.M4;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
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
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class M4Fluids {

    private static final ResourceLocation OVERLAY = ResourceLocation.withDefaultNamespace("block/water_overlay");
    private static final ResourceLocation RENDER_OVERLAY = ResourceLocation.withDefaultNamespace("textures/misc/underwater.png");
    private static final ResourceLocation LIQUID = ResourceLocation.fromNamespaceAndPath(M4.MODID, "liquid/liquid");
    private static final ResourceLocation LIQUID_FLOW = ResourceLocation.fromNamespaceAndPath(M4.MODID, "liquid/liquid_flow");

    private static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.Keys.FLUID_TYPES, "m4");
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, M4.MODID);

    // Define DeferredHolders for the custom fluids
    public static final DeferredHolder<Fluid, Fluid> WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(M4.MODID, "whispering_water"));
    public static final DeferredHolder<Fluid, Fluid> FLOWING_WHISPERING_WATER = DeferredHolder.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(M4.MODID, "flowing_whispering_water"));

    // Fluid Type Registration
    public static final DeferredHolder<FluidType, FluidType> WHISPERING_WATER_TYPE = DeferredHolder.create(NeoForgeRegistries.Keys.FLUID_TYPES, ResourceLocation.fromNamespaceAndPath(M4.MODID, "whispering_water"));

    private static <T extends Fluid> T register(String key, T fluid) {
        return (T)(Registry.register(BuiltInRegistries.FLUID, key, fluid));
    }

    public static void registerFluids(RegisterEvent event) {
        // Register Fluid Type with its properties
        event.register(NeoForgeRegistries.Keys.FLUID_TYPES, helper -> {
            helper.register(WHISPERING_WATER_TYPE.getId(), new M4FluidType(
                    FluidType.Properties.create()
                            .descriptionId("block.m4.whispering_water")
                            .fallDistanceModifier(0F)
                            .canExtinguish(true)
                            .canConvertToSource(true)
                            .supportsBoating(true)
                            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                            .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH)
                            .canHydrate(true),
                    new FluidTypeRenderProperties()
                            .texture(ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/whispering_water_still"),
                                    ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/whispering_water_flowing"),
                                    ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/whispering_water_overlay"))
            ));
        });

        // Register Fluids with their properties
        event.register(Registries.FLUID, helper -> {
            // Set up properties for the source and flowing versions
            BaseFlowingFluid.Properties properties = new BaseFlowingFluid.Properties(
                    WHISPERING_WATER_TYPE::value,
                    WHISPERING_WATER::value,
                    FLOWING_WHISPERING_WATER::value
            )
                    .bucket(M4Items.WHISPERING_WATER_BUCKET)
                    .tickRate(5)
                    .slopeFindDistance(1)
                    .levelDecreasePerBlock(1);

            // Register the fluids
            helper.register(WHISPERING_WATER.getId(), new BaseFlowingFluid.Source(properties));
            helper.register(FLOWING_WHISPERING_WATER.getId(), new BaseFlowingFluid.Flowing(properties));
        });

        System.out.println("Registering Fluid Type: " + WHISPERING_WATER_TYPE.getId());
        System.out.println("Registering Fluid: " + WHISPERING_WATER.getId());
        System.out.println("Still texture path: " + ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/whispering_water_still"));
        System.out.println("Flowing texture path: " + ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/whispering_water_flowing"));
        System.out.println("Overlay texture path: " + ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/whispering_water_overlay"));
    }

    public static void registerFluidExtensions(RegisterClientExtensionsEvent event) {
        System.out.println("Types; " + NeoForgeRegistries.FLUID_TYPES);
        for (FluidType fluidTypeEntry : NeoForgeRegistries.FLUID_TYPES) {
            System.out.println("Entry: " + fluidTypeEntry);
            if (fluidTypeEntry instanceof M4FluidType fluidType) {
                System.out.println("Register extension for: " + fluidType.stillTexture);

                event.registerFluidType(new IClientFluidTypeExtensions() {
                    @NotNull
                    @Override
                    public ResourceLocation getStillTexture() {
                        System.out.println("POLLED STILL TEXTURE: " + fluidType.stillTexture);
                        return fluidType.stillTexture;
                    }

                    @NotNull
                    @Override
                    public ResourceLocation getFlowingTexture() {
                        return fluidType.flowingTexture;
                    }

                    @Override
                    public ResourceLocation getOverlayTexture() {
                        return fluidType.overlayTexture;
                    }

                    @Nullable
                    @Override
                    public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                        return fluidType.renderOverlayTexture;
                    }

                    @Override
                    public void modifyFogRender(@NotNull Camera camera, @NotNull FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance,
                                                float farDistance, @NotNull FogShape shape) {
                        farDistance = 24F;
                        if (farDistance > renderDistance) {
                            farDistance = renderDistance;
                            shape = FogShape.CYLINDER;
                        }
                        RenderSystem.setShaderFogStart(-8);
                        RenderSystem.setShaderFogEnd(farDistance);
                        RenderSystem.setShaderFogShape(shape);
                    }

                    @Override
                    public int getTintColor() {
                        return fluidType.color;
                    }
                }, fluidType);
            }
        }
    }

    // FluidTypeRenderProperties: Simplified builder pattern
    public static class FluidTypeRenderProperties {
        private ResourceLocation stillTexture = LIQUID;
        private ResourceLocation flowingTexture = LIQUID_FLOW;
        private ResourceLocation overlayTexture = OVERLAY;
        private ResourceLocation renderOverlayTexture = RENDER_OVERLAY;
        private int color = 0xFFFFFFFF;

        public static FluidTypeRenderProperties builder() {
            return new FluidTypeRenderProperties();
        }

        public FluidTypeRenderProperties texture(ResourceLocation still, ResourceLocation flowing) {
            this.stillTexture = still;
            this.flowingTexture = flowing;
            return this;
        }

        public FluidTypeRenderProperties texture(ResourceLocation still, ResourceLocation flowing, ResourceLocation overlay) {
            this.stillTexture = still;
            this.flowingTexture = flowing;
            this.overlayTexture = overlay;
            return this;
        }

        public FluidTypeRenderProperties renderOverlay(ResourceLocation renderOverlay) {
            this.renderOverlayTexture = renderOverlay;
            return this;
        }

        public FluidTypeRenderProperties tint(int color) {
            this.color = color;
            return this;
        }
    }

    // Custom FluidType subclass
    public static class M4FluidType extends FluidType {
        public final ResourceLocation stillTexture;
        public final ResourceLocation flowingTexture;
        public final ResourceLocation overlayTexture;
        public final ResourceLocation renderOverlayTexture;
        public final int color;

        public M4FluidType(FluidType.Properties properties, FluidTypeRenderProperties renderProperties) {
            super(properties);
            this.stillTexture = renderProperties.stillTexture;
            this.flowingTexture = renderProperties.flowingTexture;
            this.overlayTexture = renderProperties.overlayTexture;
            this.renderOverlayTexture = renderProperties.renderOverlayTexture;
            this.color = renderProperties.color;
        }

        @Override
        public boolean isVaporizedOnPlacement(Level level, BlockPos pos, FluidStack stack) {
            return false;  // Default behavior
        }
    }
}