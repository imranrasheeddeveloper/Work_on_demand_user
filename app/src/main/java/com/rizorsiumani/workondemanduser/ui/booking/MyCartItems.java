package com.rizorsiumani.workondemanduser.ui.booking;

import com.rizorsiumani.workondemanduser.common.BookingTimingItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;

import java.util.List;

public class MyCartItems {

    String id;
    ServicesDataItem data;
    List<BookingTimingItem> availability;
    String description,date;

    public MyCartItems(String id, ServicesDataItem data, List<BookingTimingItem> hours, String des, String start_date) {
        this.id = id;
        this.data = data;
        this.availability = hours;
        this.description = des;
        this.date = start_date;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ServicesDataItem getData() {
        return data;
    }

    public void setData(ServicesDataItem data) {
        this.data = data;
    }

    public List<BookingTimingItem> getAvailability() {
        return availability;
    }

    public String getDescription() {
        return description;
    }
}
