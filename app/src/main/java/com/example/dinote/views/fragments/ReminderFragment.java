package com.example.dinote.views.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dinote.R;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.FragmentReminderBinding;
import com.example.dinote.reciver.RemindReceiver;

import java.util.Calendar;


public class ReminderFragment extends BaseFragment<FragmentReminderBinding> implements View.OnClickListener {

    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_reminder;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void resizeViews() {

    }

    @Override
    protected void onClickViews() {
        mBinding.imvRemindCancel.setOnClickListener(this);
        mBinding.btnRemind.setOnClickListener(this);
        mBinding.lnlRemindTimeSelect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imv_remind_cancel) {
            getActivity().onBackPressed();
        } else if (view.getId() == R.id.lnl_remind_time_select) {
            showSelectTimeDefault();
        }

    }

    private void showSelectTimeDefault() {
        calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minus = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                Toast.makeText(getActivity(), "Thời gian nhắc nhở " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
                mBinding.tvTimeSelect.setText("Thời gian nhắc nhở " + hour + ":" + minute);
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                setAlarmRemind(calendar);
            }
        }, hour, minus, true);
        timePicker.show();
    }

    private void setAlarmRemind(Calendar calendar) {
        Log.e("TAG", "setAlarmRemind: " + calendar.get(Calendar.HOUR_OF_DAY) +" - " + calendar.get(Calendar.MINUTE) );
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, RemindReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(mContext, 10, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Toast.makeText(getActivity(), "Set alarm success", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setTypeView() {

    }


}