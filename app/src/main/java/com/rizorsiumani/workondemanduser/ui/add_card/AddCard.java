package com.rizorsiumani.workondemanduser.ui.add_card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityAddCardBinding;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.CardParams;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.payments.paymentlauncher.PaymentResult;
import com.stripe.exception.StripeException;
import com.stripe.model.Card;
import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.PaymentMethod;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerUpdateParams;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.PaymentMethodAttachParams;
import com.stripe.stripeterminal.Terminal;
import com.stripe.stripeterminal.external.callable.SetupIntentCallback;
import com.stripe.stripeterminal.external.models.SetupIntent;
import com.stripe.stripeterminal.external.models.SetupIntentParameters;
import com.stripe.stripeterminal.external.models.TerminalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.rizorsiumani.workondemanduser.App.applicationContext;

public class AddCard extends BaseActivity<ActivityAddCardBinding> {


    String publish_key = "pk_test_51M4i3YFyKkhI7Itr08xnMiRdCI0txUVQcAlroa7CfS1nLDIjRaKN6YIdMucBtThd1u7U0VZiKrkd5qEAT20yTVMI001ZLp3IjR";
    String secret_key = "sk_test_51M4i3YFyKkhI7ItrcxzXMddb90sUJrShRKkPOXFMW25LhQjqsRrn0DKX8Cw4sA2u4PDMki7bPeMoZP2tX4nvMh7O00ijVe6qL6";


    @Override
    protected ActivityAddCardBinding getActivityBinding() {
        return ActivityAddCardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideCartButton();

        PaymentConfiguration.init(
                getApplicationContext(),
                publish_key
        );


//        final PaymentConfiguration paymentConfiguration = PaymentConfiguration.getInstance(getApplicationContext());
//
//        paymentLauncher = PaymentLauncher.Companion.create(
//                this,
//                paymentConfiguration.getPublishableKey(),
//                paymentConfiguration.getStripeAccountId(),
//                this::onPaymentResult
//        );


        activityBinding.addCardToolbar.title.setText("Add Card");
        clickListener();
    }



    private void clickListener() {
        activityBinding.addCardToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });



        activityBinding.btnAddCard.setOnClickListener(v -> {
            try {
                if (TinyDbManager.getUserInformation().getStripe_customerId() != null) {
                    String id = TinyDbManager.getUserInformation().getStripe_customerId();

                    com.stripe.Stripe.apiKey = secret_key;
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);

                    showLoading();

                    try {

                        CardParams cardParams = activityBinding.card.getCardParams();
                        if (cardParams != null) {

                            Map<String, Object> retrieveParams =
                                    new HashMap<>();
                            List<String> expandList = new ArrayList<>();
                            expandList.add("sources");
                            retrieveParams.put("expand", expandList);
                            Customer customer =
                                    Customer.retrieve(
                                            id,
                                            retrieveParams,
                                            null
                                    );

                            Map<String, Object> params = new HashMap<>();
                            params.put("source","tok_"+cardParams.getBrand().getCode());

                            com.stripe.model.Card card =
                                    (Card) customer.getSources().create(params);
                            hideLoading();

                            //todo update default method
//                    Customer resource = Customer.retrieve("cus_MnR9guf7yfBHXX");
//                    CustomerUpdateParams params1 =
//                            CustomerUpdateParams.builder().setSource("tok_"+cardParams.getBrand().getCode()).build();
//
//                    Customer customer1 = resource.update(params1);

                            onBackPressed();
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }

                    } catch (StripeException e) {
                        e.printStackTrace();
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        hideLoading();
                    }

                }else {
                    showSnackBarShort("Customer ID not available, Try Again");
                }
            }catch (NullPointerException | IllegalStateException e){
                e.printStackTrace();
            }
        });
    }


}