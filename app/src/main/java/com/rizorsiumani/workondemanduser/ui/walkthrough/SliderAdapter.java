package com.rizorsiumani.workondemanduser.ui.walkthrough;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.rizorsiumani.workondemanduser.R;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int[] slideImages = {
            R.drawable.page_1,
            R.drawable.page_1,
            R.drawable.page_1
    };

    public String[] slideHeadings = {
            "Introducing Rizorsi Usmani!",
            "Avail Services In Hours",
            "Introducing Rizorsi Usmani!"
    };

    public String[] slideDescriptions = {
            "The brand new all-in-one App Offering multiple services to Residential and Commmercial users.",
            "The brand new all-in-one App Offering multiple services to Residential and Commmercial users.",
            "The brand new all-in-one App Offering multiple services to Residential and Commmercial users."
    };


    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.onboard_layout_item_slide, container, false);

        ImageView slideImageView = (ImageView) view.findViewById(R.id.iv_image_icon);
        TextView slideHeading = (TextView) view.findViewById(R.id.tv_heading);
        TextView slideDescription = (TextView) view.findViewById(R.id.tv_description);

        slideImageView.setImageResource(slideImages[position]);
        slideHeading.setText(slideHeadings[position]);
        slideDescription.setText(slideDescriptions[position]);

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);  //todo: RelativeLayout??
    }
}
