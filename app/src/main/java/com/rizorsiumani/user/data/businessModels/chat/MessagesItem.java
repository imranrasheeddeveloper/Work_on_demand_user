package com.rizorsiumani.user.data.businessModels.chat;

import com.google.gson.annotations.SerializedName;

public class MessagesItem{

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("inbox_id")
	private int inboxId;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("sent_by")
	private int sentBy;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("id")
	private int id;

	@SerializedName("body")
	private String body;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public int getInboxId(){
		return inboxId;
	}

	public int getServiceProviderId(){
		return serviceProviderId;
	}

	public int getSentBy(){
		return sentBy;
	}

	public int getUserId(){
		return userId;
	}

	public int getId(){
		return id;
	}

	public String getBody(){
		return body;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}