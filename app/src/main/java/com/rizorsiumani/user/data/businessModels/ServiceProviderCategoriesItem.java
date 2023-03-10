package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderCategoriesItem{

	@SerializedName("price_unit")
	private String priceUnit;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("category_id")
	private int categoryId;

	@SerializedName("sub_category_id")
	private int subCategoryId;

	@SerializedName("price")
	private String price;

	@SerializedName("description")
	private String description;

	@SerializedName("service_provider")
	private ServiceProvider serviceProvider;

	@SerializedName("id")
	private int id;

	@SerializedName("title")
	private String title;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getPriceUnit(){
		return priceUnit;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getServiceProviderId(){
		return serviceProviderId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public int getSubCategoryId(){
		return subCategoryId;
	}

	public String getPrice(){
		return price;
	}

	public String getDescription(){
		return description;
	}

	public ServiceProvider getServiceProvider(){
		return serviceProvider;
	}

	public int getId(){
		return id;
	}

	public String getTitle(){
		return title;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}