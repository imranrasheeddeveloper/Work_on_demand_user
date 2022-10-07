package com.rizorsiumani.workondemanduser.ui.booking_date;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingDateTimeBinding;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookingDateTime extends BaseActivity<ActivityBookingDateTimeBinding> {


    String selectedDate = "";
    String selectedTime = "";
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

    @Override
    protected ActivityBookingDateTimeBinding getActivityBinding() {
        return ActivityBookingDateTimeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        daysRv();
        timeSlotsRv();
        clickListeners();
    }

    private void clickListeners() {
        activityBinding.btnContinue.setOnClickListener(view -> {
            if (selectedTime.isEmpty()){
                Toast.makeText(this, "Select Time", Toast.LENGTH_SHORT).show();
            } else if (selectedDate.isEmpty()) {
                Toast.makeText(this, "Select Date", Toast.LENGTH_SHORT).show();
            }else {
               showRequestedDialogue();
            }
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



    private void daysRv() {
        List<String> date = new ArrayList<>();
        date.add("05 Oct");
        date.add("05 Oct");
        date.add("05 Oct");
        date.add("05 Oct");
        date.add("05 Oct");
        date.add("05 Oct");
        date.add("05 Oct");


        LinearLayoutManager llm = new LinearLayoutManager(BookingDateTime.this, RecyclerView.HORIZONTAL, false);
        activityBinding.daysList.setLayoutManager(llm);
        DayAndDateAdapter adapter = new DayAndDateAdapter(BookingDateTime.this,date);
        activityBinding.daysList.setAdapter(adapter);

        adapter.setOnDaySelectListener((position) -> {
            selectedDate = date.get(position);
            Toast.makeText(this, date.get(position), Toast.LENGTH_SHORT).show();
        });

    }

    private void timeSlotsRv() {

        List<String> slots = new ArrayList<>();
        slots.add("12AM - 01AM");
        slots.add("01AM - 02AM");
        slots.add("02AM - 03AM");
        slots.add("03AM - 04AM");
        slots.add("04AM - 05AM");
        slots.add("05AM - 06AM");
        slots.add("06AM - 07AM");
        slots.add("07AM - 08AM");
        slots.add("08AM - 09AM");
        slots.add("09AM - 10AM");
        slots.add("10AM - 11AM");
        slots.add("11AM - 12PM");
        slots.add("12PM - 01PM");
        slots.add("01PM - 02PM");
        slots.add("02PM - 03PM");
        slots.add("03PM - 04PM");
        slots.add("04PM - 05PM");
        slots.add("05PM - 06PM");
        slots.add("06PM - 07PM");
        slots.add("07PM - 08PM");
        slots.add("08PM - 09PM");
        slots.add("09PM - 10PM");
        slots.add("10PM - 11PM");
        slots.add("11PM - 12AM");


        GridLayoutManager layoutManager = new GridLayoutManager(App.applicationContext, 3);
        activityBinding.timeList.setLayoutManager(layoutManager);
        TimeSlotsAdapter adapter = new TimeSlotsAdapter(BookingDateTime.this,slots);
        activityBinding.timeList.setAdapter(adapter);

        adapter.setOnSlotClickListener(position -> {
            selectedTime = slots.get(position);
            Toast.makeText(this, slots.get(position), Toast.LENGTH_SHORT).show();
        });
    }
}