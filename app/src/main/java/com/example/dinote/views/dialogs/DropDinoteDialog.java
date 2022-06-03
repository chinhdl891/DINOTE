package com.example.dinote.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.dinote.R;
import com.example.dinote.databinding.DialogRemoveDinoteBinding;

public class DropDinoteDialog extends Dialog implements View.OnClickListener {
    private DialogRemoveDinoteBinding dialogRemoveDinoteBinding;

    public DropDinoteDialog(@NonNull Context context) {
        super(context);
        init(dropDinoteDialogListener);
    }

    public DropDinoteDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init(dropDinoteDialogListener);
    }

    public void init(DropDinoteDialogListener dropDinoteDialogListener) {
        this.dropDinoteDialogListener = dropDinoteDialogListener;
        dialogRemoveDinoteBinding = DialogRemoveDinoteBinding.inflate(getLayoutInflater());
        setContentView(dialogRemoveDinoteBinding.getRoot());
        onClickView();
    }

    private void onClickView() {
        dialogRemoveDinoteBinding.btnDialogDelete.setOnClickListener(this);
        dialogRemoveDinoteBinding.btnDialogContinuesCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_dialog_delete:
                dropDinoteDialogListener.onDrop();
                break;
            case R.id.btn_dialog_continues_cancel:
                dismiss();
                break;
        }
    }
    private DropDinoteDialogListener dropDinoteDialogListener;

    public interface DropDinoteDialogListener {
        void onDrop();
    }

}
