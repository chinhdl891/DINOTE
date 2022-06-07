package com.example.dinote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.dinote.R;
import com.example.dinote.utils.ReDesign;

public class TutorialAdapter extends PagerAdapter {
    private Context mContext;
    private int[] images = {
            R.drawable.imv_tutorial_st,
            R.drawable.imv_tutorial_nd,
            R.drawable.imv_tutorial_rd,
            R.drawable.imv_tutorial_4th
    };
    private int[] titles = {
            R.string.txt_dinote_title,
            R.string.txt_backup_and_security_title,
            R.string.txt_theme_title,
            R.string.txt_go_to_main
    };
    private int[] des = {
            R.string.txt_dinote,
            R.string.txt_backup_and_security,
            R.string.txt_theme,
            R.string.txt_go_to_main_des
    };

    public TutorialAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_view_pager, container, false);

        ImageView imvTutorialBackground = view.findViewById(R.id.imv_tutorial_background);
        TextView tvTutorialTitle = view.findViewById(R.id.tv_tutorial_title);
        TextView tvTutorialDes = view.findViewById(R.id.tv_tutorial_des);

        imvTutorialBackground.setImageResource(images[position]);
        tvTutorialDes.setText(des[position]);
        tvTutorialTitle.setText(titles[position]);
        if (position > 3) {
            tvTutorialTitle.setVisibility(View.GONE);
        }
        container.addView(view);

        ReDesign.resizeImage(imvTutorialBackground, 1080, 720);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
