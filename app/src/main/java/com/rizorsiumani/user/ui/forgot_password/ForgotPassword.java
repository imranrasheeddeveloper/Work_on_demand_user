package com.rizorsiumani.user.ui.forgot_password;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Patterns;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.ActivityForgotPasswordBinding;
import com.rizorsiumani.user.utils.Constants;

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
                            Intent intent = new Intent(ForgotPassword.this, VerifyForgotPasswordCode.class);
                            intent.putExtra("verification_email", email);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                        }
                    }
                });
            }

        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel._password.removeObservers(this);
        viewModel = null;
    }
}