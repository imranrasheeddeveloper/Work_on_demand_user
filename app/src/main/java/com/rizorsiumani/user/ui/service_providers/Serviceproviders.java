package com.rizorsiumani.user.ui.service_providers;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;

import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.tabs.TabLayout;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.ActivityServiceprovidersBinding;
import com.rizorsiumani.user.ui.sub_category.SubCategories;

public class Serviceproviders extends BaseActivity<ActivityServiceprovidersBinding> {


    NavController mNavController;
    String intentPath;


    @Override
    protected void onStart() {
        super.onStart();


        activityBinding.serviceToolbar.title.setText("Service Providers");
        try {
            if (getIntent() != null){
                intentPath = getIntent().getStringExtra("path");
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }


        if (activityBinding.tabLayout.getTabCount() == 0) {
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Map").setIcon(R.drawable.ic_oval).setId(0));
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("List").setIcon(R.drawable.ic_booking).setId(1));
        }

        if (activityBinding.tabLayout.getSelectedTabPosition() == 0) {
            activityBinding.tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#FFFFFFFF"), PorterDuff.Mode.SRC_IN);
            activityBinding.tabLayout.getTabAt(0).view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
            activityBinding.tabLayout.getTabAt(0).view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
        }else {
            activityBinding.tabLayout.getTabAt(1).select();
        }


        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment01);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }


        clickListeners();

    }


    private void clickListeners() {
        activityBinding.serviceToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
                tab.getIcon().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
                tab.view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
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

    @Override
    public void onBackPressed() {

        if (intentPath != null) {
            Intent intent = new Intent(this, SubCategories.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }else {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }

    }
}