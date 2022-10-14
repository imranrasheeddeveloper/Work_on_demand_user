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
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.edit_profile.EditProfile;
import com.rizorsiumani.workondemanduser.ui.notification.Notification;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;


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


        fragmentBinding.tvNotifications.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), Notification.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvInvite.setOnClickListener(view -> {
            Constants.constant.shareApp(requireContext());
        });

        fragmentBinding.tvPersonalDetails.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), EditProfile.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvBookings.setOnClickListener(view -> {
            Dashboard.goToBooking();
        });



        fragmentBinding.booking.setOnClickListener(view -> {
            Dashboard.goToBooking();
        });

        fragmentBinding.wallet.setOnClickListener(view -> {
            Dashboard.goToWallet();
        });
    }
}