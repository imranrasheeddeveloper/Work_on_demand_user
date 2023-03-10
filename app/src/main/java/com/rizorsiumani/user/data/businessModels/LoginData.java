package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class LoginData {

	@SerializedName("user")
	private User user;

	@SerializedName("token")
	private String token;

	public User getUser(){
		return user;
	}

	public String getToken(){
		return token;
	}
}