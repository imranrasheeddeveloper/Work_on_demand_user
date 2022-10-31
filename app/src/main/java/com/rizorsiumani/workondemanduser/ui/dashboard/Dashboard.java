package com.rizorsiumani.workondemanduser.ui.dashboard;

import static com.rizorsiumani.workondemanduser.utils.map_utils.GeoCoders.GetProperLocationAddress;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityDashboardBinding;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.ui.post_job.PostJob;
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

        if (binding.bottomNavigation.getTabCount() > 0){
            binding.bottomNavigation.removeAllTabs();
        }
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_home).setId(0));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_booking).setId(1));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_add).setId(2));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_wallet).setId(3));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("").setIcon(R.drawable.ic_profile).setId(4));
        binding.bottomNavigation.setTabGravity(TabLayout.GRAVITY_FILL);

        binding.bottomNavigation.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#00A688"), PorterDuff.Mode.SRC_IN);


       // isLocationPermissionGranted = LocationService.service.requestLocationPermission(App.applicationContext);
        if (!Constants.isLocationPermissionGranted){
            binding.servicesSuspendLayout.setVisibility(View.VISIBLE);
        }else {
            locationHandler();
        }

        binding.turnOnLocationService.setOnClickListener(view -> {
           // if (Constants.isLocationPermissionGranted){
                LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE );
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (statusOfGPS){
                    binding.servicesSuspendLayout.setVisibility(View.GONE);
                }else {
                    Toast.makeText(this, "Enable GPS", Toast.LENGTH_SHORT).show();
                }
            //}else {
            //    LocationService.service.requestLocationPermission(Dashboard.this);
           // }
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

        //clickListeners(options);

        if (getIntent() != null){
            String nav = getIntent().getStringExtra("Navigation");
            if (nav != null) {
                if (nav.equalsIgnoreCase("Booking")) {
                    Dashboard.goToBooking();
                }
            }
        }

        binding.bottomNavigation.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.parseColor("#00A688"), PorterDuff.Mode.SRC_IN);

                switch (tab.getId()) {
                    case 0:
                        mNavController.navigate(R.id.homeFragment, null);
                        break;
                    case 1:
                        mNavController.navigate(R.id.bookingFragment, null);
                        break;
                    case 2:
                        Intent intent = new Intent(Dashboard.this, PostJob.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up,R.anim.slide_down);
//                        NavOptions.Builder builder = new NavOptions.Builder()
//                                .setEnterAnim(R.anim.slide_up)
//                                .setExitAnim(R.anim.stationary)
//                                .setPopEnterAnim(R.anim.slide_down)
//                                .setPopExitAnim(R.anim.stationary);
//
//                        NavOptions options1 = builder.build();
//                        mNavController.navigate(R.id.postJob,null, options1);
                        break;
                    case 3:
                        mNavController.navigate(R.id.walletFragment, null);
                        break;
                    case 4:
                        mNavController.navigate(R.id.profileFragment, null);
                        break;
                    default:
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_IN);

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }


//    private void clickListeners(NavOptions options) {
//        binding.navigation.bookingFragment.setOnClickListener(view -> {
//            changeIconColor( binding.navigation.bookingFragment,
//                    binding.navigation.walletFragment,
//                    binding.navigation.profileFragment,
//                    binding.navigation.homeFragment
//            );
//            binding.navigation.bookingFragment.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
//
//            mNavController.navigate(R.id.bookingFragment,null, options);
//        });
//
//        binding.navigation.walletFragment.setOnClickListener(view -> {
//            changeIconColor( binding.navigation.walletFragment,
//                    binding.navigation.bookingFragment,
//                    binding.navigation.profileFragment,
//                    binding.navigation.homeFragment
//            );
//            mNavController.navigate(R.id.walletFragment,null, options);
//
//        });
//
//        binding.navigation.profileFragment.setOnClickListener(view -> {
//            changeIconColor( binding.navigation.profileFragment,
//                    binding.navigation.walletFragment,
//                    binding.navigation.bookingFragment,
//                    binding.navigation.homeFragment
//            );
//            mNavController.navigate(R.id.profileFragment,null,options);
//
//        });
//
//        binding.navigation.homeFragment.setOnClickListener(view -> {
//            changeIconColor( binding.navigation.homeFragment,
//                    binding.navigation.walletFragment,
//                    binding.navigation.profileFragment,
//                    binding.navigation.bookingFragment
//            );
//            mNavController.navigate(R.id.homeFragment,null, options);
//
//        });
//
//        binding.navigation.postJob.setOnClickListener(view -> {
//            NavOptions.Builder builder = new NavOptions.Builder()
//                    .setLaunchSingleTop(true)
//                    .setEnterAnim(R.anim.slide_up)
//                    .setExitAnim(R.anim.stationary)
//                    .setPopEnterAnim(R.anim.slide_down)
//                    .setPopExitAnim(R.anim.stationary);
//
//            NavOptions options1 = builder.build();
//            mNavController.navigate(R.id.postJob,null, options1);
//
//        });
//
//
//    }

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