package com.kasilov.andrew.solidrobot;

public abstract class Battery {

    int chargeLevel = 0;

    abstract void charge();

    protected void turnOnIndicator(){}

    protected void turnOffIndicator() {    }


    public abstract void setCharged(boolean b);

    public abstract boolean isPlugged();

    public abstract boolean isCharged();

    public abstract boolean isIndicatorTurnedOn();

    public abstract void setPlugged(boolean b);
}
