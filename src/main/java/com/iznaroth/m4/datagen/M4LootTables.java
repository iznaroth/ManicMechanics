package com.iznaroth.m4.datagen;

import com.iznaroth.m4.common.M4;

import com.iznaroth.m4.common.blockentity.CableTubeBlockEntity;
import com.iznaroth.m4.common.blockentity.ChargingStationBlockEntity;
import com.iznaroth.m4.common.blockentity.HEPCBlockEntity;
import com.iznaroth.m4.common.blockentity.ObliterationPlinthBlockEntity;
import com.iznaroth.m4.common.registration.M4BlockEntities;
import com.iznaroth.m4.common.registration.M4Blocks;
import com.iznaroth.m4.common.registration.M4Items;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulator;
import net.minecraft.world.level.storage.loot.ContainerComponentManipulators;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.providers.nbt.ContextNbtProvider;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
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
        dropSelf(M4Blocks.PORTAL_DEOBFUSCATOR.get());

        add(M4Blocks.DYSPERSIUM_ORE.get(), block -> createMultipleOreDrops(M4Blocks.DYSPERSIUM_ORE.get(), M4Items.DYSPERSIUM_DUST.get(), 1, 5));
        add(M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.get(), block -> createMultipleOreDrops(M4Blocks.DEEPSLATE_DYSPERSIUM_ORE.get(), M4Items.DYSPERSIUM_DUST.get(), 3, 7));


        //Still need a working tag-reader ltb generator. Not sure if below works as far as prepending and preserving and reading-on-place goes.
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return BuiltInRegistries.BLOCK.holders()
                .filter(e -> e.key().location().getNamespace().equals(M4.MODID))
                .map(Holder.Reference::value)
                .collect(Collectors.toList());
    }



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

    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(pBlock,
                this.applyExplosionDecay(pBlock, LootItem.lootTableItem(item)
                        .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                        .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))));
    }
}
