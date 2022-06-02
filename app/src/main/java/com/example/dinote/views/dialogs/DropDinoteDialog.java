package com.example.dinote.views.dialogs;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;

import com.example.dinote.databinding.DialogRemoveDinoteBinding;

public class DropDinoteDialog extends Dialog {
    private DialogRemoveDinoteBinding dialogRemoveDinoteBinding;
    public DropDinoteDialog(@NonNull Context context) {
        super(context);
        init();
    }

    public DropDinoteDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }

    private void init() {
        dialogRemoveDinoteBinding = DialogRemoveDinoteBinding.inflate(getLayoutInflater());
        setContentView(dialogRemoveDinoteBinding.getRoot());
        onClickView();
    }

    private void onClickView() {

    }

}
