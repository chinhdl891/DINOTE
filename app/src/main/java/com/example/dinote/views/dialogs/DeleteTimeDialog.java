package com.example.dinote.views.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;

import androidx.annotation.NonNull;

import com.example.dinote.R;
import com.example.dinote.databinding.DialogDeleteTimeBinding;
import com.example.dinote.utils.ReDesign;

public class DeleteTimeDialog extends Dialog implements View.OnClickListener {
    private View mView;
    private DialogDeleteTimeBinding mBinding;

    public DeleteTimeDialog(@NonNull Context context) {
        super(context);
        initView();
    }

    public DeleteTimeDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        initView();
    }


    public void initView() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        mBinding = DialogDeleteTimeBinding.inflate(getLayoutInflater());
        ReDesign.resizeImage(mBinding.imvDeleteTime, 64, 64);
        mBinding.cvDeleteRemoveTime.setOnClickListener(this);
        setContentView(mBinding.getRoot());
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cv_delete_remove_time) {
            deleteTimeDialogListener.onDelete(true);
        }
    }

    private DeleteTimeDialogListener deleteTimeDialogListener;

    public void setDeleteTimeDialogListener(DeleteTimeDialogListener deleteTimeDialogListener) {
        this.deleteTimeDialogListener = deleteTimeDialogListener;
    }

    public interface DeleteTimeDialogListener {
        void onDelete(boolean delete);
    }

}
