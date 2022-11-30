package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProvider{

	@SerializedName("profile_photo")
	private Object profilePhoto;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("service_provider_reviews")
	private ServiceProviderReviews serviceProviderReviews;

	public Object getProfilePhoto(){
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

	public ServiceProviderReviews getServiceProviderReviews(){
		return serviceProviderReviews;
	}
}