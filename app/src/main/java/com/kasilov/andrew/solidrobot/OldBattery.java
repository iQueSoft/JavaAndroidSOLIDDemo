package com.kasilov.andrew.solidrobot;

public class OldBattery extends Battery {


    private boolean plugged = false;
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

    public boolean isIndicatorTurnedOn() {
        return indicatorTurnedOn;
    }

    private boolean indicatorTurnedOn;

    @Override
    void charge() {

    }

    @Override
    protected void turnOnIndicator() {
        throw new UnsupportedOperationException("It is not supported with this battery");
    }

    @Override
    protected void turnOffIndicator() {
        throw new UnsupportedOperationException("It is not supported with this battery");
    }
}
