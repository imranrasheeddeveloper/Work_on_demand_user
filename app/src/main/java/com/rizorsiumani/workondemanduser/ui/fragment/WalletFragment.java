package com.rizorsiumani.workondemanduser.ui.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentWalletBinding;


public class WalletFragment extends BaseFragment<FragmentWalletBinding> {


    @Override
    protected FragmentWalletBinding getFragmentBinding() {
        return FragmentWalletBinding.inflate(getLayoutInflater());
    }
}