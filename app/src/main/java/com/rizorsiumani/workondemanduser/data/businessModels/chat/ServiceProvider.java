package com.rizorsiumani.workondemanduser.data.businessModels.chat;

import com.google.gson.annotations.SerializedName;

public class ServiceProvider{

	@SerializedName("profile_photo")
	private String profilePhoto;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("active")
	private Object active;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	public String getProfilePhoto(){
		return profilePhoto;
	}

	public String getLastName(){
		return lastName;
	}

	public Object getActive(){
		return active;
	}

	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}
}