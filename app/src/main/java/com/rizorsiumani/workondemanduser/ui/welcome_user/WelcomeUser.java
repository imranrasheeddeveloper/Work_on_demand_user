package com.rizorsiumani.workondemanduser.ui.welcome_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityWelcomeUserBinding;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class WelcomeUser extends BaseActivity<ActivityWelcomeUserBinding> {

    @Override
    protected ActivityWelcomeUserBinding getActivityBinding() {
        return ActivityWelcomeUserBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.btnCommercial.setOnClickListener(view -> {
            TinyDbManager.saveUserType("Commercial");
            ActivityUtil.gotoPage(WelcomeUser.this, Login.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.btnResidential.setOnClickListener(view -> {
            TinyDbManager.saveUserType("Residential");
            ActivityUtil.gotoPage(WelcomeUser.this, Login.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        finishAffinity();
    }
}