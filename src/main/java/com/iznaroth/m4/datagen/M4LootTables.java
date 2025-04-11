package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;

import com.iznaroth.m4.common.blockentity.CableTubeBlockEntity;
import com.iznaroth.m4.common.blockentity.ChargingStationBlockEntity;
import com.iznaroth.m4.common.blockentity.HEPCBlockEntity;
import com.iznaroth.m4.common.blockentity.ObliterationPlinthBlockEntity;
import com.iznaroth.m4.common.registration.M4BlockEntities;
import com.iznaroth.m4.common.registration.M4Blocks;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulator;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulators;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.CopyCustomDataFunction;
import net.minecraft.world.level.storage.loot.functions.CopyNameFunction;
import net.minecraft.world.level.storage.loot.functions.SetContainerContents;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.stream.Collectors;

public class M4LootTables extends VanillaBlockLoot {
    public M4LootTables(HolderLookup.Provider registries) {
        super(registries);
    }

    @Override
    protected void generate() {
        dropSelf(M4Blocks.OBLITERATION_PLINTH.get());
        dropSelf(M4Blocks.MANUFACTORUM.get());
        dropSelf(M4Blocks.MANIPULATOR.get());
        dropSelf(M4Blocks.INOCULATOR.get());
        dropSelf(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get());
        dropSelf(M4Blocks.CHARGING_STATION.get());
        dropSelf(M4Blocks.POWER_CABLE_BLOCK.get());
        //createStandardTable(M4Blocks.POWER_CABLE_BLOCK.get(), ContainerComponentManipulators.CONTAINER, CableTubeBlockEntity.ENERGY_TAG);
        super.generate();
        //createStandardTable(M4Blocks.OBLITERATION_PLINTH.get(), M4BlockEntities.OBLITERATION_PLINTH_ENTITY.get());
        //createStandardTable(M4Blocks.HYPERFIELD_EXTRACT_POLARIZATION_CHAMBER.get(), M4BlockEntities.HEPC_BLOCK_ENTITY.get(), HEPCBlockEntity.ITEMS_TAG, HEPCBlockEntity.ENERGY_TAG);
        //createStandardTable(M4Blocks.CHARGING_STATION.get(), M4BlockEntities.CHARGING_STATION_BLOCK_ENTITY.get(), ChargingStationBlockEntity.ENERGY_TAG);
    }
/*
    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.holders()
                .filter(e -> e.key().location().getNamespace().equals(M4.MODID))
                .map(Holder.Reference::value)
                .collect(Collectors.toList());
    }
 */


    private void createStandardTable(Block block, ContainerComponentManipulator<?> type, String... tags) {
        LootPoolSingletonContainer.Builder<?> lti = LootItem.lootTableItem(block);
        lti.apply(CopyNameFunction.copyName(CopyNameFunction.NameSource.BLOCK_ENTITY));
        for (String tag : tags) {
            lti.apply(CopyCustomDataFunction.copyData(ContextNbtProvider.BLOCK_ENTITY).copy(tag, "BlockEntityTag." + tag, CopyCustomDataFunction.MergeStrategy.REPLACE));
        }
        lti.apply(SetContainerContents.setContents(type).withEntry(DynamicLoot.dynamicEntry(ResourceLocation.fromNamespaceAndPath("minecraft", "contents"))));

        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1))
                .add(lti);
        add(block, LootTable.lootTable().withPool(builder));
    }
}
