package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NotificationModel{

	@SerializedName("data")
	private List<NotificationDataItem> data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("page")
	private int page;

	@SerializedName("message")
	private String message;

	public List<NotificationDataItem> getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public int getPage(){
		return page;
	}

	public String getMessage(){
		return message;
	}
}