package com.example.dinote.views.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.dinote.R;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.FragmentDrawBinding;
import com.example.dinote.utils.ReDesign;

import java.util.UUID;

import top.defaults.colorpicker.ColorPickerPopup;

public class DrawFragment extends BaseFragment<FragmentDrawBinding> implements View.OnClickListener {
    private int stokeSize = 10;
    private boolean isSelectSize;
    private String uri;

    public static DrawFragment newInstance() {

        Bundle args = new Bundle();

        DrawFragment fragment = new DrawFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_draw;
    }

    @Override
    protected void initViews(View rootView) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        mBinding.pvDrawContent.init(displayMetrics);
    }

    @Override
    protected void resizeViews() {

        ReDesign.resizeImage(mBinding.imvDrawBack, 120, 120);
        ReDesign.resizeImage(mBinding.imvDrawChangeSize, 120, 120);
        ReDesign.resizeImage(mBinding.imvDrawChangeColor, 120, 120);
        ReDesign.resizeImage(mBinding.imvDrawEraser, 120, 120);

        ReDesign.resizeImage(mBinding.imvDrawSize16, 16, 16);
        ReDesign.resizeImage(mBinding.imvDrawSize36, 36, 36);
        ReDesign.resizeImage(mBinding.imvDrawSize64, 64, 64);

        ReDesign.resizeImage(mBinding.imvDrawCancel, 64, 64);
        ReDesign.resizeImage(mBinding.imvDrawSave, 64, 64);


    }

    @Override
    protected void onClickViews() {

        mBinding.imvDrawBack.setOnClickListener(this);
        mBinding.imvDrawEraser.setOnClickListener(this);
        mBinding.imvDrawChangeColor.setOnClickListener(this);
        mBinding.imvDrawChangeSize.setOnClickListener(this);

        mBinding.imvDrawSize16.setOnClickListener(this);
        mBinding.imvDrawSize36.setOnClickListener(this);
        mBinding.imvDrawSize64.setOnClickListener(this);

        mBinding.imvDrawCancel.setOnClickListener(this);
        mBinding.imvDrawSave.setOnClickListener(this);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setTypeView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imv_draw_eraser:
                mBinding.pvDrawContent.penColor(Color.WHITE);
                break;
            case R.id.imv_draw_change_color:
                showPopChangeColor();
                break;
            case R.id.imv_draw_back:
                mBinding.pvDrawContent.back();
                break;
            case R.id.imv_draw_size_16:
                mBinding.pvDrawContent.pen(16);
                mBinding.imvDrawChangeSize.setVisibility(View.VISIBLE);
                mBinding.lnlDrawChangeSize.setVisibility(View.GONE);
                break;
            case R.id.imv_draw_size_36:
                mBinding.pvDrawContent.pen(36);
                mBinding.imvDrawChangeSize.setVisibility(View.VISIBLE);
                mBinding.lnlDrawChangeSize.setVisibility(View.GONE);
                break;
            case R.id.imv_draw_size_64:
                mBinding.pvDrawContent.pen(64);
                mBinding.imvDrawChangeSize.setVisibility(View.VISIBLE);
                mBinding.lnlDrawChangeSize.setVisibility(View.GONE);
                break;
            case R.id.imv_draw_change_size:

                mBinding.imvDrawChangeSize.setVisibility(View.GONE);
                mBinding.lnlDrawChangeSize.setVisibility(View.VISIBLE);
                break;
            case R.id.imv_draw_cancel:
                getActivity().onBackPressed();
                break;
            case R.id.imv_draw_save:
                mBinding.pvDrawContent.setDrawingCacheEnabled(true);
                uri = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), mBinding.pvDrawContent.getDrawingCache(), UUID.randomUUID() + ".png", "Dinote");
                if (sendUriListerner != null) {
                    sendUriListerner.onSendDate(uri);
                }
                getActivity().onBackPressed();

                break;
        }
    }

    private void showPopChangeColor() {
        new ColorPickerPopup.Builder(getActivity())
                .initialColor(Color.RED) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        mBinding.pvDrawContent.penColor(color);
                    }
                });
    }

    private SendUriListerner sendUriListerner;

    public void setSendUriListerner(SendUriListerner sendUriListerner) {
        this.sendUriListerner = sendUriListerner;
    }

    public interface SendUriListerner {
        void onSendDate(String uri);
    }

}