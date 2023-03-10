package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetAddressesModel{

	@SerializedName("data")
	private List<addressDataItems> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<addressDataItems> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}