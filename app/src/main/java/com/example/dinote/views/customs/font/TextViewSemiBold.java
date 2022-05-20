package com.example.dinote.views.customs.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class TextViewSemiBold extends AppCompatTextView {
    private Context mContext;

    public TextViewSemiBold(@NonNull Context context) {
        super(context);
        mContext = context;
        setTypeFace();
    }

    public TextViewSemiBold(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setTypeFace();
    }

    private void setTypeFace(){
        setTypeface(Typeface.createFromAsset(mContext.getAssets(),"font/Inter-SemiBold.ttf"));
    }
}
