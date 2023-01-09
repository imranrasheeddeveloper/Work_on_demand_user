package com.rizorsiumani.workondemanduser.ui.add_location;

import static android.content.ContentValues.TAG;
import static com.rizorsiumani.workondemanduser.utils.map_utils.GeoCoders.GetProperLocationAddress;

import android.location.Location;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FetchPlaceResponse;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityAddAddressBinding;
import com.rizorsiumani.workondemanduser.ui.address.LocationListAdapter;
import com.rizorsiumani.workondemanduser.ui.address.PlaceModel;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationService;
import com.rizorsiumani.workondemanduser.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.workondemanduser.utils.map_utils.MapConfig;
import com.rizorsiumani.workondemanduser.utils.map_utils.OnLocationUpdateListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddAddress extends BaseActivity<ActivityAddAddressBinding> implements OnMapReadyCallback, OnLocationUpdateListener {


    private GoogleMap mMap;
    LatLng centre_location;
    double latitude = 0.0;
    double longitude = 0.0;
    String address;
    String title = "other";
    private AddressViewModel viewModel;
    int addressID;

    LocationListAdapter adapter;

    PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    ArrayList<PlaceModel> suggestionList;
    AutocompleteSessionToken token;
    String apiKey;
    @Override
    protected ActivityAddAddressBinding getActivityBinding() {
        return ActivityAddAddressBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (getIntent() != null) {
            title = getIntent().getStringExtra("address_title");
            addressID = getIntent().getIntExtra("address_id", 0);
        }

        viewModel = new ViewModelProvider(AddAddress.this).get(AddressViewModel.class);
        initMap();
        initPlacesClient();
        textWatcher();
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.backSetLocation.setOnClickListener((View.OnClickListener) v -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.confirmLocation.setOnClickListener(view -> {
            String address = activityBinding.locationAddress.getText().toString();
            TinyDbManager.saveCurrentAddress(address);

            if (latitude == 0.0 || longitude == 0.0) {
                showSnackBarShort("Select Location First");
            } else {
                if (addressID != 0) {
                    updateAddress();
                } else {
                    saveAddress();
                }
            }
        });

        activityBinding.clearIcon.setOnClickListener(view -> {
            activityBinding.search.setText("");
            activityBinding.placesList.setVisibility(View.GONE);
        });
    }

    private void initPlacesClient() {

        apiKey = getString(R.string.GOOGLE_MAP_API_KEY);
        if (!Places.isInitialized()) {
            Places.initialize(AddAddress.this, apiKey);
        }
        placesClient = Places.createClient(AddAddress.this);
        token = AutocompleteSessionToken.newInstance();
    }

    private void textWatcher() {

        activityBinding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                activityBinding.loadingView.setVisibility(View.VISIBLE);
                activityBinding.clearIcon.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                activityBinding.placesList.setVisibility(View.VISIBLE);
                GetPredictionsList(editable, token, activityBinding.search);
            }
        });

    }

    private void GetPredictionsList(Editable s, AutocompleteSessionToken token, EditText view) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setSessionToken(token)
                .setQuery(s.toString())
                .build();

        placesClient.findAutocompletePredictions(request).addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                FindAutocompletePredictionsResponse predictionsResponse = task.getResult();
                if (predictionsResponse != null) {
                    predictionList = predictionsResponse.getAutocompletePredictions();
                    suggestionList = new ArrayList<>();
                    for (int i = 0; i < predictionList.size(); i++) {
                        AutocompletePrediction prediction = predictionList.get(i);
                        suggestionList.add(new PlaceModel(prediction.getPlaceId(),prediction.getPrimaryText(null).toString(),
                                prediction.getFullText(null).toString()));
                    }

                    activityBinding.placesList.setHasFixedSize(true);
                    activityBinding.placesList.setLayoutManager(new LinearLayoutManager(AddAddress.this));
                    adapter = new LocationListAdapter(suggestionList, AddAddress.this);
                    activityBinding.placesList.setAdapter(adapter);

                    activityBinding.loadingView.setVisibility(View.GONE);
                    activityBinding.clearIcon.setVisibility(View.VISIBLE);

                    adapter.setAddressClickListener(position -> {
                        showLoading();

                        String placeId = String.valueOf(suggestionList.get(position).getId());


                        List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS);
                        FetchPlaceRequest request1 = FetchPlaceRequest.builder(placeId, placeFields).build();
                        placesClient.fetchPlace(request1).addOnSuccessListener(new OnSuccessListener<FetchPlaceResponse>() {
                            @Override
                            public void onSuccess(FetchPlaceResponse response) {

                                MapConfig.config.moveCamera(response.getPlace().getLatLng(), mMap);
                                activityBinding.placesList.setVisibility(View.GONE);
                                hideLoading();
                                activityBinding.search.setText("");
                                activityBinding.clearIcon.setVisibility(View.GONE);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                if (exception instanceof ApiException) {
                                    Toast.makeText(AddAddress.this, exception.getMessage() + "", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }); });
                }

            } else {
                Log.i("PredictionTag", "Prediction fetching task failed..");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddAddress.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void updateAddress() {
        String token = prefRepository.getString("token");
        JsonObject object = new JsonObject();
        object.addProperty("title", title);
        object.addProperty("latitude", latitude);
        object.addProperty("longitude", longitude);
        object.addProperty("address", address);
        object.addProperty("address_id", addressID);


        viewModel.update(token, object);
        viewModel._update.observe(AddAddress.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().isSuccess()) {
                    hideLoading();

                    onBackPressed();
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                }
            }
        });
    }

    private void saveAddress() {
        String token = prefRepository.getString("token");
        JsonObject object = new JsonObject();
        if (title == null || title.isEmpty()){
            title = "other";
        }
        object.addProperty("title", title);
        object.addProperty("latitude", latitude);
        object.addProperty("longitude", longitude);
        object.addProperty("address", address);


        viewModel.save(token, object);
        viewModel._address.observe(AddAddress.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    onBackPressed();
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);


                }
            }
        });
    }

    private void initMap() {
        try {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.setLocationMap);
            assert mapFragment != null;
            mapFragment.getMapAsync(this);
        } catch (NullPointerException npe) {
            Log.e(TAG, "onCreate: ", npe);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        MapConfig.config.mapStyle(googleMap, AddAddress.this);
        if (Constants.constant.isLocationPermissionGranted) {
            LocationUpdateService locationUpdateService = new LocationUpdateService();
            locationUpdateService.LocationHandler(AddAddress.this, this);
        }
    }

    @Override
    public void onLocationChange(Location location) {
        LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
        boolean isCalled = true;
        if (isCalled) {
            MapConfig.config.moveCamera(currentLocation, mMap);
            mMap.setOnCameraIdleListener(() -> {
                activityBinding.mapIcon.setImageResource(R.drawable.map_stop_position);
                centre_location = mMap.getCameraPosition().target;
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                setAddress(centre_location);
            });
            mMap.setOnCameraMoveListener(() -> activityBinding.mapIcon.setImageResource(R.drawable.map_moving_position));
        }
    }

    @Override
    public void onError(String error) {
    }

    public void setAddress(LatLng centre) {
        String centerAddress = GetProperLocationAddress(centre.latitude, centre.longitude, AddAddress.this);
        if (!centerAddress.isEmpty()) {
            activityBinding.locationAddress.setText(centerAddress);
            TinyDbManager.saveCurrentAddress(centerAddress);

            address = centerAddress;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._address.removeObservers(this);
        viewModel._update.removeObservers(this);

        viewModel = null;
    }
}