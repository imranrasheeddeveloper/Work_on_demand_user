package com.rizorsiumani.user.ui.support_chat;

import com.rizorsiumani.user.data.businessModels.chatwoot_model.Assignee;

public class SupportReceiverModel {
    private String Message;
    private int time;
    private Assignee receiver;


    public SupportReceiverModel(String message, int time, Assignee receiver1) {
        Message = message;
        this.time = time;
        this.receiver = receiver1;
    }

    public String getMessage() {
        return Message;
    }

    public int getTime() {
        return time;
    }

    public Assignee getReceiver() {
        return receiver;
    }
}
