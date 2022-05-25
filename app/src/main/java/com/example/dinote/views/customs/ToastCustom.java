package com.example.dinote.views.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.dinote.R;
import com.example.dinote.databinding.ToastLoveBinding;
import com.example.dinote.utils.ReDesign;

public class ToastCustom extends LinearLayout {
    private Context mContext;
    private View mRootView;

    public ToastCustom(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public ToastCustom(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    private void initView() {

        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mRootView = layoutInflater.inflate(R.layout.toast_love, this);
        ImageView imageView = mRootView.findViewById(R.id.imv_toast_loved);

        ReDesign.resizeImage(imageView, 64, 64);
    }
}
