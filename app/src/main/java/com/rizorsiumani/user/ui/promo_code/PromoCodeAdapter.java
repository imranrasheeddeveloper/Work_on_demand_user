package com.rizorsiumani.user.ui.promo_code;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.PromoDataItem;

import java.util.List;

public class PromoCodeAdapter extends RecyclerView.Adapter<PromoCodeAdapter.ViewHolder>{

    private final List<PromoDataItem> list;
    private Context context;
    private ItemClickListener mListener;

    public void setOnPromoCodesClickListener(ItemClickListener listener) {
        mListener = listener;
    }


    public PromoCodeAdapter(List<PromoDataItem> code, Context ctx) {
        this.list = code;
        this.context = ctx;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View car_list= layoutInflater.inflate(R.layout.promo_code_list_design, parent, false);
        return new ViewHolder(car_list,mListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PromoDataItem item = list.get(position);

        holder.discount.setText(String.valueOf(item.getDiscount()));
        holder.codeNo.setText(item.getCode());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onCodeSelected(int position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView codeNo,activate,discount;


        public ViewHolder(View itemView, ItemClickListener mListener) {
            super(itemView);

            codeNo = (itemView).findViewById(R.id.tvPromoCode);
            activate  = (itemView).findViewById(R.id.tvActivate);
            discount = (itemView).findViewById(R.id.tvCodeText);

            activate.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onCodeSelected(position);
                    }
                }
            });

            itemView.setOnClickListener(view -> {
                if (mListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onCodeSelected(position);
                    }
                }
            });

        }
    }
}


