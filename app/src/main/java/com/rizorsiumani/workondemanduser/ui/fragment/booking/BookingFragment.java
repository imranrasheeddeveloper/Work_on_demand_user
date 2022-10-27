package com.rizorsiumani.workondemanduser.ui.fragment.booking;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentBookingBinding;

import java.util.ArrayList;
import java.util.List;


public class BookingFragment extends BaseFragment<FragmentBookingBinding> {


    @Override
    protected FragmentBookingBinding getFragmentBinding() {
        return FragmentBookingBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        callStatus();
        fragmentBinding.bookingToolbar.title.setText("Bookings");
        fragmentBinding.bookingToolbar.back.setVisibility(View.INVISIBLE);


        clickListeners();

        fragmentBinding.current.setBackgroundResource(R.drawable.selected_bg);
        fragmentBinding.current.setTextColor(getResources().getColor(R.color.white));
        fragmentBinding.past.setBackgroundResource(R.drawable.selector_bg);
        fragmentBinding.past.setTextColor(getResources().getColor(R.color.black));

        getBookings();

    }
    private void callStatus() {
        List<String> status = new ArrayList<>();
        status.add("In Progress");
        status.add("Pending");
        status.add("Failed");
        status.add("Done");

        LinearLayoutManager llm = new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false);
        fragmentBinding.statusList.setLayoutManager(llm);
        BookingStatusAdapter adapter = new BookingStatusAdapter(requireContext(), status);
        fragmentBinding.statusList.setAdapter(adapter);

        adapter.setOnStatusSelectListener(position -> {
            Toast.makeText(requireContext(), status.get(position), Toast.LENGTH_SHORT).show();
        });
    }

    private void getBookings() {

        List<String> bookingTag = new ArrayList<>();
        bookingTag.add("Active");
        bookingTag.add("Active");

        fragmentBinding.bookingList.setHasFixedSize(true);
        fragmentBinding.bookingList.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        BookingAdopter bookingAdopter = new BookingAdopter(bookingTag, requireContext());
        fragmentBinding.bookingList.setAdapter(bookingAdopter);
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