package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class ServiceProviderReviews {

    @SerializedName("rating")
    private String rating;

    public String getRating() {
        return rating;
    }
}