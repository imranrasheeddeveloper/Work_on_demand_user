package com.rizorsiumani.user.ui.job_timing;

import com.google.gson.annotations.SerializedName;

public class TimeItem{

    @SerializedName("totalHours")
    private int totalHours;

    @SerializedName("fromTime")
    private String fromTime;

    @SerializedName("toTime")
    private String toTime;

    public TimeItem(int totalHours, String fromTime, String toTime) {
        this.totalHours = totalHours;
        this.fromTime = fromTime;
        this.toTime = toTime;
    }

    public int getTotalHours(){
        return totalHours;
    }

    public String getFromTime(){
        return fromTime;
    }

    public String getToTime(){
        return toTime;
    }
}