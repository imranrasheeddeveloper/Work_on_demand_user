package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.rizorsiumani.workondemanduser.R;
import com.rizorsiumani.workondemanduser.data.businessModels.HomeSliderModel;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class HomeSliderAdapter extends SliderViewAdapter<HomeSliderAdapter.SliderAdapterViewHolder> {

    List<HomeSliderModel> images;

    public HomeSliderAdapter(Context context, List<HomeSliderModel> images) {
        this.images = images;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_design, null);
        return new SliderAdapterViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {
        HomeSliderModel item = images.get(position);

        viewHolder.title.setText(item.getDesc());
        viewHolder.illustrator.setImageResource(item.getImgID());
        viewHolder.sliderBtn.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(item.getColorCode())));

        try {

            final int[] colors = new int[2];
            colors[0] = Color.parseColor(item.getColorCode());
            colors[1] = Color.parseColor("#fef4ea");

            GradientDrawable gd = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT,
                    colors
            );
            viewHolder.rootView.setBackground(gd);
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
