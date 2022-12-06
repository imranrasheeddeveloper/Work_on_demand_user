package com.rizorsiumani.workondemanduser.ui.service_providers.sp_list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderModel;
import com.rizorsiumani.workondemanduser.databinding.FragmentServiceProviderListBinding;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderAdapter;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderViewModel;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ServiceProviderList extends BaseFragment<FragmentServiceProviderListBinding> {

    List<ServiceProviderDataItem> serviceProviders;
    private ServiceProviderViewModel viewModel;
    String subCatID = "" , catID = "";
    ServiceProviderModel providerModel;


    @Override
    protected FragmentServiceProviderListBinding getFragmentBinding() {
        return FragmentServiceProviderListBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        try {
            String providerData = getActivity().getIntent().getStringExtra("providers");
            Gson gson = new Gson();
            providerModel = gson.fromJson(providerData, ServiceProviderModel.class);
            catID = getActivity().getIntent().getStringExtra("cat_id");

            if (providerModel == null){
                if (!catID.isEmpty()){
                    viewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);
                    JsonObject object = new JsonObject();
                    object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
                    object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
                    object.addProperty("category_id", catID);
                    String token = prefRepository.getString("token");
                    viewModel.catServiceProviders(1, token, object);
                    viewModel._by_cat_provider.observe(getViewLifecycleOwner(), response -> {
                        if (response != null) {
                            if (response.isLoading()) {
                                 showLoading();
                            } else if (!response.getError().isEmpty()) {
                                 hideLoading();
                                showSnackBarShort(response.getError());
                            } else if (response.getData().isSuccess()) {

                                hideLoading();
                                if (response.getData().getData().size() > 0) {
                                    serviceProviders = new ArrayList<>();
                                    serviceProviders.addAll(response.getData().getData());
                                    buildRv(serviceProviders);
                                } else {
                                    showSnackBarShort("Data not Available");
                                }
                            }

                        }
                    });
                }
            }else {
                buildRv(providerModel.getData());
//                viewModel = new ViewModelProvider(this).get(ServiceProviderViewModel.class);
//                JsonObject object = new JsonObject();
//                object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
//                object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
//                object.addProperty("sub_category_id", subCatID);
//                String token = prefRepository.getString("token");
//                viewModel.serviceProviders(1, token, object);
//                viewModel._provider.observe(getViewLifecycleOwner(), response -> {
//                    if (response != null) {
//                        if (response.isLoading()) {
//                             showLoading();
//                        } else if (!response.getError().isEmpty()) {
//                            hideLoading();
//                            showSnackBarShort(response.getError());
//                        } else if (response.getData().isSuccess()) {
//
//                            hideLoading();
//                            if (response.getData().getData().size() > 0) {
//                                hideNoDataAnimation();
//                                serviceProviders = new ArrayList<>();
//                                serviceProviders.addAll(response.getData().getData());
//                                buildRv(serviceProviders);
//                            } else {
//                                showNoDataAnimation();
//                            }
//                        }
//
//                    }
//                });
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


//
//
//            if (getArguments() != null) {
//                if (getArguments().getString("service_providers") != null) {
//                    String data = getArguments().getString("service_providers");
//                    Gson gson = new Gson();
//                    ServiceProviderModel = gson.fromJson(data,ServiceProviderModel.class);
//                    if (ServiceProviderModel.getData().size() > 0){
//                        buildRv();
//                    }else {
//                        showSnackBarShort("Data not Available");
//                    }
//                }
//            }
    }

    private void buildRv(List<ServiceProviderDataItem> serviceProviders) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
        fragmentBinding.serviceProvidersList.setLayoutManager(layoutManager);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(serviceProviders, requireContext());
        fragmentBinding.serviceProvidersList.setAdapter(adapter);

        adapter.setOnProviderSelectListener(position -> {
            Intent intent = new Intent(requireContext(), SpProfile.class);
            intent.putExtra("service_provider_id",String.valueOf(serviceProviders.get(position).getId()));
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }


//    private void servicesProvidersRv(List<DataItem> data) {
//
//        fragmentBinding.serviceProvidersList.hasFixedSize();
//        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
//        fragmentBinding.serviceProvidersList.setLayoutManager(layoutManager);
//        ServiceProviderAdapter adapter = new ServiceProviderAdapter(data, requireContext());
//        fragmentBinding.serviceProvidersList.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        fragmentBinding.serviceProvidersList.setVisibility(View.VISIBLE);
//
//        adapter.setOnProviderSelectListener(position -> {
//            ActivityUtil.gotoPage(requireContext(), SpProfile.class);
//            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });
//    }
}