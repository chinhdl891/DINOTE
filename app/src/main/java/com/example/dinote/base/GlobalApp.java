package com.example.dinote.base;

import android.app.Application;
import android.content.Context;

import com.example.dinote.myshareferences.MyDataLocal;

public class GlobalApp extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        GlobalApp.context = getApplicationContext();
        MyDataLocal.init(context);
    }

    public static Context getAppContext() {
        return GlobalApp.context;
    }

}
