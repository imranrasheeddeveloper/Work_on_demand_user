package com.rizorsiumani.workondemanduser.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityRegisterBinding;
import com.rizorsiumani.workondemanduser.ui.commercial_user_info.ComapnyInformation;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class Register extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected ActivityRegisterBinding getActivityBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        clickListeners();
    }

    private void clickListeners() {

        activityBinding.btnNext.setOnClickListener(view -> {
            ActivityUtil.gotoPage(Register.this, ComapnyInformation.class);
        });
    }
}