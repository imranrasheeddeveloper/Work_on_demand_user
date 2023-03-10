package com.rizorsiumani.user.ui.job_timing;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.mahdizareei.mztimepicker.MZTimePicker;
import com.mahdizareei.mztimepicker.interfaces.OnTimeSelectedListener;
import com.mahdizareei.mztimepicker.models.TimeModel;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.ActivityJobTimingBinding;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;



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

        activityBinding.timingToolbar.title.setText("Add Timing");
        mainList = new ArrayList<>();
        dayTimeModelList = new ArrayList<>();
        mainTimeList = new ArrayList<>();
        clickListeners();
        getNextWeekDays();
    }

    private void clickListeners() {
        activityBinding.timingToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });

        activityBinding.btnConfirm.setOnClickListener(view -> {
            for (int i = 0; i < mainList.size(); i++) {
//                if (i != 0) {
                    if (mainList.get(i).getTime() != null && mainList.get(i).getTime().size() > 0) {
                        TinyDbManager.saveTiming(mainList.get(i));
                    }
            }
            onBackPressed();
            finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        });
        activityBinding.ivAddTime.setOnClickListener(view -> {
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
//              .setTabFont("assets/poppins_medium.ttf")
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
//        if (mainList.size() == 0) {
//            mainList.add(0, new AvailabilitiesItem(mainTimeList, dayTimeModelList.get(0).getDay()));
//        }
        daysRv(dayTimeModelList);

    }

    private void daysRv(List<DayTimeModel> dayTimeModelList) {

        LinearLayoutManager llm = new LinearLayoutManager(JobTiming.this, RecyclerView.HORIZONTAL, false);
        activityBinding.daysList.setLayoutManager(llm);
        DayAndDateAdapter1 adapter = new DayAndDateAdapter1(JobTiming.this, dayTimeModelList);
        activityBinding.daysList.setAdapter(adapter);
        activityBinding.daysList.postDelayed(() -> Objects.requireNonNull(activityBinding.daysList.findViewHolderForAdapterPosition(0)).itemView.performClick(), 100);

        adapter.setOnDaySelectListener((position) -> {
            selectedIndex = position;
            if (mainList.contains(dayTimeModelList.get(position).getDay())) {
                mainList.add(new AvailabilitiesItem(mainTimeList, dayTimeModelList.get(position).getDay()));
            }else {
                mainList.add(new AvailabilitiesItem(mainTimeList, dayTimeModelList.get(position).getDay()));
                mainTimeList = new ArrayList<>();
            }
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

        GridLayoutManager layoutManager = new GridLayoutManager(JobTiming.this, 3);
        activityBinding.timeList.setLayoutManager(layoutManager);
        TimeSlotsAdapter1 adapter = new TimeSlotsAdapter1(JobTiming.this, list);
        adapter.notifyDataSetChanged();
        activityBinding.timeList.setAdapter(adapter);

    }


}