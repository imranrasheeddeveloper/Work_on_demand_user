package com.rizorsiumani.workondemanduser.data.businessModels.settings;

import com.google.gson.annotations.SerializedName;

public class SettingData {

	@SerializedName("stripe_publish_key")
	private String stripePublishKey;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("chatwoot_account_id")
	private String chatwootAccountId;

	@SerializedName("service_fee")
	private String serviceFee;

	@SerializedName("id")
	private int id;

	@SerializedName("chatwoot_api_key")
	private String chatwoot_api_key;

	@SerializedName("chatwoot_inbox_id")
	private String chatwootInboxId;

	@SerializedName("stripe_secret_key")
	private String stripeSecretKey;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getStripePublishKey(){
		return stripePublishKey;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getChatwootAccountId(){
		return chatwootAccountId;
	}

	public String getServiceFee(){
		return serviceFee;
	}

	public int getId(){
		return id;
	}

	public String getChatwootInboxId(){
		return chatwootInboxId;
	}

	public String getStripeSecretKey(){
		return stripeSecretKey;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public String getChatwoot_api_key() {
		return chatwoot_api_key;
	}
}