package com.muhammadmuzammilsharif.demolibrary;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.widget.TextView;

import com.muhammadmuzammilsharif.locationhelper.Location.LocationActivity;
import com.muhammadmuzammilsharif.locationhelper.Location.OnLocationChangeListener;

import java.text.DateFormat;
import java.util.Date;

public class DemoLocationActivityAndFragment extends LocationActivity {
    private TextView textView;
    private OnLocationChangeListener listener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        textView = (TextView) findViewById(R.id.location);

        startLocationService();

        MyFragment mFragment = new MyFragment();

        listener = (OnLocationChangeListener) mFragment;
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_location2, mFragment, "location_fragment_2");
        fragmentTransaction.commit();
    }

    @Override
    protected void showNeededLocationPermissionDialog() {
        //todo: add your logic here if you don't want this app ask for location permission again
        new AlertDialog.Builder(DemoLocationActivityAndFragment.this).
                setCancelable(false).
                setIcon(R.mipmap.ic_launcher).
                setTitle("Permission Require").
                setMessage("Location Permission Require Please Allow This App Location Permission!").
                setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DemoLocationActivityAndFragment.super.openAppPermissionSettingToEnableLocation(BuildConfig.APPLICATION_ID);
                    }
                }).show();
    }

    @Override
    protected void showLocationServiceEnableDialog() {
        //todo: add your logic here if you don't want this app ask for enable location service again
        new AlertDialog.Builder(DemoLocationActivityAndFragment.this).
                setCancelable(false).
                setIcon(R.mipmap.ic_launcher).
                setTitle("Enable Location!").
                setMessage("Location Service Disable Please Enable Location Service!").
                setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DemoLocationActivityAndFragment.super.openLocationServiceSetting();
                    }
                }).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (textView != null) {
            textView.setText("ACTIVITY: \nLocation: \nLat: " +
                    String.valueOf(location.getLatitude()) + " - Lng: " +
                    String.valueOf(location.getLongitude()) + "\n Updated On: " +
                    DateFormat.getDateTimeInstance().format(new Date()));
        }
        if (listener != null) {
            listener.onLocationChanged(location);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopLocationService();
    }
}
