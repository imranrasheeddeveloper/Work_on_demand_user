package com.rizorsiumani.workondemanduser.ui.all_services;

import androidx.recyclerview.widget.GridLayoutManager;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.ui.fragment.home.ServicesAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllServices extends BaseActivity<com.rizorsiumani.workondemanduser.databinding.ActivityAllServicesBinding> {

    @Override
    protected com.rizorsiumani.workondemanduser.databinding.ActivityAllServicesBinding getActivityBinding() {
        return com.rizorsiumani.workondemanduser.databinding.ActivityAllServicesBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.servicesToolbar.title.setText("All Services");
        clickListeners();
        allServices();
    }

    private void allServices() {
        List<String> service_categories = new ArrayList<>();
        service_categories.add("Cleaning");
        service_categories.add("Shifting");
        service_categories.add("Appliances");
        service_categories.add("Painting");
        service_categories.add("Electronice");
        service_categories.add("Repairing");
        service_categories.add("Appliances");
        service_categories.add("Painting");
        service_categories.add("Electronice");

        List<Integer> service_icons = new ArrayList<>();
        service_icons.add(R.drawable.ic_cleaning);
        service_icons.add(R.drawable.ic_shifting);
        service_icons.add(R.drawable.ic_appliances__2_);
        service_icons.add(R.drawable.ic_painting__2_);
        service_icons.add(R.drawable.ic_electronics__2_);
        service_icons.add(R.drawable.ic_repairing);
        service_icons.add(R.drawable.ic_appliances__2_);
        service_icons.add(R.drawable.ic_painting__2_);
        service_icons.add(R.drawable.ic_electronics__2_);



        GridLayoutManager glm = new GridLayoutManager(AllServices.this, 3);
        activityBinding.allServicesList.setLayoutManager(glm);
        ServicesAdapter adapter = new ServicesAdapter(AllServices.this, service_categories, service_icons);
        activityBinding.allServicesList.setAdapter(adapter);
    }

    private void clickListeners() {
        activityBinding.servicesToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }
}