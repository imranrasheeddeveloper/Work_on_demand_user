package com.rizorsiumani.workondemanduser.data.businessModels.chat;

import com.google.gson.annotations.SerializedName;

public class GetAllMessageModel{

	@SerializedName("data")
	private MessageData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public MessageData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}