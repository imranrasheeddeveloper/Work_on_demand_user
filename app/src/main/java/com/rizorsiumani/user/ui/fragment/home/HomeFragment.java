package com.rizorsiumani.user.ui.fragment.home;

import static com.rizorsiumani.user.utils.map_utils.GeoCoders.GetProperLocationAddress;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseFragment;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.CategoriesDataItem;
import com.rizorsiumani.user.data.businessModels.HomeContentDataItem;
import com.rizorsiumani.user.data.businessModels.SliderDataItem;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.FragmentHomeBinding;
import com.rizorsiumani.user.ui.address.SavedAddresses;
import com.rizorsiumani.user.ui.booking_detail.BookingDetail;
import com.rizorsiumani.user.ui.category.Categories;
import com.rizorsiumani.user.ui.dashboard.Dashboard;
import com.rizorsiumani.user.ui.filter.CategoryFilterAdapter;
import com.rizorsiumani.user.ui.inbox.Inbox;
import com.rizorsiumani.user.ui.inbox.InboxViewModel;
import com.rizorsiumani.user.ui.notification.Notification;
import com.rizorsiumani.user.ui.search_provider.SearchProvider;
import com.rizorsiumani.user.ui.service_providers.Serviceproviders;
import com.rizorsiumani.user.ui.sub_category.SubCategories;
import com.rizorsiumani.user.utils.ActivityUtil;
import com.rizorsiumani.user.utils.AppBarStateChangeListener;
import com.rizorsiumani.user.utils.Constants;
import com.rizorsiumani.user.utils.map_utils.LocationService;
import com.rizorsiumani.user.utils.map_utils.LocationUpdateService;
import com.rizorsiumani.user.utils.map_utils.OnLocationUpdateListener;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment<FragmentHomeBinding> implements OnLocationUpdateListener {

    private HomeViewModel viewModel;
    private HomeContentViewModel homeContentViewModel;
    private SliderViewModel sliderViewModel;
    List<CategoriesDataItem> categoriesDataItems;
    List<Integer> selectedFilter;

    List<SliderDataItem> sliderDataItems;
    List<HomeContentDataItem> contentDataItems;
    int count = 0;
    boolean isLocationPermissionGranted;
    private InboxViewModel inboxViewModel;


    @Override
    protected FragmentHomeBinding getFragmentBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideCartButton();
        locationHandler();
        Dashboard.hideTabs(false);
        inboxViewModel = new ViewModelProvider(this).get(InboxViewModel.class);

        if (TinyDbManager.getCartData().size() > 0){
            fragmentBinding.homeCartButton.setVisibility(View.VISIBLE);
            fragmentBinding.cartCount.setText(String.valueOf(TinyDbManager.getCartData().size()));
        }else {
            fragmentBinding.homeCartButton.setVisibility(View.GONE);
        }


        selectedFilter = new ArrayList<>();
        isLocationPermissionGranted = LocationService.service.requestLocationPermission(requireContext());
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        sliderViewModel = new ViewModelProvider(this).get(SliderViewModel.class);
        homeContentViewModel = new ViewModelProvider(this).get(HomeContentViewModel.class);


        if (Constants.constant.isLocationPermissionGranted) {
            locationHandler();
        }else {
            isLocationPermissionGranted = LocationService.service.requestLocationPermission(requireContext());
        }

        fragmentBinding.skeletonLayout.startLoading();
        fragmentBinding.skeletonLayout1.startLoading();
        Constants.constant.isHome = true;

        if (!TinyDbManager.getSelectedAddress().isEmpty()) {
            fragmentBinding.tvChooseAddress.setText(TinyDbManager.getSelectedAddress());
        } else if (!TinyDbManager.getCurrentAddress().isEmpty()) {
            fragmentBinding.tvChooseAddress.setText(TinyDbManager.getCurrentAddress());
        }else {
            fragmentBinding.tvChooseAddress.setText("Please set your location");
        }

        fragmentBinding.appBar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state.equals(State.COLLAPSED)) {
                    fragmentBinding.toolbar.setBackgroundResource(R.drawable.custom_toolbar);
                    fragmentBinding.title.setTextColor(Color.parseColor("#081533"));

                    fragmentBinding.view2.setVisibility(View.VISIBLE);
                    fragmentBinding.view2.setBackgroundColor(Color.parseColor("#00000000"));
                } else if (state.equals(State.EXPANDED)) {
                    fragmentBinding.view2.setVisibility(View.GONE);

                    fragmentBinding.toolbar.setBackgroundResource(R.color.transparent);
                    fragmentBinding.title.setTextColor(Color.parseColor("#00000000"));

                } else if ((state.equals(State.IDLE))) {
                    fragmentBinding.toolbar.setBackgroundResource(R.color.transparent);
                    fragmentBinding.title.setTextColor(Color.parseColor("#00000000"));
                }
            }
        });

        getServices();
        setHomeContent(null);
        setSlider();
        clickListeners();

        fragmentBinding.swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getServices();
                setSlider();
                setHomeContent(null);
                fragmentBinding.swipeContainer.setRefreshing(false);
            }
        });

    }

    private void setHomeContent(List<Integer> selectedFilter) {

        JsonObject object = new JsonObject();

        if (selectedFilter == null) {
            object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
            object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
        } else {
            object.addProperty("latitude", String.valueOf(Constants.constant.latitude));
            object.addProperty("longitude", String.valueOf(Constants.constant.longitude));
            Gson gson = new Gson();
            object.add("categoriesFillter", gson.toJsonTree(selectedFilter));
        }
        String token = prefRepository.getString("token");
        homeContentViewModel.getHomeContent(token, object);

        homeContentViewModel._home_content.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    if (response.getData().getData().size() > 0) {
                        contentDataItems = new ArrayList<>();
                        for (int i = 0; i < response.getData().getData().size(); i++) {
                            if (response.getData().getData().get(i).getServiceProviderCategories() != null
                            && response.getData().getData().get(i).getServiceProviderCategories().size() > 0){
                                contentDataItems.add(response.getData().getData().get(i));
                            }
                        }
                        setHomeContentRv(contentDataItems);
                    }
                }
            }
        });

    }

    private void setHomeContentRv(List<HomeContentDataItem> contentDataItems) {
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.allService.setLayoutManager(layoutManager1);
        AllSerAdapter adapter1 = new AllSerAdapter(requireContext(), contentDataItems);
        fragmentBinding.allService.setAdapter(adapter1);

        adapter1.setOnServiceClickListener(position -> {
            Constants.constant.isHome = false;
            Intent intent = new Intent(requireContext(), Serviceproviders.class);
            intent.putExtra("cat_id",String.valueOf(contentDataItems.get(position).getId()));
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

        });

        fragmentBinding.skeletonLayout1.stopLoading();
        fragmentBinding.allService.setVisibility(View.VISIBLE);
        fragmentBinding.skeletonLayout1.setVisibility(View.GONE);
    }

    private void setSlider() {
        if (sliderViewModel._slider.getValue() == null) {
            sliderViewModel.getSliderInfo();
        }

        sliderViewModel._slider.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getSliderData() != null) {
                    hideLoading();
                    sliderDataItems = new ArrayList<>();
                    sliderDataItems.addAll(response.getData().getSliderData());
                    slider(sliderDataItems);

                }
            }
        });

    }

    private void clickListeners() {

        fragmentBinding.searchIcon.setOnClickListener(view -> {
            Constants.constant.isHome = false;
            ActivityUtil.gotoPage(requireContext(), SearchProvider.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.inboxIcon.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), Inbox.class);
            startActivity(intent);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            Constants.constant.isHome = false;
//            String token = prefRepository.getString("token");
//
//            inboxViewModel.isInboxExist(token, );
//            inboxViewModel._is_exist.observe(getViewLifecycleOwner() , response -> {
//                if (response != null) {
//                    if (response.isLoading()) {
//                        showLoading();
//                    } else if (!response.getError().isEmpty()) {
//                        hideLoading();
//                        showSnackBarShort(response.getError());
//                    } else if (response.getData().isSuccess()) {
//                        hideLoading();
//                        if (response.getData().getInboxId() != 0) {
//                            hideNoDataAnimation();
//                            Intent intent = new Intent(requireContext(), Inbox.class);
//                            startActivity(intent);
//                            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                        }
//                    }
//                }
//            });

        });

        fragmentBinding.tvSearch.setOnClickListener(view -> {
            Constants.constant.isHome = false;
            ActivityUtil.gotoPage(requireContext(), SearchProvider.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.notifications.setOnClickListener(view -> {
            Constants.constant.isHome = false;
            ActivityUtil.gotoPage(requireContext(), Notification.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.homeCartButton.setOnClickListener(view -> {
            Constants.constant.isHome = false;
            ActivityUtil.gotoPage(requireContext(), BookingDetail.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        fragmentBinding.tvViewAll.setOnClickListener(view -> {
            Constants.constant.isHome = false;
            ActivityUtil.gotoPage(requireContext(), Categories.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });


        fragmentBinding.filter.setOnClickListener(view -> {
            Constants.constant.isHome = false;
            expandFiltersSheet();

//            if (count == 0) {
//                count = 1;
//                expandFiltersSheet();
//            }
        });

        fragmentBinding.tvChooseAddress.setOnClickListener(view -> {
            Constants.constant.isHome = false;
            ActivityUtil.gotoPage(requireContext(), SavedAddresses.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void expandFiltersSheet() {
        final BottomSheetDialog bt = new BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme);
        bt.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.filters_bottom_sheet, null, false);
        bt.getBehavior().addBottomSheetCallback(mBottomSheetBehaviorCallback);

        TextView cancel = view.findViewById(R.id.tv_cancel);
        TextView select = view.findViewById(R.id.tv_done);
        RecyclerView list = view.findViewById(R.id.filtersList);

        if (viewModel._ddCategory.getValue() == null) {
            viewModel.dropDownCategories();
        }

        viewModel._ddCategory.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                 } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    categoriesDataItems = new ArrayList<>();
                    categoriesDataItems.addAll(response.getData().getData());
                    LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);
                    list.setLayoutManager(layoutManager);
                    CategoryFilterAdapter adapter = new CategoryFilterAdapter(categoriesDataItems, requireContext());
                    list.setAdapter(adapter);

                    adapter.setOnItemClickListener(new CategoryFilterAdapter.OnItemClickListener() {
                        @Override
                        public void onSelect(int position) {
                            selectedFilter.add(categoriesDataItems.get(position).getId());
                        }

                        @Override
                        public void onUnselect(int position) {
                            for (int i = 0; i < selectedFilter.size() - 1; i++) {
                                if (selectedFilter.get(i).equals(categoriesDataItems.get(position).getId())) {
                                    selectedFilter.remove(i);
                                }
                            }
                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(view1 -> {
            count = 0;
            bt.dismiss();
        });

        select.setOnClickListener(view1 -> {
            bt.dismiss();
            setHomeContent(selectedFilter);
        });

        bt.setContentView(view);
        bt.show();
    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                count = 0;
            } else {
                count = 1;
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    private void getServices() {

        viewModel.categories(1);

        viewModel._category.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                      showLoading();
                } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                } else if (response.getData().getData() != null) {
                     hideLoading();
                    categoriesDataItems = new ArrayList<>();
                    categoriesDataItems.addAll(response.getData().getData());
                    if (categoriesDataItems.size() > 0) {
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

        fragmentBinding.categoriesList.setVisibility(View.VISIBLE);
        fragmentBinding.skeletonLayout.setVisibility(View.INVISIBLE);


        adapter.setOnServiceClickListener(position -> {
            Constants.constant.isHome = false;
            TinyDbManager.saveCategory(categoriesDataItems.get(position));
            ActivityUtil.gotoPage(requireContext(), SubCategories.class);
            requireActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void slider(List<SliderDataItem> sliderDataItems) {

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
        inboxViewModel._is_exist.removeObservers(this);
        inboxViewModel = null;
        viewModel = null;
    }


    private void locationHandler() {
        LocationUpdateService locationUpdateService = new LocationUpdateService();
        locationUpdateService.LocationHandler(requireActivity(), this);
    }

    @Override
    public void onLocationChange(Location location) {
        Constants.constant.latitude = location.getLatitude();
        Constants.constant.longitude = location.getLongitude();
        String address = GetProperLocationAddress(location.getLatitude(), location.getLongitude(),requireContext());
        TinyDbManager.saveCurrentAddress(address);
//        fragmentBinding.tvChooseAddress.setText(address);
    }

    @Override
    public void onError(String error) {

    }




}