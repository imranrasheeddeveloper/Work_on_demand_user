package com.rizorsiumani.workondemanduser.ui.fragment.booking;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.GetBookingDataItem;
import com.rizorsiumani.workondemanduser.databinding.FragmentBookingBinding;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.ui.login.Login;
import com.rizorsiumani.workondemanduser.ui.requested_sevices.RequestServices;
import com.rizorsiumani.workondemanduser.ui.view_booking_information.BookingInformation;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class BookingFragment extends BaseFragment<FragmentBookingBinding> {

    private BookingsViewModel viewModel;
    List<GetBookingDataItem> dataItems;
    Parcelable recyclerViewState;
    private boolean flag_loading;
    int nextPage = 1;
    private int maxPageLimit;
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    LinearLayoutManager mLayoutManager;
    String current_status;

    @Override
    protected FragmentBookingBinding getFragmentBinding() {
        return FragmentBookingBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BookingsViewModel.class);
        mLayoutManager = new LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false);

        fragmentBinding.bookingToolbar.title.setText("Bookings");
        fragmentBinding.bookingToolbar.back.setVisibility(View.INVISIBLE);

        callStatus();
        clickListeners();

        fragmentBinding.current.setBackgroundResource(R.drawable.selected_bg);
        fragmentBinding.current.setTextColor(getResources().getColor(R.color.white));
        fragmentBinding.past.setBackgroundResource(R.drawable.selector_bg);
        fragmentBinding.past.setTextColor(getResources().getColor(R.color.black));

        getBookings(nextPage, "Pending");
        current_status = "Pending";

        fragmentBinding.bookingList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recyclerViewState = fragmentBinding.bookingList.getLayoutManager().onSaveInstanceState(); // save recycleView state

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {

                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisiblesItems = mLayoutManager.findFirstVisibleItemPosition();

                    if (flag_loading) {

                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                            flag_loading = false;
                            nextPage++;

                            if (nextPage > maxPageLimit) {
                                Toast.makeText(requireContext(), "No more data!", Toast.LENGTH_SHORT).show();
                            } else {
                                getBookings(nextPage, current_status);
                                showLoading();
                            }

                        }
                    }
                }
            }
        });


    }

    private void callStatus() {
        List<String> status = new ArrayList<>();
        status.add("Pending");
        status.add("In Progress");
        status.add("Canceled");
        status.add("Completed");

        LinearLayoutManager llm = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        fragmentBinding.statusList.setLayoutManager(llm);
        BookingStatusAdapter adapter = new BookingStatusAdapter(requireContext(), status);
        fragmentBinding.statusList.setAdapter(adapter);
        fragmentBinding.statusList.postDelayed(() -> Objects.requireNonNull(fragmentBinding.statusList.findViewHolderForAdapterPosition(0)).itemView.performClick(), 100);


        adapter.setOnStatusSelectListener(position -> {
            getBookings(1, status.get(position));
            current_status = status.get(position);
        });
    }

    private void getBookings(int page, String status) {

        String token = prefRepository.getString("token");
        viewModel.getBookings(token, page, status);
        viewModel._bookings.observe(getViewLifecycleOwner(), response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    if (response.getData().getData().size() > 0) {
                        flag_loading = true;
                        hideNoDataAnimation();
                        fragmentBinding.bookingList.setVisibility(View.VISIBLE);
                        maxPageLimit = response.getData().getPage();

                        dataItems = new ArrayList<>();
                        dataItems.addAll(response.getData().getData());
                        buildList(dataItems, status);
                    } else {
                        flag_loading = false;
                        showNoDataAnimation();
                        fragmentBinding.bookingList.setVisibility(View.GONE);

                    }
                }
            }
        });
    }


    private void buildList(List<GetBookingDataItem> dataItems, String status) {

        fragmentBinding.bookingList.setHasFixedSize(true);
        fragmentBinding.bookingList.setLayoutManager(mLayoutManager);
        BookingAdopter bookingAdopter = new BookingAdopter(dataItems, status, requireContext());
        bookingAdopter.notifyDataSetChanged();
        fragmentBinding.bookingList.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        fragmentBinding.bookingList.setAdapter(bookingAdopter);

        bookingAdopter.setOnBookingClickListener(new BookingAdopter.ItemClickListener() {
            @Override
            public void allRequestedBookings(int position) {
                // ActivityUtil.gotoPage(requireContext(), RequestServices.class);
                Intent intent = new Intent(requireContext(), BookingDateTime.class);
                intent.putExtra("service_provider_id", String.valueOf(dataItems.get(position).getServiceProvider().getId()));
                intent.putExtra("booking_id", String.valueOf(dataItems.get(position).getId()));
                intent.putExtra("availability_id", String.valueOf(dataItems.get(position).getBookingAvailability().getId()));
                startActivity(intent);
            }

            @Override
            public void cancelBooking(int position) {
                confirmCancellationDialogue(dataItems.get(position).getId());
            }

            @Override
            public void bookingInformation(int position) {
                Intent intent = new Intent(requireContext(), BookingInformation.class);
                intent.putExtra("booking_id", String.valueOf(dataItems.get(position).getId()));
                startActivity(intent);
            }
        });
    }

    private void confirmCancellationDialogue(int id) {
        AlertDialog.Builder dialogBuilder;
        AlertDialog alertDialog;
        dialogBuilder = new AlertDialog.Builder(requireContext());
        View layoutView = getLayoutInflater().inflate(R.layout.cancel_booking_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.cancel_dialogue);
        TextView confirm = (TextView) layoutView.findViewById(R.id.confirm);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        cancel.setOnClickListener(view -> alertDialog.dismiss());
        confirm.setOnClickListener(view -> {

            String token = prefRepository.getString("token");
            viewModel.cancelBooking(token, id);
            viewModel._cancel_booking.observe(getViewLifecycleOwner(), response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData().isSuccess()) {
                        hideLoading();
                        alertDialog.dismiss();
                        showSnackBarShort(response.getData().getMessage());
                        Navigation.findNavController(requireView()).navigate(R.id.bookingFragment);
                    }
                }
            });
        });

    }

    private void clickListeners() {
        fragmentBinding.past.setOnClickListener(view1 -> {
            fragmentBinding.past.setBackgroundResource(R.drawable.selected_bg);
            fragmentBinding.past.setTextColor(getResources().getColor(R.color.white));
            fragmentBinding.current.setBackgroundResource(R.drawable.selector_bg);
            fragmentBinding.current.setTextColor(getResources().getColor(R.color.black));

            fragmentBinding.bookingList.setVisibility(View.GONE);
            fragmentBinding.noDataLayout.setVisibility(View.VISIBLE);
        });

        fragmentBinding.current.setOnClickListener(view1 -> {
            fragmentBinding.current.setBackgroundResource(R.drawable.selected_bg);
            fragmentBinding.current.setTextColor(getResources().getColor(R.color.white));
            fragmentBinding.past.setBackgroundResource(R.drawable.selector_bg);
            fragmentBinding.past.setTextColor(getResources().getColor(R.color.black));

            fragmentBinding.bookingList.setVisibility(View.VISIBLE);
            fragmentBinding.noDataLayout.setVisibility(View.GONE);
        });
    }
}