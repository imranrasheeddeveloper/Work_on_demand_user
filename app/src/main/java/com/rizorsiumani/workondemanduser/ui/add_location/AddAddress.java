package com.rizorsiumani.workondemanduser.ui.add_location;

import static android.content.ContentValues.TAG;
import static com.rizorsiumani.workondemanduser.utils.map_utils.GeoCoders.GetProperLocationAddress;

import android.location.Location;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityAddAddressBinding;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationService;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.workondemanduser.utils.map_utils.MapConfig;
import com.rizorsiumani.workondemanduser.utils.map_utils.OnLocationUpdateListener;

public class AddAddress extends BaseActivity<ActivityAddAddressBinding> implements OnMapReadyCallback, OnLocationUpdateListener {


    private GoogleMap mMap;
    LatLng centre_location;
    double latitude = 0.0;
    double longitude = 0.0;
    String address;
    String title = "other";
    private AddressViewModel viewModel;
    int addressID;

    @Override
    protected ActivityAddAddressBinding getActivityBinding() {
        return ActivityAddAddressBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getIntent() != null) {
            title = getIntent().getStringExtra("address_title");
            addressID = getIntent().getIntExtra("address_id", 0);
        }

        viewModel = new ViewModelProvider(AddAddress.this).get(AddressViewModel.class);
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

            if (latitude == 0.0 || longitude == 0.0) {
                showSnackBarShort("Select Location First");
            } else {
                if (addressID != 0) {
                    updateAddress();
                } else {
                    saveAddress();
                }
            }
        });
    }

    private void updateAddress() {
        String token = prefRepository.getString("token");
        JsonObject object = new JsonObject();
        object.addProperty("title", title);
        object.addProperty("latitude", latitude);
        object.addProperty("longiutude", longitude);
        object.addProperty("address", address);
        object.addProperty("address_id", addressID);


        viewModel.update(token, object);
        viewModel._update.observe(AddAddress.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().isSuccess()) {
                    hideLoading();

                    onBackPressed();
                    finish();
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                }
            }
        });
    }

    private void saveAddress() {
        String token = prefRepository.getString("token");
        JsonObject object = new JsonObject();
        object.addProperty("title", title);
        object.addProperty("latitude", latitude);
        object.addProperty("longitude", longitude);
        object.addProperty("address", address);


        viewModel.save(token, object);
        viewModel._address.observe(AddAddress.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    onBackPressed();
                    finish();
                    overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);


                }
            }
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
                latitude = location.getLatitude();
                longitude = location.getLongitude();
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
            address = centerAddress;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._address.removeObservers(this);
        viewModel._update.removeObservers(this);

        viewModel = null;
    }
}