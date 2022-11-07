package com.rizorsiumani.workondemanduser.common;

import com.google.gson.annotations.SerializedName;

public class AddBookingDataItem {

	@SerializedName("total")
	private int total;

	@SerializedName("address")
	private String address;

	@SerializedName("service_provider_id")
	private int service_provider_id;

	@SerializedName("user_id")
	private int user_id;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("availability_id")
	private int availability_id;

	@SerializedName("discount")
	private int discount;

	@SerializedName("promotion_id")
	private int promotion_id;

	@SerializedName("service_id")
	private int service_id;

	@SerializedName("subTotal")
	private int subTotal;

	@SerializedName("payment_type_id")
	private int payment_type_id;

	@SerializedName("longitude")
	private double longitude;

	public AddBookingDataItem(int total,
                              String address,
                              int serviceProviderId,
                              int userId,
                              double latitude,
                              String description,
                              int availabilityId,
                              int discount,
                              int promotionId,
                              int subTotal,
                              int paymentTypeId,
                              double longitude,
							  int service_id) {
		this.total = total;
		this.address = address;
		this.service_provider_id = serviceProviderId;
		this.user_id = userId;
		this.latitude = latitude;
		this.description = description;
		this.availability_id = availabilityId;
		this.discount = discount;
		this.promotion_id = promotionId;
		this.subTotal = subTotal;
		this.payment_type_id = paymentTypeId;
		this.longitude = longitude;
		this.service_id = service_id;
	}

	public int getTotal() {
		return total;
	}

	public String getAddress() {
		return address;
	}

	public int getService_provider_id() {
		return service_provider_id;
	}

	public int getUser_id() {
		return user_id;
	}

	public double getLatitude() {
		return latitude;
	}

	public String getDescription() {
		return description;
	}

	public int getAvailability_id() {
		return availability_id;
	}

	public int getDiscount() {
		return discount;
	}

	public int getPromotion_id() {
		return promotion_id;
	}

	public int getService_id() {
		return service_id;
	}

	public int getSubTotal() {
		return subTotal;
	}

	public int getPayment_type_id() {
		return payment_type_id;
	}

	public double getLongitude() {
		return longitude;
	}
}