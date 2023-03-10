package com.rizorsiumani.user.ui.forgot_password;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.text.TextUtils;

import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.ActivityVerifyForgotPasswordCodeBinding;
import com.rizorsiumani.user.utils.Constants;

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
        viewModel._verify.removeObservers(this);
        viewModel = null;
    }
}