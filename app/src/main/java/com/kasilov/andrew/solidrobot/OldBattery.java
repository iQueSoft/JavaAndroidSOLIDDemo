package com.kasilov.andrew.solidrobot;

import com.kasilov.andrew.solidrobot.interfaces.IBatteryStatus;

public class OldBattery extends Battery {


    private int chargeLevel = 0;


    public OldBattery(IBatteryStatus iBatteryStatus) {
        this.iBatteryStatus = iBatteryStatus;
        this.setCharged(false);
    }

    @Override
    public void charge() {
        new BatteryCharging().execute(10);
    }

    @Override
    public int getChargeLevel() {
        return this.chargeLevel;
    }


    @Override
    void notifyBatteriesChargingIsFinished(Integer chargeLevel) {
        this.chargeLevel = chargeLevel;
    }

}
