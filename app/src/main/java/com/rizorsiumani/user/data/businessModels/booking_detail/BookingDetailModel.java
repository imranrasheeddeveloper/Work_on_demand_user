package com.rizorsiumani.user.data.businessModels.booking_detail;

import com.google.gson.annotations.SerializedName;

public class BookingDetailModel{

	@SerializedName("data")
	private BookingDetailData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public BookingDetailData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}