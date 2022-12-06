package com.iznaroth.manicmechanics.block.tube;

import com.iznaroth.manicmechanics.blockentity.TubeBundleBE;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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
    public boolean canBuildConnection(BlockGetter iBlockReader, BlockPos blockPos, Direction direction){
        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        TubeBundleBE here = (TubeBundleBE) iBlockReader.getBlockEntity(blockPos); //We can presume this is not null, because this function is only called inside a function that verifies it.

        //System.out.println("Logistic Tube trying to connect to non-conduit face");


        if(neighborBlockState.hasBlockEntity() && iBlockReader.getBlockEntity(neighborPos) instanceof IItemHandler || iBlockReader.getBlockEntity(neighborPos) instanceof BaseContainerBlockEntity){
            here.buildOrUpdateConnection(this.getTubeType(), direction); //dont worry it wont
            return true;
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        if(Screen.hasShiftDown()){
            list.add(Component.translatable("message.transporttube.tooltip").withStyle(ChatFormatting.AQUA));
        } else {
            list.add(Component.translatable("message.manicmechanics.tooltip").withStyle(ChatFormatting.GRAY));
        }
    }

}