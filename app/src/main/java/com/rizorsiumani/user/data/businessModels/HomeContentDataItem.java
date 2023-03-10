package com.rizorsiumani.user.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class HomeContentDataItem {

	@SerializedName("service_provider_categories")
	private List<ServiceProviderCategoriesItem> serviceProviderCategories;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	public List<ServiceProviderCategoriesItem> getServiceProviderCategories(){
		return serviceProviderCategories;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}
}