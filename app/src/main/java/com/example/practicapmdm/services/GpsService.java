package com.example.practicapmdm.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;

import static android.content.ContentValues.TAG;
import static com.example.practicapmdm.constants.Constants.INTENT_LOCALIZATION_ACTION;
import static com.example.practicapmdm.constants.Constants.LATITUDE;
import static com.example.practicapmdm.constants.Constants.LONGITUDE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class GpsService extends Service implements LocationListener {
    private LocationManager mLocManager = null;
    private Double latitude = 0.0;
    private Double longitude = 0.0;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Intent intent = new Intent(INTENT_LOCALIZATION_ACTION);
        intent.putExtra(LATITUDE, latitude);
        intent.putExtra(LONGITUDE, longitude);
        Log.d(TAG, "GpsService latitude: " + latitude);
        Log.d(TAG, "GpsService longitude: " + longitude);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startLocation();
        return START_STICKY;
    }

    @SuppressLint("MissingPermission")
    private void startLocation() {
        mLocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsProvider = mLocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean networkProvider = mLocManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        if (!gpsProvider) {
            Log.d("d", "Service: provider not enabled");
            Intent callGPSSettingIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            callGPSSettingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(callGPSSettingIntent);
        }
        if (gpsProvider) {
            Log.d("d", "Service: provider enabled");
            mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1, 100, this);
        }
        if (networkProvider) {
            Log.d("d", "Service: network provider enabled");
            mLocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1, 100, this);
        }

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}
