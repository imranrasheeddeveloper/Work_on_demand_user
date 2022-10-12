package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class LoginModel{

	@SerializedName("data")
	private LoginData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public LoginData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}