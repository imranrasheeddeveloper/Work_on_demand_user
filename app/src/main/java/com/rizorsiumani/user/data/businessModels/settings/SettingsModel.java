package com.rizorsiumani.user.data.businessModels.settings;

import com.google.gson.annotations.SerializedName;

public class SettingsModel{

	@SerializedName("data")
	private SettingData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public SettingData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}