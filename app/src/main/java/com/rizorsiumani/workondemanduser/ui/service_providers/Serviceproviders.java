package com.rizorsiumani.workondemanduser.ui.service_providers;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.DataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderServicesItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProvidersModel;
import com.rizorsiumani.workondemanduser.databinding.ActivityServiceprovidersBinding;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Serviceproviders extends BaseActivity<ActivityServiceprovidersBinding> {


    NavController mNavController;
    private ServiceProviderViewModel viewModel;
    String subCatID;
    ServiceProvidersModel model;



    @Override
    protected void onStart() {
        super.onStart();

        try {
            subCatID = getIntent().getStringExtra("sub_cat_id");
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        viewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);
        JsonObject object = new JsonObject();
        object.addProperty("lat", Constants.latitude);
        object.addProperty("long", Constants.longitude);
        object.addProperty("sub_category_id", subCatID);

        viewModel.serviceProviders(1,Constants.ACCESS_TOKEN,object);
        viewModel._provider.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().isSuccess()) {
                    hideLoading();

                    if (response.getData().getData().size() > 0){
                        model = response.getData();
                    }
                }
            }
        });







        activityBinding.serviceToolbar.title.setText("Service Providers");

        if (activityBinding.tabLayout.getTabCount() == 0) {
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Map").setIcon(R.drawable.ic_oval).setId(0));
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("List").setIcon(R.drawable.ic_booking).setId(1));
        }
        activityBinding.tabLayout.getTabAt(0).view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
        activityBinding.tabLayout.getTabAt(0).view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment01);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }


        clickListeners();
    }



    private void clickListeners() {
        activityBinding.serviceToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        activityBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
                tab.view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));

                Gson gson = new Gson();
                String service_providers = gson.toJson(model, ServiceProvidersModel.class);
                Bundle bundle = new Bundle();
                bundle.putString("service_providers", service_providers);

                if (tab.getId() == 1) {
                    mNavController.navigate(R.id.serviceProviderList,bundle);
                } else {
                    mNavController.navigate(R.id.serviceProviderMaps,bundle);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFFFFFFF")));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }


    @Override
    protected ActivityServiceprovidersBinding getActivityBinding() {
        return ActivityServiceprovidersBinding.inflate(getLayoutInflater());
    }
}