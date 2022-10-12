package com.rizorsiumani.workondemanduser.ui.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityLoginBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.register.Register;
import com.rizorsiumani.workondemanduser.ui.walkthrough.OnBoardingViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;

public class Login extends BaseActivity<ActivityLoginBinding> {

    private LoginViewModel viewModel;

    @Override
    protected ActivityLoginBinding getActivityBinding() {
        return ActivityLoginBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);



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

            String email = activityBinding.edEmail.getText().toString().trim();
            String password = activityBinding.edPassword.getText().toString().trim();
            loginApi(email,password);

        });

    }

    private void loginApi(String email, String password) {

        JsonObject object = new JsonObject();
        object.addProperty("email", email);
        object.addProperty("password", password);

        if (viewModel._loginData.getValue() == null){
            viewModel.login(object);
        }

        viewModel._loginData.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    //we have error to show
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                    ActivityUtil.gotoPage(Login.this, Dashboard.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });




    }

}