package com.kasilov.andrew.solidrobot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kasilov.andrew.solidrobot.interfaces.IErrorState;
import com.kasilov.andrew.solidrobot.interfaces.IViewControl;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener, IViewControl {

    private TextView tv_robot_power;
    private TextView tv_battery_inject_state;
    private TextView tv_battery_charge_state;
    private TextView tv_charging_state;
    private TextView tv_robot_movement_state;
    private ImageView iv_battery_indicator;
    private ToggleButton btn_robot_power;
    private ToggleButton btn_plug_charging_device;
    private Presenter presenter;

    private IErrorState iErrorState = new IErrorState() {
        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        presenter = new Presenter(this, iErrorState);
    }


    private void initializeViews() {
        iv_battery_indicator = (ImageView) findViewById(R.id.imv_battery_indicator);
        tv_robot_power = (TextView) findViewById(R.id.tv_indicator_robot_on);
        tv_battery_inject_state = (TextView) findViewById(R.id.tv_indicator_battery_plugged_in);
        tv_battery_charge_state = (TextView) findViewById(R.id.tv_indicator_battery_discharged);
        tv_charging_state = (TextView) findViewById(R.id.tv_indicator_charging);
        tv_robot_movement_state = (TextView) findViewById(R.id.tv_indicator_moving);
        btn_robot_power = (ToggleButton) findViewById(R.id.btn_robot_power);
        Button btn_new_type_battery_injection = (Button) findViewById(R.id.btn_new_type_battery_injection);
        btn_plug_charging_device = (ToggleButton) findViewById(R.id.btn_charging_device_injection);
        Button btn_old_type_battery_injection = (Button) findViewById(R.id.btn_old_type_battery_injection);
        Button btn_plug_out_battery = (Button) findViewById(R.id.btn_plug_out_battery);
        btn_plug_out_battery.setOnClickListener(this);
        btn_old_type_battery_injection.setOnClickListener(this);
        btn_robot_power.setOnCheckedChangeListener(this);
        btn_new_type_battery_injection.setOnClickListener(this);
        btn_plug_charging_device.setOnCheckedChangeListener(this);
        Button btn_move = (Button) findViewById(R.id.btn_move);
        btn_move.setOnClickListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.btn_robot_power:
                if (isChecked) this.presenter.turnOnRobot();
                else this.presenter.turnOffRobot();
                break;
            case R.id.btn_charging_device_injection:
                if (isChecked) this.presenter.plugInChargingDevice();
                else this.presenter.plugOutChargingDevice();
                break;
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_type_battery_injection:
                this.presenter.plugInNewTypeBattery();
                break;
            case R.id.btn_old_type_battery_injection:
                this.presenter.plugInOldTypeBattery();
                break;
            case R.id.btn_plug_out_battery:
                this.presenter.plugOutBattery();
                break;
            case R.id.btn_move:
                this.presenter.move();
                break;
        }
    }

    @Override
    public void switchOffPowerButton() {
        this.btn_robot_power.setChecked(false);
    }

    @Override
    public void onRobotTurnedOn() {
        this.tv_robot_power.setText("Robot is turned on");
        this.tv_robot_power.setTextColor(this.getColorGreen());
    }

    @Override
    public void onRobotTurnedOff() {
        this.tv_robot_power.setText("Robot is turned off");
        this.tv_robot_power.setTextColor(this.getColorRed());
        switchOffPowerButton();
    }

    @Override
    public void turnOffBatteryIndicator() {
        this.iv_battery_indicator.setImageResource(R.drawable.red_circle);
    }

    @Override
    public void turnOnBatteryIndicator() {
        this.iv_battery_indicator.setImageResource(R.drawable.green_circle);
    }

    @Override
    public void onBatteryChargingStarted() {
        this.tv_battery_charge_state.setText("Battery is charging...");
        this.tv_battery_charge_state.setTextColor(this.getColorYellow());
    }

    @Override
    public void onBatteryCharged() {
        this.tv_battery_charge_state.setText("Battery charged");
        this.tv_battery_charge_state.setTextColor(this.getColorGreen());
    }

    @Override
    public void onBatteryDischarged(int distanceToTravel) {
        this.tv_battery_charge_state.setText("Battery discharged");
        this.tv_battery_charge_state.setTextColor(this.getColorRed());
        Toast.makeText(this, "Battery discharged. " + distanceToTravel + "m left to ride.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChargingDevicePluggedIn() {
        this.tv_charging_state.setText("Charging device is plugged in");
        this.tv_charging_state.setTextColor(getColorGreen());
    }

    @Override
    public void onChargingDevicePluggedOut() {
        this.btn_plug_charging_device.setChecked(false);
        this.tv_charging_state.setText("Charging device is not plugged in");
        this.tv_charging_state.setTextColor(getColorRed());
    }

    @Override
    public void onOldTypeBatteryPluggedIn() {
        this.turnOffBatteryIndicator();
        this.tv_battery_inject_state.setText("Old type battery plugged in");
        this.tv_battery_inject_state.setTextColor(this.getColorGreen());
    }

    @Override
    public void onNewTypeBatteryPluggedIn() {
        this.tv_battery_inject_state.setText("New type battery plugged in");
        this.tv_battery_inject_state.setTextColor(this.getColorGreen());
    }

    @Override
    public void onBatteryPluggedOut() {
        this.turnOffBatteryIndicator();
        this.tv_battery_charge_state.setText("Battery discharged");
        this.tv_battery_charge_state.setTextColor(getColorRed());
        this.tv_battery_inject_state.setText("Battery plugged out");
        this.tv_battery_inject_state.setTextColor(getColorRed());
    }

    @Override
    public void onDestinationReached(int batteryChargeLevel) {
        this.tv_robot_movement_state.setText("Destination is reached");
        this.tv_robot_movement_state.setTextColor(getColorGreen());
        Toast.makeText(this, "Destination is reached. " + batteryChargeLevel + "% left in the battery.", Toast.LENGTH_LONG).show();
    }

    private int getColorGreen() {
        return getResources().getColor(R.color.colorGreen);
    }

    private int getColorRed() {
        return getResources().getColor(R.color.colorRed);
    }

    private int getColorYellow() {
        return getResources().getColor(R.color.colorYellow);
    }

}
