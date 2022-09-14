package com.rizorsiumani.workondemanduser.ui.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentProfileBinding;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;


public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {


    @Override
    protected FragmentProfileBinding getFragmentBinding() {
        return FragmentProfileBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        clickListeners();
    }

    private void clickListeners() {
        fragmentBinding.tvAddress.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), SavedAddresses.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}