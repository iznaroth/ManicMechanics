package com.iznaroth.manicmechanics.util;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;

public class TieredSlot extends Slot {

    /**
     * A special inventory slot that has different behavior depending on
     * the machine tier.
     * @param p_40223_
     * @param p_40224_
     * @param p_40225_
     * @param p_40226_
     */

    public TieredSlot(Container p_40223_, int p_40224_, int p_40225_, int p_40226_, int parent_tier) {
        super(p_40223_, p_40224_, p_40225_, p_40226_);
    }

    @Override
    public boolean isActive() {
        return super.isActive();
    }
}
