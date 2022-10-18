package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class addressDataItems {

	@SerializedName("user_id")
	private int userId;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("lat")
	private String lat;

	@SerializedName("long")
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
}