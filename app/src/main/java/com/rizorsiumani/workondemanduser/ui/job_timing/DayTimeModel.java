package com.rizorsiumani.workondemanduser.ui.job_timing;

import java.util.List;

public class DayTimeModel {

    private  Integer id;
    private String day;
    private  String date;
    private List<TimeItem> timeItems;

    public DayTimeModel(Integer id, String date, String day, List<TimeItem> timeItems) {

        this.id = id;
        this.date = date;
        this.day = day;
        this.timeItems = timeItems;
    }

    public String getDay() {
        return day;
    }

    public String getDate() {
        return date;
    }

    public Integer getId() {
        return id;
    }

    public List<TimeItem> getTimeItems() {
        return timeItems;
    }

    public void setTimeItems(List<TimeItem> timeItems) {
        this.timeItems = timeItems;
    }
}
