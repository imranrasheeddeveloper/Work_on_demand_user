package com.rizorsiumani.workondemanduser.data.businessModels.booking_detail;

import com.google.gson.annotations.SerializedName;

public class ServiceProvider{

	@SerializedName("profile_photo")
	private String profilePhoto;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("active")
	private Object active;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	public String getProfilePhoto(){
		return profilePhoto;
	}

	public String getLastName(){
		return lastName;
	}

	public Object getActive(){
		return active;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}
}