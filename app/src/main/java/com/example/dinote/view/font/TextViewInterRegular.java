package com.example.dinote.view.font;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class TextViewInterRegular extends androidx.appcompat.widget.AppCompatTextView {
    private Context mContext;

    public TextViewInterRegular(@NonNull Context context) {
        super(context);
        mContext = context;
        setTypeFace();
    }

    public TextViewInterRegular(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setTypeFace();
    }

    private void setTypeFace(){
        setTypeface(Typeface.createFromAsset(mContext.getAssets(),"font/Inter-Regular.ttf"));
    }
}
