package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class JobData {

	@SerializedName("price_unit")
	private String priceUnit;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("category_id")
	private String categoryId;

	@SerializedName("attachment")
	private String attachment;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("sub_category_id")
	private String subCategoryId;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("budget")
	private String budget;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getPriceUnit(){
		return priceUnit;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getCategoryId(){
		return categoryId;
	}

	public String getAttachment(){
		return attachment;
	}

	public int getUserId(){
		return userId;
	}

	public String getSubCategoryId(){
		return subCategoryId;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getBudget(){
		return budget;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}