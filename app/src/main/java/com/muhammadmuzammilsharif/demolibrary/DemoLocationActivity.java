package com.muhammadmuzammilsharif.demolibrary;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.muhammadmuzammilsharif.locationhelper.Location.LocationAppCompatActivity;

import java.text.DateFormat;
import java.util.Date;


public class DemoLocationActivity extends LocationAppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.location);
        startLocationService();
    }

    @Override
    protected void showNeededLocationPermissionDialog() {
        //todo: add your logic here if you don't want this app ask for location permission again
        new AlertDialog.Builder(DemoLocationActivity.this).
                setCancelable(false).
                setIcon(R.mipmap.ic_launcher).
                setTitle("Permission Require").
                setMessage("Location Permission Require Please Allow This App Location Permission!").
                setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DemoLocationActivity.super.openAppPermissionSettingToEnableLocation(BuildConfig.APPLICATION_ID);
                    }
                }).show();
    }

    @Override
    protected void showLocationServiceEnableDialog() {
        //todo: add your logic here if you don't want this app ask for enable location service again
        new AlertDialog.Builder(DemoLocationActivity.this).
                setCancelable(false).
                setIcon(R.mipmap.ic_launcher).
                setTitle("Enable Location").
                setMessage("Location Service Disable Please Enable Location Service!").
                setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DemoLocationActivity.super.openLocationServiceSetting();
                    }
                }).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (textView != null)
            textView.setText("Activity: Location: \nLat: " + String.valueOf(location.getLatitude()) + " - Lng: " + String.valueOf(location.getLongitude()) + "\n Updated On: " + DateFormat.getDateTimeInstance().format(new Date()));
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationService();
    }
}
