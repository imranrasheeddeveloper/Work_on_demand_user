package com.rizorsiumani.workondemanduser.data.remote;


import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.common.CommonResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.HomeContentModel;
import com.rizorsiumani.workondemanduser.data.businessModels.LoginModel;
import com.rizorsiumani.workondemanduser.data.businessModels.OnBoardingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostImageModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostJobModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostedJobsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderAvailabilityModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderGalleryModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ProviderServicesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.RegistrationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SaveAddressModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProviderProfileModel;
import com.rizorsiumani.workondemanduser.data.businessModels.ServiceProvidersModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderModel;
import com.rizorsiumani.workondemanduser.data.businessModels.SubCategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdaeAddressModel;
import com.rizorsiumani.workondemanduser.data.businessModels.UpdateUserModel;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @Multipart
    @POST("users/jobPostingAttachment")
    Observable<PostImageModel> uploadJobImage(@Part MultipartBody.Part filePart);



    @POST("users/register_user")
    Observable<RegistrationModel> registerUser(@Body JsonObject object);

    @POST("users/update_user")
    Observable<UpdateUserModel> updateUser(@Header("Authorization") String token,
                                           @Body JsonObject object);

    @POST("users/save_user_address")
    Observable<SaveAddressModel> saveAddress(@Header("Authorization") String token,
                                             @Body JsonObject object);

    @POST("users/update_user_address")
    Observable<UpdaeAddressModel> updateAddress(@Header("Authorization") String token,
                                                @Body JsonObject object);

    @GET("users/get_addresses")
    Observable<GetAddressesModel> getAddresses(@Header("Authorization") String token);

    @POST("users/job_post")
    Observable<PostJobModel> postNewJob(@Header("Authorization") String token,
                                        @Body JsonObject object);

    @GET("categories/getCategoriesDropdown")
    Observable<CategoriesModel> getDropDownCategories();

    @GET("subcategories/getSubCategoriesDropdown/{category_id}")
    Observable<SubCategoriesModel> getDropDownSubCategories(@Path("category_id") int category_id);

    @GET("users/get_posted_jobs")
    Observable<PostedJobsModel> getAllPostedJobs(@Header("Authorization") String token);

    @POST("serviceProvider/get_home_content")
    Observable<HomeContentModel> getContent(@Header("Authorization") String token,
                                            @Body JsonObject object);

    @POST("serviceProvider/get_service_providers/{page_no}")
    Observable<ServiceProvidersModel> getServiceProviders(@Header("Authorization") String token,
                                                          @Path("page_no") int page_no,
                                                          @Body JsonObject object);

    @GET("serviceProvider/get_service_providers_gallery/{id}")
    Observable<ProviderGalleryModel> getGallery (@Path("id") int id);

    @GET("serviceProvider/get_service_providers_services/{id}")
    Observable<ProviderServicesModel> getServices(@Path("id") int id);

    @GET("serviceProvider/get_service_providers_availbility/{id}")
    Observable<ProviderAvailabilityModel> getProviderAvailability(@Path("id") int id);

    @GET("serviceProvider/get_service_provider_by_id/{id}")
    Observable<ServiceProviderProfileModel> getProviderProfile(@Path("id") int id);
}
