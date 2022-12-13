package com.iznaroth.manicmechanics.menu.logistics.job;

import net.minecraftforge.energy.IEnergyStorage;

import java.util.ArrayList;

public class PowerJob {

    IEnergyStorage from;
    ArrayList<IEnergyStorage> to = new ArrayList<>();
    int batchAmount = 500; //RF per Tick, set in config by tube tier.

    public PowerJob(IEnergyStorage from){
        this.from = from;
    }

    public void addReciever(IEnergyStorage rcv){
        to.add(rcv);
    }

    public boolean tryAndExecute(){
        for(IEnergyStorage reciever : to){

            if(!(reciever.getEnergyStored() == reciever.getMaxEnergyStored())) {

                int taken = from.extractEnergy(batchAmount, false);

                if (taken == 0) {
                    return false; //Failed job due to empty buffer.
                }

                int sent = reciever.receiveEnergy(taken, false);

                System.out.print(sent);

                int refund = batchAmount - sent;
                if (refund > 0) {
                    from.receiveEnergy(refund, false); //Get back any wasted power in transfer.
                }
            }
        }
        return true; //NOT FINISHED - return value is for various expectation checks
    }
}
