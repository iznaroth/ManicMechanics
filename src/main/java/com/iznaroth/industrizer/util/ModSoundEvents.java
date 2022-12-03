package com.iznaroth.industrizer.util;

import com.iznaroth.industrizer.IndustrizerMod;
import net.minecraft.block.Block;
import net.minecraft.client.audio.Sound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, IndustrizerMod.MOD_ID);

    public static final RegistryObject<SoundEvent> COP_SIREN = registerSoundEvent("cop_siren");
    public static final RegistryObject<SoundEvent> PINCH_1 = registerSoundEvent("pinch_1");

    public static final RegistryObject<SoundEvent> EVA_MUSIC_DISC = registerSoundEvent("eva");

    //DYSPERSIUM BITES
    public static final RegistryObject<SoundEvent> DYSPERSIUM_0 = registerSoundEvent("dyspersium_0");
    public static final RegistryObject<SoundEvent> DYSPERSIUM_1 = registerSoundEvent("dyspersium_1");



    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(IndustrizerMod.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
