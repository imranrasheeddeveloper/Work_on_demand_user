package com.rizorsiumani.workondemanduser.ui.notification;

import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.NotificationModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class NotificationViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<NotificationModel>> notification = new MutableLiveData<>();
    public LiveData<ResponseWrapper<NotificationModel>> _notification = notification;


    public void getNotification(String token, int page) {

        notification.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .notifications(token,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NotificationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            try {
                                notification.setValue(new ResponseWrapper<>(
                                        false,
                                        body.string(),
                                        null
                                ));
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onNext(NotificationModel model) {
                        notification.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

}


