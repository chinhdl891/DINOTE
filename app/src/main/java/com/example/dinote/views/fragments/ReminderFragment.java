package com.example.dinote.views.fragments;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.dinote.R;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentReminderBinding;
import com.example.dinote.model.TimeRemind;
import com.example.dinote.myshareferences.MyDataLocal;
import com.example.dinote.reciver.RemindReceiver;
import com.example.dinote.utils.Constant;
import com.example.dinote.views.customs.TimeSetUpView;
import com.example.dinote.views.dialogs.DeleteTimeDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


public class ReminderFragment extends BaseFragment<FragmentReminderBinding> implements View.OnClickListener, TimeSetUpView.TimeSetUpViewListener, DeleteTimeDialog.DeleteTimeDialogListener {

    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private List<TimeRemind> timeRemindList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
    private static final String TAG = "ReminderFragment";
    private long timeSetAlarm;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_reminder;
    }

    @Override
    protected void initViews(View rootView) {
        getListTime();

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
        } else if (view.getId() == R.id.btn_remind) {
            addTimeRemind();
        }

    }

    private void addTimeRemind() {
        TimeSetUpView timeSetUpView = new TimeSetUpView(mContext);
        timeSetUpView.setTimeSetUpViewListener(this);
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minus = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);

                String date = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));
                TimeRemind timeRemind = new TimeRemind(System.currentTimeMillis(), calendar.getTimeInMillis(), 0);
                timeSetUpView.setUpData(date, timeRemind);
                timeSetUpView.setTag(mBinding.lnlRemindListTime.getChildCount());
                int loop = DinoteDataBase.getInstance(getActivity()).timeRemindDAO().getCountTimeAlarm(calendar.getTimeInMillis());
                if (loop > 0) {
                    Toast.makeText(getActivity(), R.string.time_reminded, Toast.LENGTH_SHORT).show();
                } else {
                    DinoteDataBase.getInstance(getActivity()).timeRemindDAO().insertTime(timeRemind);
                    mBinding.lnlRemindListTime.addView(timeSetUpView);
                    timeRemindList.add(timeRemind);
                }

            }
        }, hour, minus, true);
        timePicker.show();


    }

    private void showSelectTimeDefault() {
        calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minus = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hour);
                calendar.set(Calendar.MINUTE, minute);
                calendar.set(Calendar.SECOND, 0);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm a");
                String date = simpleDateFormat.format(new Date(calendar.getTimeInMillis()));
                mBinding.tvTimeSelect.setText(getString(R.string.time_remind) + date);
                mySharePreference.pushTimeRemind(calendar.getTimeInMillis());
                mySharePreference.pushTimeValue(Constant.TIME_REMIND, date);
                setAlarmRemind(calendar);
            }
        }, hour, minus, false);
        timePicker.setTitle(getString(R.string.choose_time_remind));
        timePicker.show();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void setAlarmRemind(Calendar calendar) {
        alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(mContext, RemindReceiver.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntent = PendingIntent.getBroadcast(mContext, 10, intent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);
        } else {
            pendingIntent = PendingIntent.getBroadcast(mContext, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        int type = AlarmManager.RTC_WAKEUP;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(type, calendar.getTimeInMillis(), pendingIntent);
        } else {
            alarmManager.set(type, calendar.getTimeInMillis(), pendingIntent);
        }
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void setTypeView() {
//        String s = String.format(getString(R.string.time_remid), mySharePreference.getDataTime(Constant.TIME_REMIND), 999);
//        String s = String.format("Th???i gian nh???c nh???:  rffry %d sadasd %s", mySharePreference.getDataTime(Constant.TIME_REMIND), 999);
        mBinding.tvTimeSelect.setText(getString(R.string.time_remid, mySharePreference.getDataTime(Constant.TIME_REMIND)));
    }

    private int tag;
    private DeleteTimeDialog deleteTimeDialog;

    @Override
    public void onDeleteTime(int tag) {
        this.tag = tag;
        deleteTimeDialog = new DeleteTimeDialog(getActivity());
        deleteTimeDialog.setDeleteTimeDialogListener(this::onDelete);
        Window window = deleteTimeDialog.getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        deleteTimeDialog.show();

    }

    @Override
    public void onSetUpStatusTime(int status, TimeRemind timeRemind) {
        long timeRemindDefault = MyDataLocal.getTimeRemind();
        timeRemind.setStatus(status);
        DinoteDataBase.getInstance(getActivity()).timeRemindDAO().update(timeRemind);
        if (status == 1) {
            Intent reIntent = new Intent(mContext, RemindReceiver.class);
            PendingIntent piReMind;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                piReMind = PendingIntent.getBroadcast(mContext, 10, reIntent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                piReMind = PendingIntent.getBroadcast(mContext, 10, reIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            List<TimeRemind> timeRemindListNoSort = DinoteDataBase.getInstance(mContext).timeRemindDAO().getListTimeRemind();
            timeRemindListNoSort.add(new TimeRemind(0, timeRemindDefault, 1));
            Collections.sort(timeRemindListNoSort);

            long dayMilis = AlarmManager.INTERVAL_DAY;

            for (int j = 0; j < timeRemindListNoSort.size(); j++) {
                TimeRemind timeRemindNext = timeRemindListNoSort.get(j);
                if (timeRemindNext.getStatus() == 1) {
                    if (timeRemindNext.getTime() > System.currentTimeMillis()) {
                        timeSetAlarm = timeRemindNext.getTime();
                        break;
                    } else {
                        timeRemindNext.setTime(timeRemindNext.getTime() + dayMilis);
                        DinoteDataBase.getInstance(mContext).timeRemindDAO().update(timeRemindNext);
                    }
                }
            }

            AlarmManager alarmManager = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
            int type = AlarmManager.RTC_WAKEUP;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(type, timeSetAlarm, piReMind);
            } else {
                alarmManager.set(type, timeSetAlarm, piReMind);
            }

        }
    }

    @Override
    public void onDelete(boolean delete) {
        if (delete) {

            if (mBinding.lnlRemindListTime.getChildAt(tag) instanceof TimeSetUpView) {
                DinoteDataBase.getInstance(getActivity()).timeRemindDAO().delete(((TimeSetUpView) mBinding.lnlRemindListTime.getChildAt(tag)).getmTimeRemind());
            }
        }
        getListTime();
        deleteTimeDialog.dismiss();
    }

    public void getListTime() {
        timeRemindList = new ArrayList<>();
        mBinding.lnlRemindListTime.removeAllViews();
        timeRemindList = DinoteDataBase.getInstance(getActivity()).timeRemindDAO().getListTimeRemind();
        for (int i = 0; i < timeRemindList.size(); i++) {
            TimeSetUpView timeSetUpView = new TimeSetUpView(getActivity());
            timeSetUpView.setTimeSetUpViewListener(this);
            timeSetUpView.setTag(mBinding.lnlRemindListTime.getChildCount());
            TimeRemind timeRemind = timeRemindList.get(i);
            String time = simpleDateFormat.format(timeRemind.getTime());
            timeSetUpView.setUpData(time, timeRemind);
            mBinding.lnlRemindListTime.addView(timeSetUpView);
        }
    }

}