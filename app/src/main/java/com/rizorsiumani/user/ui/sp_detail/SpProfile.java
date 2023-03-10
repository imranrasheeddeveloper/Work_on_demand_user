package com.rizorsiumani.user.ui.sp_detail;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.SProfileData;
import com.rizorsiumani.user.data.businessModels.inbox.ServiceProvider;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivitySpProfileBinding;
import com.rizorsiumani.user.ui.booking_detail.BookingDetail;
import com.rizorsiumani.user.ui.chat.Chatroom;
import com.rizorsiumani.user.ui.inbox.InboxViewModel;
import com.rizorsiumani.user.utils.ActivityUtil;
import com.rizorsiumani.user.utils.Constants;

public class SpProfile extends BaseActivity<ActivitySpProfileBinding> {

    NavController mNavController;
    ProviderDetailViewModel viewModel;
    String id;
    InboxViewModel inboxViewModel;
    SProfileData sProfileData;

    @Override
    protected ActivitySpProfileBinding getActivityBinding() {
        return ActivitySpProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        inboxViewModel = new ViewModelProvider(this).get(InboxViewModel.class);

        try {

        id = TinyDbManager.getServiceProviderID();

        viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
        viewModel.getProfile(Integer.parseInt(id));
        viewModel._profile.observe(this , response -> {
            if (response != null) {
                if (response.isLoading()) {
                  //  showLoading();
                } else if (response.getError() != null) {
                    //hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().isSuccess()) {
                  //  hideLoading();
                    if (response.getData().getData() != null){
                        sProfileData = response.getData().getData();
                        Glide.with(SpProfile.this)
                                .load(Constants.IMG_PATH + sProfileData.getProfilePhoto())
                                .placeholder(R.color.placeholder_bg)
                                .into(activityBinding.ivSp);
                        activityBinding.tvSpName.setText(sProfileData.getFirstName() + " " + sProfileData.getLastName());
                        activityBinding.title.setText(sProfileData.getFirstName() + " " + "Details");
                        if (sProfileData.getServiceProviderReviews() != null && sProfileData.getServiceProviderReviews().size() > 0){
                            int ratings = 0;
                            for (int i = 0; i < sProfileData.getServiceProviderReviews().size(); i++) {
                                int currentRating = sProfileData.getServiceProviderReviews().get(i).getRaiting();
                                ratings  = ratings + currentRating;
                            }
                            int average = ratings / sProfileData.getServiceProviderReviews().size();
                            if (average > 0){
                                activityBinding.arting.setRating((float) average);
                            }
                        }
                    }
                }
            }
        });



        if (TinyDbManager.getCartData().size() > 0){
            showCartButton();
        } else {
            hideCartButton();
        }

        }catch (NullPointerException e){
            e.printStackTrace();
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
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

//        activityBinding.availability.setOnClickListener(view -> {
//            showProfileDetail();
//        });

        activityBinding.cartItem.setOnClickListener(view -> {
            ActivityUtil.gotoPage(SpProfile.this, BookingDetail.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.tvChat.setOnClickListener(v -> {
            String token = prefRepository.getString("token");
            inboxViewModel.isInboxExist(token, Integer.parseInt(id));
            inboxViewModel._is_exist.observe(this, response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                  } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                    } else if (response.getData().isSuccess()) {
                        hideLoading();
                        if (response.getData().getInboxId() != 0) {
                            hideNoDataAnimation();
                            Gson gson = new Gson();
                            ServiceProvider serviceProvider = new ServiceProvider();
                            serviceProvider.setFirstName(sProfileData.getFirstName());
                            serviceProvider.setLastName(sProfileData.getLastName());
                            serviceProvider.setProfilePhoto(sProfileData.getProfilePhoto());
                            serviceProvider.setId(sProfileData.getId());

                            String providerData = gson.toJson(serviceProvider, ServiceProvider.class);

                            Intent intent = new Intent(SpProfile.this, Chatroom.class);
                            intent.putExtra("inbox_id",String.valueOf(response.getData().getInboxId()));
                            intent.putExtra("maps","true");
                            intent.putExtra("provider_detail",providerData);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        }
                    }
                }
            });

        });
    }

    private void showProfileDetail() {
        final BottomSheetDialog bt = new BottomSheetDialog(SpProfile.this, R.style.BottomSheetDialogTheme);
        bt.setCanceledOnTouchOutside(false);
        View profileView = LayoutInflater.from(SpProfile.this).inflate(R.layout.sp_profile_detail_bottomsheet, null, false);

        bt.getBehavior().addBottomSheetCallback(mBottomSheetBehaviorCallback);



        bt.setContentView(profileView);
        bt.show();

    }


    @SuppressLint("UseCompatLoadingForDrawables")
    private void setTabs() {
        if (activityBinding.tabLayout.getTabCount() == 0) {
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Services").setId(0));
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Gallery").setId(1));
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Review").setId(2));
            activityBinding.tabLayout.addTab(activityBinding.tabLayout.newTab().setText("Available").setId(3));
        }

        if (activityBinding.tabLayout.getSelectedTabPosition() == 0) {

            activityBinding.tabLayout.getTabAt(0).view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
            activityBinding.tabLayout.getTabAt(0).view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
        }

        activityBinding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.view.setBackground(getResources().getDrawable(R.drawable.rect_bg));
                tab.view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));


                if (tab.getId() == 0) {
                    mNavController.navigate(R.id.services);
                } else if (tab.getId() == 1) {
                    mNavController.navigate(R.id.gallery2);
                } else if (tab.getId() == 3) {
                    mNavController.navigate(R.id.availability2);
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
                //count = 0;
            } else {
                //count = 1;
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._profile.removeObservers(this);
        viewModel = null;
    }
}