package com.iznaroth.industrizer.logistics.job;

import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;

public class PowerJob {

    IEnergyStorage from;
    ArrayList<IEnergyStorage> to = new ArrayList<>();
    int batchAmount = 4; //RF per Tick, set in config by tube tier.

    public PowerJob(IEnergyStorage from){
        this.from = from;
    }

    public void addReciever(IEnergyStorage rcv){
        to.add(rcv);
    }

    public boolean tryAndExecute(){
        for(IEnergyStorage reciever : to){
            int sent = reciever.receiveEnergy(batchAmount, false);

            int refund = batchAmount - sent;
            if(refund > 0){
                from.receiveEnergy(refund, false);
            }
        }
        return true; //NOT FINISHED - return value is for various expectation checks
    }
}
