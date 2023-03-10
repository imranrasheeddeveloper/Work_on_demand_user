package com.rizorsiumani.user.data.businessModels.chatwoot_model;

import com.google.gson.annotations.SerializedName;

public class Assignee{

	@SerializedName("available_name")
	private String availableName;

	@SerializedName("availability_status")
	private String availabilityStatus;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("avatar_url")
	private String avatarUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	public String getAvailableName(){
		return availableName;
	}

	public String getAvailabilityStatus(){
		return availabilityStatus;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public String getAvatarUrl(){
		return avatarUrl;
	}

	public String getName(){
		return name;
	}

	public int getId(){
		return id;
	}

	public String getType(){
		return type;
	}
}