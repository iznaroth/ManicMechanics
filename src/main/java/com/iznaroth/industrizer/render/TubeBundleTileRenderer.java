package com.iznaroth.industrizer.render;

import com.iznaroth.industrizer.tile.TubeBundleTile;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3f;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.awt.*;

/**
 * User: The Grey Ghost
 * Date: 12/01/2015
 * This class renders the artifact floating above the block.
 * The base model (the hopper shape) is drawn by the block model, not this class.
 * See assets/minecraftbyexample/blockstates/mbe21_tesr_block_registry_name.json
 *
 * The class demonstrates four different examples of rendering:
 * 1) Lines
 * 2) Manually drawing quads
 * 3) Rendering a block model
 * 4) Rendering a wavefront object
 *
 * 1) The lines have position and colour information only  (RenderType.getLines().  No lightmap information, which means that they will always be the
 *   same brightness regardless of day/night or nearby torches.
 *
 * 2) The quads have position, colour, texture, normal, and lightmap information
 *   RenderType.getSolid() is suitable if you're using a texture which has been stitched into the block texture sheet (either by defining it
 *      in a block model, or by manually adding it during TextureStitchEvent.)
 *   Otherwise you need to create your own RenderType.
 *
 * 3) Reads the block model for vanilla object and renders a smaller version of it
 *
 * 4) Reads a custom wavefront object (as a block model), and renders it using block rendering methods
 *
 */
public class TubeBundleTileRenderer extends TileEntityRenderer<TubeBundleTile> {

    public TubeBundleTileRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }


    @Override
    public void render(TubeBundleTile tubeTile, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer renderBuffers,
                       int combinedLight, int combinedOverlay) {

        //This is the loop that should render the Tile.

        //First we get the current config from the Tile.
        //tubeTile.getTypes() & tubeTile.getConnections();

        //We use those sets to



        // if you need to manually change the combinedLight you can use these helper functions...
        int blockLight = LightTexture.block(combinedLight);
        int skyLight = LightTexture.sky(combinedLight);
        int repackedValue = LightTexture.pack(blockLight, skyLight);
    }


    /**BUILDER CONDITIONAL TREE
     *
     * Run-thru connections
     *  If JUST N-S, JUST E-W or JUST U-D, skip Midbox and refer to # of Conduit
     *  Any more
     *
     *
     */


    /**
     * Add a quad.
     * The vertices are added in anti-clockwise order from the VIEWER's  point of view, i.e.
     * bottom left; bottom right, top right, top left
     * If you add the vertices in clockwise order, the quad will face in the opposite direction; i.e. the viewer will be
     *   looking at the back face, which is usually culled (not visible)
     * See
     * http://greyminecraftcoder.blogspot.com/2014/12/the-tessellator-and-worldrenderer-18.html
     * http://greyminecraftcoder.blogspot.com/2014/12/block-models-texturing-quads-faces.html
     */
    private static void addQuad(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder renderBuffer,
                                Vector3f blpos, Vector3f brpos, Vector3f trpos, Vector3f tlpos,
                                Vector2f blUVpos, Vector2f brUVpos, Vector2f trUVpos, Vector2f tlUVpos,
                                Vector3f normalVector, Color color, int lightmapValue) {
        addQuadVertex(matrixPos, matrixNormal, renderBuffer, blpos, blUVpos, normalVector, color, lightmapValue);
        addQuadVertex(matrixPos, matrixNormal, renderBuffer, brpos, brUVpos, normalVector, color, lightmapValue);
        addQuadVertex(matrixPos, matrixNormal, renderBuffer, trpos, trUVpos, normalVector, color, lightmapValue);
        addQuadVertex(matrixPos, matrixNormal, renderBuffer, tlpos, tlUVpos, normalVector, color, lightmapValue);
    }

    // suitable for vertexbuilders using the DefaultVertexFormats.ENTITY format
    private static void addQuadVertex(Matrix4f matrixPos, Matrix3f matrixNormal, IVertexBuilder renderBuffer,
                                      Vector3f pos, Vector2f texUV,
                                      Vector3f normalVector, Color color, int lightmapValue) {
        renderBuffer.vertex(matrixPos, pos.x(), pos.y(), pos.z()) // position coordinate
                .color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha())        // color
                .uv(texUV.x, texUV.y)                     // texel coordinate
                .overlayCoords(OverlayTexture.NO_OVERLAY)  // only relevant for rendering Entities (Living)
                .uv2(lightmapValue)             // lightmap with full brightness
                .normal(matrixNormal, normalVector.x(), normalVector.y(), normalVector.z())
                .endVertex();
    }




    // this should be true for tileentities which render globally (no render bounding box), such as beacons.
    @Override
    public boolean shouldRenderOffScreen(TubeBundleTile tubeTile)
    {
        return false;
    }



    private static final Logger LOGGER = LogManager.getLogger();
}
