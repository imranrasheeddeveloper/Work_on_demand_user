package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SubCategoriesModel{

	@SerializedName("data")
	private List<SubCategoryDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("totalRows")
	private int totalRows;

	@SerializedName("message")
	private String message;

	public List<SubCategoryDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public int getTotalRows(){
		return totalRows;
	}

	public String getMessage(){
		return message;
	}
}