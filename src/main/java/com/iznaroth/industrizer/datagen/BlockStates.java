package com.iznaroth.industrizer.datagen;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.block.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.function.Function;

public class BlockStates extends BlockStateProvider {

    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, IndustrizerMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        registerHEPBlock();
    }

    private void registerHEPBlock() {
        ResourceLocation txt = new ResourceLocation(IndustrizerMod.MOD_ID, "block/hep");
        BlockModelBuilder modelFirstblock = models().cube("hep", txt, txt, new ResourceLocation(IndustrizerMod.MOD_ID, "block/hep_front"), txt, txt, txt);
        BlockModelBuilder modelFirstblockPowered = models().cube("hep_powered", txt, txt, new ResourceLocation(IndustrizerMod.MOD_ID, "block/hep_powered"), txt, txt, txt);
        orientedBlock(ModBlocks.HEP.get(), state -> {
            if (state.getValue(BlockStateProperties.POWERED)) {
                return modelFirstblockPowered;
            } else {
                return modelFirstblock;
            }
        });
    }

    private void orientedBlock(Block block, Function<BlockState, ModelFile> modelFunc) {
        getVariantBuilder(block)
                .forAllStates(state -> {
                    Direction dir = state.getValue(BlockStateProperties.FACING);
                    return ConfiguredModel.builder()
                            .modelFile(modelFunc.apply(state))
                            .rotationX(dir.getAxis() == Direction.Axis.Y ? dir.getAxisDirection().getStep() * -90 : 0)
                            .rotationY(dir.getAxis() != Direction.Axis.Y ? ((dir.get2DDataValue() + 2) % 4) * 90 : 0)
                            .build();
                });
    }

}

