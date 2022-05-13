package com.example.dinote.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.dinote.R;
import com.example.dinote.adapter.MotionAdapter;
import com.example.dinote.databinding.ActivityDialogMotionBinding;
import com.example.dinote.interfaces.SendMotionListener;
import com.example.dinote.model.Motion;
import com.example.dinote.utils.Constant;
import com.example.dinote.viewmodel.MotionViewModel;

public class DialogActivityMotion extends AppCompatActivity implements MotionAdapter.EditMotionListener {
    private static final String TAG = "DialogActivityMotion";

    private Motion mMotion;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDialogMotionBinding motionBinding = DataBindingUtil.setContentView(this, R.layout.activity_dialog_motion);
        MotionViewModel viewModel = new ViewModelProvider(this).get(MotionViewModel.class);
        MotionAdapter motionAdapter = new MotionAdapter();
        motionBinding.rcvMotionSelect.setLayoutManager(new GridLayoutManager(this, 3));
        motionBinding.rcvMotionSelect.setAdapter(motionAdapter);
        motionAdapter.setEditMotionListener(this);
        motionAdapter.setMotionList(viewModel.motionList());
        getWindow().setLayout((int) (motionBinding.lnlPopupRcv.getLayoutParams().width), (int) (motionBinding.lnlPopupRcv.getLayoutParams().height * 1.2));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = 50;
        getWindow().setAttributes(params);


    }

    @Override
    public void onSelectMotion(Motion motion) {
        mMotion = motion;
        if (mMotion == null) {
            mMotion = new Motion(0, R.drawable.ic_motion_item_fun, R.string.funny);
        } else {

            Intent returnIntent = new Intent();
            returnIntent.putExtra("obj_motion",mMotion);
            setResult(Activity.RESULT_OK, returnIntent);

        }
        finish();

    }



}