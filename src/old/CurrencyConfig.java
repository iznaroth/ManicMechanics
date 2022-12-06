package com.iznaroth.manicmechanics.capability;

import net.minecraftforge.common.ForgeConfigSpec;

public class CurrencyConfig {


    public static ForgeConfigSpec.IntValue CURR_HUD_X;
    public static ForgeConfigSpec.IntValue CURR_HUD_Y;
    public static ForgeConfigSpec.IntValue CURR_HUD_COLOR;

    public static void registerServerConfig(ForgeConfigSpec.Builder SERVER_BUILDER) {
        SERVER_BUILDER.comment("Settings for the mana system").push("mana");

        SERVER_BUILDER.pop();
    }

    public static void registerClientConfig(ForgeConfigSpec.Builder CLIENT_BUILDER) {
        CLIENT_BUILDER.comment("Settings for the mana system").push("mana");

        MANA_HUD_X = CLIENT_BUILDER
                .comment("X location of the mana hud")
                .defineInRange("manaHudX", 10, -1, Integer.MAX_VALUE);
        MANA_HUD_Y = CLIENT_BUILDER
                .comment("Y location of the mana hud")
                .defineInRange("manaHudY", 10, -1, Integer.MAX_VALUE);
        MANA_HUD_COLOR = CLIENT_BUILDER
                .comment("Color of the mana hud")
                .defineInRange("manaHudColor", 0xffffffff, Integer.MIN_VALUE, Integer.MAX_VALUE);

        CLIENT_BUILDER.pop();
    }
}
