package com.iznaroth.manicmechanics.blockentity.interfaces;

public interface IHasButtonState {

    public void cycleForward(int which);

    public void cycleBackward(int which);

    public void nonCyclePayload(int which, int payload){
        
    }
}
