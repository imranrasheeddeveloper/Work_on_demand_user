package com.rizorsiumani.workondemanduser.ui.forgot_password;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityForgotPasswordBinding;
import com.rizorsiumani.workondemanduser.ui.login.Login;

public class ForgotPassword extends BaseActivity<ActivityForgotPasswordBinding> {

    @Override
    protected ActivityForgotPasswordBinding getActivityBinding() {
        return ActivityForgotPasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideCartButton();

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
            Toast.makeText(view.getContext(), "Your password is reset, check out your email", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ForgotPassword.this, Login.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            view.getContext().startActivity(intent);
            finish();
        });
    }
}