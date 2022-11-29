package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderReviewsItem{

	@SerializedName("rating")
	private String rating;

	public String getRating(){
		return rating;
	}
}