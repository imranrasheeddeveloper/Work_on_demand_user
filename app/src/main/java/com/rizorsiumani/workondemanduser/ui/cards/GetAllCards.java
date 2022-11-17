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
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
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

    String publish_key = "pk_test_51M4i3YFyKkhI7Itr08xnMiRdCI0txUVQcAlroa7CfS1nLDIjRaKN6YIdMucBtThd1u7U0VZiKrkd5qEAT20yTVMI001ZLp3IjR";
    String secret_key = "sk_test_51M4i3YFyKkhI7ItrcxzXMddb90sUJrShRKkPOXFMW25LhQjqsRrn0DKX8Cw4sA2u4PDMki7bPeMoZP2tX4nvMh7O00ijVe6qL6";


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
        try {

        if (TinyDbManager.getUserInformation().getStripe_customerId() != null) {
            String id = TinyDbManager.getUserInformation().getStripe_customerId();
            StringRequest request = new StringRequest(Request.Method.GET,
                    "https://api.stripe.com/v1/customers/"+id+"/sources?object=card",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if (response != null) {
                                Gson gson = new Gson();
                                GetCardsModel result = gson.fromJson(response, GetCardsModel.class);
                                if (result.getData().size() > 0) {
                                    activityBinding.noPaymentMethod.setVisibility(View.GONE);
                                    activityBinding.cardsExistLayout.setVisibility(View.VISIBLE);
                                    buildRv(result.getData());
                                } else {
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

        }catch (NullPointerException |IllegalStateException e){
            e.printStackTrace();
        }
    }

    private void buildRv(List<DataItem> data) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(GetAllCards.this, RecyclerView.VERTICAL, false);
        activityBinding.cardsList.setLayoutManager(layoutManager);
        CardsAdapter adapter = new CardsAdapter(data, GetAllCards.this);
        activityBinding.cardsList.setAdapter(adapter);

        adapter.setOnCardClickListener(position -> {
            TinyDbManager.selectedCard(data.get(position));
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }

    private void clickListeners() {
        activityBinding.addCard.setOnClickListener(view -> {
            Intent intent = new Intent(GetAllCards.this, AddCard.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        activityBinding.btnAdd1.setOnClickListener(view -> {
            Intent intent = new Intent(GetAllCards.this, AddCard.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}