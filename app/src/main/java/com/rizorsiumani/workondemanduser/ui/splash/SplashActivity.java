package com.rizorsiumani.workondemanduser.ui.splash;

import static android.view.WindowManager.LayoutParams.*;
import static com.rizorsiumani.workondemanduser.utils.map_utils.GeoCoders.GetProperLocationAddress;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;

import com.google.android.gms.maps.GoogleMap;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDB;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivitySplashBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.login.Login;
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

        hideCartButton();

        new Handler().postDelayed(() -> {
            String token = prefRepository.getString("token");
            if (token.equals("Bearer ") || token.equals("nil")) {
                boolean firstVisit = TinyDbManager.getVisit();
                if (firstVisit) {
                    ActivityUtil.gotoPage(SplashActivity.this, WelcomeUser.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                } else {
                    ActivityUtil.gotoPage(SplashActivity.this, OnboardingActivity.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            } else {
                ActivityUtil.gotoPage(SplashActivity.this, Dashboard.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        }, 3000);

    }

    @Override
    protected void onResume() {
        super.onResume();

//        isLocationPermissionGranted = LocationService.service.requestLocationPermission(SplashActivity.this);
//        if (isLocationPermissionGranted) {
//            locationHandler();
//        } else {
//            isLocationPermissionGranted = LocationService.service.requestLocationPermission(SplashActivity.this);
//            new Handler().postDelayed(() -> {
//                String token = prefRepository.getString("token");
//                if (token.equals("Bearer ") || token.equals("nil")) {
//                    boolean firstVisit = TinyDbManager.getVisit();
//                    if (firstVisit) {
//                        ActivityUtil.gotoPage(SplashActivity.this, WelcomeUser.class);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    } else {
//                        ActivityUtil.gotoPage(SplashActivity.this, OnboardingActivity.class);
//                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                    }
//                } else {
//                    ActivityUtil.gotoPage(SplashActivity.this, Dashboard.class);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                }
//            }, 3000);
//
//        }

    }

    private void locationHandler() {
        LocationUpdateService locationUpdateService = new LocationUpdateService();
        locationUpdateService.LocationHandler(SplashActivity.this, this);
    }

    @Override
    public void onLocationChange(Location location) {
        Constants.constant.latitude = location.getLatitude();
        Constants.constant.longitude = location.getLongitude();
        String address = GetProperLocationAddress(location.getLatitude(), location.getLongitude(), SplashActivity.this);
        TinyDbManager.saveCurrentAddress(address);
        String token = prefRepository.getString("token");
        if (token.equals("Bearer ") || token.equals("nil")) {

            boolean firstVisit = TinyDbManager.getVisit();
            if (firstVisit) {
                ActivityUtil.gotoPage(SplashActivity.this, WelcomeUser.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                ActivityUtil.gotoPage(SplashActivity.this, OnboardingActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        } else {
            ActivityUtil.gotoPage(SplashActivity.this, Dashboard.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }

    @Override
    public void onError(String error) {

    }
}