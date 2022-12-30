package com.rizorsiumani.workondemanduser.ui.sp_detail.service;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.FragmentServicesBinding;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ProviderDetailViewModel;
import com.rizorsiumani.workondemanduser.ui.start_date.StartDate;

import java.util.ArrayList;
import java.util.List;


public class Services extends BaseFragment<FragmentServicesBinding> {

    ProviderDetailViewModel viewModel;
    List<ServicesDataItem> servicesList;
    String spID;

    @Override
    protected FragmentServicesBinding getFragmentBinding() {
        return FragmentServicesBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideCartButton();

        try {

            spID = TinyDbManager.getServiceProviderID();

            viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
            viewModel.getServices(Integer.parseInt(spID));
            viewModel._services.observe(getViewLifecycleOwner(), response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData().getData() != null) {
                        hideLoading();
                        if (response.getData().getData().size() > 0) {
                            hideNoDataAnimation();
                            servicesList = new ArrayList<>();
                            servicesList.addAll(response.getData().getData());
                            buildRv(servicesList);
                        } else {
                            showNoDataAnimation(R.raw.no_job, "No Services");
                        }
                    }
                }
            });

        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void buildRv(List<ServicesDataItem> servicesList) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.servicesList.setLayoutManager(layoutManager);
        SpServicesAdapter adapter = new SpServicesAdapter(servicesList, requireContext());
        fragmentBinding.servicesList.setAdapter(adapter);

        adapter.setOnClickListener(position -> {
            TinyDbManager.saveServiceProviderID(spID);

            Gson gson = new Gson();
            String data = gson.toJson(servicesList.get(position), ServicesDataItem.class);
            Intent intent = new Intent(requireContext(), StartDate.class);
            intent.putExtra("service_data", data);
            startActivity(intent);

//          Calendar cal = Calendar.getInstance();
//
//          DatePickerDialog dpd = new DatePickerDialog(requireContext(), (view1, year, month, dayOfMonth) -> {
//                Toast.makeText(requireContext(), String.format("%d", year) + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth), Toast.LENGTH_SHORT).show();
//            }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
//            dpd.show();

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel._services.removeObservers(this);
        viewModel = null;

    }
}