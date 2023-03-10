package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class CategoriesModel{

	@SerializedName("data")
	private List<CategoriesDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("totalRows")
	private int totalRows;

	@SerializedName("message")
	private String message;

	public List<CategoriesDataItem> getData(){
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