package com.rizorsiumani.workondemanduser.ui.inbox;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.rizorsiumani.workondemanduser.BaseActivity;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.InboxDataItem;
import com.rizorsiumani.workondemanduser.data.businessModels.inbox.ServiceProvider;
import com.rizorsiumani.workondemanduser.databinding.ActivityInboxBinding;
import com.rizorsiumani.workondemanduser.ui.chat.Chatroom;
import com.rizorsiumani.workondemanduser.utils.ActivityUtil;

import java.util.ArrayList;
import java.util.List;

public class Inbox extends BaseActivity<ActivityInboxBinding> {

    private InboxViewModel viewModel;
    List<InboxDataItem> list;
    int inbox_id;

    @Override
    protected ActivityInboxBinding getActivityBinding() {
        return ActivityInboxBinding.inflate(getLayoutInflater());
    }


    @Override
    protected void onStart() {
        super.onStart();

        //inbox_id = getIntent().getStringExtra("inbox_id");
        viewModel = new ViewModelProvider(this).get(InboxViewModel.class);
        activityBinding.inboxToolbar.title.setText("Inbox");
        clickListeners();
        getInboxData();
    }

    private void getInboxData() {
        String token = prefRepository.getString("token");
        viewModel.getInbox(token);
        viewModel._inbox.observe(this , response -> {
            if (response != null) {
                if (response.isLoading()) {
                    showLoading();
                } else if (!response.getError().isEmpty()) {
                    hideLoading();
                    showSnackBarShort(response.getError());
                } else if (response.getData().getData() != null) {
                    hideLoading();
                    if (response.getData().getData().size() > 0){
                        hideNoDataAnimation();
                        list = new ArrayList<>();
                        for (int i = 0; i < response.getData().getData().size(); i++) {
                            if (response.getData().getData().get(i).getLastMsg() != null){
                                list.add(response.getData().getData().get(i));
                            }
                        }
                        if (list.size() > 0) {
                            setInboxRv(list);
                        }
                    }else {
                        showNoDataAnimation(R.raw.empty_inbox,"No Chat");
                    }
                }
            }
        });

    }

    private void clickListeners() {
        activityBinding.inboxToolbar.back.setOnClickListener(v -> {
            onBackPressed();
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        });
    }
    private void setInboxRv(List<InboxDataItem> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(Inbox.this, RecyclerView.VERTICAL, false);
        activityBinding.inboxList.setLayoutManager(layoutManager);
        InboxAdapter adapter = new InboxAdapter(list, Inbox.this);
        activityBinding.inboxList.setAdapter(adapter);

        adapter.setOnChatClickListener(position -> {
            Gson gson = new Gson();
            String providerData = gson.toJson(list.get(position).getServiceProvider(), ServiceProvider.class);
            Intent intent = new Intent(Inbox.this, Chatroom.class);
            intent.putExtra("maps","false");
            intent.putExtra("inbox_id",String.valueOf(list.get(position).getId()));
            intent.putExtra("provider_detail",providerData);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
    }
}