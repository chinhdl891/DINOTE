package com.example.dinote.reciver;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dinote.R;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.model.TimeRemind;
import com.example.dinote.myshareferences.MyDataLocal;
import com.example.dinote.utils.Constant;
import com.example.dinote.views.activities.MainActivity;

import java.util.Collections;
import java.util.List;

public class RemindReceiver extends BroadcastReceiver {
    private long timeSetAlarm;

    @SuppressLint("UnspecifiedImmutableFlag")
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(Constant.RE_CREATE_ALARM, 892001);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, context.getString(R.string.notifi_id))
                .setContentTitle(context.getString(R.string.notifi_title))
                .setContentText(context.getString(R.string.notifi_cation_content))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_text_loved)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());

        long timeRemindDefault = MyDataLocal.getTimeRemind();
        if (timeRemindDefault < System.currentTimeMillis()) {
            timeRemindDefault = timeRemindDefault + 24 * 60 * 60 * 1000;
            MyDataLocal.setTimeRemind(timeRemindDefault);
        }

        Intent reIntent = new Intent(context, RemindReceiver.class);
        PendingIntent piReMind;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            piReMind = PendingIntent.getBroadcast(context,10,reIntent,PendingIntent.FLAG_IMMUTABLE);
        }else {
            piReMind = PendingIntent.getBroadcast(context,10,reIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        }

        List<TimeRemind> timeRemindListNoSort = DinoteDataBase.getInstance(context).timeRemindDAO().getListTimeRemind();
        timeRemindListNoSort.add(new TimeRemind(0, timeRemindDefault, 1));
        Collections.sort(timeRemindListNoSort);
        for (int j = 0; j < timeRemindListNoSort.size(); j++) {
            TimeRemind timeRemind = timeRemindListNoSort.get(j);
            if (timeRemind.getStatus() == 1) {
                if (timeRemind.getTime() > System.currentTimeMillis()) {
                    timeSetAlarm = timeRemind.getTime();
                    break;
                } else {
                    timeRemind.setTime(timeRemind.getTime() + 24 * 60 * 60 * 1000);
                    DinoteDataBase.getInstance(context).timeRemindDAO().update(timeRemind);
                }
            }
        }
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        int type = AlarmManager.RTC_WAKEUP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(type, timeSetAlarm, piReMind);
        } else {
            alarmManager.set(type, timeSetAlarm, piReMind);
        }

    }
}


