package com.rizorsiumani.workondemanduser.ui.job_timing;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.mahdizareei.mztimepicker.MZTimePicker;
import com.mahdizareei.mztimepicker.interfaces.OnTimeSelectedListener;
import com.mahdizareei.mztimepicker.models.TimeModel;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityJobTimingBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class JobTiming extends BaseActivity<ActivityJobTimingBinding> {

    List<AvailabilitiesItem> mainList;
    List<DayTimeModel> dayTimeModelList;
    List<TimeItem> mainTimeList;
    Integer selectedIndex = 0;
    String fromTime;
    String toTime;

    @Override
    protected ActivityJobTimingBinding getActivityBinding() {
        return ActivityJobTimingBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
        mainList = new ArrayList<>();
        dayTimeModelList = new ArrayList<>();
        mainTimeList = new ArrayList<>();
        clickListeners();
        getNextWeekDays();
    }

    private void clickListeners() {
        activityBinding.searchedToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        activityBinding.btnConfirm.setOnClickListener(view -> {
            for (int i = 0; i < mainList.size(); i++) {
                TinyDbManager.saveTiming(mainList.get(i));
            }
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
        activityBinding.tvAddTime.setOnClickListener(view -> {
            callTimePickerDialogue();
        });
    }



    private void callTimePickerDialogue() {
        new MZTimePicker(JobTiming.this)
                .setFromTitle("From") //set title
                .setToTitle("To") //set title
                .setDeleteTimeText("Clear") //set text
                .setConfirmTimeText("Confirm") //set text
                .setTabColor(getResources().getColor(R.color.primary)) //set color
//                .setTabFont("assets/poppins_medium.ttf")
                .setConfirmButtonColor(getResources().getColor(R.color.primary)) //set color
                .setDeleteButtonColor(getResources().getColor(R.color.red)) //set color
                .setConfirmTextColor(getResources().getColor(R.color.white)) //set color
                .setDeleteTextColor(getResources().getColor(R.color.white)) //set color
                .BuildTimePicker(new OnTimeSelectedListener() {
                    @Override
                    public void onTimeSelected(TimeModel time1, TimeModel time2) {
                        fromTime = time1.getHour()+ " : " + time1.getMinute();
                        toTime = time2.getHour()+ " : " + time2.getMinute();
                        int hours = Integer.valueOf(time1.getHour()) - Integer.valueOf(time2.getHour());
                        appendTimeSlotsInWeekDays(selectedIndex , fromTime , toTime,hours);
                    }
                });
    }


    private void getNextWeekDays(){
        SimpleDateFormat sdf = new SimpleDateFormat("E dd-MMM-yyyy");
        for (int i = 0; i < 7; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DATE, i);
            String day = sdf.format(calendar.getTime());
            String[] dayDate = day.split(" ");
            day = dayDate[0];
            String date  =  dayDate[1];
            dayTimeModelList.add(new DayTimeModel(i,date,day,null));
        }
        if (mainList.size() == 0) {
            mainList.add(0, new AvailabilitiesItem(null, dayTimeModelList.get(0).getDay()));
        }
        daysRv(dayTimeModelList);

    }

    private void daysRv(List<DayTimeModel> dayTimeModelList) {

        LinearLayoutManager llm = new LinearLayoutManager(JobTiming.this, RecyclerView.HORIZONTAL, false);
        activityBinding.daysList.setLayoutManager(llm);
        DayAndDateAdapter1 adapter = new DayAndDateAdapter1(JobTiming.this, dayTimeModelList);
        activityBinding.daysList.setAdapter(adapter);

        adapter.setOnDaySelectListener((position) -> {
            selectedIndex = position;
            mainList.add(new AvailabilitiesItem(null,dayTimeModelList.get(position).getDay()));
            mainTimeList = new ArrayList<>();
            timeSlotsRv(dayTimeModelList.get(selectedIndex).getTimeItems());
        });


    }

    private void appendTimeSlotsInWeekDays(Integer index , String fromTime , String toTime,int diffHours ){

        mainTimeList.add(new TimeItem(diffHours,fromTime,toTime));
        new TimeItem(diffHours,fromTime,toTime);
        dayTimeModelList.get(index).setTimeItems(mainTimeList);
        mainList.get(index).setTime(mainTimeList);
        timeSlotsRv(dayTimeModelList.get(index).getTimeItems());


    }


    private void timeSlotsRv(List<TimeItem> list) {

        LinearLayoutManager llm = new LinearLayoutManager(JobTiming.this, RecyclerView.VERTICAL, false);
        activityBinding.timeList.setLayoutManager(llm);
        TimeSlotsAdapter1 adapter = new TimeSlotsAdapter1(JobTiming.this, list);
        activityBinding.timeList.setAdapter(adapter);

    }






}