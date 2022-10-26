package com.rizorsiumani.workondemanduser.ui.booking;

import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;

public class MyCartItems {

    String id;
    ServicesDataItem data;

    public MyCartItems(String id, ServicesDataItem data) {
        this.id = id;
        this.data = data;
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
}
