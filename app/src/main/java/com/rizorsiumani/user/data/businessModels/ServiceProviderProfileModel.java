package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderProfileModel{

	@SerializedName("data")
	private SProfileData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public SProfileData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}