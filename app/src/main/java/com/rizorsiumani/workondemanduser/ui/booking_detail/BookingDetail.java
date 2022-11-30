package com.rizorsiumani.workondemanduser.ui.booking_detail;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.common.AddBookingDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.PaymentDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.PromoDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.stripe.CustomerIdResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.stripe.EphemeralKeyResponse;
import com.rizorsiumani.workondemanduser.data.businessModels.stripe.PaymentIntentResponse;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityBookingDetailBinding;
import com.rizorsiumani.workondemanduser.ui.address.SavedAddresses;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.ui.cards.GetAllCards;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.promo_code.PromoCode;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.stripe.Stripe;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.model.Customer;
import com.stripe.param.ChargeCreateParams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingDetail extends BaseActivity<ActivityBookingDetailBinding> {

    CartServicesAdapter adapter;
    List<String> name;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;

//    String publish_key = "pk_test_51LqBTjGmaWwccsNaWNAb8x6B51zmMVMsI62gcxZTpC6lvhvGy7vdcw1CX1vkkrHYMkkN2C79mexjkPpuGeHW8Kg500CEi0L3Vm";
//    String secret_key = "sk_test_51LqBTjGmaWwccsNaRmfGGvK4TOL4j0rXloATiyVD7Nou0aCzqjttDMqrjJjf7sRt4mIHaFx8bivnmlzsazUI1Zie00ob2H1tvR";

    String publish_key = "pk_test_51M4i3YFyKkhI7Itr08xnMiRdCI0txUVQcAlroa7CfS1nLDIjRaKN6YIdMucBtThd1u7U0VZiKrkd5qEAT20yTVMI001ZLp3IjR";
    String secret_key = "sk_test_51M4i3YFyKkhI7ItrcxzXMddb90sUJrShRKkPOXFMW25LhQjqsRrn0DKX8Cw4sA2u4PDMki7bPeMoZP2tX4nvMh7O00ijVe6qL6";


    PaymentSheet paymentSheet;
    String customerID;
    int amount_of_discount;
    String ephemeralKey;
    String clientSecret;
    CustomerIdResponse idResponse;
    EphemeralKeyResponse ephemeralKeyResponse;
    PaymentIntentResponse intentResponse;
    String service_provider_id;
    int total;
    int payment_type_id = 0;
    String payment_type;

    BookingDetailViewModel viewModel;

    @Override
    protected ActivityBookingDetailBinding getActivityBinding() {
        return ActivityBookingDetailBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideCartButton();

        service_provider_id = getIntent().getStringExtra("service_provider_id");
        if (service_provider_id == null) {
            service_provider_id = TinyDbManager.getServiceProviderID();
        }

        viewModel = new ViewModelProvider(this).get(BookingDetailViewModel.class);

        setData();
        clickListeners();


        PaymentConfiguration.init(BookingDetail.this, publish_key);
        paymentSheet = new PaymentSheet(this, this::onPaymentResult);

//        try {
//
//            Stripe.apiKey = secret_key;
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);
//
//            Map<String, Object> params = new HashMap<>();
//            params.put("email", TinyDbManager.getUserInformation().getEmail());
//            params.put("name", TinyDbManager.getUserInformation().getFirstName());
//            params.put("phone", TinyDbManager.getUserInformation().getPhoneNumber());
//
//
//            Customer customer = Customer.create(params);
//        } catch (StripeException e) {
//            e.printStackTrace();
//        }

//        StringRequest request = new StringRequest(Request.Method.POST,
//                "https://api.stripe.com/v1/customers",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        if (response != null) {
//
//                            Gson gson = new Gson();
//                            idResponse = gson.fromJson(response, CustomerIdResponse.class);
//                            customerID = idResponse.getId();
//                            getEphemeralKey(customerID);
//
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<>();
//                header.put("Authorization", "Bearer " + secret_key);
//                return header;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(BookingDetail.this);
//        requestQueue.add(request);
//

    }

    private void setData() {
        try {

            try {
                if (TinyDbManager.getSelectedCard() != null) {
                    activityBinding.totalPayment.setText(Constants.constant.CURRENCY + getCartTotal());
                    activityBinding.addPaymentMethod.setText("**********" + TinyDbManager.getSelectedCard().getLast4());

                    if (TinyDbManager.getSelectedCard().getBrand() != null) {
                        String brand = TinyDbManager.getSelectedCard().getBrand();
                        if (brand.equalsIgnoreCase("Visa")) {
                            activityBinding.paymentMethodIcon.setImageResource(R.drawable.ic_visa);
                        } else if (brand.equalsIgnoreCase("American Express")) {
                            activityBinding.paymentMethodIcon.setImageResource(R.drawable.ic_amex);
                        } else if (brand.equalsIgnoreCase("Master Card")) {
                            activityBinding.paymentMethodIcon.setImageResource(R.drawable.ic_mastercard);
                        } else {
                            activityBinding.paymentMethodIcon.setImageResource(R.drawable.ic_mastercard);
                        }
                    }

                }
            } catch (NullPointerException | IllegalStateException e) {
                e.printStackTrace();
            }

            if (activityBinding.addPaymentMethod.getText().toString().startsWith("**")){
                getCardID();
            }

            if (!TinyDbManager.getCurrentAddress().isEmpty()) {
                activityBinding.tvAddress.setText(TinyDbManager.getCurrentAddress());
            } else {
                activityBinding.tvAddress.setText("Please set your location");
            }

            activityBinding.bookingDetailToolbar.title.setText("Booking Details");
            activityBinding.btnPayNow.setText(Constants.constant.CURRENCY + getCartTotal());

            if (TinyDbManager.getCartData() != null) {
                if (TinyDbManager.getCartData().size() > 0) {
                    getCartServices(TinyDbManager.getCartData());
                    activityBinding.totalCharges.setText(Constants.constant.CURRENCY + getCartTotal());
                    activityBinding.subTotal.setText(Constants.constant.CURRENCY + getCartTotal());
                }
            }

            try {

                if (TinyDbManager.getPromo() != null) {
                    PromoDataItem promoDataItem = TinyDbManager.getPromo();
                    Constants.constant.promotion_id = String.valueOf(promoDataItem.getId());
                    amount_of_discount = (int) (Integer.parseInt(getCartTotal()) * Math.round(promoDataItem.getDiscount()) / 100);
                    Constants.constant.discount = String.valueOf(amount_of_discount);
                    activityBinding.tvDiscount.setText(promoDataItem.getDiscount() + "% OFF (" + promoDataItem.getCode() + ")");
                    activityBinding.discountPrice.setText("- " + amount_of_discount);
                    int subTotal = Integer.valueOf(getCartTotal()) - amount_of_discount;
                    activityBinding.btnPayNow.setText(Constants.constant.CURRENCY + subTotal);
                    activityBinding.subTotal.setText(Constants.constant.CURRENCY + subTotal);
                    activityBinding.totalPayment.setText(Constants.constant.CURRENCY + subTotal);

                    activityBinding.discountPrice.setVisibility(View.VISIBLE);
                    activityBinding.tvDiscount.setVisibility(View.VISIBLE);
                } else {
                    activityBinding.discountPrice.setVisibility(View.GONE);
                    activityBinding.tvDiscount.setVisibility(View.GONE);
                }

            } catch (NullPointerException e) {
                e.printStackTrace();
            }


        } catch (NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void onPaymentResult(PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(BookingDetail.this, "Payment Success", Toast.LENGTH_SHORT).show();
            addToBookingList();
        }
    }

    private void getEphemeralKey(String customerID) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/ephemeral_keys",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            Gson gson = new Gson();
                            ephemeralKeyResponse = gson.fromJson(response, EphemeralKeyResponse.class);
                            ephemeralKey = ephemeralKeyResponse.getId();
                            getClientSecret(customerID, ephemeralKey);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + secret_key);
                header.put("Stripe-Version", "2022-08-01");
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("customer", customerID);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BookingDetail.this);
        requestQueue.add(request);
    }

    private void getClientSecret(String customerID, String ephemeralKey) {
        StringRequest request = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/payment_intents",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            Gson gson = new Gson();
                            intentResponse = gson.fromJson(response, PaymentIntentResponse.class);
                            clientSecret = intentResponse.getClientSecret();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<>();
                header.put("Authorization", "Bearer " + secret_key);
                return header;
            }

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param = new HashMap<>();
                param.put("customer", customerID);
                param.put("amount", getCartTotal() + "00");
                param.put("currency", "eur");
                param.put("automatic_payment_methods[enabled]", "true");

                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(BookingDetail.this);
        requestQueue.add(request);
    }

    private void paymentFlow() {
//        paymentSheet.presentWithPaymentIntent(
//                clientSecret , new PaymentSheet.Configuration("ITRID",
//                new PaymentSheet.Configuration(
//                        customerID,
//                        new PaymentSheet.Configuration(
//                        ephemeralKey,
//                )
//        );
        try {

            final PaymentSheet.Configuration configuration = null;
            paymentSheet.presentWithPaymentIntent(
                    clientSecret,
                    configuration
            );

        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    private void showRequestedDialogue() {
        dialogBuilder = new AlertDialog.Builder(BookingDetail.this);
        View layoutView = getLayoutInflater().inflate(R.layout.booking_request_complete_dialogue, null);
        TextView cancel = (TextView) layoutView.findViewById(R.id.cancel_dialogue);
        Button booking_ = (Button) layoutView.findViewById(R.id.view_booking);

        dialogBuilder.setView(layoutView);
        alertDialog = dialogBuilder.create();
        alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimations;
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();

        cancel.setOnClickListener(view -> alertDialog.dismiss());
        booking_.setOnClickListener(view -> {
            Intent intent = new Intent(BookingDetail.this, Dashboard.class);
            intent.putExtra("Navigation", "Booking");
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

        });

        cancel.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, Dashboard.class);
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }


    private void getCartServices(List<MyCartItems> cartData) {

        LinearLayoutManager layoutManager = new LinearLayoutManager(App.applicationContext, RecyclerView.VERTICAL, false);
        activityBinding.cartServicesList.setLayoutManager(layoutManager);
        adapter = new CartServicesAdapter(cartData, App.applicationContext);
        activityBinding.cartServicesList.setAdapter(adapter);


        adapter.setOnCartListener(position -> {
            TinyDbManager.removeCartItem(cartData.get(position));

            adapter.update(position);
            if (TinyDbManager.getCartData().size() == 0) {

                TinyDbManager.savePromo(null);
                TinyDbManager.saveServiceProviderID("null");

                onBackPressed();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            } else {
                Intent intent = new Intent(BookingDetail.this, BookingDetail.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addToBookingList() {

        try {


            for (int i = 0; i < TinyDbManager.getCartData().size(); i++) {
                MyCartItems cartItems = TinyDbManager.getCartData().get(i);

                int userID = TinyDbManager.getUserInformation().getId();
                int service_id = cartItems.getData().getId();

                if (Constants.constant.discount == null || Constants.constant.discount.isEmpty()) {
                    Constants.constant.discount = "0";
                } else {
                    int discount_per_cart = amount_of_discount / TinyDbManager.getCartData().size();
                    Constants.constant.discount = String.valueOf(discount_per_cart);
                }
                if (Constants.constant.promotion_id == null || Constants.constant.promotion_id.isEmpty()) {
                    Constants.constant.promotion_id = "0";
                }

                int cartSubTotal = Integer.parseInt(cartItems.getData().getPrice()) - Integer.parseInt(Constants.constant.discount);

                AddBookingDataItem bookingDataItem = new AddBookingDataItem(
                        Integer.parseInt(cartItems.getData().getPrice()),
                        TinyDbManager.getCurrentAddress(),
                        Integer.parseInt(service_provider_id),
                        Integer.parseInt(String.valueOf(userID)),
                        Constants.constant.latitude,
                        cartItems.getDescription(),
                        Integer.parseInt(cartItems.getAvailability_id()),
                        Integer.parseInt(Constants.constant.discount),
                        Integer.parseInt(Constants.constant.promotion_id),
                        cartSubTotal,
                        payment_type_id,
                        Constants.constant.longitude,
                        service_id
                );

                TinyDbManager.saveBookingList(bookingDataItem);
            }

            TinyDbManager.getBookingList();
            if (TinyDbManager.getBookingList().size() > 0) {
                addBooking(TinyDbManager.getBookingList());
            }

        } catch (NullPointerException | IllegalStateException | NumberFormatException e) {
            e.printStackTrace();
        }

    }

    private void clickListeners() {

        activityBinding.bookingDetailToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        activityBinding.checkPromo.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, PromoCode.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.paymentMethodEdit.setOnClickListener(v -> {
//            if (customerID != null) {
            Intent intent = new Intent(BookingDetail.this, GetAllCards.class);
//                intent.putExtra("customer_id", customerID);
//                intent.putExtra("client_secret", clientSecret);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//            }
        });


        activityBinding.btnPayNow.setOnClickListener(view -> {

            try {

            if (payment_type_id == 0) {
                showSnackBarShort("Select Payment Method");
            } else if (activityBinding.tvAddress.getText().toString().equalsIgnoreCase("Please set your location")) {
                showSnackBarShort("Select Your Location");
            } else {
                if (payment_type.equalsIgnoreCase("Card")) {
                    chargeByCard();
                    //paymentFlow();
                } else {
                    addToBookingList();
                }
            }
            //paymentFlow();
            }catch (NullPointerException e){
                e.printStackTrace();
            }

        });

        activityBinding.btnPayLater.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, BookingDateTime.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.addPaymentMethod.setOnClickListener(view -> {
            callPaymentmethodApi();
        });

        activityBinding.editAddress.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, SavedAddresses.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void getCardID() {
        viewModel.getPaymentMethods();
        viewModel._payment.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    for (int i = 0; i < response.getData().getData().size(); i++) {
                        PaymentDataItem paymentDataItem = response.getData().getData().get(i);
                        if (paymentDataItem.getTitle().equalsIgnoreCase("Card")){
                            payment_type_id = paymentDataItem.getId();
                            payment_type = paymentDataItem.getTitle();
                        }

                    }
                }
            }
        });
    }

    private void chargeByCard() {
        try {

            com.stripe.Stripe.apiKey = secret_key;
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            ChargeCreateParams chargeParams =
                    ChargeCreateParams.builder()
                            .setAmount(Long.valueOf(getCartTotal() + 000))
                            .setCurrency("usd")
                            .setCustomer("cus_MoL5hcuaju8aSb")
                            .build();


            Charge charge = Charge.create(chargeParams);
            addToBookingList();
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    private void callPaymentmethodApi() {
        final BottomSheetDialog bt = new BottomSheetDialog(BookingDetail.this, R.style.BottomSheetDialogTheme);
        View items = LayoutInflater.from(BookingDetail.this).inflate(R.layout.layout_item_chooser, null, false);
        RecyclerView recyclerView = items.findViewById(R.id.paymentList);

        viewModel.getPaymentMethods();
        viewModel._payment.observe(this, response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setLayoutManager(new LinearLayoutManager(App.applicationContext, RecyclerView.HORIZONTAL, false));
                    PaymentMethodsAdapter adapter = new PaymentMethodsAdapter(response.getData().getData(), BookingDetail.this);
                    recyclerView.setAdapter(adapter);

                    adapter.setonClickListener(position -> {
                        PaymentDataItem paymentDataItem = response.getData().getData().get(position);
                        try {
                            String selected_payment = String.valueOf(paymentDataItem.getTitle());
                            activityBinding.addPaymentMethod.setText(selected_payment);
                            Glide.with(BookingDetail.this).load(Constants.IMG_PATH + paymentDataItem.getImage()).into(activityBinding.paymentMethodIcon);

                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }

                        payment_type_id = paymentDataItem.getId();
                        payment_type = paymentDataItem.getTitle();
                        if (paymentDataItem.getTitle().equalsIgnoreCase("Card")) {
                            //  if (customerID != null) {
                            Intent intent = new Intent(BookingDetail.this, GetAllCards.class);
                            //   intent.putExtra("customer_id", customerID);
                            //  intent.putExtra("client_secret", clientSecret);
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                            // }
                        }
                        activityBinding.totalPayment.setText(Constants.constant.CURRENCY + getCartTotal());
                        bt.dismiss();
                        // activityBinding.btnPayNow.performClick();
                    });
                }
            }
        });
        bt.setContentView(items);
        bt.show();
    }

    private void addBooking(List<AddBookingDataItem> bookingList) {
        try {

            String token = prefRepository.getString("token");
            Gson gson = new Gson();

            JsonObject obj = new JsonObject();
            obj.add("data", gson.toJsonTree(bookingList));

            viewModel.addBooking(token, obj);
            viewModel._add_booking.observe(this, response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData() != null) {
                        hideLoading();
                        TinyDbManager.savePromo(null);
                        //TinyDbManager.saveCartData(null);
                        TinyDbManager.saveServiceProviderID("null");
                        TinyDbManager.clearCart();
                        TinyDbManager.clearBookingList();


                        showRequestedDialogue();
                    }
                }
            });


        } catch (IllegalStateException | NumberFormatException | NullPointerException exception) {
            exception.printStackTrace();
            showSnackBarShort("Something Went Wrong");
        }
    }


}