package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class PostJobModel{

	@SerializedName("data")
	private JobData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public JobData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}