package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.SliderDataItem;
import com.rizorsiumani.workondemanduser.ui.sub_category.SubCategories;
import com.rizorsiumani.workondemanduser.utils.Constants;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class HomeSliderAdapter extends SliderViewAdapter<HomeSliderAdapter.SliderAdapterViewHolder> {

    List<SliderDataItem> images;
    Context context;

    public HomeSliderAdapter(Context context, List<SliderDataItem> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_design, null);
        return new SliderAdapterViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {
        SliderDataItem item = images.get(position);

        viewHolder.title.setText(item.getDescription());
        Glide.with(context)
                .load(Constants.IMG_PATH + item.getImage())
                .into(viewHolder.illustrator);
        String color1 = item.getColor();
        if (!color1.startsWith("#")){
            color1 = "#" + color1;
            viewHolder.sliderBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color1)));
        }else {
            viewHolder.sliderBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(color1)));
        }
        viewHolder.sliderBtn.setText(item.getTitle());

        viewHolder.sliderBtn.setOnClickListener(view -> {
            Intent intent = new Intent(context, SubCategories.class);
            intent.putExtra("category_id", item.getId());
            context.startActivity(intent);
            //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });

        try {
            String color = item.getColor();
            if (!color.startsWith("#")) {
                color = "#" + color;
                final int[] colors = new int[2];
                colors[0] = Color.parseColor(color);
                colors[1] = Color.parseColor("#fef4ea");

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        colors
                );
                viewHolder.rootView.setBackground(gd);
            }else {
                final int[] colors = new int[2];
                colors[0] = Color.parseColor(color);
                colors[1] = Color.parseColor("#fef4ea");

                GradientDrawable gd = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT,
                        colors
                );
                viewHolder.rootView.setBackground(gd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getCount() {
        return images.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        ConstraintLayout rootView;
        View itemView;
        TextView title, sliderBtn;
        ImageView illustrator;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.slider_title);
            illustrator = itemView.findViewById(R.id.illustrator);
            sliderBtn = itemView.findViewById(R.id.slider_btn);
            rootView = itemView.findViewById(R.id.root_view);
            this.itemView = itemView;
        }
    }
}
