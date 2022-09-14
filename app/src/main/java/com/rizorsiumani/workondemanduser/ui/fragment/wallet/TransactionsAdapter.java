package com.rizorsiumani.workondemanduser.ui.fragment.wallet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rizorsiumani.workondemanduser.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {

    private final List<String> list;
    private final Context ctx;

    public TransactionsAdapter(List<String> transactions, Context context) {
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
        String name = list.get(position);
        holder.name.setText(name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.payment_name);
        }
    }
}
