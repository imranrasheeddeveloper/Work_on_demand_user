package com.rizorsiumani.workondemanduser.ui.sp_detail.reviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.RatingDataItem;
import com.rizorsiumani.workondemanduser.utils.Constants;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    private final List<RatingDataItem> list;
    private final Context ctx;

    public ReviewsAdapter(List<RatingDataItem> data, Context context) {
        this.list = data;
        this.ctx = context;
    }

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sp_reviews_item_design, parent, false);
        return new ReviewsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        try {

        RatingDataItem ratingDataItem = list.get(position);
        Glide.with(ctx)
                .load(Constants.IMG_PATH +ratingDataItem.getUser().getImage())
                        .into(holder.userImg);
        holder.review.setText(ratingDataItem.getDescription());
        holder.usename.setText(ratingDataItem.getUser().getFirstName());
        holder.rating.setText(String.valueOf(ratingDataItem.getRaiting()));

        }catch (NullPointerException | IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView review;
        CircleImageView userImg;
        TextView usename,rating;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            rating = itemView.findViewById(R.id.sp_reviews);
            userImg = itemView.findViewById(R.id.user_image);
            usename = itemView.findViewById(R.id.tv_name);
            review = itemView.findViewById(R.id.comment);

        }
    }
}
