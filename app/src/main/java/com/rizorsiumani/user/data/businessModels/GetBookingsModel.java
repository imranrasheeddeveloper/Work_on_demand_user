package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetBookingsModel{

	@SerializedName("data")
	private List<GetBookingDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("page")
	private int page;

	@SerializedName("message")
	private String message;

	public List<GetBookingDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public int getPage(){
		return page;
	}

	public String getMessage(){
		return message;
	}
}