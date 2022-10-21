package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderSubcategories{

	@SerializedName("image")
	private String image;

	@SerializedName("color")
	private String color;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public String getImage(){
		return image;
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
}