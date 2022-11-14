package com.rizorsiumani.workondemanduser.ui.add_card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.databinding.ActivityAddCardBinding;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.Stripe;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.exception.StripeException;
import com.stripe.param.CustomerCreateParams;

import static com.rizorsiumani.workondemanduser.App.applicationContext;

public class AddCard extends BaseActivity<ActivityAddCardBinding> {

    String publish_key = "pk_test_51LqBWECQ7dojez1jeJpRCqumuAAhzrtnllMzOLKBWRJi8YcSQCalUNElMinY3Jp2mz6NCNvNqE8Su2c8sCKFWOZR00gY2QVC9k";
    String secret_key = "sk_test_51LqBWECQ7dojez1jHKh6u2A4sxsizRNO7ciTF1znIAeQD1Nu8yLoULZ7s5uqLByJ6q8RDw3AHxnhoF8vWtc0f3BJ001tFvbnv8";
    private com.stripe.android.Stripe stripe;


    @Override
    protected ActivityAddCardBinding getActivityBinding() {
        return ActivityAddCardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        PaymentConfiguration.init(
                getApplicationContext(),
                publish_key
        );
        
        activityBinding.addCardToolbar.title.setText("Add Card");
        clickListener();
    }

    private void clickListener() {
     activityBinding.addCardToolbar.back.setOnClickListener(view -> {
         onBackPressed();
         finish();
         overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
     });

        activityBinding.btnAddCard.setOnClickListener(view1 -> {
            PaymentMethodCreateParams params = activityBinding.cardWidgets.getPaymentMethodCreateParams();
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
                    showSnackBarShort(e.getMessage());
                }
            });
        });


    }
}