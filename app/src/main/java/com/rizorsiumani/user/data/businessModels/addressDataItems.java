package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class addressDataItems {

	@SerializedName("user_id")
	private int userId;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("latitude")
	private String lat;

	@SerializedName("address")
	private String address;

	@SerializedName("longitude")
	private String jsonMemberLong;

	@SerializedName("updatedAt")
	private String updatedAt;

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

	public String getAddress() {
		return address;
	}
}