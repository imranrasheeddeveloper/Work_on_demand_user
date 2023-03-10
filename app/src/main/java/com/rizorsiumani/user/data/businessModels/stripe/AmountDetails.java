package com.rizorsiumani.user.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class AmountDetails{

	@SerializedName("tip")
	private Tip tip;

	public Tip getTip(){
		return tip;
	}
}