package com.iznaroth.manicmechanics.client.capability;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.api.ICurrency;
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

public class CurrencyCapability {

    @CapabilityInject(ICurrency.class)
    public static final Capability<ICurrency> CURRENCY_CAPABILITY = null;

    public static final Direction DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(ManicMechanics.MOD_ID, "currency");

    public static void register(){
        CapabilityManager.INSTANCE.register(ICurrency.class, new Capability.IStorage<ICurrency>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<ICurrency> capability, ICurrency instance, Direction side){
                CompoundNBT tag = new CompoundNBT();
                tag.putDouble("current", instance.getCurrentBalance());
                return tag;
            }

            @Override
            public void readNBT(Capability<ICurrency> capability, ICurrency instance, Direction side, INBT nbt){
                if(!(nbt instanceof CompoundNBT))
                    return;
                CompoundNBT tag = (CompoundNBT)nbt;
                instance.setBalance(tag.getDouble("current"));
            }
        }, () -> new Currency(null));
        System.out.println("Finished Registering CurrencyCapability");
    }

    /**
     * Get the {@link ICurrency} from the specified entity.
     *
     * @param entity The entity
     * @return A lazy optional containing the IMana, if any
     */
    public static LazyOptional<ICurrency> getBalance(final LivingEntity entity){
        return entity.getCapability(CURRENCY_CAPABILITY, DEFAULT_FACING);
    }

    public static ICapabilityProvider createProvider(final ICurrency money) {
        return new SerializableCapabilityProvider<>(CURRENCY_CAPABILITY, DEFAULT_FACING, money);
    }

    /**
     * Event handler for the {@link ICurrency} capability.
     */
    @SuppressWarnings("unused")
    @Mod.EventBusSubscriber(modid = ManicMechanics.MOD_ID)
    private static class EventHandler {

        /**
         * Attach the {@link ICurrency} capability to all living entities.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void attachCapabilities(final AttachCapabilitiesEvent<Entity> event) {

            if (event.getObject() instanceof PlayerEntity) {
                final Currency curr = new Currency((LivingEntity) event.getObject());
                event.addCapability(ID, createProvider(curr));
            }
        }

        /**
         * Copy the player's mana when they respawn after dying or returning from the end.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void playerClone(final PlayerEvent.Clone event) {
            getBalance(event.getOriginal()).ifPresent(oldBalance -> getBalance(event.getPlayer()).ifPresent(newBalance -> {
                newBalance.setBalance(oldBalance.getCurrentBalance());
            }));
        }
    }

}
