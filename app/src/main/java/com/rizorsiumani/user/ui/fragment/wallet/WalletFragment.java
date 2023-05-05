package com.rizorsiumani.user.ui.fragment.wallet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseFragment;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.TransactionsDataItem;
import com.rizorsiumani.user.data.businessModels.UserData;
import com.rizorsiumani.user.data.local.TinyDbManager;
import com.rizorsiumani.user.databinding.FragmentWalletBinding;
import com.rizorsiumani.user.ui.dashboard.Dashboard;
import com.rizorsiumani.user.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class WalletFragment extends BaseFragment<FragmentWalletBinding> {

    TransactionsAdapter adapter;
    private WalletViewModel viewModel;
    private List<TransactionsDataItem> transactionsDataItemList;


    @Override
    protected FragmentWalletBinding getFragmentBinding() {
        return FragmentWalletBinding.inflate(getLayoutInflater());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Dashboard.hideTabs(false);

        viewModel = new ViewModelProvider(this).get(WalletViewModel.class);
        String token = prefRepository.getString("token");
        getBalance(token);
        getTransactions(token);
        transactionsDataItemList = new ArrayList<>();
        setProfileInfo();
        transactionsRv(transactionsDataItemList);

        fragmentBinding.tvTopup.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_walletFragment_to_walletTopup);
        });

    }

    private void getTransactions(String token) {
        viewModel.getWalletTransactions(token);
        viewModel._transactions.observe(getViewLifecycleOwner() , response -> {
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
                    if (response.getData().getData() != null) {
                        if (response.getData().getData().size() > 0){
                            hideNoDataAnimation();
                            fragmentBinding.noTransLayout.setVisibility(View.GONE);

                            fragmentBinding.transactionsList.setVisibility(View.VISIBLE);
                            transactionsDataItemList.addAll(response.getData().getData());
                            transactionsRv(transactionsDataItemList);
                        }else {
                            fragmentBinding.transactionsList.setVisibility(View.GONE);
                            fragmentBinding.noTransLayout.setVisibility(View.VISIBLE);
//                            showNoDataAnimation(R.raw.no_transaction,"No Transaction");
                        }
                    }
                }
            }
        });
    }

    private void getBalance(String token) {
        viewModel.getWalletBalance(token);
        viewModel._balance.observe(getViewLifecycleOwner() , response -> {
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
                    if (response.getData().getData() != null) {
                        hideNoDataAnimation();
                        fragmentBinding.walletPrice.setText(String.valueOf(response.getData().getData().getBalance()));
                    }
                }
            }
        });
    }

    private void setProfileInfo() {
        try {

            if (TinyDbManager.getUserInformation() != null){
                UserData userData = TinyDbManager.getUserInformation();
                fragmentBinding.username.setText(userData.getFirstName() + " " + userData.getLastName());
                fragmentBinding.userNumber.setText(userData.getPhoneNumber());
                if (userData.getImage().startsWith("http")){
                    Glide.with(requireContext())
                            .load(userData.getImage())
                            .placeholder(R.color.teal_700)
                            .into(fragmentBinding.userImage);
                }else {
                    Glide.with(requireContext())
                            .load(Constants.IMG_PATH + userData.getImage())
                            .placeholder(R.color.teal_700)
                            .into(fragmentBinding.userImage);
                }
            }

        }catch (NullPointerException e){
            e.printStackTrace();
        }

    }

    private void transactionsRv(List<TransactionsDataItem> transactionsDataItemList) {


        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        fragmentBinding.transactionsList.setLayoutManager(layoutManager);
        adapter = new TransactionsAdapter(transactionsDataItemList, requireContext());
        fragmentBinding.transactionsList.setAdapter(adapter);

//        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
//        itemTouchHelper.attachToRecyclerView(fragmentBinding.transactionsList);

    }

//    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback( 0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
//        @Override
//        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
//            return false;
//        }
//
//        @Override
//        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
//            // Take action for the swiped item
//
//            final int position=viewHolder.getAdapterPosition();
//
//            switch (direction){
//                case ItemTouchHelper.LEFT:
//
//                    transactions.remove(position);
//                    adapter.notifyDataSetChanged();
//
//                    Snackbar.make(fragmentBinding.transactionsList,transactions.get(position), BaseTransientBottomBar.LENGTH_LONG)
//                            .setAction("undo", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    transactions.add(position,transactions.get(position));
//                                    adapter.notifyItemInserted(position);
//
//                                }
//                            }).show();
//
//                    break;
//
//                case ItemTouchHelper.RIGHT:
//
//                    transactions.remove(position);
//                    adapter.notifyDataSetChanged();
//                    transactions.add(position,transactions.get(position));
//                    adapter.notifyItemInserted(position);
//
//                    Snackbar.make(fragmentBinding.transactionsList,transactions.get(position), BaseTransientBottomBar.LENGTH_LONG)
//                            .setAction("ADD", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//
//                                    transactions.add(position,transactions.get(position));
//                                    adapter.notifyItemInserted(position);
//
//                                }
//                            }).show();
//
//                    break;
//            }
//
//        }
//
//
//
//        @Override
//        public void onChildDraw (Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//
//            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//
//                View itemView = viewHolder.itemView;
//                if (dX < 0) {
//                    itemView.setTranslationX(dX / 5);
//                    String toshow = "DELETE";
//                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//                            .addSwipeLeftBackgroundColor(R.color.primary)
//                            .addSwipeLeftLabel(toshow)
//                            .addSwipeRightLabel("ADD")
//                            .addSwipeRightBackgroundColor(R.color.blue)
//                            .create()
//                            .decorate();
//                } else {
//
//                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//                }
//            }
//        }
//
//
//    };
//
//    SwipeHelper swipeHelper = new SwipeHelper(
//            getContext(), fragmentBinding.transactionsList) {
//        @Override
//        public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
//            underlayButtons.add(new SwipeHelper.UnderlayButton(
//                    "Delete",
//                    0,
//                    Color.parseColor("#FF3C30"),
//                    new SwipeHelper.UnderlayButtonClickListener() {
//                        @Override
//                        public void onClick(int pos) {
//                            // TODO: onDelete
//                        }
//                    }
//            ));
//
//            underlayButtons.add(new SwipeHelper.UnderlayButton(
//                    "Transfer",
//                    0,
//                    Color.parseColor("#FF9502"),
//                    new SwipeHelper.UnderlayButtonClickListener() {
//                        @Override
//                        public void onClick(int pos) {
//                            // TODO: OnTransfer
//                        }
//                    }
//            ));
//            underlayButtons.add(new SwipeHelper.UnderlayButton(
//                    "Unshare",
//                    0,
//                    Color.parseColor("#C7C7CB"),
//                    new SwipeHelper.UnderlayButtonClickListener() {
//                        @Override
//                        public void onClick(int pos) {
//                            // TODO: OnUnshare
//                        }
//                    }
//            ));
//        }
//    };

}