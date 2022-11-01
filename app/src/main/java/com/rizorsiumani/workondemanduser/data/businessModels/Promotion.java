package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class Promotion{

	@SerializedName("code")
	private String code;

	@SerializedName("discount")
	private int discount;

	public String getCode(){
		return code;
	}

	public int getDiscount(){
		return discount;
	}
}