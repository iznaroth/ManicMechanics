package com.iznaroth.manicmechanics.util;

import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class EmailHandler {

    public static Email[] active_entries = new Email[5];

    public int page = 0;

    public static boolean isSlotEmpty(int idx){
        return active_entries[idx] == null;
    }

    public static final Email ANON_01 = new Email(new TranslationTextComponent("subject.manicmechanics.anon01"), new TranslationTextComponent("email.manicmechanics.anon01"), 0);

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
