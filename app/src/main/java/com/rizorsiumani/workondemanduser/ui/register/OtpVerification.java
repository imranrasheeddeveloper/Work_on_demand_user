package com.rizorsiumani.workondemanduser.ui.register;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityOtpVerificationBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

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