package com.rizorsiumani.workondemanduser.data.remote;


import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.data.businessModels.LoginModel;
import com.rizorsiumani.workondemanduser.data.businessModels.OnBoardingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {

    @GET("onboarding/get_on_boarding")
    Observable<OnBoardingModel> getOnBoardingData();

    @POST("admin/login")
    Observable<LoginModel> loginUser(@Body JsonObject object);
}
