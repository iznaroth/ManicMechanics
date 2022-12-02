package com.iznaroth.industrizer.client.capability;

import com.iznaroth.industrizer.IndustrizerMod;
import com.iznaroth.industrizer.api.ICurrency;
import com.iznaroth.industrizer.api.ISuspicion;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

public class SuspicionCapability {

    @CapabilityInject(ISuspicion.class)
    public static final Capability<ISuspicion> SUSPICION_CAPABILITY = null;

    public static final Direction DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(IndustrizerMod.MOD_ID, "suspicion");

    public static void register(){
        CapabilityManager.INSTANCE.register(ISuspicion.class, new Capability.IStorage<ISuspicion>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<ISuspicion> capability, ISuspicion instance, Direction side){
                CompoundNBT tag = new CompoundNBT();
                tag.putDouble("current", instance.getCurrentSuspicion());
                return tag;
            }

            @Override
            public void readNBT(Capability<ISuspicion> capability, ISuspicion instance, Direction side, INBT nbt){
                if(!(nbt instanceof CompoundNBT))
                    return;
                CompoundNBT tag = (CompoundNBT)nbt;
                instance.setSuspicion(tag.getDouble("current"));
            }
        }, () -> new Suspicion(null));
        System.out.println("Finished Registering CurrencyCapability");
    }

    /**
     * Get the {@link ICurrency} from the specified entity.
     *
     * @param entity The entity
     * @return A lazy optional containing the IMana, if any
     */
    public static LazyOptional<ISuspicion> getSuspicion(final LivingEntity entity){
        return entity.getCapability(SUSPICION_CAPABILITY, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider(final ISuspicion sus) {
        return new SerializableCapabilityProvider<>(SUSPICION_CAPABILITY, DEFAULT_FACING, sus);
    }

    /**
     * Event handler for the {@link ICurrency} capability.
     */
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = IndustrizerMod.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link ICurrency} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {

            if (event.getObject() instanceof PlayerEntity) {
                final Suspicion sus = new Suspicion((LivingEntity) event.getObject());
                event.addCapability(ID, createProvider(sus));
            }
        }

        /**
         * Copy the player's suspicion when they respawn after dying or returning from the end.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerClone(final PlayerEvent.Clone event) {
            getSuspicion(event.getOriginal()).ifPresent(oldSusp -> getSuspicion(event.getPlayer()).ifPresent(newSusp -> {
                newSusp.setSuspicion(oldSusp.getCurrentSuspicion());
            }));
        }
    }

}
