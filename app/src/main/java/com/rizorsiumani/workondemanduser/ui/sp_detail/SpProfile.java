package com.rizorsiumani.workondemanduser.ui.sp_detail;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivitySpProfileBinding;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class SpProfile extends BaseActivity<ActivitySpProfileBinding> {

    NavController mNavController;
    int count = 0;


    @Override
    protected ActivitySpProfileBinding getActivityBinding() {
        return ActivitySpProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (prefRepository.getString("cart").equalsIgnoreCase("true")){
            activityBinding.cartItem.setVisibility(View.VISIBLE);
        }

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

        activityBinding.availability.setOnClickListener(view -> {
            showProfileDetail();
        });

        activityBinding.cartItem.setOnClickListener(view -> {
            ActivityUtil.gotoPage(SpProfile.this, BookingDetail.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    private void showProfileDetail() {
        if (count == 0) {
            count = 1;
            final BottomSheetDialog bt = new BottomSheetDialog(SpProfile.this, R.style.BottomSheetDialogTheme);
            bt.setCanceledOnTouchOutside(false);
            View profileView = LayoutInflater.from(SpProfile.this).inflate(R.layout.sp_profile_detail_bottomsheet, null, false);

            bt.getBehavior().addBottomSheetCallback(mBottomSheetBehaviorCallback);


            bt.setContentView(profileView);
            bt.show();
        }
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
                } else if (tab.getId() == 1) {
                    mNavController.navigate(R.id.gallery2);
                } else {
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

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                count = 0;
            } else {
                count = 1;
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

}