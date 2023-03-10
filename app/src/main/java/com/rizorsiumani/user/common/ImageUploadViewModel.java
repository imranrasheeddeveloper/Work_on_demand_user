package com.rizorsiumani.user.common;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.data.businessModels.UpdateUserModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
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
                        true, null, null
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
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            response.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(CommonResponse commonResponse) {
                        response.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                commonResponse
                        ));
                    }
                });
    }

    public void update(String token ,JsonObject object) {

        update.setValue(
                new ResponseWrapper<>(
                        true, null, null
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
                    public void onNext(UpdateUserModel userModel) {
                        update.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                userModel
                        ));
                    }
                });
    }


}
