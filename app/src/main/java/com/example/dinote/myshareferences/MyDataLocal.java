package com.example.dinote.myshareferences;


import android.content.Context;

public class MyDataLocal {
    private static final String KEY_PREFERENCE_FIRST = "first_install";
    private static final String KEY_LANGUAGE = "language";
    private static MyDataLocal instance;
    private MySharePreference mySharePreference;
    public static Context mContext;

    public static void init(Context context){
        instance = new MyDataLocal();
        mContext = context;
        instance.mySharePreference = new MySharePreference(context);

    }
    public static MyDataLocal getInstance(){
        if (instance==null){
            instance = new MyDataLocal();
        }
            return instance;
    }
    public static void setTheme(int intTheme){
        MyDataLocal.getInstance().mySharePreference.pushThemeValue(KEY_PREFERENCE_FIRST,intTheme);


    }
    public static int getTheme(){
      return MyDataLocal.getInstance().mySharePreference.getDataTheme(KEY_PREFERENCE_FIRST);
    }



}
