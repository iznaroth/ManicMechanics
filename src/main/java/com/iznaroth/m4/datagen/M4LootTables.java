package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.blockentity.ObliterationPlinthBlockEntity;
import com.iznaroth.m4.common.m4;

import com.iznaroth.m4.common.registration.M4BlockEntities;
import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulator;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyCustomDataFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.stream.Collectors;

public class M4LootTables extends VanillaBlockLoot {
    public M4LootTables(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        createStandardTable(M4Blocks.OBLITERATION_PLINTH.get(), M4BlockEntities.OBLITERATION_PLINTH_ENTITY.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.holders()
                .filter(e -> e.key().location().getNamespace().equals(m4.MODID))
                .map(Holder.Reference::value)
                .collect(Collectors.toList());
    }

    private void createStandardTable(Block block, BlockEntityType<?> type) {
        /*LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(LootItem.lootTableItem(block)
                        .apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY))
                        .apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY)
                                .copy(ObliterationPlinthBlockEntity.ITEMS_TAG, "BlockEntityTag." + ObliterationPlinthBlockEntity.ITEMS_TAG, CopyCustomDataFunction.MergeStrategy.REPLACE))
                        .apply(SetContainerContents.setContents() {
                                })
                                .withEntry(DynamicLoot.dynamicEntry(ResourceLocation.fromNamespaceAndPath("minecraft", "contents"))))
                );
        add(block, LootTable.lootTable().withPool(builder));*/
    }
}
