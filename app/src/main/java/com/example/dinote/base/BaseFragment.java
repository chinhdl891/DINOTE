package com.example.dinote.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.example.dinote.utils.GetDisplayInfo;


public abstract class BaseFragment<VB extends ViewDataBinding> extends Fragment {
    protected VB mBinding;
    protected Context mContext;
    protected int widthView;
    protected int heightView;
    protected int widthDisplay;
    protected int heightDisplay;
    protected int pointViewX;
    protected int pointViewY;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false);
        widthDisplay = GetDisplayInfo.listInfoDisplay(getActivity())[0];
        heightDisplay = GetDisplayInfo.listInfoDisplay(getActivity())[1];
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        resizeViews();
        onClickViews();
        setView();
        getParamView(view);

    }

    protected abstract int getLayoutResource();

    protected abstract void initViews(View rootView);

    protected abstract void resizeViews();

    protected abstract void onClickViews();

    protected abstract void setView();

    protected void getParamView(@NonNull View view) {
        widthView = view.getWidth();
        heightView = view.getHeight();
    }

    protected void getPoint(@NonNull View view) {
        pointViewX = GetDisplayInfo.locateView(view)[0];
        pointViewY = GetDisplayInfo.locateView(view)[1];

    }


}