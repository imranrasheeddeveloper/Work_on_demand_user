package com.rizorsiumani.workondemanduser.ui.service_providers;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityServiceprovidersBinding;

public class Serviceproviders extends BaseActivity<ActivityServiceprovidersBinding> {


    NavController mNavController;



    @Override
    protected void onStart() {
        super.onStart();



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
                tab.getIcon().setColorFilter(Color.parseColor("#FFFFFFFF"), PorterDuff.Mode.SRC_IN);
                tab.view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
                tab.view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));



                if (tab.getId() == 1) {
                    mNavController.navigate(R.id.serviceProviderList);
                } else {
                    mNavController.navigate(R.id.serviceProviderMaps);
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