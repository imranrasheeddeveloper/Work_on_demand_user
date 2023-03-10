package com.rizorsiumani.user.common;

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

	int id;

	public BookingTimingItem(int id,String fromTime, String totalHours, String day, String toTime) {
		this.id = id;
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

	public int getId() {
		return id;
	}
}