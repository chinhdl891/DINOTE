package com.example.dinote.views.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.dinote.R;
import com.example.dinote.databinding.TimeSetUpViewBinding;
import com.example.dinote.model.TimeRemind;

public class TimeSetUpView extends ConstraintLayout {
    private static final String TAG = "TimeSetUpView";
    private Context mContext;
    private View view;
    private TimeSetUpViewBinding mBinding;
    private TextView tvTimeSetup, tvHintTimeNotice;
    private SwitchCompat swTimeAlarmStatus;

    public TimeSetUpView(@NonNull Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public TimeSetUpView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public TimeSetUpView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.time_set_up_view, this, true);
        view.setOnLongClickListener(view -> {
            timeSetUpViewListener.onDeleteTime((Integer) getTag());
            return true;
        });
        swTimeAlarmStatus = view.findViewById(R.id.sw_tiem_on_off);
        tvHintTimeNotice = view.findViewById(R.id.tv_time_notice);
        swTimeAlarmStatus.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                timeSetUpViewListener.onSetUpStatusTime(1, mTimeRemind);

            } else {
                timeSetUpViewListener.onSetUpStatusTime(0, mTimeRemind);
            }
        });

    }

    public void setUpData(String time, TimeRemind timeRemind) {

        tvTimeSetup = view.findViewById(R.id.tv_time_list_setup);
        tvTimeSetup.setText(time);
        this.mTimeRemind = timeRemind;

        if (timeRemind.getStatus() == 1) {
            swTimeAlarmStatus.setChecked(true);
        } else {
            swTimeAlarmStatus.setChecked(false);
        }

    }

    private TimeRemind mTimeRemind;
    private TimeSetUpViewListener timeSetUpViewListener;

    public void setTimeSetUpViewListener(TimeSetUpViewListener timeSetUpViewListener) {
        this.timeSetUpViewListener = timeSetUpViewListener;
    }

    public interface TimeSetUpViewListener {
        void onDeleteTime(int tag);

        void onSetUpStatusTime(int status, TimeRemind timeRemind);

    }

    public TimeRemind getmTimeRemind() {
        return mTimeRemind;
    }
}
