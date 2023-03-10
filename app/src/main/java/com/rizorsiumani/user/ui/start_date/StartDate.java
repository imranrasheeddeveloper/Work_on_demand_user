package com.rizorsiumani.user.ui.start_date;

import android.content.Intent;

import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.ActivityStartDateBinding;
import com.rizorsiumani.user.ui.booking_date.BookingDateTime;
import com.rizorsiumani.user.utils.Constants;

public class StartDate extends BaseActivity<ActivityStartDateBinding> {

    String serviceData;
    @Override
    protected ActivityStartDateBinding getActivityBinding() {
        return ActivityStartDateBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.dateToolbar.title.setText("Select Start Date");
        serviceData = getIntent().getStringExtra("service_data");
        activityBinding.datePicker.setMinDate(System.currentTimeMillis() - 1000);

        clickEvents();
    }

    private void clickEvents() {
        activityBinding.dateToolbar.back.setOnClickListener(v -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.btnConfirm.setOnClickListener(v -> {
            int year = activityBinding.datePicker.getYear();
            int month = activityBinding.datePicker.getMonth() + 1;
            int day = activityBinding.datePicker.getDayOfMonth();

            Constants.constant.start_date =  String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", day);

            Intent intent = new Intent(StartDate.this, BookingDateTime.class);
            intent.putExtra("service_data", serviceData);
            startActivity(intent);
        });
    }
}