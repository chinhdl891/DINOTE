package com.example.dinote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.dinote.R;
import com.example.dinote.utils.ReDesign;

import java.util.Objects;

public class ThemeAdapter extends PagerAdapter {
    private Context context;
    private int[] images;
    private LayoutInflater mLayoutInflater;

    public ThemeAdapter(Context context, int[] images) {
        this.context = context;
        this.images = images;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        View itemView = mLayoutInflater.inflate(R.layout.item_photo, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imv_item_photo);
        ReDesign.resizeImage(imageView,640,720);
        Glide.with(context).load(images[position]).into(imageView);
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }
}
