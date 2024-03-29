package com.rizorsiumani.user.ui.chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.data.businessModels.BasicModel;
import com.rizorsiumani.user.data.businessModels.chat.GetAllMessageModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ChatViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<BasicModel>> send_message = new MutableLiveData<>();
    public LiveData<ResponseWrapper<BasicModel>> _send_message = send_message;

    private final MutableLiveData<ResponseWrapper<GetAllMessageModel>> get_messages = new MutableLiveData<>();
    public LiveData<ResponseWrapper<GetAllMessageModel>> _get_messages = get_messages;

    public void sendMessage(String token, JsonObject object) {

        send_message.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .sendMsg(token,object)
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
                            send_message.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(BasicModel model) {
                        send_message.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });

    }

    public void getMessages(String token, int inbox_id) {

        get_messages.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .getMessages(token,inbox_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetAllMessageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            get_messages.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(GetAllMessageModel model) {
                        get_messages.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });

    }



}
