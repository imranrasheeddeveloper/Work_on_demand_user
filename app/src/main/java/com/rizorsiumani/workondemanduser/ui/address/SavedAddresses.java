package com.rizorsiumani.workondemanduser.ui.address;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.addressDataItems;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
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
    private GetAddressViewModel viewModel;
    int homeID;
    int workID;

    @Override
    protected ActivitySavedAddressesBinding getActivityBinding() {
        return ActivitySavedAddressesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        String token = prefRepository.getString("token");
        viewModel = new ViewModelProvider(SavedAddresses.this).get(GetAddressViewModel.class);
        viewModel.get(token);

        viewModel._address.observe(SavedAddresses.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    if (response.getData().getData().size() > 0) {
                        setAddresses(response.getData().getData());
                    }

                }
            }
        });

        initPlacesClient();
        clickListeners();
        textWatcher();

    }

    private void setAddresses(List<addressDataItems> data) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getTitle().equals("Home")) {
                activityBinding.homeAddress.setVisibility(View.VISIBLE);
                activityBinding.homeAddress.setText(data.get(i).getAddress());
                activityBinding.add.setImageResource(R.drawable.ic_edit_blue);
                activityBinding.add.setTag("edit");
                homeID = data.get(i).getId();

            } else if (data.get(i).getTitle().equals("Work")) {
                activityBinding.workddress.setVisibility(View.VISIBLE);
                activityBinding.workddress.setText(data.get(i).getAddress());
                activityBinding.addWorkAddress.setImageResource(R.drawable.ic_edit_blue);
                activityBinding.addWorkAddress.setTag("edit");
                workID = data.get(i).getId();

            } else {

            }
        }
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
                        TinyDbManager.saveCurrentAddress(suggestionList.get(position).getAddress());
                        prefRepository.setString("CURRENT_LOCATION", suggestionList.get(position).getAddress());
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
            String tag = activityBinding.add.getTag().toString();
            if (tag.equals("edit")) {
                if (homeID != 0) {
                    navigateWithExtras("Home", homeID);
                }
            } else {
                navigateWithExtras("Home", 0);
            }
        });

        activityBinding.addCurrentAddress.setOnClickListener(view -> {
            navToAddAddress();
        });

        activityBinding.addWorkAddress.setOnClickListener(view -> {
            String tag = activityBinding.addWorkAddress.getTag().toString();
            if (tag.equals("edit")) {
                if (workID != 0) {
                    navigateWithExtras("Work", workID);
                }
            } else {
                navigateWithExtras("Work", 0);
            }
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

    private void navigateWithExtras(String title, int id) {
        Intent intent = new Intent(SavedAddresses.this, AddAddress.class);
        intent.putExtra("address_title", title);
        intent.putExtra("address_id", id);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._address.removeObservers(this);
        viewModel = null;
    }

}