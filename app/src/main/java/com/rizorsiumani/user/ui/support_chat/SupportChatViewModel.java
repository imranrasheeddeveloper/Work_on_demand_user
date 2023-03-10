package com.rizorsiumani.user.ui.support_chat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.ConversationModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.MessagesModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.SendSupportMessageModel;
import com.rizorsiumani.user.data.remote.ChatwootApiManager;
import com.rizorsiumani.user.data.remote.ResponseWrapper;

import okhttp3.ResponseBody;
import retrofit2.adapter.rxjava.HttpException;
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
                        true, null, null
                ));

        ChatwootApiManager.getInstance()
                .getSupportChat(token, account_id, conversation_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessagesModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            messages.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(MessagesModel model) {
                        messages.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }

    public void createConversation(String token, int account_id, JsonObject object) {

        conversation.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        ChatwootApiManager.getInstance()
                .create_conversation(token, account_id, object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ConversationModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            conversation.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(ConversationModel model) {
                        conversation.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }

                });

    }


    public void sendMessage(String token, int conversation_id, int account_id, JsonObject object) {

        send_query.setValue(
                new ResponseWrapper<>(
                        true, null, null
                ));

        ChatwootApiManager.getInstance()
                .send_support_message(token, conversation_id, account_id, object)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<SendSupportMessageModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e instanceof HttpException) {
                            ResponseBody body = ((HttpException) e).response().errorBody();
                            send_query.setValue(new ResponseWrapper<>(
                                    false,
                                    body,
                                    null
                            ));
                        }
                    }

                    @Override
                    public void onNext(SendSupportMessageModel model) {
                        send_query.setValue(new ResponseWrapper<>(
                                false,
                                null,
                                model
                        ));
                    }
                });
    }


}
