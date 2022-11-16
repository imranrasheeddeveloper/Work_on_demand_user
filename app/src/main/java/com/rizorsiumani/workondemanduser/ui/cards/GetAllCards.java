package com.rizorsiumani.workondemanduser.ui.cards;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

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
import com.rizorsiumani.workondemanduser.App;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityGetAllCardsBinding;
import com.rizorsiumani.workondemanduser.ui.add_card.AddCard;
import com.rizorsiumani.workondemanduser.ui.booking_detail.model.DataItem;
import com.rizorsiumani.workondemanduser.ui.booking_detail.model.GetCardsModel;
import com.rizorsiumani.workondemanduser.ui.sp_detail.DiscountPlansAdapter;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;
import com.stripe.exception.StripeException;
import com.stripe.param.CustomerCreateParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rizorsiumani.workondemanduser.App.applicationContext;

public class GetAllCards extends BaseActivity<ActivityGetAllCardsBinding> {

    String CustomerID, client_secret;
    private com.stripe.android.Stripe stripe;
    String publish_key = "pk_test_51LqBTjGmaWwccsNaWNAb8x6B51zmMVMsI62gcxZTpC6lvhvGy7vdcw1CX1vkkrHYMkkN2C79mexjkPpuGeHW8Kg500CEi0L3Vm";
    String secret_key = "sk_test_51LqBTjGmaWwccsNaRmfGGvK4TOL4j0rXloATiyVD7Nou0aCzqjttDMqrjJjf7sRt4mIHaFx8bivnmlzsazUI1Zie00ob2H1tvR";


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
//
//        try {
//            CustomerID = getIntent().getStringExtra("customer_id");
//            client_secret = getIntent().getStringExtra("client_secret");
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }

        activityBinding.cardsToolbar.title.setText("Cards");
        activityBinding.cardsToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

        clickListeners();
        Cards();
    }

    private void Cards() {

        StringRequest request = new StringRequest(Request.Method.GET,
                "https://api.stripe.com/v1/customers/cus_MnR9guf7yfBHXX/sources?object=card",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {
                            Gson gson = new Gson();
                            GetCardsModel result = gson.fromJson(response, GetCardsModel.class);
                            if (result.getData().size() > 0){
                             activityBinding.noPaymentMethod.setVisibility(View.GONE);
                             activityBinding.cardsExistLayout.setVisibility(View.VISIBLE);
                             buildRv(result.getData());
                            }else {
                                activityBinding.noPaymentMethod.setVisibility(View.VISIBLE);
                                activityBinding.cardsExistLayout.setVisibility(View.GONE);
                            }
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
                param.put("object", "card");
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(GetAllCards.this);
        requestQueue.add(request);
    }

    private void buildRv(List<DataItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetAllCards.this, RecyclerView.VERTICAL, false);
        activityBinding.cardsList.setLayoutManager(layoutManager);
        CardsAdapter adapter = new CardsAdapter(data, GetAllCards.this);
        activityBinding.cardsList.setAdapter(adapter);
    }

    private void clickListeners() {
        activityBinding.addCard.setOnClickListener(view -> {
            Intent intent = new Intent(GetAllCards.this, AddCard.class);
//            intent.putExtra("customer_id", CustomerID);
//            intent.putExtra("client_secret", client_secret);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.btnAdd1.setOnClickListener(view -> {
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

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Stripe stripe = new Stripe(
                        GetAllCards.this,
                        publish_key
                );
                try {
                    final PaymentIntent paymentIntent =
                            stripe.retrievePaymentIntentSynchronous(client_secret);
                    final PaymentIntent.Error lastPaymentError = paymentIntent != null ?
                            paymentIntent.getLastPaymentError() : null;
                    final String failureReason;
                    if (lastPaymentError != null &&
                            PaymentIntent.Error.Type.CardError.equals(lastPaymentError.getType())) {
                        failureReason = lastPaymentError.getMessage();
                    } else {
                        failureReason = "Payment failed, try again"; // Default to a generic error message
                    }
                    // Display the failure reason to your customer
                } catch (Exception e) {
                    // Handle error
                }
            }
        });
//        StringRequest request = new StringRequest(Request.Method.GET,
//                "https://api.stripe.com/v1/customers/"+customerID+"/sources?object=card",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//
//                        if (response != null) {
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
//
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(GetAllCards.this);
//        requestQueue.add(request);

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