package com.rizorsiumani.workondemanduser.ui.search;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivitySearchServicesBinding;

public class SearchServices extends BaseActivity<ActivitySearchServicesBinding> {

    @Override
    protected ActivitySearchServicesBinding getActivityBinding() {
        return ActivitySearchServicesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.searchToolbar.title.setText("Search");
        clickListeners();

    }

    private void clickListeners() {
        activityBinding.searchToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }
}