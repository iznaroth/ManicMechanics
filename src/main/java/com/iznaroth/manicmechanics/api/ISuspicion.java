package com.iznaroth.manicmechanics.api;

public interface ISuspicion {

    double getCurrentSuspicion();

    double setSuspicion(final double val);

    double incrementSuspicion(final double add);

    double decrementSuspicion(final double remove);

}
