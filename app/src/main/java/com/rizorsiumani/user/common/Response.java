package com.rizorsiumani.user.common;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("service_provider")
	private ServiceProvider serviceProvider;

	public ServiceProvider getServiceProvider(){
		return serviceProvider;
	}
}