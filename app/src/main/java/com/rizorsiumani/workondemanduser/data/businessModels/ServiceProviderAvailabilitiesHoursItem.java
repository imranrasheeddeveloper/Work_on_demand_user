package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderAvailabilitiesHoursItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("id")
	private int id;

	@SerializedName("time")
	private String time;

	@SerializedName("availability_day_id")
	private int availabilityDayId;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public int getAvailabilityDayId(){
		return availabilityDayId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}