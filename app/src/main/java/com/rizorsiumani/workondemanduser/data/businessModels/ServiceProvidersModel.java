package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceProvidersModel{

	@SerializedName("pages")
	private int pages;

	@SerializedName("data")
	private List<DataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public int getPages(){
		return pages;
	}

	public List<DataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}