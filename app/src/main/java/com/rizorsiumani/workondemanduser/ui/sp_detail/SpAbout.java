package com.rizorsiumani.workondemanduser.ui.sp_detail;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.view.View;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.databinding.FragmentSpAboutBinding;


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