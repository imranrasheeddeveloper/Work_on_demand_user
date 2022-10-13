package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.HomeSliderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.RecommendedServicesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderDataItem;
import com.rizorsiumani.workondemanduser.databinding.FragmentHomeBinding;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.ui.category.Categories;
import com.rizorsiumani.workondemanduser.ui.filter.FilterSearch;
import com.rizorsiumani.workondemanduser.ui.search.SearchServices;
import com.rizorsiumani.workondemanduser.ui.sub_category.SubCategories;
import com.rizorsiumani.workondemanduser.ui.sp_detail.SpProfile;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements PromotionalAdapter.OnPromotionAdapterClick {

    private HomeViewModel viewModel;
    private SliderViewModel sliderViewModel;
    List<CategoriesDataItem> categoriesDataItems;
    List<SliderDataItem> sliderDataItems;

    @Override
    protected FragmentHomeBinding getFragmentBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        sliderViewModel = new ViewModelProvider(this).get(SliderViewModel.class);
        setSlider();


        Constants.isHome = true;

        if (prefRepository.getString("CURRENT_LOCATION") != null) {
            String address = prefRepository.getString("CURRENT_LOCATION");
            if (address.equalsIgnoreCase("nil")) {
                fragmentBinding.tvChooseAddress.setText("Please set your location");
            } else {
                fragmentBinding.tvChooseAddress.setText(address);
            }
        }

        getServices();
        clickListeners();

        ArrayList<RecommendedServicesModel> names = new ArrayList<>();
        names.add(new RecommendedServicesModel(
                "Jermy Either",
                29.99,
                R.drawable.ic_car_one
        ));
        names.add(new RecommendedServicesModel(
                "Mr White",
                45.99,
                R.drawable.ic_car_two
        ));
        names.add(new RecommendedServicesModel(
                "Santiago Don",
                9.99,
                R.drawable.ic_car_three
        ));

        ArrayList<ServiceModel> serviceModels = new ArrayList<>();
        serviceModels.add(new ServiceModel("Car Service",names));
        serviceModels.add(new ServiceModel("Cleaning Service",names));
        serviceModels.add(new ServiceModel("Shifting Service",names));

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.allService.setLayoutManager(layoutManager1);
        AllSerAdapter adapter1 = new AllSerAdapter(serviceModels);
        fragmentBinding.allService.setAdapter(adapter1);

//        adapter1.setOnCellClickListener(this);


        populateCarServices();

    }

    private void setSlider() {
        if (sliderViewModel._slider.getValue() == null){
            sliderViewModel.getSliderInfo();
        }

        sliderViewModel._slider.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    //showLoading();
                } else if (!response.getError().isEmpty()) {
                   // hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getSliderData() != null) {
                   // hideLoading();

                    sliderDataItems = new ArrayList<>();
                    sliderDataItems.addAll(response.getData().getSliderData());
                    slider(sliderDataItems);

                }
            }
        });

    }

    private void populateCarServices() {

        ArrayList<RecommendedServicesModel> names = new ArrayList<>();
        names.add(new RecommendedServicesModel(
                "Marsha Williams",
                39.99,
                R.drawable.ic_one
        ));
        names.add(new RecommendedServicesModel(
                "Marco Piera",
                49.99,
                R.drawable.ic_two
        ));
        names.add(new RecommendedServicesModel(
                "Gordon Ramsy",
                79.99,
                R.drawable.ic_three
        ));



        LinearLayoutManager layoutManager1 = new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false);
        fragmentBinding.recomendedList.setLayoutManager(layoutManager1);
        PromotionalAdapter adapter1 = new PromotionalAdapter(names);
        fragmentBinding.recomendedList.setAdapter(adapter1);
    }

    private void clickListeners() {

        fragmentBinding.searchIcon.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), SearchServices.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvViewAll.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), Categories.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });


        fragmentBinding.filter.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), FilterSearch.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvChooseAddress.setOnClickListener(view -> {
            ActivityUtil.gotoPage(requireContext(), SavedAddresses.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void getServices() {

        if (viewModel._category.getValue() == null){
            viewModel.categories(1);
        }

        viewModel._category.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                  //  showLoading();
                } else if (!response.getError().isEmpty()) {
                   // hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                   // hideLoading();
                    categoriesDataItems = new ArrayList<>();
                    categoriesDataItems.addAll(response.getData().getData());
                    if (categoriesDataItems.size() > 0){
                        buildCategoryRv(categoriesDataItems);
                    }


                }
            }
        });


//        List<SerCategoryModel> service_categories = new ArrayList<>();
//        service_categories.add(new SerCategoryModel("Cleaning", R.drawable.ic_cleaning, "#eb5657"));
//        service_categories.add(new SerCategoryModel("Appliances", R.drawable.ic_electric_appliance, "#0ebdde"));
//        service_categories.add(new SerCategoryModel("Electronic", R.drawable.ic_electrician, "#1aa882"));
//        service_categories.add(new SerCategoryModel("Washing", R.drawable.ic_laundry_machine, "#5824c4"));
//        service_categories.add(new SerCategoryModel("Painting", R.drawable.ic_paint_roller, "#fda145"));
//        service_categories.add(new SerCategoryModel("Wood Working", R.drawable.ic_woodworking, "#0ebdde"));
//        service_categories.add(new SerCategoryModel("Shifting", R.drawable.ic_shiftinng, "#eb5657"));




    }

    private void buildCategoryRv(List<CategoriesDataItem> categoriesDataItems) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false);
        fragmentBinding.categoriesList.setLayoutManager(layoutManager);
        CategoriesAdapter adapter = new CategoriesAdapter(requireContext(), categoriesDataItems);
        fragmentBinding.categoriesList.setAdapter(adapter);


        adapter.setOnServiceClickListener(position -> {
            Intent intent = new Intent(requireContext(),SubCategories.class);
            intent.putExtra("category_id",categoriesDataItems.get(position).getId());
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void slider(List<SliderDataItem> sliderDataItems) {

//        sliderModel.add(new HomeSliderModel(
//                "Fix the Broken Stuff by Asking for he Technicians.",
//                R.drawable.ic_home_slider_car_illus,
//                "#00A688"
//        ));
//
//        sliderModel.add(new HomeSliderModel(
//                "Assigning a Handyman At Work To Fix The Household.",
//                R.drawable.ic_sliderr,
//                "#ffa24a"
//        ));
//        sliderModel.add(new HomeSliderModel(
//                "Add Hands To Fix Your Car.",
//                R.drawable.ic_home_slider_car_illus,
//                "#00A688"
//        ));

        HomeSliderAdapter sliderAdapter = new HomeSliderAdapter(requireContext(), sliderDataItems);
        fragmentBinding.imageSlider.setSliderAdapter(sliderAdapter);
        fragmentBinding.imageSlider.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        fragmentBinding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.THIN_WORM);
        fragmentBinding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        fragmentBinding.imageSlider.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        fragmentBinding.imageSlider.setIndicatorSelectedColor(
                requireContext().getResources().getColor(R.color.primary, null)
        );
        fragmentBinding.imageSlider.setIndicatorUnselectedColor(Color.WHITE);
        fragmentBinding.imageSlider.setScrollTimeInSec(2);
        fragmentBinding.imageSlider.setAutoCycle(true);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();


        viewModel._category.removeObservers(this);
        viewModel = null;
        Constants.isHome = false;
    }

    @Override
    public void onCellClick(int pos) {
        startActivity(new Intent(requireContext(), SpProfile.class));
    }
}