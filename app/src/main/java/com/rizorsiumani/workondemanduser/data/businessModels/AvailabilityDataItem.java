package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AvailabilityDataItem {

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("availability_hours")
	private List<AvailabilityHoursItem> availabilityHours;

	@SerializedName("id")
	private int id;

	@SerializedName("day")
	private String day;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public List<AvailabilityHoursItem> getAvailabilityHours(){
		return availabilityHours;
	}

	public int getId(){
		return id;
	}

	public String getDay(){
		return day;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}