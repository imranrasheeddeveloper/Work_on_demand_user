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
import android.widget.TextView;
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

        if (binding.bottomNavigation.getSelectedTabPosition() == 0) {
            binding.bottomNavigation.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#00A688"), PorterDuff.Mode.SRC_IN);
        }

        if (checkPermission()) {
            locationHandler();
        } else {
            requestPermission();
        }

        binding.turnOnLocationService.setOnClickListener(view -> {
            checkRunTimePermission();
        });


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }

//        if ()
//        binding.navigation.homeFragment.setImageTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));


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
                        intent.putExtra("status", "add");
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        if (Constants.constant.isHome) {
            if (press == 1) {
                finishAffinity();
            } else {
                Toast.makeText(Dashboard.this, "Press again to exit.", Toast.LENGTH_SHORT).show();
                press++;
            }
        } else {
            binding.bottomNavigation.getTabAt(0).select();
            mNavController.navigate(R.id.homeFragment);
        }
    }

    public static void goToWallet() {
        binding.bottomNavigation.getTabAt(3).select();
        mNavController.navigate(R.id.walletFragment, null, options);
    }

    public static void goToBooking() {
        binding.bottomNavigation.getTabAt(1).select();
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
        TinyDbManager.saveSelectedAddress("");


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