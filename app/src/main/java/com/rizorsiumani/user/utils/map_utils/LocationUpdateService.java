package com.rizorsiumani.user.utils.map_utils;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class LocationUpdateService  {
    private Activity activity;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Location mLastKnownLocation;
    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;
    private OnLocationUpdateListener onLocationUpdateListener;
    private boolean updateStartedInternally = false;

    public void LocationHandler(Activity activity, OnLocationUpdateListener onLocationUpdateListener) {
        this.activity = activity;
        this.onLocationUpdateListener = onLocationUpdateListener;
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        MapConfig.config.checkLocationPermission(activity.getApplicationContext());
        createLocationRequest();
        getDeviceLocation();

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                List<Location> locationList = locationResult.getLocations();
                if (locationList.size() > 0) {
                    //The last location in the list is the newest
                    Location location = locationList.get(locationList.size() - 1);
                    mLastKnownLocation = location;
                    if (onLocationUpdateListener != null) {
                        onLocationUpdateListener.onLocationChange(location);
                        if(updateStartedInternally){
                            stopLocationUpdate();
                        }
                    }
                }
            }
        };
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(activity, task -> {
                if (task.isSuccessful()) {
                    // Set the map's camera position to the current location of the device.
                    mLastKnownLocation = (Location) task.getResult();
                    if (mLastKnownLocation == null) {
                        updateStartedInternally = true;
                        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
                    } else {
                        onLocationUpdateListener.onLocationChange(mLastKnownLocation);
                    }
                } else {
                    onLocationUpdateListener.onError("Can't get Location");
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
            onLocationUpdateListener.onError(e.getMessage());

        }
    }

    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        updateStartedInternally = false;
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private void stopLocationUpdate() {
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback);
    }


    //other new Methods but not using right now..
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);//set the interval in which you want to get locations
        mLocationRequest.setFastestInterval(5000);//if a location is available sooner you can get it (i.e. another app is using the location services)
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }
}//end class
