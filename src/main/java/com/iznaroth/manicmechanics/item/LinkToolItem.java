package com.iznaroth.manicmechanics.item;

import com.iznaroth.manicmechanics.block.MMBlocks;
import com.iznaroth.manicmechanics.logistics.INetworkNavigable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.ArrayList;

public class LinkToolItem extends Item {

    BlockPos first;
    BlockPos second;
    Block case_type;


    public LinkToolItem(Item.Properties p_i48487_1_) {
        super(p_i48487_1_);
    }

    @Override
    public InteractionResult useOn(UseOnContext use){
        Level world = use.getLevel();
        BlockPos pos = use.getClickedPos();
        Player player = use.getPlayer();

        System.out.println("CLICKED LINK_TOOL WITH PROPERTIES - " + first + " " + second + " " + case_type);

        if(world.getBlockState(pos).getBlock() == MMBlocks.HIGHWAY_CONTROLLER.get() && case_type == null){
            Component msg = Component.literal("Attempting to create Vacuum Highway from this controller. Right click your desired endpoint.");

            case_type = MMBlocks.HIGHWAY_CONTROLLER.get();
            first = pos;

            System.out.println("Clicked Highway Controller");
            player.sendSystemMessage(msg);
            return InteractionResult.SUCCESS;
        }

        if(case_type == MMBlocks.HIGHWAY_CONTROLLER.get() && world.getBlockState(pos).getBlock() instanceof INetworkNavigable && first != null){
            second = pos;

            System.out.println("Start build attempt");

            //Which axis
            boolean x = first.getX() == second.getX();

            boolean y = first.getY() == second.getY();

            boolean z = first.getZ() == second.getZ();

            NonNullList<ItemStack> items = player.inventoryMenu.getItems();
            int vacuum_inv_ct = 0;

            for(int i = 0; i < items.size(); i++){
                ItemStack comp = items.get(i);
                System.out.println(comp + " | " + new ItemStack(MMBlocks.VACUUM_HIGHWAY_SEGMENT.get(), comp.getCount()));

                if(comp.toString().equals(new ItemStack(MMBlocks.VACUUM_HIGHWAY_SEGMENT.get(), comp.getCount()).toString())){
                    vacuum_inv_ct += comp.getCount();
                }
            }

            System.out.println(vacuum_inv_ct);

            Direction dir; //Lotta unnecessary shit here.
            int dist = 0;

            if(x && y){
                dist = first.getZ() - second.getZ();
                if(dist > first.getZ()){
                   dir =  Direction.NORTH;
                } else {
                   dir = Direction.SOUTH;
                }
            } else if(x && z){
                dist = first.getY() - second.getY();
                if(dist > first.getY()){
                    dir =  Direction.DOWN;
                } else {
                    dir = Direction.UP;
                }
            }else if(y && z){
                dist = first.getX() - second.getX();
                if(dist > first.getX()){
                    dir =  Direction.WEST;
                } else {
                    dir = Direction.EAST;
                }
            } else {
                Component msg = Component.literal("Not a straight line.");
                player.sendSystemMessage(msg);
                return InteractionResult.FAIL;
            }

            dist = Math.abs(dist);

            System.out.println(dist);

            if(dist > vacuum_inv_ct){ //CHECK INV QUANTITY
                second = null;

                Component msg = Component.literal("Not enough blocks in inventory.");
                player.sendSystemMessage(msg);
                return InteractionResult.FAIL;
            }

            BlockPos incrementer = first;
            ArrayList<BlockPos> to_edit = new ArrayList<BlockPos>();

            for(int i = 0; i < dist - 1; i++){
                incrementer = incrementer.relative(dir);
                if(world.getBlockState(incrementer).getBlock() == Blocks.AIR){
                       to_edit.add(incrementer);
                } else {
                    Component msg = Component.literal("Obstruction at pos:" + incrementer);
                    player.sendSystemMessage(msg);
                    return InteractionResult.FAIL;
                }
            }

            for(BlockPos new_highway : to_edit){
                world.setBlock(new_highway, MMBlocks.VACUUM_HIGHWAY_SEGMENT.get().defaultBlockState().setValue(BlockStateProperties.FACING, dir), 0);
                player.getInventory().removeItem(new ItemStack(MMBlocks.VACUUM_HIGHWAY_SEGMENT.get(), 1));
            }

            clearParams();
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }

    private void clearParams(){
        this.case_type = null;
        this.first = null;
        this.second = null;
    }



}
