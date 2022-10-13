package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class SubCategoryDataItem {

	@SerializedName("image")
	private String image;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("color")
	private String color;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("category")
	private Object category;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getImage(){
		return image;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getCategoryId(){
		return categoryId;
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

	public Object getCategory(){
		return category;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}