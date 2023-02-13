package com.rizorsiumani.workondemanduser.ui.forgot_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityForgotPasswordBinding;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class ForgotPassword extends BaseActivity<ActivityForgotPasswordBinding> {

    ForgotPasswordViewModel viewModel;

    @Override
    protected ActivityForgotPasswordBinding getActivityBinding() {
        return ActivityForgotPasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideCartButton();
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);

        activityBinding.forgotPassToolbar.title.setText("Recover Password");
        clickListeners();
    }

    private void clickListeners() {

        activityBinding.forgotPassToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.resetBtn.setOnClickListener(view -> {
            String email = activityBinding.edEmail.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                showSnackBarShort("Enter email");
            }else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                showSnackBarShort("Enter valid Email Address");
            }else {
                viewModel.sendEmail(email);
                viewModel._password.observe(this, response -> {
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
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            showSnackBarShort(response.getData().getMessage());
                            Intent intent = new Intent(ForgotPassword.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            view.getContext().startActivity(intent);
                            finish();
                        }
                    }
                });
            }

        });
    }
}