package com.rizorsiumani.workondemanduser.ui.category;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.databinding.ActivityCategoriesBinding;

public class Categories extends BaseActivity<ActivityCategoriesBinding> {

    @Override
    protected ActivityCategoriesBinding getActivityBinding() {
        return ActivityCategoriesBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}