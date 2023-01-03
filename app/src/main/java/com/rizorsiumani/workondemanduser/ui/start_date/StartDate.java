package com.rizorsiumani.workondemanduser.ui.start_date;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.common.BookingTimingItem;
import com.rizorsiumani.workondemanduser.databinding.ActivityStartDateBinding;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.utils.Constants;

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