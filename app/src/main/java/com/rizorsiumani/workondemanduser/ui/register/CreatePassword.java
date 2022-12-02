package com.rizorsiumani.workondemanduser.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityCreatePasswordBinding;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.ui.login.LoginViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

public class CreatePassword extends BaseActivity<ActivityCreatePasswordBinding> {

    private RegisterUserViewModel viewModel;

    @Override
    protected ActivityCreatePasswordBinding getActivityBinding() {
        return ActivityCreatePasswordBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel = new ViewModelProvider(this).get(RegisterUserViewModel.class);

        clickListeners();
    }

    private void clickListeners() {

        activityBinding.btnNext.setOnClickListener(view -> {
            String password = activityBinding.edPassword.getText().toString();
            if (password.equals(activityBinding.edConfirmPass.getText().toString())){
                registerUser(password);
            }else {
                showSnackBarShort("Invalid entry");
            }
        });
    }

    private void registerUser(String password) {
        try {

            String first_name = getIntent().getStringExtra("first_name");
            String last_name = getIntent().getStringExtra("last_name");
            String email_address = getIntent().getStringExtra("email");
            String phone_number = getIntent().getStringExtra("number");

            JsonObject object = new JsonObject();
            object.addProperty("first_name",first_name);
            object.addProperty("last_name",last_name);
            object.addProperty("email",email_address);
            object.addProperty("phone_number",phone_number);
            object.addProperty("password",password);
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
                        ActivityUtil.gotoPage(CreatePassword.this, Dashboard.class);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                }
            });

        }catch (NullPointerException e){
            e.printStackTrace();
        }



    }
}