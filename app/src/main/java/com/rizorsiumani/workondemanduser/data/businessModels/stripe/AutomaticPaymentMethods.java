package com.rizorsiumani.workondemanduser.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class AutomaticPaymentMethods{

	@SerializedName("enabled")
	private boolean enabled;

	public boolean isEnabled(){
		return enabled;
	}
}