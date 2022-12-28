package com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessagesModel{

	@SerializedName("payload")
	private List<PayloadItem> payload;

	@SerializedName("meta")
	private Meta meta;

	public List<PayloadItem> getPayload(){
		return payload;
	}

	public Meta getMeta(){
		return meta;
	}
}