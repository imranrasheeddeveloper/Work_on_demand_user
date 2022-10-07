package com.rizorsiumani.workondemanduser.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class AssociatedObjectsItem{

	@SerializedName("id")
	private String id;

	@SerializedName("type")
	private String type;

	public String getId(){
		return id;
	}

	public String getType(){
		return type;
	}
}