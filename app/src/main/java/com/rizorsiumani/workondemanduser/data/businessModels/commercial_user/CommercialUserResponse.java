package com.rizorsiumani.workondemanduser.data.businessModels.commercial_user;

import com.google.gson.annotations.SerializedName;

public class CommercialUserResponse{

	@SerializedName("data")
	private CommercialUserData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	@SerializedName("token")
	private String token;

	public CommercialUserData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}

	public String getToken(){
		return token;
	}
}