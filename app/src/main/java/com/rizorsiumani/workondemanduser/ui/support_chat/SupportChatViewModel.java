package com.rizorsiumani.workondemanduser.ui.support_chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model.ConversationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model.MessagesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.chatwoot_model.SendSupportMessageModel;
import com.rizorsiumani.workondemanduser.data.remote.RemoteRepository;
import com.rizorsiumani.workondemanduser.data.remote.ResponseWrapper;


import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SupportChatViewModel extends ViewModel {

    private final MutableLiveData<ResponseWrapper<MessagesModel>> messages = new MutableLiveData<>();
    public LiveData<ResponseWrapper<MessagesModel>> _messages = messages;

    private final MutableLiveData<ResponseWrapper<ConversationModel>> conversation = new MutableLiveData<>();
    public LiveData<ResponseWrapper<ConversationModel>> _conversation = conversation;

    private final MutableLiveData<ResponseWrapper<SendSupportMessageModel>> send_query = new MutableLiveData<>();
    public LiveData<ResponseWrapper<SendSupportMessageModel>> _send_query = send_query;

    public void getChat(String token, int account_id, int conversation_id) {
            messages.setValue(
                    new ResponseWrapper<>(
                            true, "", null
                    ));

            RemoteRepository.getInstance()
                    .getSupportChat(token, account_id, conversation_id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<MessagesModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            messages.setValue(new ResponseWrapper<>(
                                    false,
                                    e.getLocalizedMessage(),
                                    null
                            ));
                        }

                        @Override
                        public void onNext(MessagesModel model) {
                            messages.setValue(new ResponseWrapper<>(
                                    false,
                                    "",
                                    model
                            ));
                        }
                    });
    }

    public void createConversation(String token, int account_id, JsonObject object) {

            conversation.setValue(
                    new ResponseWrapper<>(
                            true, "", null
                    ));

            RemoteRepository.getInstance()
                    .create_conversation(token, account_id, object)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ConversationModel>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            conversation.setValue(new ResponseWrapper<>(
                                    false,
                                    e.getLocalizedMessage(),
                                    null
                            ));
                        }

                        @Override
                        public void onNext(ConversationModel model) {
                            conversation.setValue(new ResponseWrapper<>(
                                    false,
                                    "",
                                    model
                            ));
                        }
                    });
    }

    public void sendMessage(String token, int conversation_id,int account_id, JsonObject object) {

        send_query.setValue(
                new ResponseWrapper<>(
                        true, "", null
                ));

        RemoteRepository.getInstance()
                .send_support_message(token, conversation_id,account_id, object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendSupportMessageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        send_query.setValue(new ResponseWrapper<>(
                                false,
                                e.getLocalizedMessage(),
                                null
                        ));
                    }

                    @Override
                    public void onNext(SendSupportMessageModel model) {
                        send_query.setValue(new ResponseWrapper<>(
                                false,
                                "",
                                model
                        ));
                    }
                });
    }


}
