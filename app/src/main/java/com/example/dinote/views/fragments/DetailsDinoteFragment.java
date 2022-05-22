package com.example.dinote.views.fragments;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import com.example.dinote.R;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.FragmentDetailsDinoteBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.model.Motion;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.EditTextUtils;
import com.example.dinote.utils.ReDesign;
import com.example.dinote.viewmodel.MotionViewModel;

import java.io.IOException;


public class DetailsDinoteFragment extends BaseFragment<FragmentDetailsDinoteBinding> implements View.OnClickListener {
    private Dinote mDinote;
    private static final String TAG = "DetailsDinoteFragment";



    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_details_dinote;
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
        ReDesign.resizeImage(mBinding.imvCreateMotion, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextAlignLeft, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextAlignRight, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateColorPicker, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextCancel, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextBolder, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextItalic, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextUnderline, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextBullet, 48, 48);
        ReDesign.resizeImage(mBinding.imvCreateTextListNumber, 48, 48);

    }

    @Override
    protected void onClickViews() {
        mBinding.tvCreateSave.setOnClickListener(this);

    }

    @Override
    protected void setView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDinote = (Dinote) bundle.getSerializable(Constant.SEND_DATA_OBJ_DINOTE);
        }
        mBinding.edtCreateContent.setText(mDinote.getContent());
        mBinding.edtCreateTitle.setText(mDinote.getTitle());
        Motion motion = MotionViewModel.motionList().get(mDinote.getMotion());
        mBinding.imvCreateMotion.setImageResource(motion.getImgMotion());
        mBinding.edtCreateStatus.setText(motion.getMotion());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), Uri.parse(mDinote.getImageUri()));

            int height = bitmap.getHeight();

            mBinding.imvCreateDrawer.getLayoutParams().height = height / 2;

            mBinding.imvCreateDrawer.setImageBitmap(bitmap);
        } catch (IOException e) {
            mBinding.imvCreateDrawer.setVisibility(View.GONE);
            mBinding.edtCreateDesDrawer.setVisibility(View.GONE);

        }

    }

    @Override
    protected void setTypeView() {
        onShowDetail();
    }

    private void onShowDetail() {
        EditTextUtils.disableEditText(mBinding.edtCreateContent);
        EditTextUtils.disableEditText(mBinding.edtCreateDesDrawer);
        EditTextUtils.disableEditText(mBinding.edtCreateTitle);
    }


    private boolean isEdit;

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_create_save:
                if (!isEdit) {
                    mBinding.tvCreateSave.setText("Lưu");
                    EditTextUtils.enableEditText(mBinding.edtCreateContent);
                    EditTextUtils.enableEditText(mBinding.edtCreateDesDrawer);
                    EditTextUtils.enableEditText(mBinding.edtCreateTitle);
                } else {
                    mBinding.tvCreateSave.setText("Cập Nhật");
                    EditTextUtils.disableEditText(mBinding.edtCreateContent);
                    EditTextUtils.disableEditText(mBinding.edtCreateDesDrawer);
                    EditTextUtils.disableEditText(mBinding.edtCreateTitle);
                }
                isEdit = !isEdit;
                Log.e(TAG, "onClick: " + isEdit );

                break;
            case R.id.imv_create_cancel:
                getActivity().onBackPressed();
                break;
        }
    }
}