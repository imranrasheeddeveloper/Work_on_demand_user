package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;
import com.rizorsiumani.workondemanduser.data.businessModels.commercial_user.Company;

public class UserData {

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("image")
	private String image;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("company")
	private Company company;

	@SerializedName("password")
	private String password;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("role")
	private String role;

	@SerializedName("fcm_token")
	private String fcmToken;

	@SerializedName("id")
	private int id;

	@SerializedName("email")
	private String email;

	@SerializedName("updatedAt")
	private String updatedAt;

	@SerializedName("stripe_customerId")
	private String stripe_customerId;

	@SerializedName("chatwoot_source_id")
	private String chatwoot_source_id;

	@SerializedName("chatwoot_contact_id")
	private String chatwoot_contact_id;

	public String getStripe_customerId() {
		return stripe_customerId;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public String getImage(){
		return image;
	}

	public String getCreatedAt(){
		return createdAt;
	}

	public String getPassword(){
		return password;
	}

	public String getPhoneNumber(){
		return phoneNumber;
	}

	public String getRole(){
		return role;
	}

	public String getFcmToken(){
		return fcmToken;
	}

	public int getId(){
		return id;
	}

	public String getEmail(){
		return email;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}

	public Company getCompany() {
		return company;
	}

	public String getChatwoot_source_id() {
		return chatwoot_source_id;
	}

	public String getChatwoot_contact_id() {
		return chatwoot_contact_id;
	}
}