package com.iznaroth.m4.datagen;

import com.google.gson.JsonObject;
import com.iznaroth.m4.client.model.CableTubeModelLoader;
import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.block.ManufactorumBlock;
import com.iznaroth.m4.common.registration.M4Blocks;
import com.iznaroth.m4.common.registration.M4Items;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.*;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
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
        System.out.println("DATAGEN STAGE: BlockStates");

        HashMap<String, Holder<Block>> unregisteredBlocks = new HashMap<>();
        for(Holder<Block> block : M4Blocks.BLOCKS.getEntries()){
            System.out.println(block.getRegisteredName());
            unregisteredBlocks.put(block.getRegisteredName(), block);
        }

        registerAndRemoveSimple(M4Blocks.DYSPERSIUM_ORE.get(), M4Blocks.DYSPERSIUM_ORE.getRegisteredName(), unregisteredBlocks);
        registerAndRemoveSimple(M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.get(), M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.getRegisteredName(), unregisteredBlocks);

        registerAndRemoveSimple(M4Blocks.OBLITERATION_PLINTH.get(), M4Blocks.OBLITERATION_PLINTH.getRegisteredName(), unregisteredBlocks);
        registerGenerator(unregisteredBlocks);
        registerCharger(unregisteredBlocks);
        registerCable(unregisteredBlocks);

        registerBlockPlaceholders(unregisteredBlocks.values());
    }

    //track anything we haven't registered normally so we can generate placeholders
    private void registerAndRemoveSimple(Block toRegister, String key,  HashMap<String, Holder<Block>> unregisteredBlocks){
        simpleBlock(toRegister);
        unregisteredBlocks.remove(key);
    }

    //register placeholder textures for untextured blocks (this will be deprecated once we release, but that's a ways off)
    private void registerBlockPlaceholders(Collection<Holder<Block>> blocks){
        for(Holder<Block> block : blocks){
            registerGenericFacingBlock(block.getKey().location().getPath(), block);
        }
    }

    //register a placeholder block with facing placement
    private void registerGenericFacingBlock(String pathPrefix, Holder<Block> blockToRegister){
        BlockModelBuilder model = models().cube(pathPrefix, SIDE, SIDE, modLoc("block/generic_machine_front"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(blockToRegister.value(), (state, builder) -> {
            builder.modelFile(model);
        });
    }

    private void registerCharger(HashMap<String, Holder<Block>> unregisteredBlocks) {
        BlockModelBuilder modelOn = models().slab(M4Blocks.CHARGING_STATION.getId().getPath()+"_on", SIDE, BOTTOM, modLoc("block/machine_placeholder")).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().slab(M4Blocks.CHARGING_STATION.getId().getPath()+"_off", SIDE, BOTTOM, modLoc("block/machine_placeholder")).texture("particle", SIDE);
        getVariantBuilder(M4Blocks.CHARGING_STATION.get()).forAllStates(state -> {
            ConfiguredModel.Builder<?> bld = ConfiguredModel.builder();
            bld.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff);
            return bld.build();
        });

        unregisteredBlocks.remove(M4Blocks.CHARGING_STATION.getRegisteredName());
    }

    private void registerGenerator(HashMap<String, Holder<Block>> unregisteredBlocks) {
        BlockModelBuilder modelOn = models().cube(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.getId().getPath()+"_on", BOTTOM, TOP, modLoc("block/hep_powered"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        BlockModelBuilder modelOff = models().cube(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.getId().getPath()+"_off", BOTTOM, TOP, modLoc("block/hep_front"), SIDE, SIDE, SIDE).texture("particle", SIDE);
        directionBlock(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get(), (state, builder) -> {
            builder.modelFile(state.getValue(BlockStateProperties.POWERED) ? modelOn : modelOff);
        });

        unregisteredBlocks.remove(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.getRegisteredName());
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

    private void registerCable(HashMap<String, Holder<Block>> unregisteredBlocks) {
        BlockModelBuilder model = models().getBuilder("cable")
                .parent(models().getExistingFile(mcLoc("cube")))
                .customLoader((builder, helper) -> new CableLoaderBuilder(CableTubeModelLoader.GENERATOR_LOADER, builder, helper))
                .end();
        simpleBlock(M4Blocks.POWER_CABLE_BLOCK.get(), model);

        unregisteredBlocks.remove(M4Blocks.POWER_CABLE_BLOCK.getRegisteredName());
    }

    public static class CableLoaderBuilder extends CustomLoaderBuilder<BlockModelBuilder> {

        public CableLoaderBuilder(ResourceLocation loader, BlockModelBuilder parent, ExistingFileHelper existingFileHelper) {
            super(loader, parent, existingFileHelper, false);
        }

        @Override
        public JsonObject toJson(JsonObject json) {
            JsonObject obj = super.toJson(json);
            return obj;
        }
    }



}
