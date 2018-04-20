/*
 * Copyright (C) 2018 Muhammad Muzammil Sharif
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.muhammadmuzammilsharif.locationhelper.Location;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class FusedLocationApiService implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

    private Context m_context;

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 6000;
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;

    private GoogleApiClient m_googleApiClient;
    private LocationRequest m_locationRequest;
    private Location m_currentLocation;
    private OnLocationChangeListener locationChangeListener;

    FusedLocationApiService(Context context, OnLocationChangeListener fusedLocationUpdateListener) {
        this.m_context = context;
        this.locationChangeListener = fusedLocationUpdateListener;
        startListeningToUpdates();
    }

    void startListeningToUpdates() {
        buildGoogleApiClient();
    }

    private synchronized void buildGoogleApiClient() {
        if (ContextCompat.checkSelfPermission(m_context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (m_googleApiClient == null) {
            m_googleApiClient = new GoogleApiClient.Builder(m_context).addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this).addApi(LocationServices.API).build();
        }
        createLocationRequest();
    }

    private void createLocationRequest() {
        if (m_locationRequest == null) {
            m_locationRequest = new LocationRequest();
            m_locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
            m_locationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
            m_locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }
        if (!m_googleApiClient.isConnected() && !m_googleApiClient.isConnecting())
            m_googleApiClient.connect();
    }

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(m_context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(m_context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

        }
        LocationServices.FusedLocationApi.requestLocationUpdates(m_googleApiClient, m_locationRequest, this);
    }

    void stopLocationUpdates() {
        if (m_googleApiClient != null && m_googleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(m_googleApiClient, this);
            m_googleApiClient.disconnect();
            m_googleApiClient = null;
            m_locationRequest = null;
            m_currentLocation = null;
        }
    }

    @Override
    @RequiresPermission(allOf = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    public void onConnected(Bundle connectionHint) {
        if (m_currentLocation == null) {
            if (ActivityCompat.checkSelfPermission(m_context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(m_context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            }
            m_currentLocation = LocationServices.FusedLocationApi.getLastLocation(m_googleApiClient);
            if (this.locationChangeListener != null && m_currentLocation != null) {
                this.locationChangeListener.onLocationChanged(m_currentLocation);
            }
        }
        startLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        m_currentLocation = location;
        if (this.locationChangeListener != null) {
            this.locationChangeListener.onLocationChanged(m_currentLocation);
        }
    }

    @Override
    public void onConnectionSuspended(int cause) {
        m_googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult result) {
    }
}
