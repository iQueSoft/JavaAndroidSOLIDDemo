package com.kasilov.andrew.solidrobot;

public class NewBattery extends Battery {


    public boolean isIndicatorTurnedOn() {
        return indicatorTurnedOn;
    }
    private boolean indicatorTurnedOn;

    private boolean plugged= false;
    private boolean charged = false;
    public void setPlugged(boolean plugged) {
        this.plugged = plugged;
    }

    public void setCharged(boolean charged) {
        this.charged = charged;
    }

    public boolean isCharged() {
        return charged;
    }

    public boolean isPlugged() {
        return plugged;
    }
    @Override
    void charge() {

    }

    @Override
    protected void turnOnIndicator() {
        super.turnOnIndicator();
    }
}
