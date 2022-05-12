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

import com.example.dinote.utils.ReDesign;


public abstract class BaseFragment<VB extends ViewDataBinding> extends Fragment {
    public VB mBinding;
    protected Context mContext;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResource(), container, false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        resizeViews();
        onClickViews();
        setView();

    }

    protected abstract int getLayoutResource();

    protected abstract void initViews(View rootView);

    protected abstract void resizeViews();

    protected abstract void onClickViews();

    protected abstract void setView();

    void resizeView(View view, int width, int height) {
        ReDesign.resizeImage(view, width, height);
    }

}