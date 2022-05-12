package com.example.dinote.fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.dinote.R;
import com.example.dinote.activity.DialogActivityMotion;
import com.example.dinote.activity.MainActivity;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.FragmentCreateDinoteBinding;
import com.example.dinote.utils.EditTextUtils;
import com.example.dinote.utils.ReDesign;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreateDenoteFragment extends BaseFragment<FragmentCreateDinoteBinding> implements View.OnClickListener {

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_create_dinote;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void resizeViews() {
        ReDesign.resizeImage(mBinding.imvCreateCancel, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextCustomText, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextEdit, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextLove, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextRemove, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTextTag, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateCancel, 64, 64);
        ReDesign.resizeImage(mBinding.imvCreateTag, 44, 48);
        ReDesign.resizeImage(mBinding.imvCreateMotion, 48, 48);
    }


    @Override
    protected void onClickViews() {
        mBinding.tvDateSelection.setOnClickListener(this::onClick);
        mBinding.lnlCrateStatus.setOnClickListener(this::onClick);
        mBinding.imvCreateCancel.setOnClickListener(this::onClick);


    }

    @Override
    protected void setView() {
        EditTextUtils.disableEditText(mBinding.edtCreateStatus);
    }

    @Override
    public void onClick(View view) {
        if (view == mBinding.tvDateSelection) {

            selectDate();
        } else if (view == mBinding.lnlCrateStatus) {
            Intent intent = new Intent(mContext, DialogActivityMotion.class);
            mContext.startActivity(intent);
        }
        else if (view.getId() == R.id.imv_create_cancel){
            getActivity().onBackPressed();
        }

    }

    private void selectDate() {
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DATE);
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                mBinding.tvDateSelection.setText(simpleDateFormat.format(calendar.getTime()));

            }
        }, year, month, day);
        datePickerDialog.show();
    }

}