package com.example.dinote.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.dinote.R;
import com.example.dinote.databinding.DialogDinoteCancelBinding;

public class BackCreateDinoteDialog extends Dialog implements View.OnClickListener {
    private DialogDinoteCancelBinding dialogDinoteCancelBinding;

    public BackCreateDinoteDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public BackCreateDinoteDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    public void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        dialogDinoteCancelBinding = DialogDinoteCancelBinding.inflate(getLayoutInflater());
        setContentView(dialogDinoteCancelBinding.getRoot());
        dialogDinoteCancelBinding.btnDialogDinoteContinue.setOnClickListener(this);
        dialogDinoteCancelBinding.btnDialogDinoteExit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_dinote_continue:
                dismiss();
                break;
            case R.id.btn_dialog_dinote_exit:
                if (diaLogCreateDinoteListener != null) {
                    diaLogCreateDinoteListener.onBack();
                    dismiss();
                }
                break;
        }
    }

    public void setDiaLogCreateDinoteListener(DiaLogCreateDinoteListener diaLogCreateDinoteListener) {
        this.diaLogCreateDinoteListener = diaLogCreateDinoteListener;
    }

    private DiaLogCreateDinoteListener diaLogCreateDinoteListener;

    public interface DiaLogCreateDinoteListener {
        void onBack();
    }
}
