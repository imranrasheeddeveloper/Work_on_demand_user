package com.rizorsiumani.user.ui.job_timing;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AvailabilitiesItem{

    @SerializedName("time")
    private List<TimeItem> time;

    @SerializedName("day")
    private String day;

    public AvailabilitiesItem(List<TimeItem> time, String day) {
        this.time = time;
        this.day = day;
    }

    public void setTime(List<TimeItem> time) {
        this.time = time;
    }

    public List<TimeItem> getTime(){
        return time;
    }

    public String getDay(){
        return day;
    }
}