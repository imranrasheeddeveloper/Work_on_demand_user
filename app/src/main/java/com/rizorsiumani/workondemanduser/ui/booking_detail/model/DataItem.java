package com.rizorsiumani.workondemanduser.ui.booking_detail.model;

import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("address_zip_check")
	private Object addressZipCheck;

	@SerializedName("country")
	private String country;

	@SerializedName("last4")
	private String last4;

	@SerializedName("funding")
	private String funding;

	@SerializedName("metadata")
	private Metadata metadata;

	@SerializedName("address_country")
	private Object addressCountry;

	@SerializedName("address_state")
	private Object addressState;

	@SerializedName("exp_month")
	private int expMonth;

	@SerializedName("exp_year")
	private int expYear;

	@SerializedName("address_city")
	private Object addressCity;

	@SerializedName("tokenization_method")
	private Object tokenizationMethod;

	@SerializedName("cvc_check")
	private Object cvcCheck;

	@SerializedName("address_line2")
	private Object addressLine2;

	@SerializedName("address_line1")
	private Object addressLine1;

	@SerializedName("fingerprint")
	private String fingerprint;

	@SerializedName("name")
	private Object name;

	@SerializedName("id")
	private String id;

	@SerializedName("address_line1_check")
	private Object addressLine1Check;

	@SerializedName("address_zip")
	private Object addressZip;

	@SerializedName("dynamic_last4")
	private Object dynamicLast4;

	@SerializedName("brand")
	private String brand;

	@SerializedName("object")
	private String object;

	@SerializedName("customer")
	private String customer;

	public Object getAddressZipCheck(){
		return addressZipCheck;
	}

	public String getCountry(){
		return country;
	}

	public String getLast4(){
		return last4;
	}

	public String getFunding(){
		return funding;
	}

	public Metadata getMetadata(){
		return metadata;
	}

	public Object getAddressCountry(){
		return addressCountry;
	}

	public Object getAddressState(){
		return addressState;
	}

	public int getExpMonth(){
		return expMonth;
	}

	public int getExpYear(){
		return expYear;
	}

	public Object getAddressCity(){
		return addressCity;
	}

	public Object getTokenizationMethod(){
		return tokenizationMethod;
	}

	public Object getCvcCheck(){
		return cvcCheck;
	}

	public Object getAddressLine2(){
		return addressLine2;
	}

	public Object getAddressLine1(){
		return addressLine1;
	}

	public String getFingerprint(){
		return fingerprint;
	}

	public Object getName(){
		return name;
	}

	public String getId(){
		return id;
	}

	public Object getAddressLine1Check(){
		return addressLine1Check;
	}

	public Object getAddressZip(){
		return addressZip;
	}

	public Object getDynamicLast4(){
		return dynamicLast4;
	}

	public String getBrand(){
		return brand;
	}

	public String getObject(){
		return object;
	}

	public String getCustomer(){
		return customer;
	}
}