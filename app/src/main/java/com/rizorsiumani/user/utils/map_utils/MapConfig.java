package com.rizorsiumani.user.utils.map_utils;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Coordination;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.utils.Constants;

import java.util.ArrayList;

public class MapConfig {

    com.akexorcist.googledirection.model.Route route;
    ArrayList<LatLng> d = new ArrayList<>();
    DirectionRoute directionRoute;

    public static MapConfig config = new MapConfig();

    public void checkLocationPermission(Context context) {
        LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
        }else {
        }
    }


    public void moveCamera(LatLng latLng, GoogleMap mMap) {
        Log.d(TAG, "moveCamera: moving the camera to, lat: " + latLng.latitude + ", lng: " + latLng.longitude);
//        float DEFAULT_ZOOM = 14.5f;
//        mMap.moveCamera(
//                CameraUpdateFactory.newLatLngZoom(
//                        latLng,
//                        DEFAULT_ZOOM)
//        );
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(DEFAULT_ZOOM), 2000, null);

        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15.0f));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f),20,null);
        CameraPosition build= new CameraPosition.Builder().target(latLng).zoom(15.0f)
                .bearing(15.0f).tilt(0.0f).build();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(build));

        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
    public void moveCameraWithAnimation(LatLng latLng , GoogleMap mMap) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.8f));
    }

    public void mapStyle(GoogleMap googleMap, Context context) {
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.style_json));
            if (!success) {
               // Constants.constant.snackBar(view, "Style parsing failed");
            } else {
               // Constant.constant.snackBar(view, "Style parsing passed");
            }
        } catch (Resources.NotFoundException e) {
          //  Constants.constant.snackBar(view, "Can't find style. Error:" + e);

        }
    }

    public void drawPolyline(Context context, GoogleMap mMap, ArrayList<LatLng> direction) {
        final PolylineOptions polylineOptions = DirectionConverter.createPolyline(
                context,
                direction,
                4,
                ContextCompat.getColor(context, R.color.blue)
        );
        mMap.addPolyline(polylineOptions);
    }

    public void addMarkers(GoogleMap mMap, LatLng position, Context context) {
        mMap.addMarker(new MarkerOptions()
                .position(position).icon(Constants.constant.BitmapFromVector(context, R.drawable.map_stop_position)));
    }
    public void addMarkersWithImage(GoogleMap mMap, LatLng position, Context context , Integer icon) {
//        BitmapDrawable bitmapdraw = (BitmapDrawable)  context.getResources().getDrawable(R.drawable.ic_bike);
//        Bitmap b=bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 64, 64, false);
//        mMap.addMarker(new MarkerOptions()
//                .position(position).icon(Constants.constant.BitmapFromVector(context,icon)));
    }

    public void addMarkersWithImage1(GoogleMap mMap, LatLng position, Context context , BitmapDrawable icon , Integer Height , Integer Width) {
        Bitmap b = icon.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, Width, Height, false);
        mMap.addMarker(new MarkerOptions()
                .position(position).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
    }

    public void getDirections(LatLng origin, LatLng destination, Context context, RouteCallback routeCallback) {
        GoogleDirection.withServerKey(context.getResources().getString(R.string.GOOGLE_MAP_API_KEY))
                .from(origin)
                .to(destination)
                .avoid(AvoidType.HIGHWAYS)
                .avoid(AvoidType.TOLLS)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(@Nullable Direction direction) {
                        if (direction != null) {
                            if (direction.isOK()) {
                                route = direction.getRouteList().get(0);
                                final ArrayList<LatLng> directionPoints = route.getLegList().get(0).getDirectionPoint();
                                d = directionPoints;
                                directionRoute = new DirectionRoute(route, d);
                                routeCallback.onRouteSuccess(directionRoute);
                            } else {
                                Log.e(TAG, "onDirectionSuccess: " + direction.getStatus());
                            }
                        }
                    }
                    @Override
                    public void onDirectionFailure(@NonNull Throwable t) {
                        routeCallback.onRouteFailure(t);
                        Log.e(TAG, "onDirectionFailure: ", t);
                    }
                });
    }


    public void setCameraWithCoordinationBounds(Route route, GoogleMap mMap) {
        final Coordination southwest = route.getBound().getSouthwestCoordination();
        final Coordination northeast = route.getBound().getNortheastCoordination();
        final LatLngBounds latLngBounds = new LatLngBounds(southwest.getCoordination(), northeast.getCoordination());
        mMap.setLatLngBoundsForCameraTarget(latLngBounds);
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 100));
    }

    public void setMyLocationEnabled(GoogleMap googleMap, Context context) {
        if (ActivityCompat.checkSelfPermission(context
                , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context
                , Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    public void AddRippleAnimation(GoogleMap mMap, LatLng currentLocation, Context context) {
//        MapRipple mapRipple = new MapRipple(mMap, currentLocation, context);
//        mapRipple.withNumberOfRipples(5);
//        mapRipple.withFillColor(Color.BLUE);
//        mapRipple.withStrokeColor(Color.WHITE);
//        mapRipple.withStrokewidth(10);      // 10dp
//        mapRipple.withDistance(500);      // 2000 metres radius
//        mapRipple.withRippleDuration(6000);    //12000ms
//        mapRipple.withTransparency(0.9f);
//        mapRipple.startRippleMapAnimation();
    }
}


