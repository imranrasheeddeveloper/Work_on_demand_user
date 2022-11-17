package com.rizorsiumani.workondemanduser.ui.view_booking_information;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.BookingDetailData;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingInformationBinding;
import com.rizorsiumani.workondemanduser.utils.Constants;

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

        activityBinding.bookingInfoToolbar.title.setText("Booking Details");
        activityBinding.bookingInfoToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        viewModel = new ViewModelProvider(this).get(BookingInfoViewModel.class);
        String token = prefRepository.getString("token");
        showLoading();

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
                    setData(response.getData().getData());
                }
            }
        });

    }

    private void setData(BookingDetailData data) {

        try {

        Glide.with(BookingInformation.this)
                .load(Constants.IMG_PATH + data.getServiceProvider().getProfilePhoto())
                        .into(activityBinding.ivSp);
            activityBinding.budgetUnit.setText(data.getService().getPriceUnit());
            activityBinding.bookingTitle.setText(data.getService().getTitle());
            activityBinding.total.setText(Constants.constant.CURRENCY + data.getTotal());
        activityBinding.tvSpName.setText(data.getServiceProvider().getFirstName() + " " + data.getServiceProvider().getLastName() );
        activityBinding.orderNumber.setText(String.valueOf(data.getId()));
        activityBinding.orderFrom.setText(data.getAddress());
        activityBinding.serviceName.setText(data.getService().getTitle());
        activityBinding.serviceRate.setText(Constants.constant.CURRENCY + data.getService().getPrice());
        activityBinding.serviceDetail.setText(data.getService().getDescription());
        activityBinding.deliveryAddress.setText(data.getAddress());
        activityBinding.subTotal.setText(Constants.constant.CURRENCY + data.getSubTotal());
        if (data.getPromotion() != null){
            activityBinding.voucherDiscount.setText( "-" + Constants.constant.CURRENCY + data.getDiscount());
            activityBinding.tvVoucher.setText("Voucher :" + String.valueOf(data.getPromotion().getCode()));
        }else {
            activityBinding.voucherDiscount.setVisibility(View.GONE);
            activityBinding.tvVoucher.setVisibility(View.GONE);
        }


        activityBinding.informationView.setVisibility(View.VISIBLE);
        activityBinding.skeletonLayout.setVisibility(View.GONE);


        }catch (NullPointerException | NumberFormatException | IllegalStateException e){
            e.printStackTrace();
        }


    }
}