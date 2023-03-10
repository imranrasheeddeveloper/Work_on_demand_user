package com.rizorsiumani.user.data.businessModels.inbox;

import com.google.gson.annotations.SerializedName;

public class InboxExistModel{

	@SerializedName("inbox_id")
	private int inboxId;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public int getInboxId(){
		return inboxId;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}