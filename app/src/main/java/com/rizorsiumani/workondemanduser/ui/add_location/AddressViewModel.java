package com.rizorsiumani.workondemanduser.ui.add_location;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.SaveAddressModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdaeAddressModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<SaveAddressModel>> address = new MutableLiveData<>();
    public LiveData<ResponseWrapper<SaveAddressModel>> _address = address;

    private final MutableLiveData<ResponseWrapper<UpdaeAddressModel>> update = new MutableLiveData<>();
    public LiveData<ResponseWrapper<UpdaeAddressModel>> _update = update;

    public void save(String token, JsonObject object) {

        address.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .saveAddress(token, object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SaveAddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            address.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(SaveAddressModel saveAddressModel) {
                        address.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                saveAddressModel
                        ));
                    }
                });
    }

    public void update(String token, JsonObject object) {

        update.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .updateAddress(token, object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdaeAddressModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            update.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(UpdaeAddressModel model) {
                        update.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

}
