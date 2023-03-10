package com.rizorsiumani.user.ui.fragment.wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.TransactionsDataItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private final List<TransactionsDataItem> list;
    private final Context ctx;

    public TransactionsAdapter(List<TransactionsDataItem> transactions, Context context) {
        this.list = transactions;
        this.ctx = context;
    }

    @NonNull
    @Override
    public TransactionsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_list_item_design, parent, false);
        return new TransactionsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionsAdapter.ViewHolder holder, int position) {
        TransactionsDataItem dataItem = list.get(position);

        if (dataItem.getDebit() != 0){
            holder.amount.setText("- " + Constants.constant.CURRENCY + dataItem.getDebit());
            holder.icon.setImageResource(R.drawable.debit_icon);
        }else if (dataItem.getCredit() != 0){
            holder.amount.setText("+ " + Constants.constant.CURRENCY + dataItem.getCredit());
            holder.icon.setImageResource(R.drawable.ic_credit);
        }

        holder.date.setText(Constants.constant.getDate(dataItem.getCreatedAt()));
        holder.name.setText(dataItem.getDescription());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name,amount,date;
        public ImageView icon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.payment_name);
            amount = itemView.findViewById(R.id.payment_amount);
            date = itemView.findViewById(R.id.payment_date);
            icon = itemView.findViewById(R.id.payment_icon);
        }
    }
}
