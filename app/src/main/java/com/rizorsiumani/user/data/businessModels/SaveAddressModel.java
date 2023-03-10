package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class SaveAddressModel{

	@SerializedName("data")
	private AddressData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public AddressData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}