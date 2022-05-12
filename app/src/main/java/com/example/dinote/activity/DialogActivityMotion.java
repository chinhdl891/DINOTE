package com.example.dinote.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.example.dinote.R;
import com.example.dinote.adapter.MotionAdapter;
import com.example.dinote.base.BaseActivity;
import com.example.dinote.databinding.ActivityDialogMotionBinding;
import com.example.dinote.databinding.ActivityMainBinding;
import com.example.dinote.viewmodel.MotionViewModel;

public class DialogActivityMotion extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dialog_motion);
        ActivityDialogMotionBinding motionBinding = DataBindingUtil.setContentView(this,R.layout.activity_dialog_motion);
        
        MotionViewModel viewModel =new ViewModelProvider(this).get(MotionViewModel.class);
        MotionAdapter motionAdapter = new MotionAdapter();
        motionBinding.rcvMotionSelect.setLayoutManager(new GridLayoutManager(this,3));
        motionBinding.rcvMotionSelect.setAdapter(motionAdapter);
        motionAdapter.setMotionList(viewModel.motionList());
        getWindow().setLayout((int) (motionBinding.lnlPopupRcv.getLayoutParams().width),(int) (motionBinding.lnlPopupRcv.getLayoutParams().height*1.2));
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity= Gravity.CENTER;
        params.x = 0;
        params.y = 50;
        getWindow().setAttributes(params);


    }
}