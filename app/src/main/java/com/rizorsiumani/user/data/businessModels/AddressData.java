package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class AddressData {

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("address")
	private String address;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("lat")
	private String lat;

	@SerializedName("longitude")
	private String jsonMemberLong;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public String getAddress(){
		return address;
	}

	public int getUserId(){
		return userId;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getLat(){
		return lat;
	}

	public String getJsonMemberLong(){
		return jsonMemberLong;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}