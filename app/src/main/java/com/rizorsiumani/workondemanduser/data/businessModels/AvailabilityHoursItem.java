package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class AvailabilityHoursItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("booked")
	private boolean booked;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("id")
	private int id;

	@SerializedName("time")
	private String time;

	@SerializedName("day_id")
	private int dayId;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public boolean isBooked(){
		return booked;
	}

	public int getServiceProviderId(){
		return serviceProviderId;
	}

	public int getId(){
		return id;
	}

	public String getTime(){
		return time;
	}

	public int getDayId(){
		return dayId;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}