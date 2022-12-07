package com.iznaroth.manicmechanics.util;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public abstract class EmailHandler {

    public static Email[] active_entries = new Email[5];

    public int page = 0;

    public static boolean isSlotEmpty(int idx){
        return active_entries[idx] == null;
    }

    public static final Email ANON_01 = new Email(Component.translatable("subject.manicmechanics.anon01"), Component.translatable("email.manicmechanics.anon01"), 0);

    public static class Email{
        public Component subject;
        public Component body;

        public ItemStack output;
        public ItemStack blueprint;


        public Email(Component subject, Component body, int position){
            this.subject = subject;
            this.body = body;

            active_entries[position] = this;
        }
    }
}
