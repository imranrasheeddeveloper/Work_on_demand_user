package com.rizorsiumani.workondemanduser.ui.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityLoginBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.forgot_password.ForgotPassword;
import com.rizorsiumani.workondemanduser.ui.register.Register;
import com.rizorsiumani.workondemanduser.ui.walkthrough.OnBoardingViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.net.Socket;
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

        viewModel = new ViewModelProvider(Login.this).get(LoginViewModel.class);



        clickListeners();
    }

    @SuppressLint("ResourceAsColor")
    private void clickListeners() {
        activityBinding.tvRegister.setOnClickListener(view -> {
            ActivityUtil.gotoPage(Login.this, Register.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.textForgot.setOnClickListener(view -> {
            ActivityUtil.gotoPage(Login.this, ForgotPassword.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.tvPhone.setOnClickListener(view -> {
            activityBinding.loginByEmail.setTag("gone");
            activityBinding.loginByNumber.setTag("visible");

            activityBinding.tvEmail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DDDDDD")));
            activityBinding.tvEmail.setTextColor(Color.parseColor("#FF000000"));

            activityBinding.tvPhone.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
            activityBinding.tvPhone.setTextColor(Color.parseColor("#FFFFFFFF"));

            activityBinding.loginByNumber.setVisibility(View.VISIBLE);
            activityBinding.loginByEmail.setVisibility(View.GONE);


        });

        activityBinding.tvEmail.setOnClickListener(view -> {
            activityBinding.loginByEmail.setTag("visible");
            activityBinding.loginByNumber.setTag("gone");

            activityBinding.tvPhone.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DDDDDD")));
            activityBinding.tvPhone.setTextColor(Color.parseColor("#FF000000"));

            activityBinding.tvEmail.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#00A688")));
            activityBinding.tvEmail.setTextColor(Color.parseColor("#FFFFFFFF"));

            activityBinding.loginByNumber.setVisibility(View.GONE);
            activityBinding.loginByEmail.setVisibility(View.VISIBLE);

        });

        activityBinding.btnLogin.setOnClickListener(view -> {

            try {
       
            if (activityBinding.loginByNumber.getTag().equals("visible")){
                LoginByNumber();
            }else if (activityBinding.loginByEmail.getTag().equals("visible")){
                LoginByEmail();
            }else {
                
            }


            }catch (IllegalStateException| NullPointerException e){
                e.printStackTrace();
            }



        });

    }

    private void LoginByNumber() {
        String number = activityBinding.edNumber.getText().toString().trim();
        String password = activityBinding.edPassword1.getText().toString().trim();

        if (TextUtils.isEmpty(number)) {
            showSnackBarShort("Enter Number");
        } else if (TextUtils.isEmpty(password)) {
            showSnackBarShort("Enter Password");
        } else {
            loginApiByNumber("+92"+ number, password);
        }
    }

    private void loginApiByNumber(String number, String password) {
        JsonObject object = new JsonObject();
        object.addProperty("phoneNumber", number);
        object.addProperty("password", password);
        object.addProperty("fcm_token" , Constants.constant.FCM_TOKEN);
        object.addProperty("type","individual");

        viewModel.login(object);

        viewModel._loginData.observe(Login.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    prefRepository.setString("token" , "Bearer "+response.getData().getToken());
                    TinyDbManager.saveUserData(response.getData().getData());


                    ActivityUtil.gotoPage(Login.this, Dashboard.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else {
                    hideLoading();
                }
            }
        });

    }

    private void LoginByEmail(){
        String email = activityBinding.edEmail.getText().toString().trim();
        String password = activityBinding.edPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            showSnackBarShort("Enter Email");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showSnackBarShort("Valid Email Required");
        } else if (TextUtils.isEmpty(password)) {
            showSnackBarShort("Enter Password");
        } else {
            loginApi(email, password);
        }
    }
    private void loginApi(String email, String password) {

        JsonObject object = new JsonObject();
        object.addProperty("email", email);
        object.addProperty("password", password);
        object.addProperty("fcm_token" , Constants.constant.FCM_TOKEN);
        object.addProperty("type","individual");

        viewModel.login(object);

//        if (viewModel._loginData.getValue() == null){
//            viewModel.login(object);
//        }

        viewModel._loginData.observe(Login.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    prefRepository.setString("token" , "Bearer "+response.getData().getToken());
                    TinyDbManager.saveUserData(response.getData().getData());


                    ActivityUtil.gotoPage(Login.this, Dashboard.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


        viewModel._loginData.removeObservers(this);
        viewModel = null;
    }
}