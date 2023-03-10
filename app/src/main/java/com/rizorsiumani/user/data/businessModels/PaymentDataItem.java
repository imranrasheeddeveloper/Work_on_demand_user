package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class PaymentDataItem {

	@SerializedName("image")
	private String image;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public String getImage(){
		return image;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}