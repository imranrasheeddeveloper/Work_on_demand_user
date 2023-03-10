package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class PromoActivationModel{

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}