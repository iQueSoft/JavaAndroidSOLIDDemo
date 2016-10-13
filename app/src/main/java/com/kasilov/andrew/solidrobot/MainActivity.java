package com.kasilov.andrew.solidrobot;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {


    private static final String LOG = "ROBOT_LOG";
    Robot robot;
    private NewBattery newBattery;
    private TextView robotIndicator, battery_plugged, battery_discharged, charging, moving, reachedDestination;
    private int alreadyMoved = 170;
    private boolean destinationReached = false;
    ImageView imageView;
    private OldBattery oldBattery;
    private ChargingDevice chargingDevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        robotIndicator = (TextView) findViewById(R.id.tv_indicator_robot_on);
        battery_plugged = (TextView) findViewById(R.id.tv_indicator_battery_plugged_in);
        battery_discharged = (TextView) findViewById(R.id.tv_indicator_battery_discharged);
        charging = (TextView) findViewById(R.id.tv_indicator_charging);
        moving = (TextView) findViewById(R.id.tv_indicator_moving);
        reachedDestination = (TextView) findViewById(R.id.tv_indicator_reached_the_destination);
        ToggleButton btn_robot_power = (ToggleButton) findViewById(R.id.switch_turn_on_off);
        ToggleButton btn_battery_plugged = (ToggleButton) findViewById(R.id.switch_battery_plugged);
        ToggleButton btn_plug_charging_device = (ToggleButton) findViewById(R.id.switch_plug_chrging_device);
        ToggleButton btn_old_type_battery = (ToggleButton) findViewById(R.id.toggleButton);
        btn_old_type_battery.setOnCheckedChangeListener(this);
        btn_robot_power.setOnCheckedChangeListener(this);
        btn_battery_plugged.setOnCheckedChangeListener(this);
        btn_plug_charging_device.setOnCheckedChangeListener(this);
        robot = new Robot();
        chargingDevice = new ChargingDevice();
        newBattery = new NewBattery();
        oldBattery = new OldBattery();
        (findViewById(R.id.btn_move)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (robot.turnedOn) {
                    if (!robot.getChargingDevice().isPluggedIn()) {
                        robot.getBattery().setCharged(false);
                        battery_discharged.setText("Battery discharged");
                        battery_discharged.setTextColor(getResources().getColor(R.color.colorRed));
                        imageView.setImageResource(R.drawable.red_circle);
                        if (robot.getBattery().isIndicatorTurnedOn()) {
                            imageView.setImageResource(R.drawable.red_circle);
                            try {
                                robot.getBattery().turnOffIndicator();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        alreadyMoved -= robot.getBattery().chargeLevel;
                        if (alreadyMoved < 0) {
                            destinationReached = true;
                        }else
                        {
                            Toast.makeText(MainActivity.this, "Insert another battery", Toast.LENGTH_LONG).show();
                        }
                        if (destinationReached) {
                            reachedDestination.setText("Destination reached");
                            reachedDestination.setTextColor(getResources().getColor(R.color.colorGreen));
                            Toast.makeText(getApplicationContext(), "Destination reached. Battery level " + Math.abs(alreadyMoved), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Charging device is plugged in", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Robot is turned off", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        switch (compoundButton.getId()) {
            case R.id.switch_turn_on_off:
                if (robot.getBattery().isPlugged()) {
                    if (robot.getBattery().isCharged()) {
                        if (isChecked) {
                            robot.turnedOn = true;
                            robotIndicator.setText("Robot turned on");
                            robotIndicator.setTextColor(getResources().getColor(R.color.colorGreen));
                        } else {
                            robot.turnedOn = false;
                            robotIndicator.setText("Robot turned off");
                            robotIndicator.setTextColor(getResources().getColor(R.color.colorRed));
                        }
                    } else {
                        compoundButton.setChecked(false);
                        Toast.makeText(this, "Battery discharged", Toast.LENGTH_LONG).show();
                    }

                } else {
                    compoundButton.setChecked(false);
                    Toast.makeText(this, "Battery is not plugged in", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.switch_plug_chrging_device:
                if (robot.getBattery().isPlugged()) {
                    if (isChecked) {
                        robot.pluginchargingDevice(chargingDevice);
                        robot.getChargingDevice().setPLuggedIn(true);
                        charging.setText("Charging");
                        charging.setTextColor(getResources().getColor(R.color.colorGreen));
                        robot.getBattery().setCharged(true);
                        try {
                            charging.setText("Charging");
                            Thread.sleep(4000);
                            robot.getBattery().chargeLevel = 100;
                            battery_discharged.setText("Battery is charged");
                            battery_discharged.setTextColor(getResources().getColor(R.color.colorGreen));
                            if (!robot.getBattery().isIndicatorTurnedOn()) {
                                imageView.setImageResource(R.drawable.green_circle);
                                try {
                                    robot.getBattery().turnOnIndicator();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    } else {
                        robot.getChargingDevice().setPLuggedIn(false);
                        charging.setText("Not charging");
                        charging.setTextColor(getResources().getColor(R.color.colorRed));
                    }
                } else {
                    compoundButton.setChecked(false);
                    Toast.makeText(this, "Battery is plugged out", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.switch_battery_plugged:
                if (isChecked) {
                    robot.plugInNewTypeBattery(newBattery);
                    battery_plugged.setText("New type battery plugged in");
                    battery_plugged.setTextColor(getResources().getColor(R.color.colorGreen));
                    robot.getBattery().setPlugged(true);
                    if (robot.getBattery().isCharged()) {
                        battery_discharged.setText("New type battery charged");
                        battery_discharged.setTextColor(getResources().getColor(R.color.colorGreen));
                        robot.getBattery().setCharged(true);
                    } else {
                        battery_discharged.setText("New type battery discharged");
                        battery_discharged.setTextColor(getResources().getColor(R.color.colorRed));
                        robot.getBattery().setCharged(false);
                    }
                } else {
                    battery_plugged.setText("New type battery plugged out");
                    battery_plugged.setTextColor(getResources().getColor(R.color.colorRed));
                    robot.getBattery().setPlugged(false);
                }
                break;
            case R.id.toggleButton:
                if (isChecked) {
                    robot.plugInoldTypeBattery(oldBattery);
                    battery_plugged.setText("Old type battery plugged in");
                    battery_plugged.setTextColor(getResources().getColor(R.color.colorGreen));
                    robot.getBattery().setPlugged(true);
                    if (robot.getBattery().isCharged()) {
                        battery_discharged.setText("Old type battery charged");
                        battery_discharged.setTextColor(getResources().getColor(R.color.colorGreen));
                        robot.getBattery().setCharged(true);
                    } else {
                        battery_discharged.setText("Old type battery discharged");
                        battery_discharged.setTextColor(getResources().getColor(R.color.colorRed));
                        robot.getBattery().setCharged(false);
                    }
                } else {
                    battery_plugged.setText("Old type battery plugged out");
                    battery_plugged.setTextColor(getResources().getColor(R.color.colorRed));
                    robot.getBattery().setPlugged(false);
                }
                break;
        }
    }

}
