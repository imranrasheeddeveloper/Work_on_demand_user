package com.rizorsiumani.workondemanduser.data.businessModels.chat;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MessageData {

	@SerializedName("messages")
	private List<MessagesItem> messages;

	@SerializedName("service_provider")
	private ServiceProvider serviceProvider;

	@SerializedName("id")
	private int id;

	@SerializedName("user")
	private User user;

	public List<MessagesItem> getMessages(){
		return messages;
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
}