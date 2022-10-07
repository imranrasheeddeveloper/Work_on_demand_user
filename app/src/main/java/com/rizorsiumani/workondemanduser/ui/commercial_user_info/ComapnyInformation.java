package com.rizorsiumani.workondemanduser.ui.commercial_user_info;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityComapnyInformationBinding;
import com.rizorsiumani.workondemanduser.ui.register.CreatePassword;
import com.rizorsiumani.workondemanduser.ui.register.Register;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class ComapnyInformation extends BaseActivity<ActivityComapnyInformationBinding> {

    @Override
    protected ActivityComapnyInformationBinding getActivityBinding() {
        return ActivityComapnyInformationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.btnSaveCompanyDetail.setOnClickListener(view -> {
            ActivityUtil.gotoPage(ComapnyInformation.this, CreatePassword.class);
        });
    }
}