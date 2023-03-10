package com.rizorsiumani.user.data.businessModels.commercial_user;

import com.google.gson.annotations.SerializedName;

public class CommercialUserData {

	@SerializedName("image")
	private Object image;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("commercial_user_id")
	private int commercialUserId;

	@SerializedName("type")
	private String type;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("password")
	private String password;

	@SerializedName("stripe_customerId")
	private String stripeCustomerId;

	@SerializedName("fcm_token")
	private String fcmToken;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("company")
	private Company company;

	@SerializedName("id")
	private int id;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("updatedAt")
	private String updatedAt;

	public Object getImage(){
		return image;
	}

	public String getLastName(){
		return lastName;
	}

	public int getCommercialUserId(){
		return commercialUserId;
	}

	public String getType(){
		return type;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPassword(){
		return password;
	}

	public String getStripeCustomerId(){
		return stripeCustomerId;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public Company getCompany(){
		return company;
	}

	public int getId(){
		return id;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}