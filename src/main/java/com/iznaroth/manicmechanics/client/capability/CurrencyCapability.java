package com.iznaroth.manicmechanics.client.capability;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.api.ICurrency;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.AutoRegisterCapability;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.tags.ITag;

import javax.annotation.Nullable;

public class CurrencyCapability {

    @AutoRegisterCapability(ICurrency.class)
    public static final Capability<ICurrency> CURRENCY_CAPABILITY = null;

    public static final Direction DEFAULT_FACING = null;

    public static final ResourceLocation ID = new ResourceLocation(ManicMechanics.MOD_ID, "currency");

    public static void register(){
        CapabilityManager.INSTANCE.register(ICurrency.class, new Capability.IStorage<ICurrency>() {
            @Nullable
            @Override
            public ITag writeNBT(Capability<ICurrency> capability, ICurrency instance, Direction side){
                CompoundTag tag = new CompoundTag();
                tag.putDouble("current", instance.getCurrentBalance());
                return tag;
            }

            @Override
            public void readNBT(Capability<ICurrency> capability, ICurrency instance, Direction side, CompoundTag nbt){
                if(!(nbt instanceof CompoundTag))
                    return;
                CompoundTag tag = (CompoundTag)nbt;
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

            if (event.getObject() instanceof Player) {
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
            getBalance(event.getOriginal()).ifPresent(oldBalance -> getBalance(event.getOriginal()).ifPresent(newBalance -> {
                newBalance.setBalance(oldBalance.getCurrentBalance());
            }));
        }
    }

}
