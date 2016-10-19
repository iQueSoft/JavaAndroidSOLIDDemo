package com.kasilov.andrew.solidrobot;

import android.os.AsyncTask;

import com.kasilov.andrew.solidrobot.interfaces.IBatteryCharging;
import com.kasilov.andrew.solidrobot.interfaces.IBatteryStatus;
import com.kasilov.andrew.solidrobot.utils.LogUtil;

import java.util.concurrent.TimeUnit;

abstract class Battery implements IBatteryCharging, IBatteryStatus {

    protected IBatteryStatus iBatteryStatus;
    protected BatteryCharging batteryCharging;

    private boolean isChargeable = true;
    private boolean charged = false;

    Battery() {
        this.batteryCharging = new BatteryCharging();
    }

    @Override
    public void onBatteryCharged() {
        this.setCharged(true);
        this.setChargeable(false);
        this.iBatteryStatus.onBatteryCharged();
    }

    public abstract int getChargeLevel();

    public boolean isChargeable() {
        return isChargeable;
    }

    private void setChargeable(boolean chargeable) {
        isChargeable = chargeable;
    }

    public boolean isCharged() {
        return charged;
    }

    protected void setCharged(boolean charged) {
        this.charged = charged;
    }

    abstract void notifyBatteriesChargingIsFinished(Integer chargeLevel);

    protected class BatteryCharging extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... integers) {
            LogUtil.makeLog("Charging started");
            int chargeLevel = 0;
            while (chargeLevel < 100) {
                chargeLevel += integers[0];
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return chargeLevel;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Battery.this.notifyBatteriesChargingIsFinished(integer);
            Battery.this.onBatteryCharged();
        }
    }
}
