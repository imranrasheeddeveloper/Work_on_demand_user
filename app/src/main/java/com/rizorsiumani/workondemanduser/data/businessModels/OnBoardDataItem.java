package com.rizorsiumani.workondemanduser.data.businessModels;

import com.google.gson.annotations.SerializedName;

public class OnBoardDataItem {

    @SerializedName("image")
    private String image;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("updatedAt")
    private String updatedAt;

    public String getImage() {
        return image;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

}