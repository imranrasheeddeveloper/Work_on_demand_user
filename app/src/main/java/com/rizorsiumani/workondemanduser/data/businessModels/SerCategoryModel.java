package com.rizorsiumani.workondemanduser.data.businessModels;

import android.graphics.drawable.Drawable;

public class SerCategoryModel {
    String name ;
    Integer icon ;
    Integer background;

    public SerCategoryModel(String name, Integer icon, Integer background) {
        this.name = name;
        this.icon = icon;
        this.background = background;
    }

    public String getName() {
        return name;
    }

    public Integer getIcon() {
        return icon;
    }

    public Integer getBackground() {
        return background;
    }
}
