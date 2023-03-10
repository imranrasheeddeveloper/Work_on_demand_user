package com.rizorsiumani.user.ui.address;

public class PlaceModel {
    String id,title , address;

    public PlaceModel(String id, String title, String address) {
        this.id = id;
        this.title = title;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }
}
