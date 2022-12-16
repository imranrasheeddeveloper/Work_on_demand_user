package com.rizorsiumani.workondemanduser.data.businessModels.inbox;

import com.google.gson.annotations.SerializedName;

public class InboxDataItem {

	@SerializedName("last_msg")
	private String lastMsg;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("service_provider")
	private ServiceProvider serviceProvider;

	@SerializedName("id")
	private int id;

	@SerializedName("user")
	private User user;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getLastMsg(){
		return lastMsg;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public int getServiceProviderId(){
		return serviceProviderId;
	}

	public int getUserId(){
		return userId;
	}

	public ServiceProvider getServiceProvider(){
		return serviceProvider;
	}

	public int getId(){
		return id;
	}

	public User getUser(){
		return user;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}