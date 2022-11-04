package com.rizorsiumani.workondemanduser.ui.booking;

import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;

public class MyCartItems {

    String id;
    ServicesDataItem data;
    String availability_id;
    String description;

    public MyCartItems(String id, ServicesDataItem data,String hours, String des) {
        this.id = id;
        this.data = data;
        this.availability_id = hours;
        this.description = des;
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

    public String getAvailability_id() {
        return availability_id;
    }

    public String getDescription() {
        return description;
    }
}
