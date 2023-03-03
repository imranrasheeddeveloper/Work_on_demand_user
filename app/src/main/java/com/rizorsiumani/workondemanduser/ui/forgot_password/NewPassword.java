package com.rizorsiumani.workondemanduser.ui.forgot_password;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.lifecycle.ViewModelProvider;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityNewPasswordBinding;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class NewPassword extends BaseActivity<ActivityNewPasswordBinding> {

    ForgotPasswordViewModel viewModel;
    String email;

    @Override
    protected ActivityNewPasswordBinding getActivityBinding() {
        return ActivityNewPasswordBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        hideCartButton();
        viewModel = new ViewModelProvider(this).get(ForgotPasswordViewModel.class);
        email = getIntent().getStringExtra("verification_email");

        activityBinding.newPasswordToolbar.title.setText("Create Password");
        clickListeners();

    }

    private void clickListeners() {

        activityBinding.newPasswordToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.btnConfirmPassword.setOnClickListener(view -> {
            String pass = activityBinding.edPassword.getText().toString().trim();
            String confirm_pass = activityBinding.edConfirmPassword.getText().toString().trim();

            if (TextUtils.isEmpty(pass)) {
                showSnackBarShort("Enter Password");
            }else if (TextUtils.isEmpty(confirm_pass)) {
                showSnackBarShort("Enter Confirmed Password");
            }else if (!pass.equals(confirm_pass)) {
                showSnackBarShort("Password doesn't match");
            }else {
                viewModel.reset_password(email, pass);
                viewModel._pass_reset.observe(this, response -> {
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
                            resetDialogue();
                        }
                    }
                });
            }

        });
    }

    private void resetDialogue() {
        AlertDialog.Builder dialogBuilder;
        AlertDialog alertDialog;
        dialogBuilder = new AlertDialog.Builder(NewPassword.this);
        View layoutView = getLayoutInflater().inflate(R.layout.reset_password_dialogue, null);
        Button login = (Button) layoutView.findViewById(R.id.btn_login01);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        login.setOnClickListener(view -> {
            Intent intent = new Intent(NewPassword.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_up, R.anim.slide_down);
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewModel._pass_reset.removeObservers(this);
        viewModel = null;
    }
}