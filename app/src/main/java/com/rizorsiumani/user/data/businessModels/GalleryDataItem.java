package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class GalleryDataItem {

	@SerializedName("image")
	private String image;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("id")
	private int id;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getImage(){
		return image;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getServiceProviderId(){
		return serviceProviderId;
	}

	public int getId(){
		return id;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}