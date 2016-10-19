package com.kasilov.andrew.solidrobot.interfaces;

public interface IRobotMovement {
    void onBatteryDischarged(int distanceToTravel);

    void onDestinationReached(int batteryChargeLevel);
}
