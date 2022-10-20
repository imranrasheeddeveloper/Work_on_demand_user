package com.rizorsiumani.workondemanduser.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.gms.maps.GoogleMap;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivitySplashBinding;
import com.rizorsiumani.workondemanduser.ui.walkthrough.OnboardingActivity;
import com.rizorsiumani.workondemanduser.ui.welcome_user.WelcomeUser;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationService;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.workondemanduser.utils.map_utils.OnLocationUpdateListener;

import java.util.List;

public class SplashActivity extends BaseActivity<ActivitySplashBinding>
        implements OnLocationUpdateListener {

    boolean isLocationPermissionGranted;

    @Override
    protected ActivitySplashBinding getActivityBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(() -> {
            ActivityUtil.gotoPage(SplashActivity.this, OnboardingActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }, 3000);

        if (isLocationPermissionGranted) {
            locationHandler();
        } else {
            LocationService.service.requestLocationPermission(SplashActivity.this);
        }
    }


    private void locationHandler() {
        LocationUpdateService locationUpdateService = new LocationUpdateService();
        locationUpdateService.LocationHandler(SplashActivity.this, this);
    }

    @Override
    public void onLocationChange(Location location) {
        Constants.latitude = location.getLatitude();
        Constants.longitude = location.getLongitude();

    }

    @Override
    public void onError(String error) {

    }
}