package com.rizorsiumani.workondemanduser.ui.search_provider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderDataItem;
import com.rizorsiumani.workondemanduser.databinding.ActivitySearchProviderBinding;
import com.rizorsiumani.workondemanduser.databinding.ActivitySearchServicesBinding;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderAdapter;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderViewModel;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class SearchProvider extends BaseActivity<ActivitySearchProviderBinding> {

    ServiceProviderViewModel viewModel;
    List<ServiceProviderDataItem> serviceProviders;


    @Override
    protected ActivitySearchProviderBinding getActivityBinding() {
        return ActivitySearchProviderBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();


        activityBinding.searchToolbar.title.setText("Search");
        activityBinding.searchToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.clearIcon.setOnClickListener(view -> {
            activityBinding.tvSearch.setText("");
        });

        viewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);
       activityBinding.searchIcon.setOnClickListener(view -> {
           String data = activityBinding.tvSearch.getText().toString();
           if (!data.isEmpty()){
               getSearchedData(data);
           }else {
               showSnackBarShort("Enter Service for Search");
           }
       });


    }


    private void getSearchedData(String data) {
        JsonObject object = new JsonObject();
        object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
        object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
        object.addProperty("searchTerm", data);
        String token = prefRepository.getString("token");
        viewModel.serviceProvidersSearch(1, token, object);
        viewModel._search_provider.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {

                    hideLoading();
                    if (response.getData().getData().size() > 0) {
                       // hideNoDataAnimation();
                        serviceProviders = new ArrayList<>();
                        serviceProviders.addAll(response.getData().getData());
                        buildRv(serviceProviders);
                    } else {
                        showSnackBarShort("No Data For " + data);
                    }
                }

            }
        });
    }

    private void buildRv(List<ServiceProviderDataItem> serviceProviders) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchProvider.this, RecyclerView.VERTICAL, false);
        activityBinding.list.setLayoutManager(layoutManager);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(serviceProviders, SearchProvider.this);
        activityBinding.list.setAdapter(adapter);

        adapter.setOnProviderSelectListener(position -> {
            Intent intent = new Intent(SearchProvider.this, SpProfile.class);
            intent.putExtra("service_provider_id",String.valueOf(serviceProviders.get(position).getId()));
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}