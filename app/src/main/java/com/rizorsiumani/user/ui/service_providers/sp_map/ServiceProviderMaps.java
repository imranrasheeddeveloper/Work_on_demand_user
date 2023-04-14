package com.rizorsiumani.user.ui.service_providers.sp_map;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
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
import androidx.lifecycle.ViewModelProvider;
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
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseFragment;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.ServiceProviderDataItem;
import com.rizorsiumani.user.data.businessModels.ServiceProviderModel;
import com.rizorsiumani.user.data.businessModels.inbox.ServiceProvider;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.FragmentServiceProviderMapsBinding;
import com.rizorsiumani.user.ui.chat.Chatroom;
import com.rizorsiumani.user.ui.inbox.InboxViewModel;
import com.rizorsiumani.user.ui.service_providers.ServiceProviderViewModel;
import com.rizorsiumani.user.ui.sp_detail.SpProfile;
import com.rizorsiumani.user.utils.Constants;
import com.rizorsiumani.user.utils.map_utils.LocationService;
import com.rizorsiumani.user.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.user.utils.map_utils.MapConfig;
import com.rizorsiumani.user.utils.map_utils.OnLocationUpdateListener;

import java.util.ArrayList;
import java.util.List;


public class ServiceProviderMaps extends BaseFragment<FragmentServiceProviderMapsBinding> implements OnMapReadyCallback,
        OnLocationUpdateListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private ArrayList<Marker> markers;
    SpMapAdapter adapter;
    private Marker selectedMarker = null;
    LinearLayoutManager layoutManager;
    List<LatLng> providersLatLng;
    List<ServiceProviderDataItem> serviceProviders;
    private ServiceProviderViewModel viewModel;
    ServiceProviderModel providerModel;
    String catID = "";
    private InboxViewModel inboxViewModel;


    @Override
    protected FragmentServiceProviderMapsBinding getFragmentBinding() {
        return FragmentServiceProviderMapsBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inboxViewModel = new ViewModelProvider(this).get(InboxViewModel.class);
        if (Constants.constant.isLocationPermissionGranted) {
            LocationUpdateService locationUpdateService = new LocationUpdateService();
            locationUpdateService.LocationHandler(requireActivity(), this);
        }
            initMap();
        markers = new ArrayList<>();
        layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        providersLatLng = new ArrayList<>();
        clickListeners();


        fragmentBinding.markersLocationList.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (layoutManager.findFirstVisibleItemPosition() > 0) {

                    try {

                        //if (markers.size() > 0) {
                        final Marker marker = markers.get(layoutManager.findFirstVisibleItemPosition());
                        final LatLng markerPosition = marker.getPosition();
                        for (int i = 0; i < providersLatLng.size(); i++) {
                            if (markerPosition.latitude == providersLatLng.get(i).longitude && markerPosition.longitude == providersLatLng.get(i).longitude) {

                            }
                        }

                        CameraPosition cameraPosition = new CameraPosition.Builder().target(markerPosition).zoom(14).build();
                        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        Log.d("Scrolling Right", "SCROLL");

                        // }
                    } catch (NullPointerException e) {
                        Log.e("location", e.getMessage());

                    }

                } else {

                    Log.d("Scrolling left", "SCROLL");
                }
            }
        });

    }

    private void getData() {
        try {
            catID = getActivity().getIntent().getStringExtra("cat_id");

            try {
//               String providerData = getActivity().getIntent().getStringExtra("providers");
//               Gson  gson = new Gson();
//               providerModel = gson.fromJson(providerData, ServiceProviderModel.class);
                providerModel = TinyDbManager.getProviderMap();

            }catch (NullPointerException e){
                e.printStackTrace();
            }

                if (catID != null){
                    viewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);
                    JsonObject object = new JsonObject();
                    object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
                    object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
                    object.addProperty("category_id", catID);
                    String token = prefRepository.getString("token");
                    viewModel.catServiceProviders(1, token, object);
                    viewModel._by_cat_provider.observe(getViewLifecycleOwner(), response -> {
                        if (response != null) {
                            if (response.isLoading()) {
                                 showLoading();
                            } else if (response.getError() != null) {
                                hideLoading();
                                if (response.getError() == null){
                                    showSnackBarShort("Something went wrong!!");
                                }else {
                                    Constants.constant.getApiError(App.applicationContext,response.getError());
                                }
                            } else if (response.getData().isSuccess()) {
                                  hideLoading();
                                if (response.getData().getData().size() > 0) {
                                    serviceProviders = new ArrayList<>();
                                    serviceProviders.addAll(response.getData().getData());
                                    buildRv(serviceProviders);
                                } else {
                                    showSnackBarShort("Data not Available");
                                }
                            }

                        }
                    });
                } else {
                buildRv(providerModel.getData());
//                viewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);
//                JsonObject object = new JsonObject();
//                object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
//                object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
//                object.addProperty("sub_category_id", subCatID);
//                String token = prefRepository.getString("token");
//                viewModel.serviceProviders(1, token, object);
//                viewModel._provider.observe(getViewLifecycleOwner(), response -> {
//                    if (response != null) {
//                        if (response.isLoading()) {
//                             showLoading();
//                        } else if (!response.getError().isEmpty()) {
//                             hideLoading();
//                            showSnackBarShort(response.getError());
//                        } else if (response.getData().isSuccess()) {
//
//                            hideLoading();
//                            if (response.getData().getData().size() > 0) {
//                                serviceProviders = new ArrayList<>();
//                                serviceProviders.addAll(response.getData().getData());
//                                buildRv(serviceProviders);
//                            } else {
//                                showSnackBarShort("Data not Available");
//                            }
//                        }
//
//                    }
//                });
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
//
//
//            if (getArguments() != null) {
//                if (getArguments().getString("service_providers") != null) {
//                    String data = getArguments().getString("service_providers");
//                    Gson gson = new Gson();
//                    ServiceProviderModel = gson.fromJson(data,ServiceProviderModel.class);
//                    if (ServiceProviderModel.getData().size() > 0){
//                        buildRv();
//                    }else {
//                        showSnackBarShort("Data not Available");
//                    }
//                }
//            }


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
        mMap.setOnMarkerClickListener(this);

      //  boolean isLocationPermissionGranted = LocationService.service.requestLocationPermission(App.applicationContext);
        if (Constants.constant.isLocationPermissionGranted) {
            getData();
            LocationUpdateService locationUpdateService = new LocationUpdateService();
            locationUpdateService.LocationHandler(getActivity(), this);
        }else {
            LocationService.service.requestLocationPermission(App.applicationContext);
        }
    }

    @Override
    public void onLocationChange(Location location) {
        Constants.constant.latitude = location.getLatitude();
        Constants.constant.longitude = location.getLongitude();
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

    private void buildRv(List<ServiceProviderDataItem> serviceProviders) {

        for (int i = 0; i <= serviceProviders.size() - 1; i++) {
            ServiceProviderDataItem dataItem = serviceProviders.get(i);
            providersLatLng.add(new LatLng(dataItem.getLatitude(),dataItem.getLongitude()));

            LatLng loc = new LatLng(dataItem.getLatitude(), dataItem.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(loc).icon(bitmapDescriptorFromVector(requireActivity(), R.drawable.map_stop_position)));
            markers.add(marker);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc,15.0f));
            mMap.animateCamera(CameraUpdateFactory.zoomIn());
            mMap.animateCamera(CameraUpdateFactory.zoomTo(15.0f),20,null);
            CameraPosition build= new CameraPosition.Builder().target(loc).zoom(15.0f)
                    .bearing(15.0f).tilt(0.0f).build();
            mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(build));
        }

        fragmentBinding.markersLocationList.setOnFlingListener(null);
        fragmentBinding.markersLocationList.setHasFixedSize(true);
        SnapHelper snapHelper = new PagerSnapHelper();
        fragmentBinding.markersLocationList.setLayoutManager(layoutManager);
        snapHelper.attachToRecyclerView(fragmentBinding.markersLocationList);
        fragmentBinding.markersLocationList.setItemAnimator(new DefaultItemAnimator());
        adapter = new SpMapAdapter(serviceProviders, requireContext());
        fragmentBinding.markersLocationList.setAdapter(adapter);
        fragmentBinding.markersLocationList.setVisibility(View.VISIBLE);

        adapter.setOnProviderClickListener(new SpMapAdapter.ItemClickListener() {
            @Override
            public void onMessageClick(int position) {
                String token = prefRepository.getString("token");
                inboxViewModel.isInboxExist(token, serviceProviders.get(position).getId());
                inboxViewModel._is_exist.observe(getViewLifecycleOwner() , response -> {
                    if (response != null) {
                        if (response.isLoading()) {
                            showLoading();
                        } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            if (response.getData().getInboxId() != 0) {
                                hideNoDataAnimation();
                                Gson gson = new Gson();
                                ServiceProvider serviceProvider = new ServiceProvider();
                                serviceProvider.setFirstName(serviceProviders.get(position).getFirstName());
                                serviceProvider.setLastName(serviceProviders.get(position).getLastName());
                                serviceProvider.setProfilePhoto(serviceProviders.get(position).getProfilePhoto());
                                serviceProvider.setId(serviceProviders.get(position).getId());

                                String providerData = gson.toJson(serviceProvider, ServiceProvider.class);

                                Intent intent = new Intent(requireContext(), Chatroom.class);
                                intent.putExtra("inbox_id",String.valueOf(response.getData().getInboxId()));
                                intent.putExtra("maps","true");
                                intent.putExtra("provider_detail",providerData);
                                startActivity(intent);
                                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        }
                    }
                });

            }

            @Override
            public void onProfileClick(int position) {
                TinyDbManager.saveServiceProviderID(String.valueOf(serviceProviders.get(position).getId()));
                Intent intent = new Intent(requireContext(),SpProfile.class);
                startActivity(intent);
                requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


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
                selectedMarker.setIcon(bitmapDescriptorFromVector(requireActivity(), R.drawable.map_stop_position));
            }
            selectedMarker = marker;
            selectedMarker.setIcon(bitmapDescriptorFromVector(requireActivity(), R.drawable.map_stop_position));

            final LatLng markerPosition = marker.getPosition();
            int selected_marker = -1;
            for (int i = 0; i <= providersLatLng.size() - 1; i++) {
                if (markerPosition.latitude == providersLatLng.get(i).latitude && markerPosition.longitude == providersLatLng.get(i).longitude) {
                    selected_marker = i;
                }
            }
            if (selected_marker != -1) {
                CameraPosition cameraPosition = new CameraPosition.Builder().target(markerPosition).zoom(12).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                adapter.notifyDataSetChanged();
                fragmentBinding.markersLocationList.smoothScrollToPosition(selected_marker);
            } else {
                Toast.makeText(requireContext(), "List doesn't contain this location", Toast.LENGTH_SHORT).show();
            }

        } catch (NullPointerException e) {
            Log.e("location", e.getMessage());
        }
        return true;

    }
}