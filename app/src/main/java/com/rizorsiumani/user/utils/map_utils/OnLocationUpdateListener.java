package com.rizorsiumani.user.utils.map_utils;

import android.location.Location;

public interface OnLocationUpdateListener {
    void onLocationChange(Location location);
    void onError(String error);
}
