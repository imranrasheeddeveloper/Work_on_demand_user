package com.rizorsiumani.user.data.remote;


import com.google.gson.JsonObject;
import com.rizorsiumani.user.common.CommonResponse;
import com.rizorsiumani.user.data.businessModels.AddBookingModel;
import com.rizorsiumani.user.data.businessModels.BasicModel;
import com.rizorsiumani.user.data.businessModels.CategoriesModel;
import com.rizorsiumani.user.data.businessModels.GetAddressesModel;
import com.rizorsiumani.user.data.businessModels.GetAllCardsModel;
import com.rizorsiumani.user.data.businessModels.GetBookingsModel;
import com.rizorsiumani.user.data.businessModels.GetFeeModel;
import com.rizorsiumani.user.data.businessModels.GetRatingModel;
import com.rizorsiumani.user.data.businessModels.HomeContentModel;
import com.rizorsiumani.user.data.businessModels.LoginModel;
import com.rizorsiumani.user.data.businessModels.NotificationModel;
import com.rizorsiumani.user.data.businessModels.OnBoardingModel;
import com.rizorsiumani.user.data.businessModels.PaymentGatewayModel;
import com.rizorsiumani.user.data.businessModels.PostImageModel;
import com.rizorsiumani.user.data.businessModels.PostJobModel;
import com.rizorsiumani.user.data.businessModels.PostedJobsModel;
import com.rizorsiumani.user.data.businessModels.PromoActivationModel;
import com.rizorsiumani.user.data.businessModels.PromotionModel;
import com.rizorsiumani.user.data.businessModels.ProviderAvailabilityModel;
import com.rizorsiumani.user.data.businessModels.ProviderGalleryModel;
import com.rizorsiumani.user.data.businessModels.ProviderServicesModel;
import com.rizorsiumani.user.data.businessModels.RegistrationModel;
import com.rizorsiumani.user.data.businessModels.SaveAddressModel;
import com.rizorsiumani.user.data.businessModels.ServiceProviderModel;
import com.rizorsiumani.user.data.businessModels.ServiceProviderProfileModel;
import com.rizorsiumani.user.data.businessModels.SliderModel;
import com.rizorsiumani.user.data.businessModels.SubCategoriesModel;
import com.rizorsiumani.user.data.businessModels.UpdaeAddressModel;
import com.rizorsiumani.user.data.businessModels.UpdateUserModel;
import com.rizorsiumani.user.data.businessModels.UserPayLaterModel;
import com.rizorsiumani.user.data.businessModels.WalletBalanceModel;
import com.rizorsiumani.user.data.businessModels.WalletTransactionsModel;
import com.rizorsiumani.user.data.businessModels.booking_detail.BookingDetailModel;
import com.rizorsiumani.user.data.businessModels.chat.GetAllMessageModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.ConversationModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.MessagesModel;
import com.rizorsiumani.user.data.businessModels.chatwoot_model.SendSupportMessageModel;
import com.rizorsiumani.user.data.businessModels.inbox.GetInboxModel;
import com.rizorsiumani.user.data.businessModels.inbox.InboxExistModel;
import com.rizorsiumani.user.data.businessModels.job_timing.JobTimingModel;
import com.rizorsiumani.user.data.businessModels.settings.SettingsModel;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @Multipart
    @POST("users/profilePhoto")
    Observable<CommonResponse> uploadImage(@Part MultipartBody.Part object);

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

    @POST("users/update_posted_job")
    Observable<PostJobModel> updatePostedJob(@Header("Authorization") String token,
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
    Observable<ServiceProviderModel> getServiceProviders(@Header("Authorization") String token,
                                                         @Path("page_no") int page_no,
                                                         @Body JsonObject object);

    @POST("serviceProvider/viewAll_home_content_category/{page_no}")
    Observable<ServiceProviderModel> getServiceProvidersByCat(@Header("Authorization") String token,
                                                               @Path("page_no") int page_no,
                                                               @Body JsonObject object);

    @GET("serviceProvider/get_service_providers_gallery/{id}")
    Observable<ProviderGalleryModel> getGallery(@Path("id") int id);

    @POST("raiting/rate_service_provider")
    Observable<BasicModel> rateProvider(@Header("Authorization") String token,
                                                  @Body JsonObject object);

    @GET("raiting/service_provider_all_raitings/{id}")
    Observable<GetRatingModel> getRating(@Path("id") int id);

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
    Observable<ServiceProviderModel> getProviderBySearch(@Header("Authorization") String token,
                                                          @Path("page_no") int page_no,
                                                          @Body JsonObject object);

    @GET("users/wallet_balance")
    Observable<WalletBalanceModel> getBalance(@Header("Authorization") String token);

    @GET("users/user_wallet_transactions")
    Observable<WalletTransactionsModel> getTransactions(@Header("Authorization") String token);

    @POST("users/create_card")
    Observable<BasicModel> createCard(@Header("Authorization") String token,
                                      @Body JsonObject object);

    @POST("users/remove_card/{card_id}")
    Observable<BasicModel> removeCard(@Header("Authorization") String token,
                                      @Path("card_id") String card_id);

    @GET("users/retrive_cards")
    Observable<GetAllCardsModel> getCards(@Header("Authorization") String token);

    @POST("users/wallet_toup")
    Observable<BasicModel> wallet_up(@Header("Authorization") String token,
                                     @Body JsonObject object);

    @GET("booking/update_booking_status/{status}/{id}")
    Observable<BasicModel> bookingStatus(@Header("Authorization") String token,
                                         @Path("status") String status,
                                         @Path("id") int id);

    @POST("messages/create_inbox")
    Observable<BasicModel> create_inbox(@Header("Authorization") String token,
                                     @Body JsonObject object);

    @POST("messages/send_message")
    Observable<BasicModel> send_message(@Header("Authorization") String token,
                                        @Body JsonObject object);

    @GET("messages/get_user_all_inobexs")
    Observable<GetInboxModel> getInboxList(@Header("Authorization") String token);

    @GET("messages/get_messages/{id}")
    Observable<GetAllMessageModel> get_messages(@Header("Authorization") String token,
                                                @Path("id") int id);

    @GET("messages/is_inbox_exist/{id}")
    Observable<InboxExistModel> is_inbox_exsit(@Header("Authorization") String token,
                                               @Path("id") int id);

    @GET("api/v1/accounts/{account_id}/conversations/{conversation_id}/messages")
    Observable<MessagesModel> get_support_chat(@Header("api_access_token") String apiKey,
                                               @Path("account_id") int  account_id ,
                                               @Path("conversation_id") int conversation_id);

    @POST("api/v1/accounts/{account_id}/conversations")
    Observable<ConversationModel> createConversation(@Header("api_access_token") String apiKey,
                                                     @Path("account_id") int  account_id ,
                                                     @Body JsonObject object);

    @POST("api/v1/accounts/{account_id}/conversations/{conversation_id}/messages")
    Observable<SendSupportMessageModel> sendSupportQuery(@Header("api_access_token") String apiKey,
                                                         @Path("conversation_id") int conversation_id,
                                                         @Path("account_id") int  account_id ,
                                                         @Body JsonObject object);

    @GET("serviceProvider/get_posted_job_timing/{job_id}")
    Observable<JobTimingModel> getJobTiming(@Path("job_id") int job_id);

    @DELETE("users/delete_posted_job/{job_id}")
    Observable<BasicModel> delete_posted_job(@Header("Authorization") String token,
                                             @Path("job_id") int job_id);

    @DELETE("users/delete_posted_job_time/{job_id}")
    Observable<BasicModel> delete_posted_job_timing(@Header("Authorization") String token,
                                             @Path("job_id") int job_id);

    @GET("globalSettings/getFees")
    Observable<GetFeeModel> getBookingFee();

    @GET("users/forgotPassword/{email}")
    Observable<BasicModel> forgot_password(@Path("email") String  email);

    @GET("users/verifyCodeForgotPassword/{email}/{code}")
    Observable<BasicModel> verify_email(@Path("email") String  email,
                                           @Path("code") int code);

    @GET("users/resetPassword/{email}/{password}")
    Observable<BasicModel> reset(@Path("email") String  email,
                                        @Path("password") String password);

    @GET("users/getSettings")
    Observable<SettingsModel> get_settings();

    @GET("serviceProvider/isPermissionToPayLater/{user_id}")
    Observable<UserPayLaterModel> is_pay_later(@Path("user_id") int user_id);
}
