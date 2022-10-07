package com.rizorsiumani.workondemanduser.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class Bancontact{

	@SerializedName("preferred_language")
	private String preferredLanguage;

	public String getPreferredLanguage(){
		return preferredLanguage;
	}
}