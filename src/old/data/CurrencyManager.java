package com.iznaroth.manicmechanics.capability.data;

import com.sun.istack.internal.NotNull;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.server.

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CurrencyManager extends WorldSavedData {
    private final Map<ChunkPos, Currency> manaMap = new HashMap<>();
    private final Random random = new Random();

    private int counter = 0;

    @Nonnull
    public static CurrencyManager get(Level level) {
        if (level.isClientSide) {
            throw new RuntimeException("Don't access this client-side!");
        }
        DimensionDataStorage storage = ((ServerLevel)level).getDataStorage();
        return storage.computeIfAbsent(ManaManager::new, ManaManager::new, "manamanager");
    }

    @NotNull
    private Mana getManaInternal(BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        return manaMap.computeIfAbsent(chunkPos, cp -> new Mana(random.nextInt(ManaConfig.CHUNK_MAX_MANA.get()) + ManaConfig.CHUNK_MIN_MANA.get()));
    }

    public int getMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        return mana.getMana();
    }

    public int extractMana(BlockPos pos) {
        Mana mana = getManaInternal(pos);
        int present = mana.getMana();
        if (present > 0) {
            mana.setMana(present-1);
            setDirty();
            return 1;
        } else {
            return 0;
        }
    }

    public void tick(Level level) {
        counter--;
        if (counter <= 0) {
            counter = 10;
            // Synchronize the mana to the players in this world
            // todo expansion: keep the previous data that was sent to the player and only send if changed
            level.players().forEach(player -> {
                if (player instanceof ServerPlayer serverPlayer) {
                    int playerMana = serverPlayer.getCapability(PlayerManaProvider.PLAYER_MANA)
                            .map(PlayerMana::getMana)
                            .orElse(-1);
                    int chunkMana = getMana(serverPlayer.blockPosition());
                    Messages.sendToPlayer(new PacketSyncManaToClient(playerMana, chunkMana), serverPlayer);
                }
            });

            // todo expansion: here it would be possible to slowly regenerate mana in chunks
        }
    }

    public ManaManager() {
    }

    public ManaManager(CompoundTag tag) {
        ListTag list = tag.getList("mana", Tag.TAG_COMPOUND);
        for (Tag t : list) {
            CompoundTag manaTag = (CompoundTag) t;
            Mana mana = new Mana(manaTag.getInt("mana"));
            ChunkPos pos = new ChunkPos(manaTag.getInt("x"), manaTag.getInt("z"));
            manaMap.put(pos, mana);
        }
    }

    @Override
    public CompoundTag save(CompoundTag tag) {
        ListTag list = new ListTag();
        manaMap.forEach((chunkPos, mana) -> {
            CompoundTag manaTag = new CompoundTag();
            manaTag.putInt("x", chunkPos.x);
            manaTag.putInt("z", chunkPos.z);
            manaTag.putInt("mana", mana.getMana());
            list.add(manaTag);
        });
        tag.put("mana", list);
        return tag;
    }
}
