package com.rizorsiumani.workondemanduser.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdateUserModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import okhttp3.MultipartBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ImageUploadViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<CommonResponse>> response = new MutableLiveData<>();
    public LiveData<ResponseWrapper<CommonResponse>> _response = response;

    private final MutableLiveData<ResponseWrapper<UpdateUserModel>> update = new MutableLiveData<>();
    public LiveData<ResponseWrapper<UpdateUserModel>> _update = update;

    public void upload(MultipartBody.Part object) {

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

    public void update(String token ,JsonObject object) {

        update.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .updateUser(token,object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateUserModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        update.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(UpdateUserModel userModel) {
                        update.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                userModel
                        ));
                    }
                });
    }


}
