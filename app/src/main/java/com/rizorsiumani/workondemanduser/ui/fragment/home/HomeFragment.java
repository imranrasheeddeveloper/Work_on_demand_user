package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.graphics.Color;
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
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.SerCategoryModel;
import com.rizorsiumani.workondemanduser.databinding.FragmentHomeBinding;
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.all_services.AllServices;
import com.rizorsiumani.workondemanduser.ui.filter.FilterSearch;

import com.rizorsiumani.workondemanduser.ui.search.SearchServices;
import com.rizorsiumani.workondemanduser.ui.searched_sp.ResultantServiceProviders;
import com.rizorsiumani.workondemanduser.ui.walkthrough.SliderAdapter;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

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
                fragmentBinding.tvChooseAddress.setText("Please set your location");
            }else {
                fragmentBinding.tvChooseAddress.setText(address);
            }
        }

        getPromotionalImages();
        getServices();
        clickListeners();

        ArrayList<String> names = new ArrayList<>();
        names.add("40.01$");
        names.add("40.01$");
        names.add("40.01$");

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false);
        fragmentBinding.recomendedList.setLayoutManager(layoutManager1);
        PromotionalAdapter adapter1 = new PromotionalAdapter(requireContext(), names);
        fragmentBinding.recomendedList.setAdapter(adapter1);
    }

    private void clickListeners() {

        fragmentBinding.searchIcon.setOnClickListener(view -> {
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

//        fragmentBinding.tvLocation.setOnClickListener(view -> {
//            ActivityUtil.gotoPage(requireContext(), AddAddress.class);
//            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });
    }

    private void getServices() {

        List<SerCategoryModel> service_categories = new ArrayList<>();
        service_categories.add(new SerCategoryModel("Cleaning",R.drawable.ic_cleaning,R.drawable.gradient_bg));
        service_categories.add(new SerCategoryModel("Appliances",R.drawable.ic_electric_appliance,R.drawable.gradient_bg));
        service_categories.add(new SerCategoryModel("Electronic",R.drawable.ic_electrician,R.drawable.gradient_bg));
        service_categories.add(new SerCategoryModel("Washing",R.drawable.ic_laundry_machine,R.drawable.gradient_bg));
        service_categories.add(new SerCategoryModel("Painting",R.drawable.ic_paint_roller,R.drawable.gradient_bg));
        service_categories.add(new SerCategoryModel("Wood Working",R.drawable.ic_woodworking,R.drawable.gradient_bg));
        service_categories.add(new SerCategoryModel("Shifting",R.drawable.ic_shiftinng,R.drawable.gradient_bg));



        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false);
        fragmentBinding.categoriesList.setLayoutManager(layoutManager);
        CategoriesAdapter adapter = new CategoriesAdapter(requireContext(), service_categories);
        fragmentBinding.categoriesList.setAdapter(adapter);



//        adapter.setOnServiceClickListener(position -> {
//            ActivityUtil.gotoPage(requireContext(), ResultantServiceProviders.class);
//            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//        });

    }

    private void getPromotionalImages() {

        List<String> images = new ArrayList<>();
        images.add("Fix the Broken Stuff by Asking for he Technicians.");
        images.add("Fix the Broken Stuff by Asking for he Technicians.");
        images.add("Fix the Broken Stuff by Asking for he Technicians.");


        HomeSliderAdapter sliderAdapter = new HomeSliderAdapter(requireContext(), images);
        fragmentBinding.imageSlider.setSliderAdapter(sliderAdapter);
        fragmentBinding.imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        fragmentBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        fragmentBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        fragmentBinding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        fragmentBinding.imageSlider.setIndicatorSelectedColor(Color.WHITE);
        fragmentBinding.imageSlider.setIndicatorUnselectedColor(Color.WHITE);
        fragmentBinding.imageSlider.setAutoCycle(false);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Constants.isHome = false;
    }
}