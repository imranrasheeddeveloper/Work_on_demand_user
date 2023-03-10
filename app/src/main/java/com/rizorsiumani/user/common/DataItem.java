package com.rizorsiumani.user.common;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DataItem{

	@SerializedName("booking_timing")
	private List<BookingTimingItem> bookingTiming;

	@SerializedName("address")
	private String address;

	@SerializedName("latitude")
	private double latitude;

	@SerializedName("description")
	private String description;

	@SerializedName("discount")
	private int discount;

	@SerializedName("promotion_id")
	private int promotionId;

	@SerializedName("subTotal")
	private int subTotal;

	@SerializedName("total")
	private int total;

	@SerializedName("service_provider_id")
	private int serviceProviderId;

	@SerializedName("user_id")
	private int userId;

	@SerializedName("service_id")
	private int serviceId;

	@SerializedName("availability_id")
	private int availabilityId;

	@SerializedName("payment_type_id")
	private int paymentTypeId;

	@SerializedName("longitude")
	private double longitude;

	@SerializedName("start_date")
	private String startDate;

	public List<BookingTimingItem> getBookingTiming(){
		return bookingTiming;
	}

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

	public int getPromotionId(){
		return promotionId;
	}

	public int getSubTotal(){
		return subTotal;
	}

	public int getTotal(){
		return total;
	}

	public int getServiceProviderId(){
		return serviceProviderId;
	}

	public int getUserId(){
		return userId;
	}

	public int getServiceId(){
		return serviceId;
	}

	public int getAvailabilityId(){
		return availabilityId;
	}

	public int getPaymentTypeId(){
		return paymentTypeId;
	}

	public double getLongitude(){
		return longitude;
	}

	public String getStartDate(){
		return startDate;
	}
}