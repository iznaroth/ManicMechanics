package com.iznaroth.m4.client.model;

import com.iznaroth.m4.client.model.helper.CableTubePatterns;
import com.iznaroth.m4.common.M4;
import com.iznaroth.m4.common.block.CableTubeBlock;
import com.iznaroth.m4.common.util.ConnectorType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.IDynamicBakedModel;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.iznaroth.m4.client.model.helper.BakedModelHelper.quad;
import static com.iznaroth.m4.client.model.helper.BakedModelHelper.v;
import static com.iznaroth.m4.client.model.helper.CableTubePatterns.SpriteIdx.*;
import static com.iznaroth.m4.common.util.ConnectorType.*;

public class CableTubeBakedModel implements IDynamicBakedModel {
    private final IGeometryBakingContext context;
    // final boolean facade;

    private TextureAtlasSprite spriteConnector;
    private TextureAtlasSprite spriteNoneCable;
    private TextureAtlasSprite spriteNormalCable;
    private TextureAtlasSprite spriteEndCable;
    private TextureAtlasSprite spriteCornerCable;
    private TextureAtlasSprite spriteThreeCable;
    private TextureAtlasSprite spriteCrossCable;
    private TextureAtlasSprite spriteSide;

    static {
        // For all possible patterns we define the sprite to use and the rotation. Note that each
        // pattern looks at the existance of a cable section for each of the four directions
        // excluding the one we are looking at.
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, false, false, false), CableTubePatterns.QuadSetting.of(SPRITE_NONE, 0));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, false, false, false), CableTubePatterns.QuadSetting.of(SPRITE_END, 3));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, true, false, false), CableTubePatterns.QuadSetting.of(SPRITE_END, 0));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, false, true, false), CableTubePatterns.QuadSetting.of(SPRITE_END, 1));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, false, false, true), CableTubePatterns.QuadSetting.of(SPRITE_END, 2));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, true, false, false), CableTubePatterns.QuadSetting.of(SPRITE_CORNER, 0));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, true, true, false), CableTubePatterns.QuadSetting.of(SPRITE_CORNER, 1));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, false, true, true), CableTubePatterns.QuadSetting.of(SPRITE_CORNER, 2));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, false, false, true), CableTubePatterns.QuadSetting.of(SPRITE_CORNER, 3));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, true, false, true), CableTubePatterns.QuadSetting.of(SPRITE_STRAIGHT, 0));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, false, true, false), CableTubePatterns.QuadSetting.of(SPRITE_STRAIGHT, 1));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, true, true, false), CableTubePatterns.QuadSetting.of(SPRITE_THREE, 0));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(false, true, true, true), CableTubePatterns.QuadSetting.of(SPRITE_THREE, 1));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, false, true, true), CableTubePatterns.QuadSetting.of(SPRITE_THREE, 2));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, true, false, true), CableTubePatterns.QuadSetting.of(SPRITE_THREE, 3));
        CableTubePatterns.PATTERNS.put(CableTubePatterns.Pattern.of(true, true, true, true), CableTubePatterns.QuadSetting.of(SPRITE_CROSS, 0));
    }

    public CableTubeBakedModel(IGeometryBakingContext context) {
        this.context = context;
    }

    private void initTextures() {
        if (spriteConnector == null) {
            spriteConnector = getTexture("block/cable/connector");
            spriteNormalCable = getTexture("block/cable/normal");
            spriteNoneCable = getTexture("block/cable/none");
            spriteEndCable = getTexture("block/cable/end");
            spriteCornerCable = getTexture("block/cable/corner");
            spriteThreeCable = getTexture("block/cable/three");
            spriteCrossCable = getTexture("block/cable/cross");
            spriteSide = getTexture("block/cable/side");
        }
    }

    // All textures are baked on a big texture atlas. This function gets the texture from that atlas
    private TextureAtlasSprite getTexture(String path) {
        return Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(ResourceLocation.fromNamespaceAndPath(M4.MODID, path));
    }

    private TextureAtlasSprite getSpriteNormal(CableTubePatterns.SpriteIdx idx) {
        initTextures();
        return switch (idx) {
            case SPRITE_NONE -> spriteNoneCable;
            case SPRITE_END -> spriteEndCable;
            case SPRITE_STRAIGHT -> spriteNormalCable;
            case SPRITE_CORNER -> spriteCornerCable;
            case SPRITE_THREE -> spriteThreeCable;
            case SPRITE_CROSS -> spriteCrossCable;
        };
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    @NotNull
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData extraData, @Nullable RenderType layer) {
        initTextures();
        List<BakedQuad> quads = new ArrayList<>();
        if (side == null && (layer == null || layer.equals(RenderType.solid()))) {
            // Called with the blockstate from our block. Here we get the values of the six properties and pass that to
            // our baked model implementation. If state == null we are called from the inventory and we use the default
            // values for the properties
            ConnectorType north, south, west, east, up, down;
            if (state != null) {
                north = state.getValue(CableTubeBlock.NORTH);
                south = state.getValue(CableTubeBlock.SOUTH);
                west = state.getValue(CableTubeBlock.WEST);
                east = state.getValue(CableTubeBlock.EAST);
                up = state.getValue(CableTubeBlock.UP);
                down = state.getValue(CableTubeBlock.DOWN);
            } else {
                /* If we are a facade and we are an item then we render as the 'side' texture as a full block
                if (facade) {
                    quads.add(quad(v(0, 1, 1), v(1, 1, 1), v(1, 1, 0), v(0, 1, 0), spriteSide));
                    quads.add(quad(v(0, 0, 0), v(1, 0, 0), v(1, 0, 1), v(0, 0, 1), spriteSide));
                    quads.add(quad(v(1, 0, 0), v(1, 1, 0), v(1, 1, 1), v(1, 0, 1), spriteSide));
                    quads.add(quad(v(0, 0, 1), v(0, 1, 1), v(0, 1, 0), v(0, 0, 0), spriteSide));
                    quads.add(quad(v(0, 1, 0), v(1, 1, 0), v(1, 0, 0), v(0, 0, 0), spriteSide));
                    quads.add(quad(v(0, 0, 1), v(1, 0, 1), v(1, 1, 1), v(0, 1, 1), spriteSide));
                    return quads;
                }
                */

                north = south = west = east = up = down = NONE;
            }

            TextureAtlasSprite spriteCable = spriteNormalCable;
            Function<CableTubePatterns.SpriteIdx, TextureAtlasSprite> spriteGetter = this::getSpriteNormal;

            double o = .4;      // Thickness of the cable. .0 would be full block, .5 is infinitely thin.
            double p = .1;      // Thickness of the connector as it is put on the connecting block
            double q = .2;      // The wideness of the connector

            // For each side we either cap it off if there is no similar block adjacent on that side
            // or else we extend so that we touch the adjacent block:
            if (up == CABLE) {
                quads.add(quad(v(1 - o, 1, o), v(1 - o, 1, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o), spriteCable));
                quads.add(quad(v(o, 1, 1 - o), v(o, 1, o), v(o, 1 - o, o), v(o, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(o, 1, o), v(1 - o, 1, o), v(1 - o, 1 - o, o), v(o, 1 - o, o), spriteCable));
                quads.add(quad(v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1, 1 - o), v(o, 1, 1 - o), spriteCable));
            } else if (up == BLOCK) {
                quads.add(quad(v(1 - o, 1 - p, o), v(1 - o, 1 - p, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o), spriteCable));
                quads.add(quad(v(o, 1 - p, 1 - o), v(o, 1 - p, o), v(o, 1 - o, o), v(o, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(o, 1 - p, o), v(1 - o, 1 - p, o), v(1 - o, 1 - o, o), v(o, 1 - o, o), spriteCable));
                quads.add(quad(v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - p, 1 - o), v(o, 1 - p, 1 - o), spriteCable));

                quads.add(quad(v(1 - q, 1 - p, q), v(1 - q, 1, q), v(1 - q, 1, 1 - q), v(1 - q, 1 - p, 1 - q), spriteSide));
                quads.add(quad(v(q, 1 - p, 1 - q), v(q, 1, 1 - q), v(q, 1, q), v(q, 1 - p, q), spriteSide));
                quads.add(quad(v(q, 1, q), v(1 - q, 1, q), v(1 - q, 1 - p, q), v(q, 1 - p, q), spriteSide));
                quads.add(quad(v(q, 1 - p, 1 - q), v(1 - q, 1 - p, 1 - q), v(1 - q, 1, 1 - q), v(q, 1, 1 - q), spriteSide));

                quads.add(quad(v(q, 1 - p, q), v(1 - q, 1 - p, q), v(1 - q, 1 - p, 1 - q), v(q, 1 - p, 1 - q), spriteConnector));
                quads.add(quad(v(q, 1, q), v(q, 1, 1 - q), v(1 - q, 1, 1 - q), v(1 - q, 1, q), spriteSide));
            } else {
                CableTubePatterns.QuadSetting pattern = CableTubePatterns.findPattern(west, south, east, north);
                quads.add(quad(v(o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, o), v(o, 1 - o, o), spriteGetter.apply(pattern.sprite()), pattern.rotation()));
            }

            if (down == CABLE) {
                quads.add(quad(v(1 - o, o, o), v(1 - o, o, 1 - o), v(1 - o, 0, 1 - o), v(1 - o, 0, o), spriteCable));
                quads.add(quad(v(o, o, 1 - o), v(o, o, o), v(o, 0, o), v(o, 0, 1 - o), spriteCable));
                quads.add(quad(v(o, o, o), v(1 - o, o, o), v(1 - o, 0, o), v(o, 0, o), spriteCable));
                quads.add(quad(v(o, 0, 1 - o), v(1 - o, 0, 1 - o), v(1 - o, o, 1 - o), v(o, o, 1 - o), spriteCable));
            } else if (down == BLOCK) {
                quads.add(quad(v(1 - o, o, o), v(1 - o, o, 1 - o), v(1 - o, p, 1 - o), v(1 - o, p, o), spriteCable));
                quads.add(quad(v(o, o, 1 - o), v(o, o, o), v(o, p, o), v(o, p, 1 - o), spriteCable));
                quads.add(quad(v(o, o, o), v(1 - o, o, o), v(1 - o, p, o), v(o, p, o), spriteCable));
                quads.add(quad(v(o, p, 1 - o), v(1 - o, p, 1 - o), v(1 - o, o, 1 - o), v(o, o, 1 - o), spriteCable));

                quads.add(quad(v(1 - q, 0, q), v(1 - q, p, q), v(1 - q, p, 1 - q), v(1 - q, 0, 1 - q), spriteSide));
                quads.add(quad(v(q, 0, 1 - q), v(q, p, 1 - q), v(q, p, q), v(q, 0, q), spriteSide));
                quads.add(quad(v(q, p, q), v(1 - q, p, q), v(1 - q, 0, q), v(q, 0, q), spriteSide));
                quads.add(quad(v(q, 0, 1 - q), v(1 - q, 0, 1 - q), v(1 - q, p, 1 - q), v(q, p, 1 - q), spriteSide));

                quads.add(quad(v(q, p, 1 - q), v(1 - q, p, 1 - q), v(1 - q, p, q), v(q, p, q), spriteConnector));
                quads.add(quad(v(q, 0, 1 - q), v(q, 0, q), v(1 - q, 0, q), v(1 - q, 0, 1 - q), spriteSide));
            } else {
                CableTubePatterns.QuadSetting pattern = CableTubePatterns.findPattern(west, north, east, south);
                quads.add(quad(v(o, o, o), v(1 - o, o, o), v(1 - o, o, 1 - o), v(o, o, 1 - o), spriteGetter.apply(pattern.sprite()), pattern.rotation()));
            }

            if (east == CABLE) {
                quads.add(quad(v(1, 1 - o, 1 - o), v(1, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(1, o, o), v(1, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, o), spriteCable));
                quads.add(quad(v(1, 1 - o, o), v(1, o, o), v(1 - o, o, o), v(1 - o, 1 - o, o), spriteCable));
                quads.add(quad(v(1, o, 1 - o), v(1, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, o, 1 - o), spriteCable));
            } else if (east == BLOCK) {
                quads.add(quad(v(1 - p, 1 - o, 1 - o), v(1 - p, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(1 - p, o, o), v(1 - p, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, o), spriteCable));
                quads.add(quad(v(1 - p, 1 - o, o), v(1 - p, o, o), v(1 - o, o, o), v(1 - o, 1 - o, o), spriteCable));
                quads.add(quad(v(1 - p, o, 1 - o), v(1 - p, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, o, 1 - o), spriteCable));

                quads.add(quad(v(1 - p, 1 - q, 1 - q), v(1, 1 - q, 1 - q), v(1, 1 - q, q), v(1 - p, 1 - q, q), spriteSide));
                quads.add(quad(v(1 - p, q, q), v(1, q, q), v(1, q, 1 - q), v(1 - p, q, 1 - q), spriteSide));
                quads.add(quad(v(1 - p, 1 - q, q), v(1, 1 - q, q), v(1, q, q), v(1 - p, q, q), spriteSide));
                quads.add(quad(v(1 - p, q, 1 - q), v(1, q, 1 - q), v(1, 1 - q, 1 - q), v(1 - p, 1 - q, 1 - q), spriteSide));

                quads.add(quad(v(1 - p, q, 1 - q), v(1 - p, 1 - q, 1 - q), v(1 - p, 1 - q, q), v(1 - p, q, q), spriteConnector));
                quads.add(quad(v(1, q, 1 - q), v(1, q, q), v(1, 1 - q, q), v(1, 1 - q, 1 - q), spriteSide));
            } else {
                CableTubePatterns.QuadSetting pattern = CableTubePatterns.findPattern(down, north, up, south);
                quads.add(quad(v(1 - o, o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 1 - o), v(1 - o, o, 1 - o), spriteGetter.apply(pattern.sprite()), pattern.rotation()));
            }

            if (west == CABLE) {
                quads.add(quad(v(o, 1 - o, 1 - o), v(o, 1 - o, o), v(0, 1 - o, o), v(0, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(o, o, o), v(o, o, 1 - o), v(0, o, 1 - o), v(0, o, o), spriteCable));
                quads.add(quad(v(o, 1 - o, o), v(o, o, o), v(0, o, o), v(0, 1 - o, o), spriteCable));
                quads.add(quad(v(o, o, 1 - o), v(o, 1 - o, 1 - o), v(0, 1 - o, 1 - o), v(0, o, 1 - o), spriteCable));
            } else if (west == BLOCK) {
                quads.add(quad(v(o, 1 - o, 1 - o), v(o, 1 - o, o), v(p, 1 - o, o), v(p, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(o, o, o), v(o, o, 1 - o), v(p, o, 1 - o), v(p, o, o), spriteCable));
                quads.add(quad(v(o, 1 - o, o), v(o, o, o), v(p, o, o), v(p, 1 - o, o), spriteCable));
                quads.add(quad(v(o, o, 1 - o), v(o, 1 - o, 1 - o), v(p, 1 - o, 1 - o), v(p, o, 1 - o), spriteCable));

                quads.add(quad(v(0, 1 - q, 1 - q), v(p, 1 - q, 1 - q), v(p, 1 - q, q), v(0, 1 - q, q), spriteSide));
                quads.add(quad(v(0, q, q), v(p, q, q), v(p, q, 1 - q), v(0, q, 1 - q), spriteSide));
                quads.add(quad(v(0, 1 - q, q), v(p, 1 - q, q), v(p, q, q), v(0, q, q), spriteSide));
                quads.add(quad(v(0, q, 1 - q), v(p, q, 1 - q), v(p, 1 - q, 1 - q), v(0, 1 - q, 1 - q), spriteSide));

                quads.add(quad(v(p, q, q), v(p, 1 - q, q), v(p, 1 - q, 1 - q), v(p, q, 1 - q), spriteConnector));
                quads.add(quad(v(0, q, q), v(0, q, 1 - q), v(0, 1 - q, 1 - q), v(0, 1 - q, q), spriteSide));
            } else {
                CableTubePatterns.QuadSetting pattern = CableTubePatterns.findPattern(down, south, up, north);
                quads.add(quad(v(o, o, 1 - o), v(o, 1 - o, 1 - o), v(o, 1 - o, o), v(o, o, o), spriteGetter.apply(pattern.sprite()), pattern.rotation()));
            }

            if (north == CABLE) {
                quads.add(quad(v(o, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, 0), v(o, 1 - o, 0), spriteCable));
                quads.add(quad(v(o, o, 0), v(1 - o, o, 0), v(1 - o, o, o), v(o, o, o), spriteCable));
                quads.add(quad(v(1 - o, o, 0), v(1 - o, 1 - o, 0), v(1 - o, 1 - o, o), v(1 - o, o, o), spriteCable));
                quads.add(quad(v(o, o, o), v(o, 1 - o, o), v(o, 1 - o, 0), v(o, o, 0), spriteCable));
            } else if (north == BLOCK) {
                quads.add(quad(v(o, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, 1 - o, p), v(o, 1 - o, p), spriteCable));
                quads.add(quad(v(o, o, p), v(1 - o, o, p), v(1 - o, o, o), v(o, o, o), spriteCable));
                quads.add(quad(v(1 - o, o, p), v(1 - o, 1 - o, p), v(1 - o, 1 - o, o), v(1 - o, o, o), spriteCable));
                quads.add(quad(v(o, o, o), v(o, 1 - o, o), v(o, 1 - o, p), v(o, o, p), spriteCable));

                quads.add(quad(v(q, 1 - q, p), v(1 - q, 1 - q, p), v(1 - q, 1 - q, 0), v(q, 1 - q, 0), spriteSide));
                quads.add(quad(v(q, q, 0), v(1 - q, q, 0), v(1 - q, q, p), v(q, q, p), spriteSide));
                quads.add(quad(v(1 - q, q, 0), v(1 - q, 1 - q, 0), v(1 - q, 1 - q, p), v(1 - q, q, p), spriteSide));
                quads.add(quad(v(q, q, p), v(q, 1 - q, p), v(q, 1 - q, 0), v(q, q, 0), spriteSide));

                quads.add(quad(v(q, q, p), v(1 - q, q, p), v(1 - q, 1 - q, p), v(q, 1 - q, p), spriteConnector));
                quads.add(quad(v(q, q, 0), v(q, 1 - q, 0), v(1 - q, 1 - q, 0), v(1 - q, q, 0), spriteSide));
            } else {
                CableTubePatterns.QuadSetting pattern = CableTubePatterns.findPattern(west, up, east, down);
                quads.add(quad(v(o, 1 - o, o), v(1 - o, 1 - o, o), v(1 - o, o, o), v(o, o, o), spriteGetter.apply(pattern.sprite()), pattern.rotation()));
            }

            if (south == CABLE) {
                quads.add(quad(v(o, 1 - o, 1), v(1 - o, 1 - o, 1), v(1 - o, 1 - o, 1 - o), v(o, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(o, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, 1), v(o, o, 1), spriteCable));
                quads.add(quad(v(1 - o, o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, 1), v(1 - o, o, 1), spriteCable));
                quads.add(quad(v(o, o, 1), v(o, 1 - o, 1), v(o, 1 - o, 1 - o), v(o, o, 1 - o), spriteCable));
            } else if (south == BLOCK) {
                quads.add(quad(v(o, 1 - o, 1 - p), v(1 - o, 1 - o, 1 - p), v(1 - o, 1 - o, 1 - o), v(o, 1 - o, 1 - o), spriteCable));
                quads.add(quad(v(o, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, o, 1 - p), v(o, o, 1 - p), spriteCable));
                quads.add(quad(v(1 - o, o, 1 - o), v(1 - o, 1 - o, 1 - o), v(1 - o, 1 - o, 1 - p), v(1 - o, o, 1 - p), spriteCable));
                quads.add(quad(v(o, o, 1 - p), v(o, 1 - o, 1 - p), v(o, 1 - o, 1 - o), v(o, o, 1 - o), spriteCable));

                quads.add(quad(v(q, 1 - q, 1), v(1 - q, 1 - q, 1), v(1 - q, 1 - q, 1 - p), v(q, 1 - q, 1 - p), spriteSide));
                quads.add(quad(v(q, q, 1 - p), v(1 - q, q, 1 - p), v(1 - q, q, 1), v(q, q, 1), spriteSide));
                quads.add(quad(v(1 - q, q, 1 - p), v(1 - q, 1 - q, 1 - p), v(1 - q, 1 - q, 1), v(1 - q, q, 1), spriteSide));
                quads.add(quad(v(q, q, 1), v(q, 1 - q, 1), v(q, 1 - q, 1 - p), v(q, q, 1 - p), spriteSide));

                quads.add(quad(v(q, 1 - q, 1 - p), v(1 - q, 1 - q, 1 - p), v(1 - q, q, 1 - p), v(q, q, 1 - p), spriteConnector));
                quads.add(quad(v(q, 1 - q, 1), v(q, q, 1), v(1 - q, q, 1), v(1 - q, 1 - q, 1), spriteSide));
            } else {
                CableTubePatterns.QuadSetting pattern = CableTubePatterns.findPattern(west, down, east, up);
                quads.add(quad(v(o, o, 1 - o), v(1 - o, o, 1 - o), v(1 - o, 1 - o, 1 - o), v(o, 1 - o, 1 - o), spriteGetter.apply(pattern.sprite()), pattern.rotation()));
            }
        }

        return quads;
    }

    @Override
    public boolean useAmbientOcclusion() {
        return true;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    // Because we can potentially mimic other blocks we need to render on all render types
    @Override
    @Nonnull
    public ChunkRenderTypeSet getRenderTypes(@NotNull BlockState state, @NotNull RandomSource rand, @NotNull ModelData data) {
        return ChunkRenderTypeSet.all();
    }

    @Nonnull
    @Override
    public TextureAtlasSprite getParticleIcon() {
        return spriteNormalCable == null
                ? Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply((ResourceLocation.fromNamespaceAndPath("minecraft", "missingno")))
                : spriteNormalCable;
    }

    // To let our cable/facade render correctly as an item (both in inventory and on the ground) we
    // get the correct transforms from the context
    @Nonnull
    @Override
    public ItemTransforms getTransforms() {
        return context.getTransforms();
    }

    @Nonnull
    @Override
    public ItemOverrides getOverrides() {
        return ItemOverrides.EMPTY;
    }
}


