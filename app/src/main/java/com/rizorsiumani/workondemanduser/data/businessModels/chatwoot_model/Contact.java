package com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model;

import com.google.gson.annotations.SerializedName;

public class Contact{

	@SerializedName("identifier")
	private Object identifier;

	@SerializedName("thumbnail")
	private String thumbnail;

	@SerializedName("name")
	private String name;

	@SerializedName("phone_number")
	private Object phoneNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("type")
	private String type;

	@SerializedName("email")
	private String email;

	@SerializedName("additional_attributes")
	private AdditionalAttributes additionalAttributes;

	@SerializedName("custom_attributes")
	private CustomAttributes customAttributes;

	public Object getIdentifier(){
		return identifier;
	}

	public String getThumbnail(){
		return thumbnail;
	}

	public String getName(){
		return name;
	}

	public Object getPhoneNumber(){
		return phoneNumber;
	}

	public int getId(){
		return id;
	}

	public String getType(){
		return type;
	}

	public String getEmail(){
		return email;
	}

	public AdditionalAttributes getAdditionalAttributes(){
		return additionalAttributes;
	}

	public CustomAttributes getCustomAttributes(){
		return customAttributes;
	}
}