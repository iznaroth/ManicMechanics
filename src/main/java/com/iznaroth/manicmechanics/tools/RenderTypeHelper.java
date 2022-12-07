package com.iznaroth.manicmechanics.tools;



public final class RenderTypeHelper {
    /**
     *
     * TODO - this was for old 1.16.5 custom rendering and is missing loads of references. I'm not using it now.
     *
     *
    // extract the private (protected) transparency settings so that we can create custom RenderTypes with them.
    public static final RenderState.TransparencyState TRANSLUCENT_TRANSPARENCY;
    public static final RenderState.TransparencyState NO_TRANSPARENCY;
    public static final RenderState.TransparencyState LIGHTNING_TRANSPARENCY;

    public static final RenderState.LayerState VIEW_OFFSET_Z_LAYERING;

    public static final RenderState.TargetState ITEM_ENTITY_TARGET;

    public static final RenderType MBE_LINE_DEPTH_WRITING_ON;  // draws lines which will only be drawn over by objects which are closer (unlike RenderType.LINES)
    public static final RenderType MBE_LINE_NO_DEPTH_TEST;  // draws lines on top of anything already drawn

    public static final RenderType MBE_TRIANGLES_NO_TEXTURE;  // draws triangles with a colour but no texture.

    static {
        LIGHTNING_TRANSPARENCY = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228512_d_");
        TRANSLUCENT_TRANSPARENCY = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228515_g_");
        NO_TRANSPARENCY = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228510_b_");

//    PROJECTION_LAYERING = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_228500_J_");
        VIEW_OFFSET_Z_LAYERING = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_239235_M_");

        ITEM_ENTITY_TARGET = ObfuscationReflectionHelper.getPrivateValue(RenderState.class, null, "field_241712_U_");

        final boolean ENABLE_DEPTH_WRITING = true;
        final boolean ENABLE_COLOUR_COMPONENTS_WRITING = true;
        final RenderState.WriteMaskState WRITE_TO_DEPTH_AND_COLOR
                = new RenderState.WriteMaskState(ENABLE_DEPTH_WRITING, ENABLE_COLOUR_COMPONENTS_WRITING);

        final RenderState.DepthTestState NO_DEPTH_TEST = new RenderState.DepthTestState("always", GL11.GL_ALWAYS);

        final int INITIAL_BUFFER_SIZE = 128;
        final boolean AFFECTS_OUTLINE = false;
        RenderType.State renderState;
        renderState = RenderType.State.builder()
                .setLineState(new RenderState.LineState(OptionalDouble.of(1)))
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(NO_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setWriteMaskState(WRITE_TO_DEPTH_AND_COLOR)
                .createCompositeState(AFFECTS_OUTLINE);
        MBE_LINE_DEPTH_WRITING_ON = RenderType.create("mbe_line_1_depth_writing_on",
                DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, INITIAL_BUFFER_SIZE, renderState);

        renderState = RenderType.State.builder()
                .setLineState(new RenderState.LineState(OptionalDouble.of(1)))
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(NO_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setWriteMaskState(WRITE_TO_DEPTH_AND_COLOR)
                .setDepthTestState(NO_DEPTH_TEST)
                .createCompositeState(AFFECTS_OUTLINE);
        MBE_LINE_NO_DEPTH_TEST = RenderType.create("mbe_line_1_no_depth_test",
                DefaultVertexFormats.POSITION_COLOR, GL11.GL_LINES, INITIAL_BUFFER_SIZE, renderState);

        renderState = RenderType.State.builder()
                .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                .setTransparencyState(NO_TRANSPARENCY)
                .setOutputState(ITEM_ENTITY_TARGET)
                .setWriteMaskState(WRITE_TO_DEPTH_AND_COLOR)
                .createCompositeState(AFFECTS_OUTLINE);
        MBE_TRIANGLES_NO_TEXTURE = RenderType.create("mbe_triangles_no_texture",
                DefaultVertexFormats.POSITION_COLOR, GL11.GL_TRIANGLES, INITIAL_BUFFER_SIZE, renderState);
    }
     **/
}
