package com.rizorsiumani.workondemanduser.ui.view_booking_information;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.SnapHelper;

import android.view.View;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.booking_detail.BookingDetailData;
import com.rizorsiumani.workondemanduser.data.businessModels.booking_detail.BookingTimingsItem;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingInformationBinding;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.ui.job_timing.AvailabilitiesItem;
import com.rizorsiumani.workondemanduser.ui.job_timing.TimeItem;
import com.rizorsiumani.workondemanduser.ui.sp_detail.availability.AvailabilityAdapter;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;

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
            activityBinding.voucherDiscount.setVisibility(View.GONE);
            activityBinding.tvVoucher.setVisibility(View.GONE);
//            activityBinding.voucherDiscount.setText( "-" + Constants.constant.CURRENCY + data.getDiscount());
//            activityBinding.tvVoucher.setText("Voucher :" + String.valueOf(data.getPromotion().g()));
        }else {
            activityBinding.voucherDiscount.setVisibility(View.GONE);
            activityBinding.tvVoucher.setVisibility(View.GONE);
        }


        activityBinding.informationView.setVisibility(View.VISIBLE);
        activityBinding.skeletonLayout.setVisibility(View.GONE);

        if (data.getBookingTimings() != null){
            List<AvailabilitiesItem> list = new ArrayList<>();
            List<TimeItem> timeItemList = new ArrayList<>();
            for (int i = 0; i < data.getBookingTimings().size(); i++) {
                BookingTimingsItem bookingTimingsItem = data.getBookingTimings().get(i);
                if (bookingTimingsItem.getDay().equalsIgnoreCase("Monday")) {
                    timeItemList.add(new TimeItem(Integer.parseInt(bookingTimingsItem.getTotalHours()), bookingTimingsItem.getFromTime(), bookingTimingsItem.getToTime()));
                    list.add(0,new AvailabilitiesItem(timeItemList, bookingTimingsItem.getDay()));
                }else if (bookingTimingsItem.getDay().equalsIgnoreCase("Tuesday")) {
                    timeItemList.add(new TimeItem(Integer.parseInt(bookingTimingsItem.getTotalHours()), bookingTimingsItem.getFromTime(), bookingTimingsItem.getToTime()));
                    list.add(1, new AvailabilitiesItem(timeItemList, bookingTimingsItem.getDay()));
                }else if (bookingTimingsItem.getDay().equalsIgnoreCase("Wednesday")) {
                    timeItemList.add(new TimeItem(Integer.parseInt(bookingTimingsItem.getTotalHours()), bookingTimingsItem.getFromTime(), bookingTimingsItem.getToTime()));
                    list.add(2, new AvailabilitiesItem(timeItemList, bookingTimingsItem.getDay()));
                }else if (bookingTimingsItem.getDay().equalsIgnoreCase("Thursday")) {
                    timeItemList.add(new TimeItem(Integer.parseInt(bookingTimingsItem.getTotalHours()), bookingTimingsItem.getFromTime(), bookingTimingsItem.getToTime()));
                    list.add(3, new AvailabilitiesItem(timeItemList, bookingTimingsItem.getDay()));
                }else if (bookingTimingsItem.getDay().equalsIgnoreCase("Friday")) {
                    timeItemList.add(new TimeItem(Integer.parseInt(bookingTimingsItem.getTotalHours()), bookingTimingsItem.getFromTime(), bookingTimingsItem.getToTime()));
                    list.add(4, new AvailabilitiesItem(timeItemList, bookingTimingsItem.getDay()));
                }else if (bookingTimingsItem.getDay().equalsIgnoreCase("Saturday")) {
                    timeItemList.add(new TimeItem(Integer.parseInt(bookingTimingsItem.getTotalHours()), bookingTimingsItem.getFromTime(), bookingTimingsItem.getToTime()));
                    list.add(5, new AvailabilitiesItem(timeItemList, bookingTimingsItem.getDay()));
                }else if (bookingTimingsItem.getDay().equalsIgnoreCase("Sunday")) {
                    timeItemList.add(new TimeItem(Integer.parseInt(bookingTimingsItem.getTotalHours()), bookingTimingsItem.getFromTime(), bookingTimingsItem.getToTime()));
                    list.add(6, new AvailabilitiesItem(timeItemList, bookingTimingsItem.getDay()));
                }else {

                }
            }

            buildTimingRv(list);
        }


        }catch (NullPointerException | NumberFormatException | IllegalStateException e){
            e.printStackTrace();
        }


    }

    private void buildTimingRv(List<AvailabilitiesItem> list) {
        GridLayoutManager layoutManager = new GridLayoutManager(BookingInformation.this, 3);
        activityBinding.availableTimeList.setLayoutManager(layoutManager);
        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(activityBinding.availableTimeList);
        BookingTimingAdapter adapter = new BookingTimingAdapter(list, BookingInformation.this);
        activityBinding.availableTimeList.setAdapter(adapter);
    }
}