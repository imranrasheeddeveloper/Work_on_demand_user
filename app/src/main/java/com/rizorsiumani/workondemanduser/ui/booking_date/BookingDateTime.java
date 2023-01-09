package com.rizorsiumani.workondemanduser.ui.booking_date;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.common.BookingTimingItem;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.AvailabilityHoursItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingDateTimeBinding;
import com.rizorsiumani.workondemanduser.ui.booking.BookService;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ProviderDetailViewModel;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BookingDateTime extends BaseActivity<ActivityBookingDateTimeBinding> {


    String selectedDate = "";
    String selectedTime = "";
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    ProviderDetailViewModel viewModel;
    List<AvailabilityHoursItem> hoursList;
    List<AvailabilityDataItem> list;
    List<String> daysList;
    String bookingID, availabilityID, spID;
    BookingScheduleViewModel scheduleViewModel;
    String serviceData;
    TimeSlotsAdapter timeSlotsAdapter;
    BookingTimingItem bookingTimingItem;

    @Override
    protected ActivityBookingDateTimeBinding getActivityBinding() {
        return ActivityBookingDateTimeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();


        activityBinding.searchedToolbar.title.setText("Select Hours");
        spID = TinyDbManager.getServiceProviderID();
        serviceData = getIntent().getStringExtra("service_data");

        if (getIntent().getStringExtra("booking_id") != null) {
            bookingID = getIntent().getStringExtra("booking_id");
            availabilityID = getIntent().getStringExtra("availability_id");
        }


        viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
        scheduleViewModel = new ViewModelProvider(this).get(BookingScheduleViewModel.class);

        if (viewModel._available.getValue() == null) {
            viewModel.getAvailability(Integer.parseInt(spID));
        }

        viewModel._available.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                     hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                     hideLoading();
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
            try {

            if (TinyDbManager.getBookingTiming().size() > 0) {

                if (bookingID != null) {
                    String token = prefRepository.getString("token");
                    JsonObject object = new JsonObject();
                    object.addProperty("id", bookingID);
//                  object.addProperty("availability_id", selectedHours);
                    scheduleViewModel.get(token, object);
                    scheduleViewModel._reschedule.observe(this, response -> {
                        if (response != null) {
                            if (response.isLoading()) {
                                showLoading();
                            } else if (!response.getError().isEmpty()) {
                                hideLoading();
                                showSnackBarShort(response.getError());
                            } else if (response.getData().isSuccess()) {
                                hideLoading();
                                Toast.makeText(BookingDateTime.this, response.getData().getMessage(), Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(BookingDateTime.this, Dashboard.class);
                                intent.putExtra("Navigation", "Booking");
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

                            }

                        }
                    });
                } else {
//                    Intent intent = new Intent(BookingDateTime.this, BookingDetail.class);
//                    startActivity(intent);
//                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    Intent intent = new Intent(BookingDateTime.this, BookService.class);
                    intent.putExtra("service_data",serviceData);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }else {
                showSnackBarShort("Select Service Hours");
            }


            }catch (NullPointerException e){
                e.printStackTrace();
            }
        });

        activityBinding.searchedToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
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
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });

        cancel.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDateTime.this, Dashboard.class);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }


    private void daysRv(List<AvailabilityDataItem> list) {

        LinearLayoutManager llm = new LinearLayoutManager(BookingDateTime.this, RecyclerView.HORIZONTAL, false);
        activityBinding.daysList.setLayoutManager(llm);
        DayAndDateAdapter adapter = new DayAndDateAdapter(BookingDateTime.this, list);
        activityBinding.daysList.setAdapter(adapter);

        activityBinding.daysList.postDelayed(() -> Objects.requireNonNull(activityBinding.daysList.findViewHolderForAdapterPosition(0)).itemView.performClick(),100);


        adapter.setOnDaySelectListener((position) -> {
            if (list.get(position).getAvailabilityHours().size() > 0) {
                activityBinding.timeList.setVisibility(View.GONE);
                showLoading();
                timeSlotsAdapter.selectedPosition = -1;
                hoursList = new ArrayList<>();
                hoursList.addAll(list.get(position).getAvailabilityHours());
                timeSlotsRv(hoursList,list.get(position).getDay());
            }
        });

    }

    private void timeSlotsRv(List<AvailabilityHoursItem> hoursList, String day) {

        GridLayoutManager layoutManager = new GridLayoutManager(BookingDateTime.this, 3);
        activityBinding.timeList.setLayoutManager(layoutManager);
        timeSlotsAdapter = new TimeSlotsAdapter(BookingDateTime.this, hoursList);
        activityBinding.timeList.setAdapter(timeSlotsAdapter);
        activityBinding.timeList.setVisibility(View.VISIBLE);
        hideLoading();

        timeSlotsAdapter.setOnSlotClickListener(new TimeSlotsAdapter.OnItemClickListener() {
            @Override
            public void onTimeSelect(int position) {
                bookingTimingItem = new BookingTimingItem(hoursList.get(position).getId(),
                        hoursList.get(position).getFromTime(),
                        String.valueOf(hoursList.get(position).getTotalHours()),
                        day,
                        hoursList.get(position).getToTime());
                TinyDbManager.saveBookingTiming(bookingTimingItem);
            }

            @Override
            public void onTimeUnselect(int position) {
                bookingTimingItem = new BookingTimingItem(hoursList.get(position).getId(),
                        hoursList.get(position).getFromTime(),
                        String.valueOf(hoursList.get(position).getTotalHours()),
                        day,
                        hoursList.get(position).getToTime());
                TinyDbManager.removeBookingTiming(bookingTimingItem);

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        viewModel._available.removeObservers(this);
        scheduleViewModel._reschedule.removeObservers(this);

        viewModel = null;
        scheduleViewModel = null;
    }


}