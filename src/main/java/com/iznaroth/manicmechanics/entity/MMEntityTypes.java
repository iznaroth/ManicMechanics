package com.iznaroth.manicmechanics.entity;


import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.entity.custom.CopCarEntity;
import com.iznaroth.manicmechanics.entity.custom.CrystalWarlockEntity;
import com.iznaroth.manicmechanics.entity.custom.GridSkaterEntity;
import com.iznaroth.manicmechanics.entity.custom.PinchEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MMEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ManicMechanics.MOD_ID);

    public static final RegistryObject<EntityType<CopCarEntity>> COP_CAR =
            ENTITY_TYPES.register("cop_car",
                    () -> EntityType.Builder.of(CopCarEntity::new,
                                    MobCategory.MONSTER).sized(1f, 3f)
                            .build(new ResourceLocation(ManicMechanics.MOD_ID, "cop_car").toString()));

    public static final RegistryObject<EntityType<PinchEntity>> PINCH =
            ENTITY_TYPES.register("pinch",
                    () -> EntityType.Builder.of(PinchEntity::new,
                                    MobCategory.MISC).sized(1f, 3f)
                            .build(new ResourceLocation(ManicMechanics.MOD_ID, "pinch").toString()));

    public static final RegistryObject<EntityType<GridSkaterEntity>> GRID_SKATER =
            ENTITY_TYPES.register("grid_skater",
                    () -> EntityType.Builder.of(GridSkaterEntity::new,
                                    MobCategory.MONSTER).sized(1f, 3f)
                            .build(new ResourceLocation(ManicMechanics.MOD_ID, "grid_skater").toString()));

    public static final RegistryObject<EntityType<CrystalWarlockEntity>> CRYSTAL_WARLOCK =
            ENTITY_TYPES.register("crystal_warlock",
                    () -> EntityType.Builder.of(CrystalWarlockEntity::new,
                                    MobCategory.MONSTER).sized(1f, 3f)
                            .build(new ResourceLocation(ManicMechanics.MOD_ID, "crystal_warlock").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

