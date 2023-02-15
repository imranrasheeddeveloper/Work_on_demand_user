package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceProviderDataItem {

	@SerializedName("distance")
	private float distance;

	@SerializedName("profile_photo")
	private String profilePhoto;

	@SerializedName("service_provider_services")
	private List<ServiceProviderServicesItem> serviceProviderServices;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("active")
	private int active;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("service_provider_reviews")
	private List<ServiceProviderReviews> serviceProviderReviews;

	@SerializedName("longitude")
	private double longitude;

	public float getDistance(){
		return distance;
	}

	public String getProfilePhoto(){
		return profilePhoto;
	}

	public List<ServiceProviderServicesItem> getServiceProviderServices(){
		return serviceProviderServices;
	}

	public double getLatitude(){
		return latitude;
	}

	public String getLastName(){
		return lastName;
	}

	public int getActive(){
		return active;
	}

	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public List<ServiceProviderReviews> getServiceProviderReviews(){
		return serviceProviderReviews;
	}

	public double getLongitude(){
		return longitude;
	}
}