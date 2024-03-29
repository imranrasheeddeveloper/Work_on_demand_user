package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServicesDataItem {

	@SerializedName("price_unit")
	private String priceUnit;

	@SerializedName("service_provider_categories")
	private ServiceProviderCategories serviceProviderCategories;

	@SerializedName("price")
	private String price;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("service_provider_subcategories")
	private ServiceProviderSubcategories serviceProviderSubcategories;

	@SerializedName("title")
	private String title;

	public String getPriceUnit(){
		return priceUnit;
	}

	public ServiceProviderCategories getServiceProviderCategories(){
		return serviceProviderCategories;
	}

	public String getPrice(){
		return price;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public ServiceProviderSubcategories getServiceProviderSubcategories(){
		return serviceProviderSubcategories;
	}

	public String getTitle(){
		return title;
	}
}