package com.rizorsiumani.workondemanduser.ui.add_location;

import static android.content.ContentValues.TAG;

import static com.rizorsiumani.workondemanduser.utils.map_utils.GeoCoders.GetProperLocationAddress;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityAddAddressBinding;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationService;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.workondemanduser.utils.map_utils.MapConfig;
import com.rizorsiumani.workondemanduser.utils.map_utils.OnLocationUpdateListener;

public class AddAddress extends BaseActivity<ActivityAddAddressBinding> implements OnMapReadyCallback, OnLocationUpdateListener {


    private GoogleMap mMap;
    LatLng centre_location;

    @Override
    protected ActivityAddAddressBinding getActivityBinding() {
        return ActivityAddAddressBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        initMap();
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.backSetLocation.setOnClickListener((View.OnClickListener) v -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        activityBinding.confirmLocation.setOnClickListener(view -> {
            String address = activityBinding.locationAddress.getText().toString();
            prefRepository.setString("CURRENT_LOCATION", address);

//            Intent intent = new Intent(AddAddress.this, Dashboard.class);
//            startActivity(intent);
//            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });
    }

    private void initMap() {
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.setLocationMap);
            assert mapFragment != null;
            mapFragment.getMapAsync(this);
        } catch (NullPointerException npe) {
            Log.e(TAG, "onCreate: ", npe);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        MapConfig.config.mapStyle(googleMap, AddAddress.this);
        boolean isLocationPermissionGranted = LocationService.service.requestLocationPermission(AddAddress.this);
        if (isLocationPermissionGranted) {
            LocationUpdateService locationUpdateService = new LocationUpdateService();
            locationUpdateService.LocationHandler(AddAddress.this, this);
        }
    }

    @Override
    public void onLocationChange(Location location) {
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        boolean isCalled = true;
        if (isCalled) {
            MapConfig.config.moveCamera(currentLocation, mMap);
            mMap.setOnCameraIdleListener(() -> {
                activityBinding.mapIcon.setImageResource(R.drawable.map_stop_position);
                centre_location = mMap.getCameraPosition().target;
                setAddress(centre_location);
            });
            mMap.setOnCameraMoveListener(() -> activityBinding.mapIcon.setImageResource(R.drawable.map_moving_position));
        }
    }
    @Override
    public void onError(String error) {
    }

    public void setAddress(LatLng centre) {
        String centerAddress = GetProperLocationAddress(centre.latitude, centre.longitude, AddAddress.this);
        if (!centerAddress.isEmpty()) {
            activityBinding.locationAddress.setText(centerAddress);
        }
    }
}