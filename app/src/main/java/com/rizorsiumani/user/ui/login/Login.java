package com.rizorsiumani.user.ui.login;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivityLoginBinding;
import com.rizorsiumani.user.ui.dashboard.Dashboard;
import com.rizorsiumani.user.ui.forgot_password.ForgotPassword;
import com.rizorsiumani.user.ui.register.Register;
import com.rizorsiumani.user.utils.ActivityUtil;
import com.rizorsiumani.user.utils.Constants;

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


        activityBinding.edPassword.setTransformationMethod(new PasswordTransformationMethod());
        activityBinding.edPassword1.setTransformationMethod(new PasswordTransformationMethod());


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

        activityBinding.showPass.setOnClickListener(v -> {
            if (activityBinding.showPass.getTag().equals("hide")) {
                activityBinding.edPassword.setTransformationMethod(null);
                activityBinding.showPass.setImageResource(R.drawable.show_password);
                activityBinding.showPass.setTag("show");
            } else {
                activityBinding.edPassword.setTransformationMethod(new PasswordTransformationMethod());
                activityBinding.showPass.setImageResource(R.drawable.hide_password);
                activityBinding.showPass.setTag("hide");
            }
        });

        activityBinding.showPass1.setOnClickListener(v -> {
            if (activityBinding.showPass1.getTag().equals("hide")) {
                activityBinding.edPassword1.setTransformationMethod(null);
                activityBinding.showPass1.setImageResource(R.drawable.show_password);
                activityBinding.showPass1.setTag("show");
            } else {
                activityBinding.edPassword1.setTransformationMethod(new PasswordTransformationMethod());
                activityBinding.showPass1.setImageResource(R.drawable.hide_password);
                activityBinding.showPass1.setTag("hide");
            }
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
        object.addProperty("phone_number", number);
        object.addProperty("password", password);
        object.addProperty("fcm_token" , Constants.constant.FCM_TOKEN);
        object.addProperty("type",TinyDbManager.getUserType());
        viewModel.login(object);

        viewModel._loginData.observe(Login.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    prefRepository.setString("SourceID",response.getData().getData().getChatwoot_source_id());
                    prefRepository.setString("ContactID",response.getData().getData().getChatwoot_contact_id());

                    prefRepository.setString("token" , "Bearer "+response.getData().getToken());
                    TinyDbManager.saveUserData(response.getData().getData());
                    ActivityUtil.gotoPage(Login.this, Dashboard.class);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }else {
                    hideLoading();
                    showSnackBarShort(response.getData().getMessage());

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
        object.addProperty("type",TinyDbManager.getUserType());

        viewModel.login(object);

//        if (viewModel._loginData.getValue() == null){
//            viewModel.login(object);
//        }

        viewModel._loginData.observe(Login.this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    prefRepository.setString("SourceID",response.getData().getData().getChatwoot_source_id());
                    prefRepository.setString("ContactID",response.getData().getData().getChatwoot_contact_id());
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