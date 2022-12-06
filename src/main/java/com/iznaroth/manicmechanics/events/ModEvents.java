package com.iznaroth.manicmechanics.events;

import com.iznaroth.manicmechanics.ManicMechanics;
import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.item.MMItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.DamageSource;
import net.minecraft.world.Explosion;
import net.minecraft.world.ExplosionContext;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;


@Mod.EventBusSubscriber(modid = ManicMechanics.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onPlayerCloneEvent(PlayerEvent.Clone event) {
        if(!event.getOriginal().getCommandSenderWorld().isClientSide()) {
            event.getPlayer().getPersistentData().putIntArray(ManicMechanics.MOD_ID + "homepos",
                    event.getOriginal().getPersistentData().getIntArray(ManicMechanics.MOD_ID + "homepos"));
        }
    }


    private static final String NBT_KEY = "manicmechanics.firstjoin";

    @SubscribeEvent
    public void onPlayerJoin(PlayerEvent.PlayerLoggedInEvent event) {
        //if (ManicMechanics.config.isStartingBookDisabled()) {
        //    return;
        //}N


        System.out.println("Player joined.");

        CompoundNBT data = event.getPlayer().getPersistentData();
        CompoundNBT persistent;
        if (!data.contains(PlayerEntity.PERSISTED_NBT_TAG)) {
            data.put(PlayerEntity.PERSISTED_NBT_TAG, (persistent = new CompoundNBT()));
        } else {
            persistent = data.getCompound(PlayerEntity.PERSISTED_NBT_TAG);
        }

        if (!persistent.hasUUID(NBT_KEY)) {
            persistent.putBoolean(NBT_KEY, true);
            System.out.println("Player gets book.");
            event.getPlayer().inventory.add(new ItemStack(MMItems.ILLEGIBLE_TOME.get()));

        }
    }

    @SubscribeEvent
    public void onNitrolBroken(BlockEvent.BreakEvent e){

        if(e.getWorld().isClientSide())
            return;

        if(e.getWorld().getBlockState(e.getPos()).getBlock() == MMBlocks.NITROL_ORE.get()){
            System.out.println("Broke Nitrol.");

            ((ServerWorld) e.getWorld()).explode((Entity) null, new DamageSource(ManicMechanics.MOD_ID + "_nitrol_busted"), (ExplosionContext) null, e.getPos().getX(), e.getPos().getY(), e.getPos().getZ(), 10.0F, true, Explosion.Mode.DESTROY);

        }
    }
}