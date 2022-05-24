package com.example.dinote.myshareferences;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharePreference {

    private static final String MY_SHARE_PREFERENCE = "my_share_preference";
    private Context mContext;

    public MySharePreference(Context mContext) {
        this.mContext = mContext;
    }

    public  void pushThemeValue(String key, int value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();

    }


    public int getData(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(MY_SHARE_PREFERENCE, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }



}
