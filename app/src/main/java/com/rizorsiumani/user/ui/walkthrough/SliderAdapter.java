package com.rizorsiumani.user.ui.walkthrough;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.rizorsiumani.user.R;
import com.rizorsiumani.user.data.businessModels.OnBoardDataItem;
import com.rizorsiumani.user.utils.Constants;

import java.util.List;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;
    List<OnBoardDataItem> data;

    public SliderAdapter(Context context, List<OnBoardDataItem> dataItems) {
        this.context = context;
        this.data = dataItems;
    }

    @Override
    public int getCount() {
        return data.size();
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

        OnBoardDataItem boardDataItem = data.get(position);

        Glide.with(context)
                .load(Constants.IMG_PATH + boardDataItem.getImage())
                .into(slideImageView);

        slideHeading.setText(boardDataItem.getTitle());
        slideDescription.setText(boardDataItem.getDescription());

        container.addView(view);

        return view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout) object);  //todo: RelativeLayout??
    }
}
