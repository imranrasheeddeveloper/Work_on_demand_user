package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class TransactionsDataItem {

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("wallet_id")
	private Object walletId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private int id;

	@SerializedName("credit")
	private int credit;

	@SerializedName("debit")
	private int debit;

	@SerializedName("type")
	private String type;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getCreatedAt(){
		return createdAt;
	}

	public Object getWalletId(){
		return walletId;
	}

	public int getUserId(){
		return userId;
	}

	public String getDescription(){
		return description;
	}

	public int getId(){
		return id;
	}

	public int getCredit(){
		return credit;
	}

	public int getDebit(){
		return debit;
	}

	public String getType(){
		return type;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}