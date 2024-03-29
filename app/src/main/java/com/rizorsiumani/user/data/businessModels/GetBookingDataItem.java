package com.rizorsiumani.user.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class GetBookingDataItem {

	@SerializedName("address")
	private String address;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("start_date")
	private String start_date;

	@SerializedName("description")
	private String description;

	@SerializedName("discount")
	private int discount;

	@SerializedName("service_provider")
	private ServiceProvider serviceProvider;

	@SerializedName("subTotal")
	private int subTotal;

	@SerializedName("total")
	private int total;

	@SerializedName("payment_type")
	private PaymentType paymentType;

	@SerializedName("service")
	private Service service;

	@SerializedName("id")
	private int id;

	@SerializedName("user")
	private User user;

	@SerializedName("booking_availability")
	private BookingAvailability bookingAvailability;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("status")
	private String status;

	@SerializedName("promotion")
	private Promotion promotion;

	@SerializedName("createdAt")
	private String createdAt;

	public String getAddress(){
		return address;
	}

	public double getLatitude(){
		return latitude;
	}

	public String getDescription(){
		return description;
	}

	public int getDiscount(){
		return discount;
	}

	public ServiceProvider getServiceProvider(){
		return serviceProvider;
	}

	public int getSubTotal(){
		return subTotal;
	}

	public int getTotal(){
		return total;
	}

	public PaymentType getPaymentType(){
		return paymentType;
	}

	public Service getService(){
		return service;
	}

	public int getId(){
		return id;
	}

	public User getUser(){
		return user;
	}

	public BookingAvailability getBookingAvailability(){
		return bookingAvailability;
	}

	public double getLongitude(){
		return longitude;
	}

	public String getStatus(){
		return status;
	}

	public Promotion getPromotion(){
		return promotion;
	}

	public String getStart_date() {
		return start_date;
	}

	public String getCreatedAt() {
		return createdAt;
	}
}