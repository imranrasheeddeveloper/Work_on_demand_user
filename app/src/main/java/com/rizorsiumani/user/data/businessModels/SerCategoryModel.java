package com.rizorsiumani.user.data.businessModels;

public class SerCategoryModel {
    String name ;
    int icon ;
    String colorCode;

    public SerCategoryModel(String name, int icon, String colorCode) {
        this.name = name;
        this.icon = icon;
        this.colorCode = colorCode;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }

    public String getColorCode() {
        return colorCode;
    }
}
