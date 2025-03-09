package com.iznaroth.m4.datagen;

import com.google.gson.JsonObject;
import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.block.ManufactorumBlock;
import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class M4BlockStates extends BlockStateProvider {

    public static final ResourceLocation BOTTOM = ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/machine_placeholder");
    public static final ResourceLocation TOP = ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/machine_placeholder");
    public static final ResourceLocation SIDE = ResourceLocation.fromNamespaceAndPath(M4.MODID, "block/machine_placeholder");

    public M4BlockStates(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, M4.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(M4Blocks.OBLITERATION_PLINTH.get());
        registerGenerator();
        registerCharger();
        //registerCable();
        //registerFacade();
    }

    /*
    private void registerCable() {
        BlockModelBuilder model = models().getBuilder("cable")
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(CableModelLoader.GENERATOR_LOADER, builder, helper, false))
                .end();
        simpleBlock(Registration.CABLE_BLOCK.get(), model);
    }

    private void registerFacade() {
        BlockModelBuilder model = models().getBuilder("facade")
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(CableModelLoader.GENERATOR_LOADER, builder, helper, true))
                .end();
        simpleBlock(Registration.FACADE_BLOCK.get(), model);
    }
    */


    private void registerCharger() {
        BlockModelBuilder modelOn = models().slab(M4Blocks.CHARGING_STATION.getId().getPath()+"_on", SIDE, BOTTOM, modLoc("block/machine_placeholder")).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().slab(M4Blocks.CHARGING_STATION.getId().getPath()+"_off", SIDE, BOTTOM, modLoc("block/machine_placeholder")).texture("particle", SIDE);
        getVariantBuilder(M4Blocks.CHARGING_STATION.get()).forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            bld.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff);
            return bld.build();
        });
    }

    private void registerGenerator() {
        BlockModelBuilder modelOn = models().cube(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.getId().getPath()+"_on", BOTTOM, TOP, modLoc("block/hep_powered"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.getId().getPath()+"_off", BOTTOM, TOP, modLoc("block/hep_front"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get(), (state, builder) -> {
            builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff);
        });
    }

    private VariantBlockStateBuilder directionBlock(Block block, BiConsumer<BlockState, ConfiguredModel.Builder<?>> model) {
        VariantBlockStateBuilder builder = getVariantBuilder(block);
        builder.forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            model.accept(state, bld);
            applyRotationBld(bld, state.getValue(BlockStateProperties.FACING));
            return bld.build();
        });
        return builder;
    }

    private void applyRotationBld(ConfiguredModel.Builder<?> builder, Direction direction) {
        switch (direction) {
            case DOWN -> builder.rotationX(90);
            case UP -> builder.rotationX(-90);
            case NORTH -> { }
            case SOUTH -> builder.rotationY(180);
            case WEST -> builder.rotationY(270);
            case EAST -> builder.rotationY(90);
        }
    }

    /*
    public static class CableLoaderBuilder extends CustomLoaderBuilder<BlockModelBuilder> {

        private final boolean facade;

        public CableLoaderBuilder(ResourceLocation loader, BlockModelBuilder parent, ExistingFileHelper existingFileHelper,
                                  boolean facade) {
            super(loader, parent, existingFileHelper);
            this.facade = facade;
        }

        @Override
        public JsonObject toJson(JsonObject json) {
            JsonObject obj = super.toJson(json);
            obj.addProperty("facade", facade);
            return obj;
        }
    }
    */

}
