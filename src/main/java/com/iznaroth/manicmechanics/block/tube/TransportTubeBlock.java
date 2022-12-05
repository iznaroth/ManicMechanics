package com.iznaroth.manicmechanics.block.tube;

import com.iznaroth.manicmechanics.tile.TubeBundleTile;
import net.minecraft.block.*;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.List;

//Big thanks to TheGreyGhost's MinecraftByExample repo. Go check it out! --> https://github.com/TheGreyGhost/MinecraftByExample


public class TransportTubeBlock extends AbstractTubeBlock {

    public TransportTubeBlock(Properties properties) {
        super(properties);
    }

    @Override
    public int getTubeType(){
        return 0; //THIS IS OVERRIDEN BY CHILDREN - 0 is Logistic, 1 is Fluid, 2 is Gas, 3 is Power
    }

    @Override
    public boolean canBuildConnection(IBlockReader iBlockReader, BlockPos blockPos, Direction direction){
        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        TubeBundleTile here = (TubeBundleTile) iBlockReader.getBlockEntity(blockPos); //We can presume this is not null, because this function is only called inside a function that verifies it.

        //System.out.println("Logistic Tube trying to connect to non-conduit face");


        if(neighborBlockState.hasTileEntity() && iBlockReader.getBlockEntity(neighborPos) instanceof IItemHandler || iBlockReader.getBlockEntity(neighborPos) instanceof IInventory){
            here.buildOrUpdateConnection(this.getTubeType(), direction); //dont worry it wont
            return true;
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader reader, List<ITextComponent> list, ITooltipFlag flags) {
        if(Screen.hasShiftDown()){
            list.add(new TranslationTextComponent("message.transporttube.tooltip").withStyle(TextFormatting.AQUA));
        } else {
            list.add(new TranslationTextComponent("message.manicmechanics.tooltip").withStyle(TextFormatting.GRAY));
        }
    }

}