package com.rizorsiumani.workondemanduser.ui.welcome_user;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
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
            ActivityUtil.gotoPage(WelcomeUser.this, Login.class);
        });

        activityBinding.btnResidential.setOnClickListener(view -> {
            ActivityUtil.gotoPage(WelcomeUser.this, Login.class);
        });


    }


}