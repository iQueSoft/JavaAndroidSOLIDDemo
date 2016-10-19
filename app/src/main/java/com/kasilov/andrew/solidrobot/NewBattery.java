package com.kasilov.andrew.solidrobot;

import com.kasilov.andrew.solidrobot.interfaces.IBatteryIndicator;
import com.kasilov.andrew.solidrobot.interfaces.IBatteryStatus;

public class NewBattery extends Battery {


    private IBatteryIndicator iBatteryIndicator;
    private int chargeLevel = 0;


    public NewBattery(IBatteryIndicator iBatteryIndicator, IBatteryStatus iBatteryStatus) {
        this.iBatteryIndicator = iBatteryIndicator;
        this.iBatteryStatus = iBatteryStatus;
        this.setCharged(false);
    }

    @Override
    public void charge() {
        this.iBatteryIndicator.turnOffIndicator();
        new BatteryCharging().execute(10);
    }


    @Override
    public int getChargeLevel() {
        return this.chargeLevel;
    }

    @Override
    void notifyBatteriesChargingIsFinished(Integer chargeLevel) {
        this.chargeLevel = chargeLevel;
        this.iBatteryIndicator.turnOnIndicator();
    }
}
