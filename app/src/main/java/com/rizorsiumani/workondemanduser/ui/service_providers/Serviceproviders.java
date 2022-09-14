package com.rizorsiumani.workondemanduser.ui.service_providers;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityServiceprovidersBinding;
import com.rizorsiumani.workondemanduser.ui.address.AdressesAdapter;

import java.util.ArrayList;
import java.util.List;

public class Serviceproviders extends BaseActivity<ActivityServiceprovidersBinding> {

    @Override
    protected ActivityServiceprovidersBinding getActivityBinding() {
        return ActivityServiceprovidersBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.serviceToolbar.title.setText(getIntent().getStringExtra("ser_name"));
        servicesProvidersRv();
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.serviceToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });
    }

    private void servicesProvidersRv() {

        List<String> name = new ArrayList<>();
        name.add("Michel Jeff");
        name.add("Michel Jeff");
        name.add("Michel Jeff");
        name.add("Michel Jeff");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.serviceProvidersList.setLayoutManager(layoutManager);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(name, App.applicationContext);
        activityBinding.serviceProvidersList.setAdapter(adapter);
    }

}