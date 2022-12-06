package com.iznaroth.manicmechanics.render;

import com.iznaroth.manicmechanics.blockentity.MMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.Optional;
import java.util.Random;

public class TileRenderTester extends BlockEntity {
    public TileRenderTester(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    /**
    public TileRenderTester() {super(MMBlockEntities.RENDER_TESTER_TILE.get());}

    public static final Color INVALID_COLOR = null;

    // get the colour of the artifact.  returns INVALID_COLOR if not set yet.
    public Color getArtifactColour() {
        return artifactColour;
    }
    public void setArtifactColour(Color newColour)
    {
        artifactColour = newColour;
    }


    public EnumRenderStyle getArtifactRenderStyle() {
        return artifactRenderStyle;
    }
    public void setArtifactRenderStyle(EnumRenderStyle artifactRenderStyle) {
        this.artifactRenderStyle = artifactRenderStyle;
    }

    /**
     * Calculate the next angular position of the object, given its current speed.
     * @param revsPerSecond
     * @return the angular position in degrees (0 - 360)

    public double getNextAngularPosition(double revsPerSecond)
    {
        // we calculate the next position as the angular speed multiplied by the elapsed time since the last position.
        // Elapsed time is calculated using the system clock, which means the animations continue to
        //  run while the game is paused.
        // Alternatively, the elapsed time can be calculated as
        //  time_in_seconds = (number_of_ticks_elapsed + partialTick) / 20.0;
        //  where your tileEntity's update() method increments number_of_ticks_elapsed, and partialTick is passed by vanilla
        //   to your TER renderTileEntityAt() method.
        long timeNow = System.nanoTime();
        if (lastTime == INVALID_TIME) {   // automatically initialise to 0 if not set yet
            lastTime = timeNow;
            lastAngularPosition = 0.0;
        }
        final double DEGREES_PER_REV = 360.0;
        final double NANOSECONDS_PER_SECOND = 1e9;
        double nextAngularPosition = lastAngularPosition + (timeNow - lastTime) * revsPerSecond * DEGREES_PER_REV / NANOSECONDS_PER_SECOND;
        nextAngularPosition = nextAngularPosition % DEGREES_PER_REV;
        lastAngularPosition = nextAngularPosition;
        lastTime = timeNow;
        return nextAngularPosition;
    }

    // When the world loads from disk, the server needs to send the TileEntity information to the client
    //  it uses getUpdatePacket(), getUpdateTag(), onDataPacket(), and handleUpdateTag() to do this:
    //  getUpdatePacket() and onDataPacket() are used for one-at-a-time TileEntity updates
    //  getUpdateTag() and handleUpdateTag() are used by vanilla to collate together into a single chunk update packet
    // In this case, we need it for the gem colour.  There's no need to save the gem angular position because
    //  the player will never notice the difference and the client<-->server synchronisation lag will make it
    //  inaccurate anyway
    @Override
    @Nullable
    public Packet<ClientGamePacketListener> getUpdatePacket()
    {
        CompoundTag nbtTagCompound = new CompoundTag();
        saveAdditional(nbtTagCompound);
        int tileEntityType = 42;  // arbitrary number; only used for vanilla TileEntities.  You can use it, or not, as you want.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    //@Override
    //public void onDataPacket(Network net, SUpdateTileEntityPacket pkt) {
    //    BlockState blockState = level.getBlockState(worldPosition);
    //    load(blockState, pkt.getTag());   // read from the nbt in the packet
    //}

    /* Creates a tag containing the TileEntity information, used by vanilla to transmit from server to client

    @Override
    public CompoundTag getUpdateTag()
    {
        CompoundTag nbtTagCompound = new CompoundTag();
        saveAdditional(nbtTagCompound);
        return nbtTagCompound;
    }

    /* Populates this TileEntity with information from the tag, used by vanilla to transmit from server to client

    @Override
    public void handleUpdateTag(CompoundTag tag)
    {
        this.load(tag);
    }



    // This is where you save any data that you don't want to lose when the tile entity unloads
    // In this case, we only need to store the gem colour.  For examples with other types of data, see MBE20
    @Override
    public void saveAdditional(CompoundTag parentNBTTagCompound)
    {
        super.saveAdditional(parentNBTTagCompound); // The super call is required to save the tiles location
        if (artifactColour != INVALID_COLOR) {
            parentNBTTagCompound.putInt("artifactColour", artifactColour.getRGB());
        }
        artifactRenderStyle.putIntoNBT(parentNBTTagCompound,"artifactRenderStyle");

    }

    // This is where you load the data that you saved in writeToNBT
    @Override
    public void load(CompoundTag parentNBTTagCompound)
    {
        super.load(parentNBTTagCompound); // The super call is required to load the tiles location

        // important rule: never trust the data you read from NBT, make sure it can't cause a crash

        final int NBT_INT_ID = IntTag.valueOf(0).getId();
        Color readArtifactColour = INVALID_COLOR;
        if (parentNBTTagCompound.contains("artifactColour", NBT_INT_ID)) {  // check if the key exists and is an Int. You can omit this if a default value of 0 is ok.
            int colorRGB = parentNBTTagCompound.getInt("artifactColour");
            readArtifactColour = new Color(colorRGB);
        }
        artifactColour = readArtifactColour;
        artifactRenderStyle = EnumRenderStyle.fromNBT(parentNBTTagCompound, "artifactRenderStyle");
    }

    /**
     * Don't render the object if the player is too far away
     * @return the maximum distance squared at which the TER should render
     */
    //@Override
    //public double getViewDistance()
    //{
    //    final int MAXIMUM_DISTANCE_IN_BLOCKS = 32;
    //    return MAXIMUM_DISTANCE_IN_BLOCKS * MAXIMUM_DISTANCE_IN_BLOCKS;
    //}

    /** Return an appropriate bounding box enclosing the TER
     * This method is used to control whether the TER should be rendered or not, depending on where the player is looking.
     * The default is the AABB for the parent block, which might be too small if the TER renders outside the borders of the
     *   parent block.
     * If you get the boundary too small, the TER may disappear when you aren't looking directly at it.
     * @return an appropriately size AABB for the TileEntity

    @Override
    public AABB getRenderBoundingBox()
    {
        // if your render should always be performed regardless of where the player is looking, use infinite
        //   Your should also change TileEntitySpecialRenderer.isGlobalRenderer().
        AABB infiniteExample = INFINITE_EXTENT_AABB;

        // our gem will stay above the block, up to 1 block higher, so our bounding box is from [x,y,z] to  [x+1, y+2, z+1]
        AABB aabb = new AABB(getBlockPos(), getBlockPos().offset(1, 2, 1));
        return aabb;
    }
    public enum EnumRenderStyle {
        WIREFRAME(1), QUADS(2), BLOCKQUADS(3), WAVEFRONT(4);

        public EnumRenderStyle getNextStyle() {
            int nextLargestID = nbtID + 1;
            for (EnumRenderStyle enumRenderStyle : EnumRenderStyle.values()) {
                if (enumRenderStyle.nbtID == nextLargestID) return enumRenderStyle;
            }
            return WIREFRAME;
        }

        /**
         * Read the renderstyle enum out of NBT
         * @param compoundNBT
         * @param tagname
         * @return

        public static EnumRenderStyle fromNBT(CompoundTag compoundNBT, String tagname)
        {
            byte renderStyleID = 0;  // default in case of error
            if (compoundNBT != null && compoundNBT.contains(tagname)) {
                renderStyleID = compoundNBT.getByte(tagname);
            }
            Optional<EnumRenderStyle> enumRenderStyle = getEnumRenderStyleFromID(renderStyleID);
            return enumRenderStyle.orElse(WIREFRAME);
        }

        /**
         * Write this enum to NBT
         * @param compoundNBT
         * @param tagname

        public void putIntoNBT(CompoundTag compoundNBT, String tagname)
        {
            compoundNBT.putByte(tagname, nbtID);
        }

        /**
         * Pick a random render style
         * @return

        public static EnumRenderStyle pickRandom() {
            int count = EnumRenderStyle.values().length;
            int whichIdx = new Random().nextInt(count);
            return EnumRenderStyle.values()[whichIdx];
        }

        private static Optional<EnumRenderStyle> getEnumRenderStyleFromID(byte ID) {
            for (EnumRenderStyle enumRenderStyle : EnumRenderStyle.values()) {
                if (enumRenderStyle.nbtID == ID) return Optional.of(enumRenderStyle);
            }
            return Optional.empty();
        }

        EnumRenderStyle(int i_NBT_ID) {this.nbtID = (byte)i_NBT_ID;}
        private byte nbtID;
    }

    private Color artifactColour = INVALID_COLOR;  // the RGB colour of the artifact
    private EnumRenderStyle artifactRenderStyle = EnumRenderStyle.WIREFRAME;  // which method should we use to render this artifact?

    private final long INVALID_TIME = 0;
    private long lastTime = INVALID_TIME;  // used for animation
    private double lastAngularPosition; // used for animation
    **/

}
