package com.rizorsiumani.workondemanduser.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityRegisterBinding;
import com.rizorsiumani.workondemanduser.ui.commercial_user_info.ComapnyInformation;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.regex.Pattern;

public class Register extends BaseActivity<ActivityRegisterBinding> {

    private RegisterUserViewModel viewModel;

    @Override
    protected ActivityRegisterBinding getActivityBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel = new ViewModelProvider(this).get(RegisterUserViewModel.class);
        activityBinding.edPassword.setTransformationMethod(new PasswordTransformationMethod());

        hideCartButton();
        clickListeners();
    }

    private void clickListeners() {

        activityBinding.btnNext.setOnClickListener(view -> {
            validateInformation();
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

    }

    private void validateInformation() {
        String first_name = activityBinding.edFirstname.getText().toString().trim();
        String last_name = activityBinding.edLastname.getText().toString().trim();
        String email = activityBinding.edEmail.getText().toString().trim();
        String number = "+92"+activityBinding.edNumber.getText().toString().trim();
        String password = activityBinding.edPassword.getText().toString();


        if (TextUtils.isEmpty(first_name)){
            showSnackBarShort("First Name Required");
        }else if (TextUtils.isEmpty(last_name)){
            showSnackBarShort("Last Name Required");
        }else if (TextUtils.isEmpty(email)){
            showSnackBarShort("Email Required");
        }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            showSnackBarShort("Valid Email Required");
        }else if (TextUtils.isEmpty(number)){
            showSnackBarShort("Phone Number Required");
        } else if (!Patterns.PHONE.matcher(number).matches()) {
            showSnackBarShort("Valid Number Required");
        }else if(TextUtils.isEmpty(password)){
            showSnackBarShort("Enter Password");
        }else {
            registerUser(first_name,last_name,email,number,password);
        }
    }

    private void registerUser(String first_name, String last_name, String email, String number,String pass) {
        try {

            JsonObject object = new JsonObject();
            object.addProperty("firstName",first_name);
            object.addProperty("lastName",last_name);
            object.addProperty("email",email);
            object.addProperty("phoneNumber",number);
            object.addProperty("password",pass);
            object.addProperty("fcm_token", Constants.constant.FCM_TOKEN);

            viewModel.registerUser(object);

            viewModel._regData.observe(this, response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData().getData() != null) {
                        hideLoading();
                        ActivityUtil.gotoPage(Register.this, Dashboard.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            });

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }


}