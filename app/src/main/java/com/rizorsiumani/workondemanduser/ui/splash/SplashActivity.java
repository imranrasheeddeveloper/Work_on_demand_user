package com.rizorsiumani.workondemanduser.ui.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivitySplashBinding;
import com.rizorsiumani.workondemanduser.ui.walkthrough.OnboardingActivity;
import com.rizorsiumani.workondemanduser.ui.welcome_user.WelcomeUser;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected ActivitySplashBinding getActivityBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(() -> {
            ActivityUtil.gotoPage(SplashActivity.this, OnboardingActivity.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }, 3000);

    }
}