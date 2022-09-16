package com.rizorsiumani.workondemanduser.ui.service_providers.sp_map;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentServiceProviderMapsBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ServiceProviderProfile;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationService;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.workondemanduser.utils.map_utils.MapConfig;
import com.rizorsiumani.workondemanduser.utils.map_utils.OnLocationUpdateListener;

import java.util.ArrayList;
import java.util.List;


public class ServiceProviderMaps extends BaseFragment<FragmentServiceProviderMapsBinding> implements OnMapReadyCallback,
        OnLocationUpdateListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markers;
    SpMapAdapter adapter;
    private Marker selectedMarker = null;
    LinearLayoutManager layoutManager;

    List<LatLng> names;


    @Override
    protected FragmentServiceProviderMapsBinding getFragmentBinding() {
        return FragmentServiceProviderMapsBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        markers = new ArrayList<>();
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        names = new ArrayList<>();
        initMap();
        clickListeners();
        buildRv();


        fragmentBinding.markersLocationList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (layoutManager.findFirstVisibleItemPosition() > 0) {

                    try {

                    final Marker marker = markers.get(layoutManager.findFirstVisibleItemPosition());
                    final LatLng markerPosition = marker.getPosition();
                    for (int i = 0; i < names.size()-1; i++) {
                        if (markerPosition.latitude == names.get(i).longitude && markerPosition.longitude == names.get(i).longitude) {
                        }
                    }
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(markerPosition).zoom(12).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    Log.d("Scrolling Right", "SCROLL");

                    }catch (NullPointerException e){
                        Log.e("location",e.getMessage());
                    }

                } else {

                    Log.d("Scrolling left", "SCROLL");
                }
            }
        });

    }


    private void clickListeners() {

    }

    private void initMap() {
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapView);
            mapFragment.getMapAsync(this);

        } catch (NullPointerException npe) {
            Log.e(TAG, "onCreate: ", npe);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        MapConfig.config.mapStyle(googleMap, App.applicationContext);
        boolean isLocationPermissionGranted = LocationService.service.requestLocationPermission(App.applicationContext);
        if (isLocationPermissionGranted) {
            LocationUpdateService locationUpdateService = new LocationUpdateService();
            locationUpdateService.LocationHandler(getActivity(), this);
            addmarkers();

            mMap.setOnMarkerClickListener(this);

        }
    }

    @Override
    public void onLocationChange(Location location) {
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        boolean isCalled = true;
        if (isCalled) {
            MapConfig.config.moveCamera(currentLocation, mMap);
          //  MapConfig.config.addMarkers(mMap, currentLocation, App.applicationContext);
        }
    }

    @Override
    public void onError(String error) {
    }

    private void buildRv() {

        names.add(new LatLng(31.561920, 74.348080));
        names.add(new LatLng(31.562340, 74.348630));

        fragmentBinding.markersLocationList.setOnFlingListener(null);
        fragmentBinding.markersLocationList.setHasFixedSize(true);
        SnapHelper snapHelper = new PagerSnapHelper();
        fragmentBinding.markersLocationList.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(fragmentBinding.markersLocationList);
        fragmentBinding.markersLocationList.setItemAnimator(new DefaultItemAnimator());
        adapter = new SpMapAdapter(names, App.applicationContext);
        fragmentBinding.markersLocationList.setAdapter(adapter);
        fragmentBinding.markersLocationList.setVisibility(View.VISIBLE);

        adapter.setOnProviderClickListener(new SpMapAdapter.ItemClickListener() {
            @Override
            public void onMessageClick(int position) {

            }

            @Override
            public void onProfileClick(int position) {
                ActivityUtil.gotoPage(requireContext(), ServiceProviderProfile.class);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


    }

    private void addmarkers() {
        LatLng current_location = new LatLng(31.561920, 74.348080);
        Marker marker = mMap.addMarker(new MarkerOptions().position(current_location).icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.map_stop_position)));
        marker.setTag("Gullberg 3");
        markers.add(marker);

        LatLng barket = new LatLng(31.562340, 74.348630);
        Marker marker1 = mMap.addMarker(new MarkerOptions().position(barket).icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.map_stop_position)));
        marker1.setTag("Barkat Market");
        markers.add(marker1);
    }


    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        try {

        if (null != selectedMarker) {
            selectedMarker.setIcon(bitmapDescriptorFromVector(requireActivity(),R.drawable.map_stop_position));
        }
        selectedMarker = marker;
        selectedMarker.setIcon(bitmapDescriptorFromVector(requireActivity(),R.drawable.map_stop_position));

        final LatLng markerPosition = marker.getPosition();
        int selected_marker = -1;
        for (int i = 0; i <= names.size()-1; i++) {
            if (markerPosition.latitude == names.get(i).latitude && markerPosition.longitude == names.get(i).longitude) {
                selected_marker = i;
            }
        }
        if (selected_marker != -1) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(markerPosition).zoom(12).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            adapter.notifyDataSetChanged();
            fragmentBinding.markersLocationList.smoothScrollToPosition(selected_marker);
        }else {
            Toast.makeText(requireContext(), "List doesn't contain this location", Toast.LENGTH_SHORT).show();
        }

        }catch (NullPointerException e){
            Log.e("location", e.getMessage());
        }
        return true;

    }
}