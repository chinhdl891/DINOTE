package com.example.dinote.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.dinote.R;
import com.example.dinote.databinding.DialogExitBinding;

public class ExitAppDialog extends Dialog implements View.OnClickListener {
    private DialogExitBinding mBinding;
    private Context mContext;

    public ExitAppDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ExitAppDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public void init() {
        mBinding = DialogExitBinding.inflate(getLayoutInflater());
        mBinding.btnExitExit.setOnClickListener(this);
        mBinding.btnExitContinue.setOnClickListener(this);
        setContentView(mBinding.getRoot());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit_exit:
                System.exit(0);
                break;
            case  R.id.btn_exit_continue:
                dismiss();
                break;


        }
    }
}
