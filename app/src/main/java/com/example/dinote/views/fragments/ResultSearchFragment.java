package com.example.dinote.views.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.dinote.R;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.FragmentSearchBinding;
import com.example.dinote.utils.Constant;

public class ResultSearchFragment extends BaseFragment<FragmentSearchBinding> implements SearchFragment.SearchFragmentListener {


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_result_search;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void resizeViews() {

    }

    @Override
    protected void onClickViews() {

    }

    @Override
    protected void setView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            Toast.makeText(mContext, bundle.getString(Constant.KEY_SEARCH), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void setTypeView() {

    }

    @Override
    public void onSearch(String search) {

    }
}