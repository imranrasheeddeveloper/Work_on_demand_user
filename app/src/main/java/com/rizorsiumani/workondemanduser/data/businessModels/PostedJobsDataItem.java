package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class PostedJobsDataItem {

	@SerializedName("price_unit")
	private String priceUnit;

	@SerializedName("date")
	private String date;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("attachment")
	private String attachment;

	@SerializedName("sub_category_id")
	private int subCategoryId;

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

	@SerializedName("latitude")
	private String latitude;

	@SerializedName("longitude")
	private String longitude;

	@SerializedName("address")
	private String address;

	public String getPriceUnit(){
		return priceUnit;
	}

	public String getDate(){
		return date;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public String getAttachment(){
		return attachment;
	}

	public int getSubCategoryId(){
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


	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getAddress() {
		return address;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}