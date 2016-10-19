package com.kasilov.andrew.solidrobot;

import com.kasilov.andrew.solidrobot.interfaces.IBatteryIndicator;
import com.kasilov.andrew.solidrobot.interfaces.IBatteryStatus;
import com.kasilov.andrew.solidrobot.interfaces.IErrorState;
import com.kasilov.andrew.solidrobot.interfaces.IRobotMovement;
import com.kasilov.andrew.solidrobot.interfaces.IViewControl;

public class Presenter {

    private final NewBattery newBattery;
    private final OldBattery oldBattery;
    private final IViewControl viewControl;
    private final IErrorState iErrorState;
    private final ChargingDevice chargingDevice;
    private Robot robot;

    private IBatteryIndicator iBatteryIndicator = new IBatteryIndicator() {
        @Override
        public void turnOnIndicator() {
            Presenter.this.viewControl.turnOnBatteryIndicator();
        }

        @Override
        public void turnOffIndicator() {
            Presenter.this.viewControl.turnOffBatteryIndicator();

        }
    };

    private IBatteryStatus iBatteryStatus = new IBatteryStatus() {
        @Override
        public void onBatteryCharged() {
            Presenter.this.viewControl.onBatteryCharged();
        }
    };
    private IRobotMovement iRobotMovement = new IRobotMovement() {
        @Override
        public void onDestinationReached(int batteryChargeLevel) {
            Presenter.this.viewControl.onDestinationReached(batteryChargeLevel);
        }

        @Override
        public void onBatteryDischarged(int distanceToTravel) {
            Presenter.this.viewControl.onBatteryDischarged(distanceToTravel);
            Presenter.this.viewControl.onRobotTurnedOff();
        }
    };

    public Presenter(IViewControl viewControl, IErrorState iErrorState) {
        this.viewControl = viewControl;
        this.iErrorState = iErrorState;
        this.newBattery = new NewBattery(iBatteryIndicator, iBatteryStatus);
        this.oldBattery = new OldBattery(iBatteryStatus);
        this.robot = new Robot(iRobotMovement);
        this.chargingDevice = new ChargingDevice();
    }

    public void turnOnRobot() {
        if (!this.robot.isTurnedOn()) {
            try {
                this.robot.turnOn();
                this.viewControl.onRobotTurnedOn();
            } catch (NullPointerException | IllegalStateException e) {
                this.viewControl.switchOffPowerButton();
                this.iErrorState.onError(e.getLocalizedMessage());
            }
        }
    }

    public void turnOffRobot() {
        this.robot.turnOff();
        this.viewControl.onRobotTurnedOff();
    }

    public void plugInNewTypeBattery() {
        this.robot.plugInBattery(this.newBattery);
        this.viewControl.onNewTypeBatteryPluggedIn();
    }


    public void plugInOldTypeBattery() {
        this.robot.plugInBattery(this.oldBattery);
        this.viewControl.onOldTypeBatteryPluggedIn();
    }

    public void plugOutBattery() {
        this.robot.plugOutBattery();
        this.robot.turnOff();
        this.viewControl.onRobotTurnedOff();
        this.viewControl.onBatteryPluggedOut();
    }

    public void plugInChargingDevice() {
        this.robot.plugInChargingDevice(this.chargingDevice);
        this.viewControl.onChargingDevicePluggedIn();
        this.chargeBattery();
    }


    public void plugOutChargingDevice() {
        this.robot.plugOutChargingDevice();
        this.viewControl.onChargingDevicePluggedOut();
    }

    private void chargeBattery() {
        if (this.robot.getBattery() != null) {
            this.viewControl.onBatteryChargingStarted();
            this.robot.getBattery().charge();
        } else {
            this.iErrorState.onError("Nothing to charge! Plug in the battery.");
            this.viewControl.onChargingDevicePluggedOut();
        }
    }

    public void move() {
        if (!this.robot.isTurnedOn()) {
            this.iErrorState.onError("Robot is turned off. Turn on the robot.");
            return;
        }
        if (this.robot.isChargingDevicePluggedIn()) {
            this.iErrorState.onError("Charging device is plugged in. Plug out");
            return;
        }
        this.robot.move();
    }
}
