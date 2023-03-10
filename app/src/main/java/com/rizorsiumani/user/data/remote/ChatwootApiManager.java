package com.rizorsiumani.user.data.remote;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.ConversationModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.MessagesModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.SendSupportMessageModel;
import com.rizorsiumani.user.utils.ChatwootInstanceProvider;

import rx.Observable;

public class ChatwootApiManager {


    private static ChatwootApiManager _instance = null;
    private final ChatApiService mService;

    private ChatwootApiManager(ChatApiService service) {
        mService = service;
    }

    public static ChatwootApiManager getInstance() {
        if (_instance == null) {
            return _instance = new ChatwootApiManager(
                    ChatwootInstanceProvider.getInstance().getApiService());
        } else {
            return _instance;
        }
    }




    public Observable<MessagesModel> getSupportChat(String token, int accountID, int conversationID) {
        if (mService != null) {
            return mService.getSupportChat(token,accountID,conversationID);
        }
        return null;
    }

    public Observable<ConversationModel> create_conversation(String token, int account_id, JsonObject object) {
        if (mService != null) {
            return mService.createConversation(token,account_id,object);
        }
        return null;
    }

    public Observable<SendSupportMessageModel> send_support_message(String token, int conversation_id, int account_id, JsonObject object) {
        if (mService != null) {
            return mService.sendSupportQuery(token,conversation_id,account_id,object);
        }
        return null;
    }

}

