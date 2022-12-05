package com.iznaroth.manicmechanics.block;

import com.iznaroth.manicmechanics.logistics.INetworkNavigable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;

import javax.annotation.Nullable;

public class UpgradableMachine extends Block implements INetworkNavigable {
    public UpgradableMachine(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    public int tier;

    public void upgradeTo(int what){
        //Override this and set the texture overlay & name!


        this.tier = what;
    }

    public ItemStack downgradeAndReturn(){
        //Return a full upgrade casing of the current tier

        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.FACING); //syntactically incorrect? tutorial used createBlockStateDefinition
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.defaultBlockState().setValue(BlockStateProperties.FACING, context.getNearestLookingDirection().getOpposite());
    }

    public void changeFaceAssoc(int which, int to){
        //Changes how adjacent transporters interact with this specific face.
    }

    public ItemStack checkAndMove(ItemStack input){
       //Whenever an itemstack tries to enter this inventory, check face filter rules (which could have been changed before this item got here)
        // and either move it or refuse it.
        return null;
    }

    @Override
    public boolean runFilterFor(ItemStack itemStack) {
        return false;
    }
}
