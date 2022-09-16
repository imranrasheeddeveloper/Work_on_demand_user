package com.rizorsiumani.workondemanduser.ui.sp_detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivitySpProfileBinding;

public class SpProfile extends BaseActivity<ActivitySpProfileBinding> {

    NavController mNavController;


    @Override
    protected ActivitySpProfileBinding getActivityBinding() {
        return ActivitySpProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.sp_nav_host_fragment);
        if (navHostFragment != null) {
            mNavController = navHostFragment.getNavController();
        }
        clickListeners();

        setTabs();

    }

    private void clickListeners() {
        activityBinding.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });


    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setTabs() {
        if (activityBinding.tabLayout.getTabCount() == 0) {
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Services").setId(0));
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Gallery").setId(1));
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Review").setId(2));
        }

        activityBinding.tabLayout.getTabAt(0).view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
        activityBinding.tabLayout.getTabAt(0).view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));


        activityBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
                tab.view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
                if (tab.getId() == 0) {
                    mNavController.navigate(R.id.services);
                } else if (tab.getId() == 1){
                    mNavController.navigate(R.id.gallery2);
                }else {
                    mNavController.navigate(R.id.reviews);
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
}