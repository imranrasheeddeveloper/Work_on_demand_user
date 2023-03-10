package com.rizorsiumani.user.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class Link{

	@SerializedName("persistent_token")
	private Object persistentToken;

	public Object getPersistentToken(){
		return persistentToken;
	}
}