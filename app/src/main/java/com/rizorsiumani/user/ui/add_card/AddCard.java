package com.rizorsiumani.user.ui.add_card;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.JsonObject;
import com.rizorsiumani.user.App;
import com.rizorsiumani.user.BaseActivity;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.databinding.ActivityAddCardBinding;
import com.rizorsiumani.user.utils.Constants;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.CardParams;

public class AddCard extends BaseActivity<ActivityAddCardBinding> {


    String publish_key = "pk_test_51M4i3YFyKkhI7Itr08xnMiRdCI0txUVQcAlroa7CfS1nLDIjRaKN6YIdMucBtThd1u7U0VZiKrkd5qEAT20yTVMI001ZLp3IjR";
    String secret_key = "sk_test_51M4i3YFyKkhI7ItrcxzXMddb90sUJrShRKkPOXFMW25LhQjqsRrn0DKX8Cw4sA2u4PDMki7bPeMoZP2tX4nvMh7O00ijVe6qL6";
    private CardViewModel viewModel;

    @Override
    protected ActivityAddCardBinding getActivityBinding() {
        return ActivityAddCardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel = new ViewModelProvider(this).get(CardViewModel.class);

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

            CardParams cardParams = activityBinding.card.getCardParams();

            if (cardParams != null) {
                String cvc = cardParams.getCvc$payments_core_release();
                String exp_month = String.valueOf(cardParams.getExpMonth$payments_core_release());
                String exp_year = String.valueOf(cardParams.getExpYear$payments_core_release());
                String number = cardParams.getNumber$payments_core_release();

                String token = prefRepository.getString("token");
                JsonObject object = new JsonObject();
                object.addProperty("number", number);
                object.addProperty("exp_month", exp_month);
                object.addProperty("exp_year", exp_year);
                object.addProperty("cvc", cvc);

                viewModel.createCard(token, object);
                viewModel._createCard.observe(this, response -> {
                    if (response != null) {
                        if (response.isLoading()) {
                            showLoading();
                        } else if (response.getError() != null) {
                    hideLoading();
                    if (response.getError() == null){
                        showSnackBarShort("Something went wrong!!");
                    }else {
                        Constants.constant.getApiError(App.applicationContext,response.getError());
                    }
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            showSnackBarShort(response.getData().getMessage());
                            onBackPressed();
                            finish();
                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        }
                    }
                });

            }


//            try {
//                if (TinyDbManager.getUserInformation().getStripe_customerId() != null) {
//                    String id = TinyDbManager.getUserInformation().getStripe_customerId();
//
//                    com.stripe.Stripe.apiKey = secret_key;
//                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//                    StrictMode.setThreadPolicy(policy);
//
//                    showLoading();
//
//                    try {
//
//                        CardParams cardParams = activityBinding.card.getCardParams();
//                        if (cardParams != null) {
//
//                            Map<String, Object> retrieveParams =
//                                    new HashMap<>();
//                            List<String> expandList = new ArrayList<>();
//                            expandList.add("sources");
//                            retrieveParams.put("expand", expandList);
//                            Customer customer =
//                                    Customer.retrieve(
//                                            id,
//                                            retrieveParams,
//                                            null
//                                    );
//
//                            Map<String, Object> params = new HashMap<>();
//                            params.put("source","tok_"+cardParams.getBrand().getCode());
//
//                            com.stripe.model.Card card =
//                                    (Card) customer.getSources().create(params);
//                            hideLoading();
//
//                            //todo update default method
////                    Customer resource = Customer.retrieve("cus_MnR9guf7yfBHXX");
////                    CustomerUpdateParams params1 =
////                            CustomerUpdateParams.builder().setSource("tok_"+cardParams.getBrand().getCode()).build();
////
////                    Customer customer1 = resource.update(params1);
//
//                            onBackPressed();
//                            finish();
//                            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
//                        }
//
//                    } catch (StripeException e) {
//                        e.printStackTrace();
//                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//                        hideLoading();
//                    }
//
//                }else {
//                    showSnackBarShort("Customer ID not available, Try Again");
//                }
//            }catch (NullPointerException | IllegalStateException e){
//                e.printStackTrace();
//            }
        });
    }


}