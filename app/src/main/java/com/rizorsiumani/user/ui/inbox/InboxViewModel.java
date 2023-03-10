package com.rizorsiumani.user.ui.inbox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rizorsiumani.user.data.businessModels.inbox.GetInboxModel;
import com.rizorsiumani.user.data.businessModels.inbox.InboxExistModel;
import com.rizorsiumani.user.data.remote.RemoteRepository;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class InboxViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<GetInboxModel>> inbox = new MutableLiveData<>();
    public LiveData<ResponseWrapper<GetInboxModel>> _inbox = inbox;

    private final MutableLiveData<ResponseWrapper<InboxExistModel>> is_exist = new MutableLiveData<>();
    public LiveData<ResponseWrapper<InboxExistModel>> _is_exist = is_exist;


    public void getInbox(String token) {

        inbox.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .inbox(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetInboxModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            inbox.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(GetInboxModel model) {
                        inbox.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void isInboxExist(String token, int id) {

        is_exist.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        RemoteRepository.getInstance()
                .inboxExist(token,id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<InboxExistModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            is_exist.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(InboxExistModel model) {
                        is_exist.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }


}
