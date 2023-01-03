package com.rizorsiumani.workondemanduser.ui.cards;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.CardsDataItem;
import com.rizorsiumani.workondemanduser.data.local.TinyDbManager;
import com.rizorsiumani.workondemanduser.databinding.ActivityGetAllCardsBinding;
import com.rizorsiumani.workondemanduser.ui.add_card.AddCard;
import com.rizorsiumani.workondemanduser.ui.add_card.CardViewModel;
import com.stripe.android.PaymentConfiguration;

import java.util.ArrayList;
import java.util.List;

public class GetAllCards extends BaseActivity<ActivityGetAllCardsBinding> {

    String publish_key = "pk_test_51M4i3YFyKkhI7Itr08xnMiRdCI0txUVQcAlroa7CfS1nLDIjRaKN6YIdMucBtThd1u7U0VZiKrkd5qEAT20yTVMI001ZLp3IjR";
    String secret_key = "sk_test_51M4i3YFyKkhI7ItrcxzXMddb90sUJrShRKkPOXFMW25LhQjqsRrn0DKX8Cw4sA2u4PDMki7bPeMoZP2tX4nvMh7O00ijVe6qL6";
    CardsAdapter adapter;
    private CardViewModel viewModel;
    private List<CardsDataItem> cardsDataItems;

    @Override
    protected ActivityGetAllCardsBinding getActivityBinding() {
        return ActivityGetAllCardsBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void onStart() {
        super.onStart();

        viewModel = new ViewModelProvider(this).get(CardViewModel.class);
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

    @Override
    protected void onResume() {
        super.onResume();
        Cards();

    }

    private void Cards() {
        try {

            String token = prefRepository.getString("token");
            viewModel.getCards(token);
            viewModel._cards.observe(this, response -> {
                if (response != null) {
                    if (response.isLoading()) {
                        showLoading();
                    } else if (!response.getError().isEmpty()) {
                        hideLoading();
                        showSnackBarShort(response.getError());
                    } else if (response.getData().getData() != null) {
                        hideLoading();
                        if (response.getData().getData().getData().size() > 0) {
                            hideNoDataAnimation();
                            activityBinding.noPaymentMethod.setVisibility(View.GONE);
                            activityBinding.cardsExistLayout.setVisibility(View.VISIBLE);
                            cardsDataItems = new ArrayList<>();
                            cardsDataItems.addAll(response.getData().getData().getData());
                            buildRv();
                        } else {
                            activityBinding.noPaymentMethod.setVisibility(View.VISIBLE);
                            activityBinding.cardsExistLayout.setVisibility(View.GONE);
                        }
                    }
                }
                });
//        if (TinyDbManager.getUserInformation().getStripe_customerId() != null) {
//            String id = TinyDbManager.getUserInformation().getStripe_customerId();
//            StringRequest request = new StringRequest(Request.Method.GET,
//                    "https://api.stripe.com/v1/customers/"+id+"/sources?object=card",
//                    new Response.Listener<String>() {
//                        @Override
//                        public void onResponse(String response) {
//
//                            if (response != null) {
//                                Gson gson = new Gson();
//                                GetCardsModel result = gson.fromJson(response, GetCardsModel.class);
//                                if (result.getData().size() > 0) {
//                                    activityBinding.noPaymentMethod.setVisibility(View.GONE);
//                                    activityBinding.cardsExistLayout.setVisibility(View.VISIBLE);
//                                    buildRv(result.getData());
//                                } else {
//                                    activityBinding.noPaymentMethod.setVisibility(View.VISIBLE);
//                                    activityBinding.cardsExistLayout.setVisibility(View.GONE);
//                                }
//                            }
//                        }
//                    }, new Response.ErrorListener() {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//
//                }
//            }) {
//                @Override
//                public Map<String, String> getHeaders() throws AuthFailureError {
//                    Map<String, String> header = new HashMap<>();
//                    header.put("Authorization", "Bearer " + secret_key);
//                    return header;
//                }
//
//                @Nullable
//                @Override
//                protected Map<String, String> getParams() throws AuthFailureError {
//                    Map<String, String> param = new HashMap<>();
//                    param.put("object", "card");
//                    return param;
//                }
//            };
//
//            RequestQueue requestQueue = Volley.newRequestQueue(GetAllCards.this);
//            requestQueue.add(request);
//        }

            }catch(NullPointerException | IllegalStateException e){
                e.printStackTrace();
            }
        }

        private void buildRv () {
            LinearLayoutManager layoutManager = new LinearLayoutManager(GetAllCards.this, RecyclerView.VERTICAL, false);
            activityBinding.cardsList.setLayoutManager(layoutManager);
            adapter = new CardsAdapter(cardsDataItems, GetAllCards.this);
            activityBinding.cardsList.setAdapter(adapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
            itemTouchHelper.attachToRecyclerView(activityBinding.cardsList);

            adapter.setOnCardClickListener(position -> {
                TinyDbManager.selectedCard(cardsDataItems.get(position));
                onBackPressed();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            });
        }

        private void clickListeners () {
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

    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
            final int position = viewHolder.getAdapterPosition();
            if (direction == ItemTouchHelper.LEFT) {
                String token = prefRepository.getString("token");
                viewModel.removeCard(token,cardsDataItems.get(position).getId());
                viewModel._remove.observe(GetAllCards.this , response -> {
                    if (response != null){
                        if (response.isLoading()) {
                            showLoading();
                        } else if (!response.getError().isEmpty()) {
                            hideLoading();
                            showSnackBarShort(response.getError());
                        } else if (response.getData().isSuccess()) {
                            hideLoading();
                            adapter.removeItem(position);
                        }
                    }
                });
            }
        }

        @Override
        public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            try {

                Bitmap icon;
                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 5;
                    viewHolder.itemView.setTranslationX(dX / 5);

                    Paint paint = new Paint();
                    paint.setColor(Color.parseColor("#D32F2F"));
                    RectF background = new RectF((float) itemView.getRight() + dX / 5, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                    c.drawRect(background, paint);
                    icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete);
                    RectF icon_dest = new RectF((float) (itemView.getRight() + dX /7), (float) itemView.getTop()+width, (float) itemView.getRight()+dX/20, (float) itemView.getBottom()-width);
                    c.drawBitmap(icon, null, icon_dest, paint);
                } else {
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };
    }