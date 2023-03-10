package com.rizorsiumani.user.ui.sp_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.rizorsiumani.user.BaseFragment;
import com.rizorsiumani.user.databinding.FragmentSpAboutBinding;


public class SpAbout extends BaseFragment<FragmentSpAboutBinding> {


    @Override
    protected FragmentSpAboutBinding getFragmentBinding() {
        return FragmentSpAboutBinding.inflate(getLayoutInflater());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}