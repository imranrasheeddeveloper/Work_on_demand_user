package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class GetAllCardsModel {

	@SerializedName("data")
	private CardsData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public CardsData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}