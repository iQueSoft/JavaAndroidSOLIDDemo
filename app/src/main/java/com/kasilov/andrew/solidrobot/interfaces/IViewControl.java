package com.kasilov.andrew.solidrobot.interfaces;

public interface IViewControl {
    void switchOffPowerButton();

    void onRobotTurnedOn();

    void onRobotTurnedOff();

    void turnOnBatteryIndicator();

    void turnOffBatteryIndicator();

    void onBatteryChargingStarted();

    void onBatteryCharged();

    void onChargingDevicePluggedOut();

    void onNewTypeBatteryPluggedIn();

    void onOldTypeBatteryPluggedIn();

    void onBatteryPluggedOut();

    void onBatteryDischarged(int distanceToTravel);

    void onDestinationReached(int batteryChargeLevel);

    void onChargingDevicePluggedIn();
}
