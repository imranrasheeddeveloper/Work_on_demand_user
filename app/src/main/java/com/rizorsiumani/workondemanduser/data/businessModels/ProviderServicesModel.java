package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProviderServicesModel{

	@SerializedName("data")
	private List<ServicesDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<ServicesDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}