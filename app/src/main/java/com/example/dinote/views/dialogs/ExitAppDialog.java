package com.example.dinote.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.dinote.R;
import com.example.dinote.databinding.DialogExitBinding;
import com.example.dinote.utils.ReDesign;

public class ExitAppDialog extends Dialog implements View.OnClickListener {
    private DialogExitBinding mBinding;

    public ExitAppDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public ExitAppDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mBinding = DialogExitBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        mBinding.btnExitExit.setOnClickListener(this);
        mBinding.btnExitContinue.setOnClickListener(this);
        ReDesign.resizeImage(mBinding.ivmExit, 900, 900);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_exit_exit:
                if (exitDialogListener != null) {
                    dismiss();
                    exitDialogListener.onExit();
                }
                break;
            case R.id.btn_exit_continue:
                dismiss();
                break;


        }
    }

    public ExitDialogListener exitDialogListener;

    public void setExitDialogListener(ExitDialogListener exitDialogListener) {
        this.exitDialogListener = exitDialogListener;
    }

    public interface ExitDialogListener {
        void onExit();
    }
}
