package com.rizorsiumani.workondemanduser.ui.forgot_password;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityVerifyForgotPasswordCodeBinding;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class VerifyForgotPasswordCode extends BaseActivity<ActivityVerifyForgotPasswordCodeBinding> {

    ForgotPasswordViewModel viewModel;
    String email;

    @Override
    protected ActivityVerifyForgotPasswordCodeBinding getActivityBinding() {
        return ActivityVerifyForgotPasswordCodeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideCartButton();
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        email = getIntent().getStringExtra("verification_email");

        activityBinding.verifyCodeToolbar.title.setText("Verification");
        clickListeners();

    }

    private void clickListeners() {

        activityBinding.verifyCodeToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.verifyBtn.setOnClickListener(view -> {
            String code = activityBinding.edCode.getText().toString().trim();
            if (TextUtils.isEmpty(code)) {
                showSnackBarShort("Enter Code");
            }else {

                viewModel.verifyEmail(email, Integer.parseInt(code));
                viewModel._verify.observe(this, response -> {
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
                            Intent intent = new Intent(VerifyForgotPasswordCode.this, NewPassword.class);
                            intent.putExtra("verification_email", email);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);

                        }
                    }
                });
            }

        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel._verify.removeObservers(this);
        viewModel = null;
    }
}