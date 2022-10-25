package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class SProfileData {

	@SerializedName("profile_photo")
	private String profilePhoto;

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

	@SerializedName("longitude")
	private double longitude;

	public String getProfilePhoto(){
		return profilePhoto;
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

	public double getLongitude(){
		return longitude;
	}
}