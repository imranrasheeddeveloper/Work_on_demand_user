package com.rizorsiumani.workondemanduser.ui.promo_code;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityPromoCodeBinding;

public class PromoCode extends BaseActivity<ActivityPromoCodeBinding> {

    PromoCodeAdapter adapter;

    @Override
    protected ActivityPromoCodeBinding getActivityBinding() {
        return ActivityPromoCodeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.promoToolbar.title.setText("Promo Code");
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.promoToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }
}