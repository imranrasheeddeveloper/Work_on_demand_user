package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class GetFeeModel{

	@SerializedName("fees")
	private String fees;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public String getFees(){
		return fees;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}