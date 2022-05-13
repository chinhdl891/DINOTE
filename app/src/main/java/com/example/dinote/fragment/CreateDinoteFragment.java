package com.example.dinote.fragment;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.adapter.MotionAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.FragmentCreateDinoteBinding;
import com.example.dinote.interfaces.SendMotionListener;
import com.example.dinote.model.Motion;
import com.example.dinote.utils.EditTextUtils;
import com.example.dinote.utils.ReDesign;
import com.example.dinote.viewmodel.MotionViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreateDinoteFragment extends BaseFragment<FragmentCreateDinoteBinding> implements View.OnClickListener, MotionAdapter.EditMotionListener {

    private Motion mMotion;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_create_dinote;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMotion = (Motion) getArguments().getSerializable("obj_emoji");

        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

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
        mBinding.tvDateSelection.setOnClickListener(this);
        mBinding.lnlCrateStatus.setOnClickListener(this);
        mBinding.imvCreateCancel.setOnClickListener(this);


    }

    @Override
    protected void setView() {
        EditTextUtils.disableEditText(mBinding.edtCreateStatus);
        if (mMotion != null) {
            mBinding.imvCreateMotion.setImageResource(mMotion.getImgMotion());
            mBinding.edtCreateStatus.setText(getString(mMotion.getMotion()));
        }


    }


    @Override
    public void onClick(View view) {
        if (view == mBinding.tvDateSelection) {

            selectDate();
        } else if (view == mBinding.lnlCrateStatus) {
            getParamView(mBinding.lnlCrateStatus);
            getPoint(mBinding.lnlCrateStatus);
            if (getActivity() != null) {
                onDiaLog(getActivity());
            }
//            Intent intent = new Intent(mContext, DialogActivityMotion.class);
//            startActivityForResult(intent, Constant.REQUEST_MOTION);
        } else if (view.getId() == R.id.imv_create_cancel) {
            getActivity().onBackPressed();
        }

    }

    private void onDiaLog(@NonNull FragmentActivity context) {
        LinearLayout viewGroup = context.findViewById(R.id.cv_popup_motion);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.dialog_motion_pop_up, viewGroup, true);
        RecyclerView rcvMotion = layout.findViewById(R.id.rcv_pop_up_motion);
        rcvMotion.setLayoutManager(new GridLayoutManager(context, 3));
        MotionAdapter motionAdapter = new MotionAdapter();
        motionAdapter.setMotionList(MotionViewModel.motionList());
        rcvMotion.setAdapter(motionAdapter);
        motionAdapter.setEditMotionListener(this);

        final PopupWindow popup = new PopupWindow(context);
        popup.setContentView(layout);
        popup.setWidth((int) (0.8 * widthDisplay));
        popup.setHeight((int) (0.4 * heightDisplay));
        popup.setFocusable(true);

        int OFFSET_Y = 50;


        popup.setBackgroundDrawable(new BitmapDrawable());


        popup.showAtLocation(layout, Gravity.NO_GRAVITY, (int) (pointViewX*1.3), (int) (pointViewY*1.1));


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

//    public void handleEmotion(Motion mMotion) {
//        mBinding.imvCreateMotion.setImageResource(mMotion.getImgMotion());
//        mBinding.edtCreateStatus.setText(getString(mMotion.getMotion()));
//    }
//
    @Override
    public void onSelectMotion(Motion motion) {
        mBinding.imvCreateMotion.setImageResource(motion.getImgMotion());
        mBinding.edtCreateStatus.setText(getString(motion.getMotion()));


    }
}