package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceProviderAvailabilityItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("service_provider_availabilities_hours")
	private List<ServiceProviderAvailabilitiesHoursItem> serviceProviderAvailabilitiesHours;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("id")
	private int id;

	@SerializedName("day")
	private String day;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public List<ServiceProviderAvailabilitiesHoursItem> getServiceProviderAvailabilitiesHours(){
		return serviceProviderAvailabilitiesHours;
	}

	public int getServiceProviderId(){
		return serviceProviderId;
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