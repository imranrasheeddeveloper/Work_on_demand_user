package com.rizorsiumani.workondemanduser.ui.fragment.wallet;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.BasicModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.WalletBalanceModel;
import com.rizorsiumani.workondemanduser.data.businessModels.WalletTransactionsModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WalletViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<WalletBalanceModel>> balance = new MutableLiveData<>();
    public LiveData<ResponseWrapper<WalletBalanceModel>> _balance = balance;

    private final MutableLiveData<ResponseWrapper<WalletTransactionsModel>> transactions = new MutableLiveData<>();
    public LiveData<ResponseWrapper<WalletTransactionsModel>> _transactions = transactions;

    private final MutableLiveData<ResponseWrapper<BasicModel>> up_wallet = new MutableLiveData<>();
    public LiveData<ResponseWrapper<BasicModel>> _up_wallet = up_wallet;


    public void getWalletBalance(String token) {

        balance.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .balance(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WalletBalanceModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            balance.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(WalletBalanceModel model) {
                        balance.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void getWalletTransactions(String token) {

        transactions.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .transactions(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WalletTransactionsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            transactions.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(WalletTransactionsModel model) {
                        transactions.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void walletUp(String token, JsonObject object) {

        up_wallet.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .upWallet(token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BasicModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            up_wallet.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(BasicModel model) {
                        up_wallet.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

}

