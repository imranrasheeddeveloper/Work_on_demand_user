package com.rizorsiumani.workondemanduser.ui.booking;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookServiceBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

public class BookService extends BaseActivity<ActivityBookServiceBinding> {

    @Override
    protected ActivityBookServiceBinding getActivityBinding() {
        return ActivityBookServiceBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.bookToolbar.title.setText("Book Now");
        clickListeners();

    }

    private void clickListeners() {

        activityBinding.bookToolbar.back.setOnClickListener(view -> {
            prefRepository.setString("cart", String.valueOf(false));
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        activityBinding.btnAddItem.setOnClickListener(view -> {
            prefRepository.setString("cart", String.valueOf(true));
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

    }
}