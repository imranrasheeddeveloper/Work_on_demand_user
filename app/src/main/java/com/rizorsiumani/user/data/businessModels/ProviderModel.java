package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ProviderModel{

	@SerializedName("pages")
	private int pages;

	@SerializedName("data")
	private List<ServiceProviderDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public int getPages(){
		return pages;
	}

	public List<ServiceProviderDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}