package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class UpdateUserModel{

	@SerializedName("data")
	private UserData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public UserData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}