package com.rizorsiumani.workondemanduser.data.remote;


import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.common.CommonResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.AddBookingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.BasicModel;
import com.rizorsiumani.workondemanduser.data.businessModels.BookingDetailModel;
import com.rizorsiumani.workondemanduser.data.businessModels.CategoriesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetAddressesModel;
import com.rizorsiumani.workondemanduser.data.businessModels.GetBookingsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.HomeContentModel;
import com.rizorsiumani.workondemanduser.data.businessModels.LoginModel;
import com.rizorsiumani.workondemanduser.data.businessModels.NotificationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.OnBoardingModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PaymentGatewayModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostImageModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostJobModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PostedJobsModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PromoActivationModel;
import com.rizorsiumani.workondemanduser.data.businessModels.PromotionModel;
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

import org.json.JSONObject;

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

    @POST("serviceProvider/viewAll_home_content_category/{page_no}")
    Observable<ServiceProvidersModel> getServiceProvidersByCat(@Header("Authorization") String token,
                                                               @Path("page_no") int page_no,
                                                               @Body JsonObject object);

    @GET("serviceProvider/get_service_providers_gallery/{id}")
    Observable<ProviderGalleryModel> getGallery(@Path("id") int id);

    @GET("serviceProvider/get_service_providers_services/{id}")
    Observable<ProviderServicesModel> getServices(@Path("id") int id);

    @GET("serviceProvider/get_service_providers_availbility/{id}")
    Observable<ProviderAvailabilityModel> getProviderAvailability(@Path("id") int id);

    @GET("serviceProvider/get_service_provider_by_id/{id}")
    Observable<ServiceProviderProfileModel> getProviderProfile(@Path("id") int id);

    @GET("paymentgateway/get_payment_gateways")
    Observable<PaymentGatewayModel> getPaymentMethods();

    @GET("promotions/get_promotions")
    Observable<PromotionModel> getPromotions();

    @GET("promotions/validate_promo_code/{code}")
    Observable<PromoActivationModel> activate(@Path("code") String code);

    @POST("booking/add_boookings")
    Observable<AddBookingModel> addBooking(@Header("Authorization") String token,
                                           @Body JsonObject object);

    @GET("booking/get_bookings/{page_no}/{status}")
    Observable<GetBookingsModel> getBooking(@Header("Authorization") String token,
                                            @Path("page_no") int page_no,
                                            @Path("status") String status);

    @GET("booking/cancel_booking/{booking_id}")
    Observable<BasicModel> cancelBooking(@Header("Authorization") String token,
                                         @Path("booking_id") int booking_id);

    @POST("booking/reSechdule_booking")
    Observable<BasicModel> reschedule(@Header("Authorization") String token,
                                      @Body JsonObject object);

    @GET("booking/booking_detail/{booking_id}")
    Observable<BookingDetailModel> getBookingDetails(@Header("Authorization") String token,
                                                     @Path("booking_id") int booking_id);

    @GET("users/getAllNotifications/{page_no}")
    Observable<NotificationModel> getNotifications(@Header("Authorization") String token,
                                                   @Path("page_no") int page_no);

    @POST("serviceProvider/searchServices/{page_no}")
    Observable<ServiceProvidersModel> getProviderBySearch(@Header("Authorization") String token,
                                                          @Path("page_no") int page_no,
                                                          @Body JsonObject object);


}
