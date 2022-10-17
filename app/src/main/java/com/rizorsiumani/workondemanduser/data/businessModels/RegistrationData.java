package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class RegistrationData {

	@SerializedName("firstName")
	private String firstName;

	@SerializedName("lastName")
	private String lastName;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("password")
	private String password;

	@SerializedName("phoneNumber")
	private String phoneNumber;

	@SerializedName("role")
	private String role;

	@SerializedName("fcm_token")
	private String fcmToken;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPassword(){
		return password;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public String getRole(){
		return role;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}