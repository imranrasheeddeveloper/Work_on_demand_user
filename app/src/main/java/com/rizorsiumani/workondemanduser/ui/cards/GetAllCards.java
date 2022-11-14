package com.rizorsiumani.workondemanduser.ui.cards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.stripe.PaymentIntentResponse;
import com.rizorsiumani.workondemanduser.databinding.ActivityGetAllCardsBinding;
import com.rizorsiumani.workondemanduser.ui.add_card.AddCard;
import com.rizorsiumani.workondemanduser.ui.booking_detail.BookingDetail;
import com.rizorsiumani.workondemanduser.ui.filter.CategoryFilterAdapter;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.Customer;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.stripe.exception.StripeException;
import com.stripe.param.CustomerCreateParams;
import com.stripe.stripeterminal.Terminal;
import com.stripe.stripeterminal.external.callable.SetupIntentCallback;
import com.stripe.stripeterminal.external.models.SetupIntent;
import com.stripe.stripeterminal.external.models.SetupIntentParameters;
import com.stripe.stripeterminal.external.models.TerminalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.rizorsiumani.workondemanduser.App.applicationContext;

public class GetAllCards extends BaseActivity<ActivityGetAllCardsBinding> {

    String CustomerID, client_secret;
    String publish_key = "pk_test_51LqBWECQ7dojez1jeJpRCqumuAAhzrtnllMzOLKBWRJi8YcSQCalUNElMinY3Jp2mz6NCNvNqE8Su2c8sCKFWOZR00gY2QVC9k";
    String secret_key = "sk_test_51LqBWECQ7dojez1jHKh6u2A4sxsizRNO7ciTF1znIAeQD1Nu8yLoULZ7s5uqLByJ6q8RDw3AHxnhoF8vWtc0f3BJ001tFvbnv8";
    private com.stripe.android.Stripe stripe;



    @Override
    protected ActivityGetAllCardsBinding getActivityBinding() {
        return ActivityGetAllCardsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        PaymentConfiguration.init(
                getApplicationContext(),
                publish_key
        );

        try {
            CustomerID = getIntent().getStringExtra("customer_id");
            client_secret = getIntent().getStringExtra("client_secret");
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        activityBinding.cardsToolbar.title.setText("Cards");
        activityBinding.cardsToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        clickListeners();

        saveCard(CustomerID);
        getCards(CustomerID);
    }

    private void clickListeners() {
        activityBinding.addCard.setOnClickListener(view -> {
         //   showAddCardSheet();
            Intent intent = new Intent(GetAllCards.this, AddCard.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void showAddCardSheet() {
        final BottomSheetDialog bt = new BottomSheetDialog(GetAllCards.this, R.style.BottomSheetDialogTheme);
        bt.setCanceledOnTouchOutside(false);
        View view = LayoutInflater.from(GetAllCards.this).inflate(R.layout.add_card_layout, null, false);
        bt.getBehavior().addBottomSheetCallback(mBottomSheetBehaviorCallback);

        CardInputWidget cardInputWidget = view.findViewById(R.id.cardWidgets);
        Button addCard = view.findViewById(R.id.btn_add_card);

        addCard.setOnClickListener(view1 -> {
            PaymentMethodCreateParams params = cardInputWidget.getPaymentMethodCreateParams();
            if (params == null) {
                return;
            }
            // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
            stripe = new Stripe(applicationContext,
                    publish_key);
            stripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
                @Override
                public void onSuccess(@NonNull PaymentMethod result) {
                    try {
                    String paymentMethodId = result.id;
                    showSnackBarShort(result.id);
                    CustomerCreateParams params =
                            CustomerCreateParams.builder().setPaymentMethod(paymentMethodId).build();

                        com.stripe.model.Customer customer = com.stripe.model.Customer.create(params);

//                        PaymentMethod paymentMethod = PaymentMethod.retrieve("{{PAYMENT_METHOD_ID}}");
//
//                        PaymentMethodAttachParams params =
//                                PaymentMethodAttachParams.builder()
//                                        .setCustomer("{{CUSTOMER_ID}}")
//                                        .build();
//
//                        paymentMethod.attach(params);

                    } catch (StripeException e) {
                        e.printStackTrace();
                    }
                    // Send paymentMethodId to your server for the next steps
                }

                @Override
                public void onError(@NonNull Exception e) {
                    // Display the error to the user
                }
            });
        });


        bt.setContentView(view);
        bt.show();
    }

    private void saveCard(String customerID) {
//        StringRequest request = new StringRequest(Request.Method.POST,
//                "https://api.stripe.com/v1/"+customerID+"/sources",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        if (response != null) {
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
//
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> params = new HashMap<>();
//                params.put("source", "tok_amex");
//
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(GetAllCards.this);
//        requestQueue.add(request);



//        SetupIntentParameters params = new SetupIntentParameters.Builder()
//                .setCustomer(customerID)
//                .build();
//
//        Terminal.getInstance().createSetupIntent(params, new SetupIntentCallback() {
//            @Override
//            public void onSuccess(@NonNull SetupIntent setupIntent) {
//                // Placeholder for collecting a payment method with setupIntent
//            }
//
//            @Override
//            public void onFailure(@NonNull TerminalException exception) {
//                // Placeholder for handling exception
//            }
//        });
    }

    private void getCards(String customerID) {
        StringRequest request = new StringRequest(Request.Method.GET,
                "https://api.stripe.com/v1/customers/"+customerID+"/sources?object=card",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {

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

        };

        RequestQueue requestQueue = Volley.newRequestQueue(GetAllCards.this);
        requestQueue.add(request);

    }

    private BottomSheetBehavior.BottomSheetCallback mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {

        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
               // count = 0;
            } else {
               // count = 1;
            }

        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };
}