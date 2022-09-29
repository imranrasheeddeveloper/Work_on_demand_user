package com.rizorsiumani.workondemanduser.ui.fragment.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rizorsiumani.workondemanduser.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.List;

public class HomeSliderAdapter extends SliderViewAdapter<HomeSliderAdapter.SliderAdapterViewHolder> {


    List<String> images;

    public HomeSliderAdapter(Context context , List<String> images) {
        this.images=images;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_slider_design, null);
        return new SliderAdapterViewHolder(inflate);
    }


    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {


        viewHolder.title.setText(images.get(position));

    }


    @Override
    public int getCount() {
        return images.size();
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {

        View itemView;
        TextView title;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.slider_title);
            this.itemView = itemView;
        }
    }
}
