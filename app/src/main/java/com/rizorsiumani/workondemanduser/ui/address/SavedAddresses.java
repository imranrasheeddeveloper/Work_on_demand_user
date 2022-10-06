package com.rizorsiumani.workondemanduser.ui.address;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivitySavedAddressesBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class SavedAddresses extends BaseActivity<ActivitySavedAddressesBinding> {

    PlacesClient placesClient;
    private List<AutocompletePrediction> predictionList;
    ArrayList<PlaceModel> suggestionList;
    AutocompleteSessionToken token;
    LocationListAdapter adapter;

    @Override
    protected ActivitySavedAddressesBinding getActivityBinding() {
        return ActivitySavedAddressesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

      //  addressRv();
        initPlacesClient();
        clickListeners();
        textWatcher();

    }

    private void initPlacesClient() {
        String apiKey = getString(R.string.GOOGLE_MAP_API_KEY);
        if (!Places.isInitialized()) {
            Places.initialize(SavedAddresses.this, apiKey);
        }
        placesClient = Places.createClient(SavedAddresses.this);
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
                activityBinding.favLocations.setVisibility(View.GONE);
                activityBinding.placesList.setVisibility(View.VISIBLE);
                GetPredictionsList(editable, token, activityBinding.search);
            }
        });

    }

    private void GetPredictionsList(Editable s, AutocompleteSessionToken token, EditText view) {
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setCountry("PK")
                .setTypeFilter(TypeFilter.ADDRESS)
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
                        suggestionList.add(new PlaceModel(prediction.getPrimaryText(null).toString(),
                                prediction.getFullText(null).toString()));

                    }

                    activityBinding.placesList.setHasFixedSize(true);
                    activityBinding.placesList.setLayoutManager(new LinearLayoutManager(SavedAddresses.this));
                    adapter = new LocationListAdapter(suggestionList, SavedAddresses.this);
                    activityBinding.placesList.setAdapter(adapter);

                    activityBinding.loadingView.setVisibility(View.GONE);
                    activityBinding.clearIcon.setVisibility(View.VISIBLE);

                    adapter.setAddressClickListener(position -> {
                        prefRepository.setString("CURRENT_LOCATION",suggestionList.get(position).getAddress());
                        onBackPressed();
                        finish();
                        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

                        // view.setText(suggestionList.get(position).getTitle());
                    });
                }

            } else {
                Log.i("PredictionTag", "Prediction fetching task failed..");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SavedAddresses.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void clickListeners() {

        activityBinding.add.setOnClickListener(view -> {
            navToAddAddress();
        });

        activityBinding.addCurrentAddress.setOnClickListener(view -> {
            navToAddAddress();
        });

        activityBinding.addWorkAddress.setOnClickListener(view -> {
            navToAddAddress();
        });

        activityBinding.addMapLocation.setOnClickListener(view -> {
            navToAddAddress();
        });
        activityBinding.cancel.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        activityBinding.clearIcon.setOnClickListener(view -> {
            activityBinding.search.setText("");
            activityBinding.placesList.setVisibility(View.GONE);
            activityBinding.favLocations.setVisibility(View.VISIBLE);
        });

    }

    private void navToAddAddress() {
        ActivityUtil.gotoPage(SavedAddresses.this, AddAddress.class);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

//    private void addressRv() {
//        List<String> transactions = new ArrayList<>();
//        transactions.add("Nawab Town, Lahore");
//        transactions.add("Model Town, Lahore");
//        transactions.add("Gullberg 3, Lahore");
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
//        activityBinding.addressList.setLayoutManager(layoutManager);
//        AdressesAdapter adapter = new AdressesAdapter(transactions, App.applicationContext);
//        activityBinding.addressList.setAdapter(adapter);
//    }

}