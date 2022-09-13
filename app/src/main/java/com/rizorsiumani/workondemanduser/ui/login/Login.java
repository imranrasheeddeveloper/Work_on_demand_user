package com.rizorsiumani.workondemanduser.ui.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityLoginBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.register.Register;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class Login extends BaseActivity<ActivityLoginBinding> {

    @Override
    protected ActivityLoginBinding getActivityBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        clickListeners();
    }

    @SuppressLint("ResourceAsColor")
    private void clickListeners() {
        activityBinding.tvRegister.setOnClickListener(view -> {
            ActivityUtil.gotoPage(Login.this, Register.class);
        });

        activityBinding.tvPhone.setOnClickListener(view -> {

            activityBinding.tvEmail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DDDDDD")));
            activityBinding.tvEmail.setTextColor(Color.parseColor("#FF000000"));

            activityBinding.tvPhone.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
            activityBinding.tvPhone.setTextColor(Color.parseColor("#FFFFFFFF"));

            activityBinding.loginByNumber.setVisibility(View.VISIBLE);
            activityBinding.loginByEmail.setVisibility(View.GONE);


        });

        activityBinding.tvEmail.setOnClickListener(view -> {

            activityBinding.tvPhone.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DDDDDD")));
            activityBinding.tvPhone.setTextColor(Color.parseColor("#FF000000"));

            activityBinding.tvEmail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
            activityBinding.tvEmail.setTextColor(Color.parseColor("#FFFFFFFF"));

            activityBinding.loginByNumber.setVisibility(View.GONE);
            activityBinding.loginByEmail.setVisibility(View.VISIBLE);

        });

        activityBinding.btnLogin.setOnClickListener(view -> {
            ActivityUtil.gotoPage(Login.this, Dashboard.class);
        });

    }

}