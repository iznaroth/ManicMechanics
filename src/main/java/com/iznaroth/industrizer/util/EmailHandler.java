package com.iznaroth.industrizer.util;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class EmailHandler {

    public static Email[] active_entries = new Email[5];

    public int page = 0;

    public static boolean isSlotEmpty(int idx){
        return active_entries[idx] == null;
    }

    public static final Email ANON_01 = new Email(new TranslationTextComponent("subject.industrizer.anon01"), new TranslationTextComponent("email.industrizer.anon01"), 0);

    public static class Email{
        public TranslationTextComponent subject;
        public TranslationTextComponent body;

        public ItemStack output;
        public ItemStack blueprint;


        public Email(TranslationTextComponent subject, TranslationTextComponent body, int position){
            this.subject = subject;
            this.body = body;

            active_entries[position] = this;
        }
    }
}
