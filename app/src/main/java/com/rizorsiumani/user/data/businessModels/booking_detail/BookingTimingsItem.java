package com.rizorsiumani.user.data.businessModels.booking_detail;

import com.google.gson.annotations.SerializedName;

public class BookingTimingsItem{

	@SerializedName("fromTime")
	private String fromTime;

	@SerializedName("id")
	private int id;

	@SerializedName("total_hours")
	private String totalHours;

	@SerializedName("day")
	private String day;

	@SerializedName("toTime")
	private String toTime;

	public String getFromTime(){
		return fromTime;
	}

	public int getId(){
		return id;
	}

	public String getTotalHours(){
		return totalHours;
	}

	public String getDay(){
		return day;
	}

	public String getToTime(){
		return toTime;
	}
}