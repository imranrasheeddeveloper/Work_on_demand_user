package com.rizorsiumani.user.ui.register;

import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.databinding.ActivityOtpVerificationBinding;
import com.rizorsiumani.user.ui.dashboard.Dashboard;
import com.rizorsiumani.user.utils.ActivityUtil;

public class OtpVerification extends BaseActivity<ActivityOtpVerificationBinding> {

    @Override
    protected ActivityOtpVerificationBinding getActivityBinding() {
        return ActivityOtpVerificationBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();
        
        clickListeners();
    }

    private void clickListeners() {

        activityBinding.btnNext.setOnClickListener(view -> {
            ActivityUtil.gotoPage(OtpVerification.this, Dashboard.class);
           // activityBinding.loadingView.setVisibility(View.VISIBLE);
        });

    }
}