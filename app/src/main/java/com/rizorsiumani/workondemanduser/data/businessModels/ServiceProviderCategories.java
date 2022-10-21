package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderCategories{

	@SerializedName("image")
	private String image;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("color")
	private String color;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getImage(){
		return image;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getColor(){
		return color;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}