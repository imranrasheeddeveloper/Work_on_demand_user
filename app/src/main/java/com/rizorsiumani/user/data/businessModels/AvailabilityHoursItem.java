package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class AvailabilityHoursItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("booked")
	private boolean booked;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("totalHours")
	private int totalHours;

	@SerializedName("fromTime")
	private String fromTime;

	@SerializedName("id")
	private int id;

	@SerializedName("day_id")
	private int dayId;

	@SerializedName("toTime")
	private String toTime;

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

	public int getTotalHours(){
		return totalHours;
	}

	public String getFromTime(){
		return fromTime;
	}

	public int getId(){
		return id;
	}

	public int getDayId(){
		return dayId;
	}

	public String getToTime(){
		return toTime;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}