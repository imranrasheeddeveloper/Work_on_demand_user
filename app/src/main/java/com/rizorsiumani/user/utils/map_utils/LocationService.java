package com.rizorsiumani.user.utils.map_utils;

import android.Manifest;
import android.content.Context;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

public class LocationService {

    boolean flag = Boolean.parseBoolean(null);
    public static LocationService service = new LocationService();

    public  boolean requestLocationPermission(Context context) {
        Dexter.withContext(context)
                .withPermissions(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        Constants.constant.isLocationPermissionGranted = multiplePermissionsReport.areAllPermissionsGranted();
                        if (Constants.constant.isLocationPermissionGranted) {
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
         return flag;
    }
}
