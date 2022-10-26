package com.rizorsiumani.workondemanduser.ui.sp_detail.service;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.google.gson.Gson;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.data.businessModels.ServicesDataItem;
import com.rizorsiumani.workondemanduser.databinding.FragmentServicesBinding;
import com.rizorsiumani.workondemanduser.ui.booking.BookService;
import com.rizorsiumani.workondemanduser.ui.sp_detail.ProviderDetailViewModel;

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

        try {

        spID = getActivity().getIntent().getStringExtra("service_provider_id");

        viewModel = new ViewModelProvider(this).get(ProviderDetailViewModel.class);
        viewModel.getServices(Integer.parseInt(spID));
        viewModel._services.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    // showLoading();
                } else if (!response.getError().isEmpty()) {
                    // hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    if (response.getData().getData().size() > 0){
                       servicesList = new ArrayList<>();
                       servicesList.addAll(response.getData().getData());
                        buildRv(servicesList);
                    }
                }
            }
        });

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void buildRv(List<ServicesDataItem> servicesList) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.servicesList.setLayoutManager(layoutManager);
        SpServicesAdapter adapter = new SpServicesAdapter(servicesList, requireContext());
        fragmentBinding.servicesList.setAdapter(adapter);

        adapter.setOnClickListener(position -> {
            Gson gson = new Gson();
            String data = gson.toJson(servicesList.get(position), ServicesDataItem.class);

            Intent intent = new Intent(requireContext(), BookService.class);
            intent.putExtra("service_data",data);
            intent.putExtra("service_provider_id",spID);
            startActivity(intent);

        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        viewModel._services.removeObservers(this);
        viewModel = null;

    }
}