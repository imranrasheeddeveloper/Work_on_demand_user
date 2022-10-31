package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class PromoDataItem {

	@SerializedName("code")
	private String code;

	@SerializedName("expiry_date")
	private String expiryDate;

	@SerializedName("discount")
	private int discount;

	@SerializedName("id")
	private int id;

	@SerializedName("dis")
	private String dis;

	public String getCode(){
		return code;
	}

	public String getExpiryDate(){
		return expiryDate;
	}

	public int getDiscount(){
		return discount;
	}

	public int getId(){
		return id;
	}

	public String getDis(){
		return dis;
	}
}