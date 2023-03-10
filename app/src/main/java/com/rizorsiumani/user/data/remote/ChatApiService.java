package com.rizorsiumani.user.data.remote;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.ConversationModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.MessagesModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.SendSupportMessageModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ChatApiService {

    @POST("api/v1/accounts/{account_id}/conversations")
    Observable<ConversationModel> createConversation(@Header("api_access_token") String apiKey,
                                                     @Path("account_id") int  account_id ,
                                                     @Body JsonObject object);


    @GET("api/v1/accounts/{account_id}/conversations/{conversation_id}/messages")
    Observable<MessagesModel> getSupportChat(@Header("api_access_token") String apiKey,
                                       @Path("account_id") int  account_id ,
                                       @Path("conversation_id") int conversation_id);

    @POST("api/v1/accounts/{account_id}/conversations/{conversation_id}/messages")
    Observable<SendSupportMessageModel> sendSupportQuery(@Header("api_access_token") String apiKey,
                                                   @Path("conversation_id") int conversation_id,
                                                   @Path("account_id") int  account_id ,
                                                   @Body JsonObject object);

}
