package com.iznaroth.manicmechanics.blockentity.interfaces;

import net.minecraftforge.items.ItemStackHandler;

public interface IHasInvHandler { //For generic itemsync packet checking.
    public void setHandler(ItemStackHandler handler);
}
