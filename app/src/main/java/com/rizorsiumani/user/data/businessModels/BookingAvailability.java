package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class BookingAvailability{

	@SerializedName("id")
	private int id;

	@SerializedName("time")
	private String time;

	@SerializedName("availability_days")
	private AvailabilityDays availabilityDays;

	public int getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public AvailabilityDays getAvailabilityDays(){
		return availabilityDays;
	}
}