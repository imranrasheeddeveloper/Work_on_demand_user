package com.rizorsiumani.workondemanduser.data.businessModels;

import com.rizorsiumani.workondemanduser.data.businessModels.inbox.ServiceProvider;

public class SenderModel {
    private String message, time;

    public SenderModel(String message, String time) {
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
