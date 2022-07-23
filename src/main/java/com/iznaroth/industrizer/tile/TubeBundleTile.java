package com.iznaroth.industrizer.tile;

import com.iznaroth.industrizer.block.IndustrizerBlocks;
import com.iznaroth.industrizer.block.TransportTubeBlock;
import com.iznaroth.industrizer.logistics.ILogisticTube;
import com.iznaroth.industrizer.logistics.ITransporter;
import com.iznaroth.industrizer.render.TubeBundleTileRenderer;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;

public class TubeBundleTile extends TileEntity  {

    private boolean[] tubesInBlock = new boolean[]{false, false, false, false};
    private boolean[] activeConnections = new boolean[]{false, false, false, false, false, false};

    /**
     * Let's talk about implementation. This (the Tile Entity) is responsible for serving the state that the block needs to update.
     *  It will be linked to the Renderer, which will actually handle all the drawing based on what's in here.
     *  Imagine it like this - the Block recieves the updates & transmits that information as necessary,
     *  the Tile preserves that state and provides it to the renderer
     *  the renderer uses all the Tile's information to draw the block.
     *
     *  When any tube is placed, it actually creates a TubeBundle (enderIO has single-cable normals that are replaced by a Bundle, which could be more performant)
     *  which initializes the tile with one type slot filled by the placed tube. Whenever use() is called on a TubeBundle block, it updates this tile's map of
     *  stored types and their assigned locations. The default model for a bundle is a 2x2x2 cube that will not be visible in any configuration, for debugging's sake.
     *  The rest of the model is loaded by the TubeBundleTile's render helpers & model builders. The render helpers also pass-down the final tube map so that the tile can build
     *  and pass-down the VoxelShape to build for the actual block.
     *
     *  Basically, whenever the TubeBundleBlock's updateShape override is called / some kind of TileEntityUpdated function hits from an adjacent tile, it passes the request up
     *  to the tile to update the built model. We should probably cache the model somehow (but test it without caching first, space-complexity of storing those models may be WAY worse)
     *  The block basically just fires a signal, and the tile then updates whatever it needs to update. The renderer is constantly(?) asking how to draw the bundle, and so
     *  the model-bakeries & helpers will now construct an updated model for all of the changed blocks.
     *
     */


    public TubeBundleTile() {
        super(IndustrizerTileEntities.TUBE_BUNDLE_TILE.get());

        this.setupInitialConnections();

    }

    public TileEntity getNeighborTile(BlockPos thisPos, Direction where){
        return this.level.getBlockEntity(thisPos.relative(where));
    }

    public void addTubeToBlock(int which){

        //Shrink if this tube isn't already in the block.

        this.tubesInBlock[which] = true;
    }

    public void setupInitialConnections(){
        int i = 0;

        for(Direction dir : Direction.values()){
            BlockPos toCheck = this.worldPosition.relative(dir);

            if(level.getBlockState(toCheck).getBlock() instanceof TransportTubeBlock || level.getBlockState(toCheck).getBlock().equals(IndustrizerBlocks.TUBE_BUNDLE.get())){
                activeConnections[i] = true;
            }
            i++;
        }
    }

    public int getStuffedTubeCount(){
        int total = 0;
        for(boolean each : tubesInBlock){
            if(each){
                total++;
            }
        }
        return total;
    }

    public int getNumConnections(){
        int total = 0;
        for(boolean each : activeConnections){
            if(each){
                total++;
            }
        }
        return total;
    }

    public boolean[] getTubesInBlock(){
        return this.tubesInBlock;
    }

    public boolean[] getActiveConnectionSides(){
        return this.activeConnections;
    }

    public void decideCenterpieceToRender(){
        int in_this = this.getStuffedTubeCount();

        CenterpieceType result = CenterpieceType.SINGLE;
        DirectionForCenterpiece dir = DirectionForCenterpiece.NS;

        if(in_this > 1){
            result = CenterpieceType.QUAD; // Force positionals or end up with a LOT of annoying decisions for type-pair attachments
        }

        int num_connections = this.getNumConnections();
        boolean[] activeConnections = this.getActiveConnectionSides();

        if(activeConnections[0] || activeConnections[1]){
            dir = DirectionForCenterpiece.UD;
        } else if(activeConnections[2] || activeConnections[3]){
            dir = DirectionForCenterpiece.NS;
        } else if(activeConnections[4] || activeConnections[5]){
            dir = DirectionForCenterpiece.EW;
        }

        if(num_connections == 2 && (!(activeConnections[0] && activeConnections[1])
                || !(activeConnections[2] && activeConnections[3])
                || !(activeConnections[4] && activeConnections[5]))){ //This trips any time a 2-connection bundle ISN'T two cardinal directions
            dir = DirectionForCenterpiece.NONE;
            result = CenterpieceType.FULL;
        }

        if(num_connections > 2){ //Any extraneous connections necessitate a NONE/FULL.
            dir = DirectionForCenterpiece.NONE;
            result = CenterpieceType.FULL;
        }

        //At this point, the middle portion of the Tube Bundle would be decided, and the extensions can be handled independently.


    }

    public void buildTubeQuadSets(){ //Based on the array of connections and each of their types

        //For each common tube connection, check if it should be long or short and pass the correct parameters to the builder.


    }

    private enum CenterpieceType { //Todo - THIS ISN'T REALISTIC, WE'RE JUST THEORYCRAFTING DOWN HERE
        SINGLE,
        DOUBLE,
        QUAD,
        FULL
    }

    private enum DirectionForCenterpiece {
        NS,
        EW,
        UD,
        NONE
    }

}
