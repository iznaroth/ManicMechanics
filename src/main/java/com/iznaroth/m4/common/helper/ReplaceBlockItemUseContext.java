package com.iznaroth.m4.common.helper;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;

public class ReplaceBlockItemUseContext extends BlockPlaceContext {

    public ReplaceBlockItemUseContext(UseOnContext context) {
        super(context);
        replaceClicked = true;
    }
}