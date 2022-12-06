package com.iznaroth.manicmechanics.world.gen;

import com.iznaroth.manicmechanics.block.MMBlocks;
import net.minecraft.block.Block;
import net.minecraftforge.common.util.Lazy;

public enum OreType {

    DYSPERSIUM(Lazy.of(MMBlocks.DYSPERSIUM_ORE), 5, 4, 25), //material type - initializer for block - constructor
    THALLITE(Lazy.of(MMBlocks.THALLITE_ORE), 8, 45, 75),
    PHOSPHORITE(Lazy.of(MMBlocks.PHOSPHORITE_ORE), 4, 15, 31),
    NITROL(Lazy.of(MMBlocks.NITROL_ORE), 5, 2, 24);

    private final Lazy<Block> block;
    private final int maxVeinSize;
    private final int minHeight;
    private final int maxHeight;

    OreType(Lazy<Block> block, int maxVeinSize, int minHeight, int maxHeight) {
        this.block = block;
        this.maxVeinSize = maxVeinSize;
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public Lazy<Block> getBlock() {
        return block;
    }

    public int getMaxVeinSize() {
        return maxVeinSize;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public static OreType get(Block block){
        for (OreType ore : values()){
            if(block == ore.block){
                return ore;
            }
        }
        return null;
    }
}
