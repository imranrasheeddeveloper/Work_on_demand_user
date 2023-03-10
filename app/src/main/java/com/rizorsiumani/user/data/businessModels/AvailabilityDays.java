package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class AvailabilityDays{

	@SerializedName("id")
	private int id;

	@SerializedName("day")
	private String day;

	public int getId(){
		return id;
	}

	public String getDay(){
		return day;
	}
}