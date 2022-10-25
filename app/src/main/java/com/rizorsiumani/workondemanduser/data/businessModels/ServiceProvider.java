package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProvider{

	@SerializedName("address")
	private String address;

	@SerializedName("profile_photo")
	private Object profilePhoto;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("active")
	private int active;

	@SerializedName("national_id_photo")
	private String nationalIdPhoto;

	@SerializedName("longitude")
	private String jsonMemberLong;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("password")
	private String password;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("id_card_number")
	private String idCardNumber;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("lat")
	private String lat;

	@SerializedName("status")
	private String status;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getAddress(){
		return address;
	}

	public Object getProfilePhoto(){
		return profilePhoto;
	}

	public String getLastName(){
		return lastName;
	}

	public int getActive(){
		return active;
	}

	public String getNationalIdPhoto(){
		return nationalIdPhoto;
	}

	public String getJsonMemberLong(){
		return jsonMemberLong;
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

	public int getId(){
		return id;
	}

	public String getIdCardNumber(){
		return idCardNumber;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public String getLat(){
		return lat;
	}

	public String getStatus(){
		return status;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}