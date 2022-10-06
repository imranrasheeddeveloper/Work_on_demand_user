package com.rizorsiumani.workondemanduser.ui.address;

public class PlaceModel {
    String title , address;

    public PlaceModel(String title, String address) {
        this.title = title;
        this.address = address;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }
}
