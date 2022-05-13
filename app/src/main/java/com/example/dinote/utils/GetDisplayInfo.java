package com.example.dinote.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;

public class GetDisplayInfo {
    public static int[] listInfoDisplay(Activity context) {
        int[] listInfo = new int[2];
        DisplayMetrics displayMetrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        listInfo[0] = displayMetrics.widthPixels;
        listInfo[1] = displayMetrics.heightPixels;
        return listInfo;
    }


    public static int[] locateView(View view) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        Point p = new Point();
        p.x = location[0];
        p.y = location[1];
        return location;
    }
}
