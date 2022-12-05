package com.iznaroth.manicmechanics.util;

import com.iznaroth.manicmechanics.ManicMechanics;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, ManicMechanics.MOD_ID);

    public static final RegistryObject<SoundEvent> COP_SIREN = registerSoundEvent("cop_siren");
    public static final RegistryObject<SoundEvent> PINCH_1 = registerSoundEvent("pinch_1");

    public static final RegistryObject<SoundEvent> EVA_MUSIC_DISC = registerSoundEvent("eva");

    //DYSPERSIUM BITES
    public static final RegistryObject<SoundEvent> DYSPERSIUM_0 = registerSoundEvent("dyspersium_0");
    public static final RegistryObject<SoundEvent> DYSPERSIUM_1 = registerSoundEvent("dyspersium_1");



    private static RegistryObject<SoundEvent> registerSoundEvent(String name){
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(ManicMechanics.MOD_ID, name)));
    }

    public static void register(IEventBus eventBus){
        SOUND_EVENTS.register(eventBus);
    }
}
