package com.rizorsiumani.workondemanduser.data.businessModels.stripe;

import com.google.gson.annotations.SerializedName;

public class Card{

	@SerializedName("installments")
	private Object installments;

	@SerializedName("request_three_d_secure")
	private String requestThreeDSecure;

	@SerializedName("mandate_options")
	private Object mandateOptions;

	@SerializedName("network")
	private Object network;

	public Object getInstallments(){
		return installments;
	}

	public String getRequestThreeDSecure(){
		return requestThreeDSecure;
	}

	public Object getMandateOptions(){
		return mandateOptions;
	}

	public Object getNetwork(){
		return network;
	}
}