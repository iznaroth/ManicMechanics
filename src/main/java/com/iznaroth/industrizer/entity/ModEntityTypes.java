package com.iznaroth.industrizer.entity;


import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.entity.custom.CopCarEntity;
import com.iznaroth.industrizer.entity.custom.PinchEntity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntityTypes {
    public static DeferredRegister<EntityType<?>> ENTITY_TYPES
            = DeferredRegister.create(ForgeRegistries.ENTITIES, IndustrizerMod.MOD_ID);

    public static final RegistryObject<EntityType<CopCarEntity>> COP_CAR =
            ENTITY_TYPES.register("cop_car",
                    () -> EntityType.Builder.of(CopCarEntity::new,
                                    EntityClassification.MONSTER).sized(1f, 3f)
                            .build(new ResourceLocation(IndustrizerMod.MOD_ID, "cop_car").toString()));

    public static final RegistryObject<EntityType<PinchEntity>> PINCH =
            ENTITY_TYPES.register("pinch",
                    () -> EntityType.Builder.of(PinchEntity::new,
                                    EntityClassification.MISC).sized(1f, 3f)
                            .build(new ResourceLocation(IndustrizerMod.MOD_ID, "pinch").toString()));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}

