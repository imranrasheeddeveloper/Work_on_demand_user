package com.rizorsiumani.user.data.businessModels.inbox;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class GetInboxModel{

	@SerializedName("data")
	private List<InboxDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public List<InboxDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}