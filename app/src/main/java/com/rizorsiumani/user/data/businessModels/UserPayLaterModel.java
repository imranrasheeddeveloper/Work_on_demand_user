package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class UserPayLaterModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("can_pay_later")
	private boolean canPayLater;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public boolean isCanPayLater(){
		return canPayLater;
	}

	public String getMessage(){
		return message;
	}
}