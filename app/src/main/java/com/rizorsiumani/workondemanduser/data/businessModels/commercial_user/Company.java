package com.rizorsiumani.workondemanduser.data.businessModels.commercial_user;

import com.google.gson.annotations.SerializedName;

public class Company{

	@SerializedName("building_name")
	private String buildingName;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("owner_name")
	private String ownerName;

	@SerializedName("company_registration_number")
	private String companyRegistrationNumber;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("company_name")
	private String companyName;

	@SerializedName("vat")
	private String vat;

	@SerializedName("position_in_company")
	private String positionInCompany;

	@SerializedName("id")
	private int id;

	@SerializedName("company_address")
	private String companyAddress;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getBuildingName(){
		return buildingName;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getOwnerName(){
		return ownerName;
	}

	public String getCompanyRegistrationNumber(){
		return companyRegistrationNumber;
	}

	public int getUserId(){
		return userId;
	}

	public String getCompanyName(){
		return companyName;
	}

	public String getVat(){
		return vat;
	}

	public String getPositionInCompany(){
		return positionInCompany;
	}

	public int getId(){
		return id;
	}

	public String getCompanyAddress(){
		return companyAddress;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}