package com.rizorsiumani.user.ui.splash;

import static com.rizorsiumani.user.utils.map_utils.GeoCoders.GetProperLocationAddress;

import android.location.Location;
import android.os.Handler;

import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivitySplashBinding;
import com.rizorsiumani.user.ui.dashboard.Dashboard;
import com.rizorsiumani.user.ui.walkthrough.OnboardingActivity;
import com.rizorsiumani.user.ui.welcome_user.WelcomeUser;
import com.rizorsiumani.user.utils.ActivityUtil;
import com.rizorsiumani.user.utils.Constants;
import com.rizorsiumani.user.utils.map_utils.LocationService;
import com.rizorsiumani.user.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.user.utils.map_utils.OnLocationUpdateListener;

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

        isLocationPermissionGranted = LocationService.service.requestLocationPermission(SplashActivity.this);
        if (isLocationPermissionGranted) {
            locationHandler();
        } else {

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

    }

    @Override
    protected void onResume() {
        super.onResume();
//        ActivityUtil.gotoPage(SplashActivity.this, Dashboard.class);
//        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                ActivityUtil.gotoPage(SplashActivity.this, OnboardingActivity.class);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            } else {
                ActivityUtil.gotoPage(SplashActivity.this, WelcomeUser.class);
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