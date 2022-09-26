package com.rizorsiumani.workondemanduser.ui.sp_detail.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentServicesBinding;
import com.rizorsiumani.workondemanduser.ui.address.AdressesAdapter;
import com.rizorsiumani.workondemanduser.ui.fragment.home.ServicesAdapter;

import java.util.ArrayList;
import java.util.List;


public class Services extends BaseFragment<FragmentServicesBinding> {


    @Override
    protected FragmentServicesBinding getFragmentBinding() {
        return FragmentServicesBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buildRv();
    }

    private void buildRv() {
        List<String> data = new ArrayList<>();
        data.add("2 Hours Office Cleaning");
        data.add("3 Hours Home Cleaning");
        data.add("3 Hours Office Cleaning");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.servicesList.setLayoutManager(layoutManager);
        SpServicesAdapter adapter = new SpServicesAdapter(data, App.applicationContext);
        fragmentBinding.servicesList.setAdapter(adapter);

    }
}