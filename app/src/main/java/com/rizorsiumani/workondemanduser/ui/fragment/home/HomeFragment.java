package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.view.View;

import com.google.android.gms.dynamic.IFragmentWrapper;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentHomeBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.all_services.AllServices;
import com.rizorsiumani.workondemanduser.ui.filter.FilterSearch;

import com.rizorsiumani.workondemanduser.ui.search.SearchServices;
import com.rizorsiumani.workondemanduser.ui.searched_sp.ResultantServiceProviders;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> {


    @Override
    protected FragmentHomeBinding getFragmentBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Constants.isHome = true;

        if (prefRepository.getString("CURRENT_LOCATION") != null){
            String address = prefRepository.getString("CURRENT_LOCATION");
            if (address.equalsIgnoreCase("nil")){
                fragmentBinding.tvLocation.setText("Please set your location");
            }else {
                fragmentBinding.tvLocation.setText(address);
            }
        }

        getPromotionalImages();
        getServices();
        clickListeners();
    }

    private void clickListeners() {

        fragmentBinding.serachView.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), SearchServices.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvViewAll.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), AllServices.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.filter.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), FilterSearch.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvLocation.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), AddAddress.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void getServices() {

        List<String> service_categories = new ArrayList<>();
        service_categories.add("Cleaning");
        service_categories.add("Shifting");
        service_categories.add("Appliances");
        service_categories.add("Painting");
        service_categories.add("Electronic");
        service_categories.add("Repairing");
        service_categories.add("Cleaning");
        service_categories.add("More");

        List<Integer> service_icons = new ArrayList<>();
        service_icons.add(R.drawable.ic_cleaning);
        service_icons.add(R.drawable.ic_shifting);
        service_icons.add(R.drawable.ic_appliances__2_);
        service_icons.add(R.drawable.ic_painting__2_);
        service_icons.add(R.drawable.ic_electronics__2_);
        service_icons.add(R.drawable.ic_repairing);
        service_icons.add(R.drawable.ic_cleaning);
        service_icons.add(R.drawable.more);




        GridLayoutManager glm = new GridLayoutManager(requireContext(), 4);
        fragmentBinding.servicesList.setLayoutManager(glm);
        ServicesAdapter adapter = new ServicesAdapter(requireContext(), service_categories,service_icons);
        fragmentBinding.servicesList.setAdapter(adapter);
        adapter.setOnServiceClickListener(position -> {
            ActivityUtil.gotoPage(requireContext(), ResultantServiceProviders.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

    }

    private void getPromotionalImages() {

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.dummy_promotional_image);
        images.add(R.drawable.dummy_promotional_image);


        fragmentBinding.promotionsList.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
        SnapHelper snapHelper = new PagerSnapHelper();
        fragmentBinding.promotionsList.setOnFlingListener(null);

        snapHelper.attachToRecyclerView(fragmentBinding.promotionsList);
        fragmentBinding.promotionsList.setAdapter(new PromotionalAdapter(requireContext(), images));

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Constants.isHome = false;
    }
}