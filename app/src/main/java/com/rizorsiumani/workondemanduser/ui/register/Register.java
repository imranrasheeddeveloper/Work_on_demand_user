package com.rizorsiumani.workondemanduser.ui.register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Patterns;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityRegisterBinding;
import com.rizorsiumani.workondemanduser.ui.commercial_user_info.ComapnyInformation;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.regex.Pattern;

public class Register extends BaseActivity<ActivityRegisterBinding> {

    @Override
    protected ActivityRegisterBinding getActivityBinding() {
        return ActivityRegisterBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        clickListeners();
    }

    private void clickListeners() {

        activityBinding.btnNext.setOnClickListener(view -> {
            validateInformation();
        });
    }

    private void validateInformation() {
        String first_name = activityBinding.edFirstname.getText().toString().trim();
        String last_name = activityBinding.edLastname.getText().toString().trim();
        String email = activityBinding.edEmail.getText().toString().trim();
        String number = "+92"+activityBinding.edNumber.getText().toString().trim();


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
        }else {
            createPassword(first_name,last_name,email,number);
        }

    }

    private void createPassword(String first_name, String last_name, String email, String number) {
        Intent intent = new Intent(Register.this,CreatePassword.class);
        intent.putExtra("first_name",first_name);
        intent.putExtra("last_name",last_name);
        intent.putExtra("email",email);
        intent.putExtra("number",number);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);


    }


}