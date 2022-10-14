package com.rizorsiumani.workondemanduser.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImageUploadViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<CommonResponse>> response = new MutableLiveData<>();
    public LiveData<ResponseWrapper<CommonResponse>> _response = response;

    public void upload(JsonObject object) {

        response.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .upload(object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommonResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        response.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        response.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                commonResponse
                        ));
                    }
                });
    }


}
