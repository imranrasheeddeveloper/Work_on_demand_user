package com.rizorsiumani.workondemanduser.ui.booking_date;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityHoursItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingDateTimeBinding;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ProviderDetailViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class BookingDateTime extends BaseActivity<ActivityBookingDateTimeBinding> {


    String selectedDate = "";
    String selectedTime = "";
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    ProviderDetailViewModel viewModel;
    List<AvailabilityHoursItem> hoursList;
    List<AvailabilityDataItem> list;
    List<String> daysList;
    String bookingID, availabilityID,spID;

    @Override
    protected ActivityBookingDateTimeBinding getActivityBinding() {
        return ActivityBookingDateTimeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        activityBinding.searchedToolbar.title.setText("Select Hours");
         spID = getIntent().getStringExtra("service_provider_id");
        if (getIntent().getStringExtra("booking_id") != null){
             bookingID = getIntent().getStringExtra("booking_id");
             availabilityID = getIntent().getStringExtra("availability_id");
        }


        viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
        if (viewModel._available.getValue() == null) {
            viewModel.getAvailability(Integer.parseInt(spID));
        }

        viewModel._available.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    //showLoading();
                } else if (!response.getError().isEmpty()) {
                    // hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    // hideLoading();
                    if (response.getData().getData().size() > 0) {
                        list = new ArrayList<>();
                        list.addAll(response.getData().getData());
                        daysRv(list);
                    } else {

                    }
                }
            }
        });

        clickListeners();
    }


    private void clickListeners() {
        activityBinding.btnContinue.setOnClickListener(view -> {
            if (!selectedTime.isEmpty()){

                if (bookingID != null){
                    //todo
                    //reschedule api
                }else {
                    Intent intent = new Intent(BookingDateTime.this, BookingDetail.class);
                    intent.putExtra("service_provider_id", spID);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        activityBinding.searchedToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }

    private void showRequestedDialogue() {
        dialogBuilder = new AlertDialog.Builder(BookingDateTime.this);
        View layoutView = getLayoutInflater().inflate(R.layout.booking_request_complete_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.cancel_dialogue);
        Button booking_ = (Button) layoutView.findViewById(R.id.view_booking);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        cancel.setOnClickListener(view -> alertDialog.dismiss());
        booking_.setOnClickListener(view -> {
            Intent intent = new Intent(BookingDateTime.this, Dashboard.class);
            intent.putExtra("Navigation", "Booking");
            startActivity(intent);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

        });

        cancel.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDateTime.this, Dashboard.class);
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
    }



    private void daysRv(List<AvailabilityDataItem> list) {

        LinearLayoutManager llm = new LinearLayoutManager(BookingDateTime.this, RecyclerView.HORIZONTAL, false);
        activityBinding.daysList.setLayoutManager(llm);
        DayAndDateAdapter adapter = new DayAndDateAdapter(BookingDateTime.this,list);
        activityBinding.daysList.setAdapter(adapter);

        adapter.setOnDaySelectListener((position) -> {
            if (list.get(position).getAvailabilityHours().size() > 0) {
                hoursList = new ArrayList<>();
                hoursList.addAll(list.get(position).getAvailabilityHours());
                timeSlotsRv(hoursList);
            }
        });

    }

    private void timeSlotsRv(List<AvailabilityHoursItem> hoursList) {
//
//        List<String> slots = new ArrayList<>();
//        slots.add("12AM - 01AM");
//        slots.add("01AM - 02AM");
//        slots.add("02AM - 03AM");
//        slots.add("03AM - 04AM");
//        slots.add("04AM - 05AM");
//        slots.add("05AM - 06AM");
//        slots.add("06AM - 07AM");
//        slots.add("07AM - 08AM");
//        slots.add("08AM - 09AM");
//        slots.add("09AM - 10AM");
//        slots.add("10AM - 11AM");
//        slots.add("11AM - 12PM");
//        slots.add("12PM - 01PM");
//        slots.add("01PM - 02PM");
//        slots.add("02PM - 03PM");
//        slots.add("03PM - 04PM");
//        slots.add("04PM - 05PM");
//        slots.add("05PM - 06PM");
//        slots.add("06PM - 07PM");
//        slots.add("07PM - 08PM");
//        slots.add("08PM - 09PM");
//        slots.add("09PM - 10PM");
//        slots.add("10PM - 11PM");
//        slots.add("11PM - 12AM");
        GridLayoutManager layoutManager = new GridLayoutManager(App.applicationContext, 3);
        activityBinding.timeList.setLayoutManager(layoutManager);
        TimeSlotsAdapter adapter = new TimeSlotsAdapter(BookingDateTime.this,hoursList);
        activityBinding.timeList.setAdapter(adapter);

        adapter.setOnSlotClickListener(position -> {
            Constants.availability_id = String.valueOf(hoursList.get(position).getId());
            //Toast.makeText(this, hoursList.get(position).getId(), Toast.LENGTH_SHORT).show();


        });
    }
}