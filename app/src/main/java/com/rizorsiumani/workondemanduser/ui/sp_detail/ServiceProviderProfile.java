package com.rizorsiumani.workondemanduser.ui.sp_detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityServiceProviderProfileBinding;

public class ServiceProviderProfile extends BaseActivity<ActivityServiceProviderProfileBinding> {

    @Override
    protected ActivityServiceProviderProfileBinding getActivityBinding() {
        return ActivityServiceProviderProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}