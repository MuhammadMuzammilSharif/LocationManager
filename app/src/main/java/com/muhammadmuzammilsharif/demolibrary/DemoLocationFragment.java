package com.muhammadmuzammilsharif.demolibrary;

import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.muhammadmuzammilsharif.locationhelper.Location.LocationFragment;

import java.text.DateFormat;
import java.util.Date;

public class DemoLocationFragment extends LocationFragment {
    private TextView textView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_demo_location, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        textView = view.findViewById(R.id.location);
        super.onViewCreated(view, savedInstanceState);
        startLocationService();
    }

    @Override
    protected void showNeededLocationPermissionDialog() {
        //todo: add your logic here if you don't want this app ask for location permission again
        new AlertDialog.Builder(getContext()).
                setCancelable(false).
                setIcon(R.mipmap.ic_launcher).
                setTitle("Permission Require").
                setMessage("Location Permission Require Please Allow This App Location Permission!").
                setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DemoLocationFragment.super.openAppPermissionSettingToEnableLocation(BuildConfig.APPLICATION_ID);
                    }
                }).show();
    }

    @Override
    protected void showLocationServiceEnableDialog() {
        //todo: add your logic here if you don't want this app ask for enable location service again
        new AlertDialog.Builder(getContext()).
                setCancelable(false).
                setIcon(R.mipmap.ic_launcher).
                setTitle("Enable Location").
                setMessage("Location Service Disable Please Enable Location Service!").
                setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        DemoLocationFragment.super.openLocationServiceSetting();
                    }
                }).show();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (textView != null)
            textView.setText("FRAGMENT: \nLocation: \nLat: " + String.valueOf(location.getLatitude()) + " - Lng: " + String.valueOf(location.getLongitude()) + "\n Updated On: " + DateFormat.getDateTimeInstance().format(new Date()));
    }

    @Override
    public void onStop() {
        super.onStop();
        stopLocationService();
    }
}