package com.rizorsiumani.workondemanduser.ui.service_providers.sp_list;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentServiceProviderListBinding;
import com.rizorsiumani.workondemanduser.ui.searched_sp.ResultantServiceProviders;
import com.rizorsiumani.workondemanduser.ui.service_providers.ServiceProviderAdapter;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;


public class ServiceProviderList extends BaseFragment<FragmentServiceProviderListBinding> {


    @Override
    protected FragmentServiceProviderListBinding getFragmentBinding() {
        return FragmentServiceProviderListBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        servicesProvidersRv();

    }

    private void servicesProvidersRv() {

        List<String> name = new ArrayList<>();
        name.add("Michel Jeff");
        name.add("Michel Jeff");
        name.add("Michel Jeff");
        name.add("Michel Jeff");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.serviceProvidersList.setLayoutManager(layoutManager);
        ServiceProviderAdapter adapter = new ServiceProviderAdapter(name, App.applicationContext);
        fragmentBinding.serviceProvidersList.setAdapter(adapter);

        adapter.setOnProviderSelectListener(position -> {
            ActivityUtil.gotoPage(requireContext(), SpProfile.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}