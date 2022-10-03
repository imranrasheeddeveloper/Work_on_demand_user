package com.rizorsiumani.workondemanduser.data.businessModels;

public class HomeSliderModel {
    private final String desc;
    private final int imgID;
    private final String colorCode;

    public HomeSliderModel(String desc, int imgID, String colorCode) {
        this.desc = desc;
        this.imgID = imgID;
        this.colorCode = colorCode;
    }

    public String getDesc() {
        return desc;
    }

    public int getImgID() {
        return imgID;
    }

    public String getColorCode() {
        return colorCode;
    }
}
