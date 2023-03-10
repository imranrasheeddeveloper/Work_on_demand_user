package com.rizorsiumani.user.data.businessModels.chat;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("image")
	private String image;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	public String getImage(){
		return image;
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
}