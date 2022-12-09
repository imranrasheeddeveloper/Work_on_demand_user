package com.rizorsiumani.workondemanduser.ui.dashboard;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.rizorsiumani.workondemanduser.utils.map_utils.GeoCoders.GetProperLocationAddress;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityDashboardBinding;
import com.rizorsiumani.workondemanduser.ui.post_job.PostJob;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.workondemanduser.utils.map_utils.OnLocationUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity implements OnLocationUpdateListener {

    public static NavController mNavController;
    boolean isHome = false;
    public static NavOptions options;

    public static ActivityDashboardBinding binding;
    boolean isLocationPermissionGranted;
    int press = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (binding.bottomNavigation.getTabCount() > 0) {
            binding.bottomNavigation.removeAllTabs();
        }
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("Home").setIcon(R.drawable.ic_home).setId(0));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("Bookings").setIcon(R.drawable.ic_booking).setId(1));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("Post").setIcon(R.drawable.ic_add).setId(2));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("Wallet").setIcon(R.drawable.ic_wallet).setId(3));
        binding.bottomNavigation.addTab(binding.bottomNavigation.newTab().setText("Profile").setIcon(R.drawable.ic_profile).setId(4));
        binding.bottomNavigation.setTabGravity(TabLayout.GRAVITY_FILL);

        binding.bottomNavigation.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#00A688"), PorterDuff.Mode.SRC_IN);


        if (checkPermission()) {
            locationHandler();
        } else {
            requestPermission();
        }

//        isLocationPermissionGranted = LocationService.service.requestLocationPermission(Dashboard.this);
//        if (!Constants.constant.isLocationPermissionGranted){
//            binding.servicesSuspendLayout.setVisibility(View.VISIBLE);
//        }else {
//            locationHandler();
//        }

        binding.turnOnLocationService.setOnClickListener(view -> {
            checkRunTimePermission();
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

        if (getIntent() != null) {
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
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
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

        //checkAndRequestPermissions();


    }


    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, 001);
    }

    private void checkAndRequestPermissions() {
        int WExtstorePermission = ContextCompat.checkSelfPermission(Dashboard.this,
                Manifest.permission.READ_EXTERNAL_STORAGE);

        List<String> listPermissionsNeeded = new ArrayList<>();
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded
                    .add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(Dashboard.this, listPermissionsNeeded
                            .toArray(new String[listPermissionsNeeded.size()]),
                    11);
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void checkRunTimePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(Dashboard.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                    ActivityCompat.checkSelfPermission(Dashboard.this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                Constants.constant.isLocationPermissionGranted = true;
                locationHandler();

                LocationManager manager = (LocationManager) getSystemService(Dashboard.this.LOCATION_SERVICE);
                boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (statusOfGPS) {
                    Constants.constant.isLocationPermissionGranted = true;
                    binding.servicesSuspendLayout.setVisibility(View.GONE);
                } else {
                    Toast.makeText(this, "Enable GPS", Toast.LENGTH_SHORT).show();
                }

            } else {
                requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION},
                        10);
            }
        } else {
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 001:
                if (grantResults.length > 0) {

                    boolean locationAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationAccepted1 = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (locationAccepted && locationAccepted1) {
                        locationHandler();
                    } else {
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, ACCESS_FINE_LOCATION)) {
                            // If User Checked 'Don't Show Again' checkbox for runtime permission, then navigate user to Settings
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Dashboard.this);
                            dialog.setTitle("Permission Required");
                            dialog.setCancelable(false);
                            dialog.setMessage("You have to Allow permission to access user location");
                            dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",
                                            Dashboard.this.getPackageName(), null));
                                    //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivityForResult(i, 001);

                                }
                            });
                            AlertDialog alertDialog = dialog.create();
                            alertDialog.show();
                        }
                    }
                }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(Dashboard.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if (requestCode == 001) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Constants.constant.isLocationPermissionGranted = true;
//                locationHandler();
//
//            } else {
//                if (!ActivityCompat.shouldShowRequestPermissionRationale(Dashboard.this, ACCESS_FINE_LOCATION)) {
//                    // If User Checked 'Don't Show Again' checkbox for runtime permission, then navigate user to Settings
//                    AlertDialog.Builder dialog = new AlertDialog.Builder(Dashboard.this);
//                    dialog.setTitle("Permission Required");
//                    dialog.setCancelable(false);
//                    dialog.setMessage("You have to Allow permission to access user location");
//                    dialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",
//                                    Dashboard.this.getPackageName(), null));
//                            //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                            startActivityForResult(i, 1001);
//
//                        }
//                    });
//                    AlertDialog alertDialog = dialog.create();
//                    alertDialog.show();
//                }
//                //code for deny
//            }
//        }
//        if (requestCode == 11) {
//            if (ContextCompat.checkSelfPermission(Dashboard.this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(getApplicationContext(),
//                        "FlagUp Requires Access to Your Storage.",
//                        Toast.LENGTH_SHORT).show();
//            } else {
//            }
//        }
//    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        switch (requestCode) {
            case 1001:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(Dashboard.this, ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                            ActivityCompat.checkSelfPermission(Dashboard.this, ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                            || ActivityCompat.checkSelfPermission(Dashboard.this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                        locationHandler();
                    } else {
                        requestPermissions(new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_BACKGROUND_LOCATION}, 10);
                    }
                }
                break;
            default:
                break;
        }
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

        if (Constants.constant.isHome) {
            if (press == 1) {
                finishAffinity();
            } else {
                press++;
            }
        } else {
            mNavController.navigate(R.id.homeFragment);
        }
    }

    public static void goToWallet() {
        changeIconColor(binding.navigation.walletFragment,
                binding.navigation.bookingFragment,
                binding.navigation.profileFragment,
                binding.navigation.homeFragment
        );
        mNavController.navigate(R.id.walletFragment, null, options);
    }

    public static void goToBooking() {
        binding.bottomNavigation.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#8D8D8D"), PorterDuff.Mode.SRC_IN);
        binding.bottomNavigation.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#00A688"), PorterDuff.Mode.SRC_IN);
        mNavController.navigate(R.id.bookingFragment, null);
    }

    private void locationHandler() {
        LocationUpdateService locationUpdateService = new LocationUpdateService();
        locationUpdateService.LocationHandler(Dashboard.this, this);
    }

    @Override
    public void onLocationChange(Location location) {
        Constants.constant.latitude = location.getLatitude();
        Constants.constant.longitude = location.getLongitude();
        String address = GetProperLocationAddress(location.getLatitude(), location.getLongitude(), Dashboard.this);
        TinyDbManager.saveCurrentAddress(address);

    }

    @Override
    public void onError(String error) {

    }

    public static void hideTabs(boolean status) {
        if (status) {
            binding.bottomNavigation.setVisibility(View.GONE);
        } else {
            binding.bottomNavigation.setVisibility(View.VISIBLE);
        }
    }

}