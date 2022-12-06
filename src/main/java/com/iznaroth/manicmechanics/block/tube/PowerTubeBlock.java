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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import javax.annotation.Nullable;
import java.util.List;

public class PowerTubeBlock extends AbstractTubeBlock {

    public PowerTubeBlock(BlockBehaviour.Properties properties) {
        super(properties);
    }

    @Override
    public int getTubeType(){
        return 1;
    }

    @Override
    public boolean canBuildConnection(BlockGetter iBlockReader, BlockPos blockPos, Direction direction){
        BlockPos neighborPos = blockPos.relative(direction);
        BlockState neighborBlockState = iBlockReader.getBlockState(neighborPos);
        TubeBundleBE here = (TubeBundleBE) iBlockReader.getBlockEntity(blockPos); //We can presume this is not null, because this function is only called inside a function that verifies it.

        if(neighborBlockState.hasBlockEntity() && iBlockReader.getBlockEntity(neighborPos).getCapability(ForgeCapabilities.ENERGY).isPresent()){
            here.buildOrUpdateConnection(this.getTubeType(), direction); //dont worry it wont
            return true;
        }

        return false;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
        if(Screen.hasShiftDown()){
            list.add(Component.translatable("message.powertube.tooltip").withStyle(ChatFormatting.AQUA));
        } else {
            list.add(Component.translatable("message.manicmechanics.tooltip").withStyle(ChatFormatting.GRAY));
        }
    }

}


