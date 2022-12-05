package com.iznaroth.manicmechanics.capability.data;

public class Currency {
    private int mdd;

    public Currency(int mdd) {
        this.mdd = mdd;
    }

    public int getCurrency() {
        return mdd;
    }

    public void setCurrency(int mdd) {
        this.mdd = mdd;
    }
}
