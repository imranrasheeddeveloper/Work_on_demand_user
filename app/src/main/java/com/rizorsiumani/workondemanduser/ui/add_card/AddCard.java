package com.rizorsiumani.workondemanduser.ui.add_card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.os.StrictMode;
import android.widget.Toast;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityAddCardBinding;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
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

    private com.stripe.android.Stripe stripe;
    String CustomerID, client_secret;
    private String paymentIntentClientSecret;
    private PaymentLauncher paymentLauncher;
    String publish_key = "pk_test_51LqBTjGmaWwccsNaWNAb8x6B51zmMVMsI62gcxZTpC6lvhvGy7vdcw1CX1vkkrHYMkkN2C79mexjkPpuGeHW8Kg500CEi0L3Vm";
    String secret_key = "sk_test_51LqBTjGmaWwccsNaRmfGGvK4TOL4j0rXloATiyVD7Nou0aCzqjttDMqrjJjf7sRt4mIHaFx8bivnmlzsazUI1Zie00ob2H1tvR";


    @Override
    protected ActivityAddCardBinding getActivityBinding() {
        return ActivityAddCardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        hideCartButton();


//        try {
//            CustomerID = getIntent().getStringExtra("customer_id");
//            client_secret = getIntent().getStringExtra("client_secret");
//        }catch (NullPointerException e){
//            e.printStackTrace();
//        }

        PaymentConfiguration.init(
                getApplicationContext(),
                publish_key
        );


//        SetupIntentParameters params = new SetupIntentParameters.Builder()
//                .setCustomer(CustomerID)
//                .build();
//
//        Terminal.getInstance().createSetupIntent(params, new SetupIntentCallback() {
//            @Override
//            public void onSuccess(SetupIntent setupIntent) {
//                // Placeholder for collecting a payment method with setupIntent
//            }
//
//            @Override
//            public void onFailure(TerminalException exception) {
//                // Placeholder for handling exception
//            }
//        });
//
//        Terminal.getInstance().retrieveSetupIntent(client_secret,
//                new SetupIntentCallback() {
//                    @Override
//                    public void onSuccess(SetupIntent setupIntent) {
//
//                        // Placeholder for collecting a payment method with setupIntent
//                    }
//
//                    @Override
//                    public void onFailure(TerminalException exception) {
//                        // Placeholder for handling exception
//                    }
//                });

        final PaymentConfiguration paymentConfiguration = PaymentConfiguration.getInstance(getApplicationContext());

        paymentLauncher = PaymentLauncher.Companion.create(
                this,
                paymentConfiguration.getPublishableKey(),
                paymentConfiguration.getStripeAccountId(),
                this::onPaymentResult
        );
//        try {
////        CustomerCreateParams params =
////                CustomerCreateParams.builder()
////                        .build();
////
////
////            Customer customer = Customer.create(params);
//
////            PaymentIntentCreateParams param =
////                    PaymentIntentCreateParams.builder()
////                            .setAmount(1099L)
////                            .setCurrency("usd")
////                            .setCustomer(customer.getId())
////                            .build();
////
////            PaymentIntent paymentIntent = PaymentIntent.create(param);
//
//            startCheckout();
//
//        } catch (StripeException e) {
//            e.printStackTrace();
//        }

        activityBinding.addCardToolbar.title.setText("Add Card");
        clickListener();
    }

    private void startCheckout() {
    }

    private void clickListener() {
        activityBinding.addCardToolbar.back.setOnClickListener(view -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });

//        activityBinding.btnAddCard.setOnClickListener(view1 -> {
//
//            PaymentMethodCreateParams params = activityBinding.cardWidgets.getPaymentMethodCreateParams();
//            if (params != null) {
//                Map<String, String> extraParams = new HashMap<>();
//                extraParams.put("setup_future_usage", "off_session");
//
//                ConfirmPaymentIntentParams confirmParams = ConfirmPaymentIntentParams
//                        .createWithPaymentMethodCreateParams(params, secret_key,
//                                true);
//
//                stripe = new Stripe(AddCard.this, publish_key);
//                paymentLauncher.confirm(confirmParams);
//            }
////            PaymentMethodCreateParams params = activityBinding.cardWidgets.getPaymentMethodCreateParams();
////            if (params == null) {
////                return;
////            }
////            // Configure the SDK with your Stripe publishable key so that it can make requests to the Stripe API
////            stripe = new Stripe(applicationContext,
////                    publish_key);
////            stripe.createPaymentMethod(params, new ApiResultCallback<PaymentMethod>() {
////                @Override
////                public void onSuccess(@NonNull PaymentMethod result) {
////                    try {
////                        String paymentMethodId = result.id;
////                        showSnackBarShort(result.id);
////                        CustomerCreateParams params =
////                                CustomerCreateParams.builder().setPaymentMethod(paymentMethodId).build();
////
//////                        com.stripe.model.Customer customer = com.stripe.model.Customer.create(params);
////
////                        com.stripe.model.PaymentMethod paymentMethod = com.stripe.model.PaymentMethod.retrieve(paymentMethodId);
////
////                        PaymentMethodAttachParams params1 =
////                                PaymentMethodAttachParams.builder()
////                                        .setCustomer(CustomerID)
////                                        .build();
////
////                        paymentMethod.attach(params1);
////
////                    } catch (NetworkOnMainThreadException |StripeException e) {
////                        e.printStackTrace();
////                    }
////                    // Send paymentMethodId to your server for the next steps
////                }
////
////                @Override
////                public void onError(@NonNull Exception e) {
////                    showSnackBarShort(e.getMessage());
////                }
////            });
//        });

        activityBinding.btnAddCard.setOnClickListener(v -> {

//            Map<String,Object> card=new HashMap<>();
//            Map<String,Object> billingDetaild=new HashMap<>();
//
//            String expiry = "11/26";
//            String m=expiry.split("\\/")[0];
//            String y=expiry.split("\\/")[1];
//
//            int month=Integer.parseInt(m);
//            int year=Integer.parseInt(y);
//
//            card.put("number","4242424242424242");
//            card.put("exp_month",month);
//            card.put("exp_year",year);
//            card.put("cvc","124");
//
//            billingDetaild.put("name","android");
//            billingDetaild.put("email","andstudio104@gmail.com");
//
//            Map<String,Object> allDetail=new HashMap<>();
//
//            allDetail.put("type","card");
//            allDetail.put("card",card);
//            allDetail.put("billing_details",billingDetaild);
//
//            com.stripe.model.PaymentMethod paymentMethod=null;
//            try {
//                paymentMethod = com.stripe.model.PaymentMethod.create(allDetail);
//                com.stripe.model.PaymentMethod paymentMethodRetriveID=null;
//                paymentMethodRetriveID= com.stripe.model.PaymentMethod.retrieve(paymentMethod.getId());
//
//                Map<String,Object> cutomer=new HashMap<>();
//                cutomer.put("customer","cus_MnR9guf7yfBHXX");
//
//                PaymentMethod attchedCard=paymentMethodRetriveID.attach(cutomer);
//                Toast.makeText(this,"Your card added Successfully",Toast.LENGTH_LONG).show();
//
//            }catch (StripeException e){
//                e.printStackTrace();
//                Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
//            }
            com.stripe.Stripe.apiKey = "sk_test_51LqBTjGmaWwccsNaRmfGGvK4TOL4j0rXloATiyVD7Nou0aCzqjttDMqrjJjf7sRt4mIHaFx8bivnmlzsazUI1Zie00ob2H1tvR";
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            try {

                PaymentMethodCreateParams params1 = activityBinding.cardWidgets.getPaymentMethodCreateParams();
                params1.getCard();

                Map<String, Object> retrieveParams =
                        new HashMap<>();
                List<String> expandList = new ArrayList<>();
                expandList.add("sources");
                retrieveParams.put("expand", expandList);
                Customer customer =
                        Customer.retrieve(
                                "cus_MnR9guf7yfBHXX",
                                retrieveParams,
                                null
                        );

                Map<String, Object> params = new HashMap<>();
                params.put("source", "tok_visa");

                com.stripe.model.Card card =
                        (Card) customer.getSources().create(params);

                onBackPressed();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);

            } catch (StripeException e) {
                e.printStackTrace();
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void onPaymentResult(PaymentResult paymentResult) {
        String message = "";
        if (paymentResult instanceof PaymentResult.Completed) {
            message = "Completed!";
        } else if (paymentResult instanceof PaymentResult.Canceled) {
            message = "Canceled!";
        } else if (paymentResult instanceof PaymentResult.Failed) {
            message = "Failed: " + ((PaymentResult.Failed) paymentResult).getThrowable().getMessage();
        }

        showSnackBarShort("PaymentResult: " + message);
    }
}