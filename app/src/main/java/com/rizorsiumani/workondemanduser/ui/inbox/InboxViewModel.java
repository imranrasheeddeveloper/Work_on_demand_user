package com.rizorsiumani.workondemanduser.ui.inbox;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.AddBookingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PaymentGatewayModel;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.GetInboxModel;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.InboxExistModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;

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
                        true, "", null
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
                        inbox.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(GetInboxModel model) {
                        inbox.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }

    public void isInboxExist(String token, int id) {

        is_exist.setValue(
                new ResponseWrapper<>(
                        true, "", null
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
                        is_exist.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(InboxExistModel model) {
                        is_exist.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }


}
