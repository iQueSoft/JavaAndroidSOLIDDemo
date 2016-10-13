package com.kasilov.andrew.solidrobot;

public class Robot {

    private Battery battery;
    public boolean turnedOn;
    private ChargingDevice chargingDevice;

    public void plugInoldTypeBattery(Battery battery) {
    this.battery = battery;
    }


    public Battery getBattery() {
        return battery;
    }


    public void pluginchargingDevice(ChargingDevice chargingDevice) {
        this.chargingDevice = chargingDevice;
    }


    public ChargingDevice getChargingDevice() {
        return chargingDevice;
    }

    public void plugInNewTypeBattery(NewBattery newBattery) {
        this.battery = newBattery;
    }
}
