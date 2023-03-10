package com.rizorsiumani.user.ui.welcome_user;

import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivityWelcomeUserBinding;
import com.rizorsiumani.user.ui.login.Login;
import com.rizorsiumani.user.utils.ActivityUtil;

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