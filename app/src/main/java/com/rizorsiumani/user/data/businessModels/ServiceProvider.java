package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ServiceProvider{

	@SerializedName("profile_photo")
	private String profilePhoto;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("service_provider_reviews")
	private List<ServiceProviderReviews> serviceProviderReviews;

	public String getProfilePhoto(){
		return profilePhoto;
	}

	public String getLastName(){
		return lastName;
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
}