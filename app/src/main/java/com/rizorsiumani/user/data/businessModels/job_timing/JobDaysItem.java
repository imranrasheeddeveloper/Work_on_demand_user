package com.rizorsiumani.user.data.businessModels.job_timing;

import com.google.gson.annotations.SerializedName;

public class JobDaysItem{

	@SerializedName("totalHours")
	private String totalHours;

	@SerializedName("fromTime")
	private String fromTime;

	@SerializedName("id")
	private int id;

	@SerializedName("toTime")
	private String toTime;

	public String getTotalHours(){
		return totalHours;
	}

	public String getFromTime(){
		return fromTime;
	}

	public int getId(){
		return id;
	}

	public String getToTime(){
		return toTime;
	}
}