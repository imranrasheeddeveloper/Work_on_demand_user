package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderReviews {

    @SerializedName("booking_id")
    private int bookingId;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("raiting")
    private int raiting;

    @SerializedName("service_provider_id")
    private int serviceProviderId;

    @SerializedName("user_id")
    private int userId;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("updatedAt")
    private String updatedAt;

    public int getBookingId(){
        return bookingId;
    }

    public String getCreatedAt(){
        return createdAt;
    }

    public int getRaiting(){
        return raiting;
    }

    public int getServiceProviderId(){
        return serviceProviderId;
    }

    public int getUserId(){
        return userId;
    }

    public String getDescription(){
        return description;
    }

    public int getId(){
        return id;
    }

    public String getUpdatedAt(){
        return updatedAt;
    }
}