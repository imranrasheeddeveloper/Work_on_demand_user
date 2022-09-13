package com.rizorsiumani.workondemanduser.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityCreatePasswordBinding;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class CreatePassword extends BaseActivity<ActivityCreatePasswordBinding> {

    @Override
    protected ActivityCreatePasswordBinding getActivityBinding() {
        return ActivityCreatePasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        clickListeners();
    }

    private void clickListeners() {

        activityBinding.btnNext.setOnClickListener(view -> {
            ActivityUtil.gotoPage(CreatePassword.this,OtpVerification.class);
        });
    }
}