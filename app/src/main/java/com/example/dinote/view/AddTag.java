package com.example.dinote.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.dinote.R;

public class AddTag extends LinearLayout {
    private Context mContext;
    private View mRootView;
    private EditText tvTagAdd;

    public AddTag(Context context) {
        super(context);
        mContext = context;
    }

    public AddTag(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public void init() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mRootView = inflater.inflate(R.layout.add_tag, this, true);
        tvTagAdd = mRootView.findViewById(R.id.edt_add_tag);



    }
    public void setUpData(String tag){

    }
}
