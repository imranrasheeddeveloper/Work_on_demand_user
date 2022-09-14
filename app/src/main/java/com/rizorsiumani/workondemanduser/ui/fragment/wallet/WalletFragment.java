package com.rizorsiumani.workondemanduser.ui.fragment.wallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseFragment;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.FragmentWalletBinding;
import com.rizorsiumani.workondemanduser.ui.filter.CategoryFilterAdapter;
import com.rizorsiumani.workondemanduser.ui.filter.FilterSearch;
import com.rizorsiumani.workondemanduser.ui.fragment.wallet.TransactionsAdapter;

import java.util.ArrayList;
import java.util.List;


public class WalletFragment extends BaseFragment<FragmentWalletBinding> {


    @Override
    protected FragmentWalletBinding getFragmentBinding() {
        return FragmentWalletBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        transactionsRv();

    }

    private void transactionsRv() {

        List<String> transactions = new ArrayList<>();
        transactions.add("Rohit Patel");
        transactions.add("Rohit Patel");
        transactions.add("Rohit Patel");

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.transactionsList.setLayoutManager(layoutManager);
        TransactionsAdapter adapter = new TransactionsAdapter(transactions, App.applicationContext);
        fragmentBinding.transactionsList.setAdapter(adapter);
    }
}