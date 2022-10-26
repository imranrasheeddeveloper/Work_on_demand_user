package com.rizorsiumani.workondemanduser.ui.dashboard;

import static com.rizorsiumani.workondemanduser.utils.map_utils.GeoCoders.GetProperLocationAddress;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityDashboardBinding;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.ui.splash.SplashActivity;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationService;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.workondemanduser.utils.map_utils.OnLocationUpdateListener;

public class Dashboard extends AppCompatActivity implements OnLocationUpdateListener {

    public static NavController mNavController;
    boolean isHome = false;
    public static NavOptions options;

    public static ActivityDashboardBinding binding;
    boolean isLocationPermissionGranted;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        isLocationPermissionGranted = LocationService.service.requestLocationPermission(App.applicationContext);
        if (!isLocationPermissionGranted){
            binding.servicesSuspendLayout.setVisibility(View.VISIBLE);
        }else {
            locationHandler();
        }

        binding.turnOnLocationService.setOnClickListener(view -> {
            isLocationPermissionGranted =  LocationService.service.requestLocationPermission(Dashboard.this);
            if (isLocationPermissionGranted){
                binding.servicesSuspendLayout.setVisibility(View.GONE);
            }
        });




        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }

        binding.navigation.homeFragment.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));

        NavOptions.Builder builder = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.slide_in_right)
                .setExitAnim(R.anim.slide_out_left)
                .setPopEnterAnim(R.anim.slide_in_left)
                .setPopExitAnim(R.anim.slide_out_right);

        options = builder.build();

        clickListeners(options);

        if (getIntent() != null){
            String nav = getIntent().getStringExtra("Navigation");
            if (nav != null) {
                if (nav.equalsIgnoreCase("Booking")) {
                    Dashboard.goToBooking();
                }
            }
        }

    }


    private void clickListeners(NavOptions options) {
        binding.navigation.bookingFragment.setOnClickListener(view -> {
            changeIconColor( binding.navigation.bookingFragment,
                    binding.navigation.walletFragment,
                    binding.navigation.profileFragment,
                    binding.navigation.homeFragment
            );
            binding.navigation.bookingFragment.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));

            mNavController.navigate(R.id.bookingFragment,null, options);
        });

        binding.navigation.walletFragment.setOnClickListener(view -> {
            changeIconColor( binding.navigation.walletFragment,
                    binding.navigation.bookingFragment,
                    binding.navigation.profileFragment,
                    binding.navigation.homeFragment
            );
            mNavController.navigate(R.id.walletFragment,null, options);

        });

        binding.navigation.profileFragment.setOnClickListener(view -> {
            changeIconColor( binding.navigation.profileFragment,
                    binding.navigation.walletFragment,
                    binding.navigation.bookingFragment,
                    binding.navigation.homeFragment
            );
            mNavController.navigate(R.id.profileFragment,null,options);

        });

        binding.navigation.homeFragment.setOnClickListener(view -> {
            changeIconColor( binding.navigation.homeFragment,
                    binding.navigation.walletFragment,
                    binding.navigation.profileFragment,
                    binding.navigation.bookingFragment
            );
            mNavController.navigate(R.id.homeFragment,null, options);

        });

        binding.navigation.postJob.setOnClickListener(view -> {
            NavOptions.Builder builder = new NavOptions.Builder()
                    .setLaunchSingleTop(true)
                    .setEnterAnim(R.anim.slide_up)
                    .setExitAnim(R.anim.stationary)
                    .setPopEnterAnim(R.anim.slide_down)
                    .setPopExitAnim(R.anim.stationary);

            NavOptions options1 = builder.build();
            mNavController.navigate(R.id.postJob,null, options1);

        });


    }

    public static void changeIconColor(ImageView selectedTab, ImageView unselectedTab1, ImageView unselectedTab2, ImageView unselectedTab3) {
        selectedTab.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
        unselectedTab1.setImageTintList(ColorStateList.valueOf(Color.parseColor("#8D8D8D")));
        unselectedTab2.setImageTintList(ColorStateList.valueOf(Color.parseColor("#8D8D8D")));
        unselectedTab3.setImageTintList(ColorStateList.valueOf(Color.parseColor("#8D8D8D")));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (Constants.isHome){
            finishAffinity();
        }else {
            mNavController.navigate(R.id.homeFragment);
        }
    }
    
    public static void goToWallet(){
        changeIconColor(binding.navigation.walletFragment,
                binding.navigation.bookingFragment,
                binding.navigation.profileFragment,
                binding.navigation.homeFragment
        );
        mNavController.navigate(R.id.walletFragment,null, options);
    }

    public static void goToBooking(){
        changeIconColor( binding.navigation.bookingFragment,
                binding.navigation.walletFragment,
                binding.navigation.profileFragment,
                binding.navigation.homeFragment
        );
        binding.navigation.bookingFragment.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));

        mNavController.navigate(R.id.bookingFragment,null, options);
    }

    private void locationHandler() {
        LocationUpdateService locationUpdateService = new LocationUpdateService();
        locationUpdateService.LocationHandler(Dashboard.this, this);
    }

    @Override
    public void onLocationChange(Location location) {
        Constants.latitude = location.getLatitude();
        Constants.longitude = location.getLongitude();
        String address = GetProperLocationAddress(location.getLatitude(), location.getLongitude(), Dashboard.this);
        TinyDbManager.saveCurrentAddress(address);

    }

    @Override
    public void onError(String error) {

    }
}