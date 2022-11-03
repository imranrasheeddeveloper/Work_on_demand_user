package com.rizorsiumani.workondemanduser.ui.booking_detail;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
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
import com.rizorsiumani.workondemanduser.ui.add_location.AddAddress;
import com.rizorsiumani.workondemanduser.ui.booking.MyCartItems;
import com.rizorsiumani.workondemanduser.ui.booking_date.BookingDateTime;
import com.rizorsiumani.workondemanduser.ui.dashboard.Dashboard;
import com.rizorsiumani.workondemanduser.ui.promo_code.PromoCode;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.paymentsheet.PaymentSheet;
import com.stripe.android.paymentsheet.PaymentSheetResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class BookingDetail extends BaseActivity<ActivityBookingDetailBinding> {

    CartServicesAdapter adapter;
    List<String> name;
    AlertDialog.Builder dialogBuilder;
    AlertDialog alertDialog;
    String secret_key = "sk_test_51LqBTjGmaWwccsNaRmfGGvK4TOL4j0rXloATiyVD7Nou0aCzqjttDMqrjJjf7sRt4mIHaFx8bivnmlzsazUI1Zie00ob2H1tvR";
    String publish_key = "pk_test_51LqBTjGmaWwccsNaWNAb8x6B51zmMVMsI62gcxZTpC6lvhvGy7vdcw1CX1vkkrHYMkkN2C79mexjkPpuGeHW8Kg500CEi0L3Vm";
    PaymentSheet paymentSheet;
    String customerID;
    String ephemeralKey;
    String clientSecret;
    CustomerIdResponse idResponse;
    EphemeralKeyResponse ephemeralKeyResponse;
    PaymentIntentResponse intentResponse;
    String service_provider_id;
    int total;

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

        StringRequest request = new StringRequest(Request.Method.POST,
                "https://api.stripe.com/v1/customers",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response != null) {

                            Gson gson = new Gson();
                            idResponse = gson.fromJson(response, CustomerIdResponse.class);
                            customerID = idResponse.getId();
                            getEphemeralKey(customerID);

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

        RequestQueue requestQueue = Volley.newRequestQueue(BookingDetail.this);
        requestQueue.add(request);


    }

    private void setData() {
        try {


            if (!TinyDbManager.getCurrentAddress().isEmpty()) {
                activityBinding.tvAddress.setText(TinyDbManager.getCurrentAddress());
            } else {
                activityBinding.tvAddress.setText("Please set your location");
            }

            activityBinding.bookingDetailToolbar.title.setText("Booking Details");
            activityBinding.btnPayNow.setText(Constants.CURRENCY + getCartTotal());

            if (TinyDbManager.getCartData() != null) {
                if (TinyDbManager.getCartData().size() > 0) {
                    getCartServices(TinyDbManager.getCartData());
                    activityBinding.totalCharges.setText(Constants.CURRENCY + getCartTotal());
                    activityBinding.subTotal.setText(Constants.CURRENCY + getCartTotal());
                }
            }

            try {

                if (TinyDbManager.getPromo() != null) {
                    PromoDataItem promoDataItem = TinyDbManager.getPromo();
                    int amount_of_discount = (int) (Integer.parseInt(getCartTotal()) * Math.round(promoDataItem.getDiscount()) / 100);
                    Constants.discount = String.valueOf(amount_of_discount);
                    activityBinding.tvDiscount.setText(promoDataItem.getDiscount() + "% OFF (" + promoDataItem.getCode() + ")");
                    activityBinding.discountPrice.setText("- " + amount_of_discount);
                    int updatedValue = Integer.valueOf(getCartTotal()) - amount_of_discount;
                    activityBinding.btnPayNow.setText(Constants.CURRENCY  + updatedValue);
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
            ActivityUtil.gotoPage(BookingDetail.this, BookingDateTime.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                param.put("amount", "1000" + "00");
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

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(activityBinding.cartServicesList);

        adapter.setOnCartListener(position -> {
            TinyDbManager.removeCartItem(cartData.get(position));
            adapter.update(position);
            if (TinyDbManager.getCartData().size() == 0) {

                TinyDbManager.savePromo(null);
                TinyDbManager.saveServiceProviderID("null");
//                TinyDbManager.saveCartData.(null);

                onBackPressed();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            }
        });
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

        activityBinding.btnPayNow.setOnClickListener(view -> {
            if (Constants.payment_type_id == null || Constants.payment_type_id.isEmpty()) {
                showSnackBarShort("Select Payment Method");
            } else if (activityBinding.tvAddress.getText().toString().equalsIgnoreCase("Please set your location")) {
                showSnackBarShort("Select Your Location");
            } else {
                addBooking();
            }
            //paymentFlow();
        });

        activityBinding.btnPayLater.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, BookingDateTime.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.addPaymentMethod.setOnClickListener(view -> {
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

                            Constants.payment_type_id = String.valueOf(paymentDataItem.getId());
                            bt.dismiss();
                            // activityBinding.btnPayNow.performClick();
                        });
                    }
                }
            });
//            ElasticImageView cash = items.findViewById(R.id.cashIcon);
//            ElasticImageView card = items.findViewById(R.id.cardIcon);
//            cash.setOnClickListener(view1 -> {
//                bt.cancel();
//            });
//            card.setOnClickListener(view1 -> {
//                activityBinding.btnPayNow.performClick();
//                bt.cancel();
//            });
            bt.setContentView(items);
            bt.show();
        });

        activityBinding.editAddress.setOnClickListener(view -> {
            ActivityUtil.gotoPage(BookingDetail.this, AddAddress.class);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }

    private void addBooking() {
        try {

            int userID = TinyDbManager.getUserInformation().getId();
            int service_id = Integer.parseInt(TinyDbManager.getCartData().get(0).getId());
            String token = prefRepository.getString("token");


            if (Constants.discount == null || Constants.discount.isEmpty()) {
                Constants.discount = "0";
            }
            if (Constants.promotion_id == null || Constants.promotion_id.isEmpty()) {
                Constants.promotion_id = "0";
            }


            List<AddBookingDataItem> list = new ArrayList<>();
            list.add(new AddBookingDataItem(total,
                    TinyDbManager.getCurrentAddress(),
                    Integer.parseInt(service_provider_id),
                    Integer.parseInt(String.valueOf(userID)),
                    Constants.latitude,
                    Constants.description,
                    Integer.parseInt(Constants.availability_id),
                    Integer.parseInt(Constants.discount),
                    Integer.parseInt(Constants.promotion_id),
                    total,
                    Integer.parseInt(Constants.payment_type_id),
                    Constants.longitude,
                    service_id
            ));

            Gson gson = new Gson();

            JsonObject obj = new JsonObject();
            obj.add("data", gson.toJsonTree(list));

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

                        showRequestedDialogue();
                    }
                }
            });


        } catch (IllegalStateException | NumberFormatException | NullPointerException exception) {
            exception.printStackTrace();
            showSnackBarShort("Something Went Wrong");
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (resultCode == BraintreePaymentActivity.RESULT_OK) {
//            PaymentMethodNonce paymentMethodNonce = data.getParcelableExtra(BraintreePaymentActivity.EXTRA_PAYMENT_METHOD_NONCE);
//
//            RequestParams requestParams = new RequestParams();
//            requestParams.put("payment_method_nonce", paymentMethodNonce.getNonce());
//            requestParams.put("amount", "10.00");
//
//            client.post(SERVER_BASE + "/payment", requestParams, new TextHttpResponseHandler() {
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                    Toast.makeText(SDKActivity.this, responseString, Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                    Toast.makeText(SDKActivity.this, responseString, Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//
//    private void getToken() {
//        client.get(SERVER_BASE + "/token", new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//                findViewById(R.id.btn_start).setEnabled(false);
//            }
//
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                clientToken = responseString;
//                findViewById(R.id.btn_start).setEnabled(true);
//            }
//        });
//    }

    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();

            switch (direction) {
                case ItemTouchHelper.LEFT:

                    name.remove(position);
                    adapter.notifyDataSetChanged();

                    break;
            }

        }


        @Override
        public void onChildDraw(Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

            String toShow = "DELETE";
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addBackgroundColor(ContextCompat.getColor(BookingDetail.this, R.color.primary))
                    .addSwipeLeftActionIcon(R.drawable._cross)
                    .create()
                    .decorate();


            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }


    };


}