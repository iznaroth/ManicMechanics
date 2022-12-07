package com.iznaroth.manicmechanics.events;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.client.capability.Currency;
import com.iznaroth.manicmechanics.client.capability.CurrencyCapability;
import com.iznaroth.manicmechanics.item.MMItems;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.ExplosionDamageCalculator;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ManicMechanics.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if(!event.getOriginal().getCommandSenderWorld().isClientSide()) {
            event.getOriginal().getPersistentData().putIntArray(ManicMechanics.MOD_ID + "homepos",
                    event.getOriginal().getPersistentData().getIntArray(ManicMechanics.MOD_ID + "homepos"));
        }

        if(event.isWasDeath()) {
            event.getOriginal().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(oldStore -> {
                event.getOriginal().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).ifPresent(newStore -> {
                    newStore.copyFrom(oldStore);
                });
            });
        }
    }


    private static final String NBT_KEY = "manicmechanics.firstjoin";

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        //if (ManicMechanics.config.isStartingBookDisabled()) {
        //    return;
        //}N


        System.out.println("Player joined.");

        CompoundTag data = event.getEntity().getPersistentData();
        CompoundTag persistent;
        if (!data.contains(Player.PERSISTED_NBT_TAG)) {
            data.put(Player.PERSISTED_NBT_TAG, (persistent = new CompoundTag()));
        } else {
            persistent = data.getCompound(Player.PERSISTED_NBT_TAG);
        }

        if (!persistent.hasUUID(NBT_KEY)) {
            persistent.putBoolean(NBT_KEY, true);
            System.out.println("Player gets book.");
            event.getEntity().getInventory().add(new ItemStack(MMItems.ILLEGIBLE_TOME.get()));

        }
    }

    /**
    @SubscribeEvent
    public void onNitrolBroken(BlockEvent.BreakEvent e){

        if(e.getLevel().isClientSide())
            return;

        if(e.getLevel().getBlockState(e.getPos()).getBlock() == MMBlocks.NITROL_ORE.get()){
            System.out.println("Broke Nitrol.");

            ((ServerLevel) e.getLevel()).explode((Entity) null, new DamageSource(ManicMechanics.MOD_ID + "_nitrol_busted"), (ExplosionDamageCalculator) null, e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), 10.0F, true, Explosion.BlockInteraction.DESTROY);

        }
    }

     **/

    @SubscribeEvent
    public static void onAttachCapabilitiesPlayer(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof Player) {
            if(!event.getObject().getCapability(CurrencyCapability.CURRENCY_CAPABILITY).isPresent()) {
                event.addCapability(new ResourceLocation(ManicMechanics.MOD_ID, "properties"), new CurrencyCapability());
            }
        }
    }


    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(Currency.class);
    }
}