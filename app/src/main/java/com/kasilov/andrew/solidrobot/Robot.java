package com.kasilov.andrew.solidrobot;

import android.os.AsyncTask;

import com.kasilov.andrew.solidrobot.interfaces.IRobotMovement;

public class Robot {

    private final IRobotMovement iRobotMovement;
    private Battery battery;
    private boolean turnedOn;
    private ChargingDevice chargingDevice;
    private int distanceToTravel = 27;
    private int batteryChargeLevel;

    public Robot(IRobotMovement iRobotMovement) {
        this.iRobotMovement = iRobotMovement;
    }

    public void turnOn() {
        if (this.battery == null) {
            throw new NullPointerException("Battery is missed. Plug in the battery first.");
        }

        if (!this.battery.isCharged()) {
            throw new IllegalStateException("Battery is not charged. Connect charging device to charge the battery.");
        }

        this.turnedOn = true;
    }

    public void turnOff() {
        this.turnedOn = false;
    }

    public boolean isTurnedOn() {
        return turnedOn;
    }

    public void plugInBattery(Battery battery) {
        this.battery = battery;
    }

    public void plugOutBattery() {
        this.battery = null;
    }

    public void plugInChargingDevice(ChargingDevice chargingDevice) {
        this.chargingDevice = chargingDevice;
    }

    public void plugOutChargingDevice() {
        this.chargingDevice = null;
    }

    public Battery getBattery() {
        return battery;
    }

    public void move() {
        new RobotMovement().execute(this.distanceToTravel, this.battery.getChargeLevel());
    }


    private void onMovementFinished() {
        if (batteryChargeLevel == 0) {
            this.iRobotMovement.onBatteryDischarged(distanceToTravel);
        } else if (distanceToTravel == 0) {
            this.iRobotMovement.onDestinationReached(batteryChargeLevel);
        }
    }

    boolean isChargingDevicePluggedIn() {
        return this.chargingDevice != null;
    }

    private class RobotMovement extends AsyncTask<Integer, Void, Integer> {
        @Override
        protected Integer doInBackground(Integer... integers) {
            int distanceToTravel = integers[0];
            int chargeLevel = integers[1];
            while (distanceToTravel > 0 && chargeLevel > 0) {
                distanceToTravel--;
                chargeLevel = chargeLevel - 5;
            }
            Robot.this.distanceToTravel = distanceToTravel;
            Robot.this.batteryChargeLevel = chargeLevel;
            return integers[0];
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Robot.this.onMovementFinished();
        }
    }

}
