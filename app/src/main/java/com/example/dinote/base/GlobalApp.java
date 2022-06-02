package com.example.dinote.base;

import android.app.AlarmManager;
import android.app.Application;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.dinote.myshareferences.MyDataLocal;
import com.example.dinote.reciver.RemindReceiver;
import com.example.dinote.utils.Constant;

public class GlobalApp extends Application {

    public static Context context;

    public void onCreate() {
        super.onCreate();
        GlobalApp.context = getApplicationContext();
        MyDataLocal.init(context);
        setUpTimeRemind();
    }

    private void setUpTimeRemind() {
        if (!MyDataLocal.getIsFirstInstall()) {
            MyDataLocal.setInstalled();
            long timeDefault = Constant.defaultCalendar();
            Intent intent = new Intent(this, RemindReceiver.class);
            MyDataLocal.setTimeRemind(timeDefault);
            PendingIntent pendingIntent;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int type = AlarmManager.RTC_WAKEUP;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(type, timeDefault, pendingIntent);
            } else {
                alarmManager.set(type, timeDefault, pendingIntent);
            }
        }
    }

    public static Context getAppContext() {
        return GlobalApp.context;
    }

}
