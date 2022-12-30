package com.rizorsiumani.workondemanduser.common;

import com.google.gson.annotations.SerializedName;

public class BookingTimingItem{

	@SerializedName("fromTime")
	private String fromTime;

	@SerializedName("total_hours")
	private String totalHours;

	@SerializedName("day")
	private String day;

	@SerializedName("toTime")
	private String toTime;

	public BookingTimingItem(String fromTime, String totalHours, String day, String toTime) {
		this.fromTime = fromTime;
		this.totalHours = totalHours;
		this.day = day;
		this.toTime = toTime;
	}

	public String getFromTime(){
		return fromTime;
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