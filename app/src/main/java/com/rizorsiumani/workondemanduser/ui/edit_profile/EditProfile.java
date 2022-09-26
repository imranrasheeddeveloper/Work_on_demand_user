package com.rizorsiumani.workondemanduser.ui.edit_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityEditProfileBinding;

public class EditProfile extends BaseActivity<ActivityEditProfileBinding> {

    @Override
    protected ActivityEditProfileBinding getActivityBinding() {
        return ActivityEditProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.editProfileToolbar.title.setText("Edit Profile");
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.editProfileToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }
}