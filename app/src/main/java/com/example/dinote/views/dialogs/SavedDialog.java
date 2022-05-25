package com.example.dinote.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.dinote.databinding.ShowDialogSaveCompleteBinding;
import com.example.dinote.utils.ReDesign;

public class SavedDialog extends Dialog {
    private ShowDialogSaveCompleteBinding mBinding;

    public SavedDialog(@NonNull Context context) {
        super(context);
        initViews();
    }

    public SavedDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected SavedDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        initViews();
    }

    private void initViews() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mBinding = ShowDialogSaveCompleteBinding.inflate(LayoutInflater.from(getContext()));
        setContentView(mBinding.getRoot());
        resizeViews();
        onClickViews();
    }

    private void resizeViews() {
        ReDesign.resizeImage(mBinding.imvCompleteDialog,180,180);

    }

    private void onClickViews() {
        mBinding.btnSaveDialog.setOnClickListener(view -> {
            dismiss();
            if (callbackSaveDialog != null) {
                callbackSaveDialog.onClickSaved();
            }
        });
    }

    public interface CallbackSaveDialog {
        void onClickSaved();
    }

    public CallbackSaveDialog callbackSaveDialog;

    public void setCallbackSaveDialog(CallbackSaveDialog callbackSaveDialog) {
        this.callbackSaveDialog = callbackSaveDialog;
    }
}
