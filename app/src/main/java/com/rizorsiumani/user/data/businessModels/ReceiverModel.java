package com.rizorsiumani.user.data.businessModels;

public class ReceiverModel {

    private String message, time;

    public ReceiverModel(String message, String time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
