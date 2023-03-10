package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class WalletBalanceModel{

	@SerializedName("data")
	private WalletBalanceData data;

	@SerializedName("success")
	private boolean success;

	@SerializedName("message")
	private String message;

	public WalletBalanceData getData(){
		return data;
	}

	public boolean isSuccess(){
		return success;
	}

	public String getMessage(){
		return message;
	}
}