package com.rizorsiumani.workondemanduser.ui.view_booking_information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingInformationBinding;

import java.util.ArrayList;

public class BookingInformation extends BaseActivity<ActivityBookingInformationBinding> {

        BookingInfoViewModel viewModel;
        String bookingID;

    @Override
    protected ActivityBookingInformationBinding getActivityBinding() {
        return ActivityBookingInformationBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel = new ViewModelProvider(this).get(BookingInfoViewModel.class);
        String token = prefRepository.getString("token");

        try {
            bookingID = getIntent().getStringExtra("booking_id");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        viewModel.getBookingInformation(token, Integer.parseInt(bookingID));
        viewModel._detail.observe(this , response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();

                }
            }
        });

    }
}