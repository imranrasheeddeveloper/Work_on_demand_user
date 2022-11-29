package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class RatingDataItem {

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("raiting")
	private int raiting;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("user")
	private User user;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getRaiting(){
		return raiting;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public User getUser(){
		return user;
	}
}