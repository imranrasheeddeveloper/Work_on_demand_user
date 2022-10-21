package com.rizorsiumani.workondemanduser.data.businessModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("address")
	private String address;

	@SerializedName("profile_photo")
	private Object profilePhoto;

	@SerializedName("service_provider_services")
	private List<ServiceProviderServicesItem> serviceProviderServices;

	@SerializedName("service_provider_gallery")
	private List<Object> serviceProviderGallery;

	@SerializedName("last_name")
	private String lastName;

	@SerializedName("active")
	private int active;

	@SerializedName("national_id_photo")
	private String nationalIdPhoto;

	@SerializedName("long")
	private String longitude;

	@SerializedName("createdAt")
	private String createdAt;

	@SerializedName("password")
	private String password;

	@SerializedName("phone_number")
	private String phoneNumber;

	@SerializedName("id")
	private int id;

	@SerializedName("id_card_number")
	private String idCardNumber;

	@SerializedName("service_provider_availability")
	private List<ServiceProviderAvailabilityItem> serviceProviderAvailability;

	@SerializedName("first_name")
	private String firstName;

	@SerializedName("email")
	private String email;

	@SerializedName("lat")
	private String lat;

	@SerializedName("status")
	private String status;

	@SerializedName("updatedAt")
	private String updatedAt;

	public String getAddress(){
		return address;
	}

	public Object getProfilePhoto(){
		return profilePhoto;
	}

	public List<ServiceProviderServicesItem> getServiceProviderServices(){
		return serviceProviderServices;
	}

	public List<Object> getServiceProviderGallery(){
		return serviceProviderGallery;
	}

	public String getLastName(){
		return lastName;
	}

	public int getActive(){
		return active;
	}

	public String getNationalIdPhoto(){
		return nationalIdPhoto;
	}

	public String getLongitude(){
		return longitude;
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

	public int getId(){
		return id;
	}

	public String getIdCardNumber(){
		return idCardNumber;
	}

	public List<ServiceProviderAvailabilityItem> getServiceProviderAvailability(){
		return serviceProviderAvailability;
	}

	public String getFirstName(){
		return firstName;
	}

	public String getEmail(){
		return email;
	}

	public String getLat(){
		return lat;
	}

	public String getStatus(){
		return status;
	}

	public String getUpdatedAt(){
		return updatedAt;
	}
}