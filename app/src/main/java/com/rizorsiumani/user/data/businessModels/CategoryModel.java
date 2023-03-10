package com.rizorsiumani.user.data.businessModels;

public class CategoryModel {
    private final String title;
    private final String colorCode;
    private final int imageResource;

    public CategoryModel(String title, int imageResource, String colorCode) {
        this.title = title;
        this.colorCode = colorCode;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getColorCode() {
        return colorCode;
    }

    public int getImageResource() {
        return imageResource;
    }
}
