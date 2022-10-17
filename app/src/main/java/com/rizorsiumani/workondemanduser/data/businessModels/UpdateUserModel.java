package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class UpdateUserModel{

	@SerializedName("data")
	private UpdateUserData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public UpdateUserData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}