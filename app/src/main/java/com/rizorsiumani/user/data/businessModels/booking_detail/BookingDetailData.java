package com.rizorsiumani.user.data.businessModels.booking_detail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class BookingDetailData {

	@SerializedName("address")
	private String address;

	@SerializedName("latitude")
	private double latitude;

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

	@SerializedName("booking_timings")
	private List<BookingTimingsItem> bookingTimings;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("status")
	private String status;

	@SerializedName("start_date")
	private String startDate;

	@SerializedName("promotion")
	private Object promotion;

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

	public List<BookingTimingsItem> getBookingTimings(){
		return bookingTimings;
	}

	public double getLongitude(){
		return longitude;
	}

	public String getStatus(){
		return status;
	}

	public String getStartDate(){
		return startDate;
	}

	public Object getPromotion(){
		return promotion;
	}
}