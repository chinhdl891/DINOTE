package com.example.dinote.reciver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dinote.R;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.model.TimeRemind;
import com.example.dinote.utils.Constant;
import com.example.dinote.views.activities.MainActivity;

import java.util.Calendar;
import java.util.List;

public class RemindReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra(Constant.RE_CREATE_ALARM,892001);
//        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),
//                R.drawable.ic_text_loved);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "dinoteId")
                .setContentTitle("Dinote luôn bên cạnh bạn")
                .setContentText("Bạn đã sử dụng ghi chú hôm nay chưa")
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.ic_text_loved)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(123, builder.build());
        Calendar calendar = Calendar.getInstance();
        List<TimeRemind> timeRemindList = DinoteDataBase.getInstance(context).timeRemindDAO().getListTimeRemind();




    }

}
