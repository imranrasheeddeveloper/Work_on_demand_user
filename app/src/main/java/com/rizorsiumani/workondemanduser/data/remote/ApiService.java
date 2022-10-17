package com.rizorsiumani.workondemanduser.data.remote;


import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.common.CommonResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.LoginModel;
import com.rizorsiumani.workondemanduser.data.businessModels.OnBoardingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.RegistrationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdateUserData;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdateUserModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface ApiService {

    @GET("onboarding/get_on_boarding")
    Observable<OnBoardingModel> getOnBoardingData();

    @POST("users/login")
    Observable<LoginModel> loginUser(@Body JsonObject object);

    @GET("categories/get_categories/{page_no}")
    Observable<CategoriesModel> getCategories(@Path("page_no") int page_no);

    @GET("subcategories/get_subCategories/{category_id}/{page_no}")
    Observable<SubCategoriesModel> getSubCategories(@Path("category_id") int category_id,
                                                    @Path("page_no") int page_no);

    @GET("slider/getSlider")
    Observable<SliderModel> getSliderData();

    @POST("users/uploadProfile")
    Observable<CommonResponse> uploadImage(@Body JsonObject object);

    @POST("users/register_user")
    Observable<RegistrationModel> registerUser(@Body JsonObject object);

    @POST("users/update_user")
    Observable<UpdateUserModel> updateUser(@Header("Authorization") String token,
                                           @Body JsonObject object);
}
