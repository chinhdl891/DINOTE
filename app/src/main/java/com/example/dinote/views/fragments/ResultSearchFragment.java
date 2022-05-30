package com.example.dinote.views.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dinote.R;
import com.example.dinote.adapter.DinoteAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentResultSearchBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.ReDesign;

import java.util.List;

public class ResultSearchFragment extends BaseFragment<FragmentResultSearchBinding> implements View.OnClickListener, DinoteAdapter.DinoteAdapterListener {
    private String search;
    private DinoteAdapter dinoteAdapter;


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
        mBinding.imvResultCancel.setOnClickListener(this);
    }

    @Override
    protected void setView() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            search = bundle.getString(Constant.KEY_SEARCH);
        }
        dinoteAdapter = new DinoteAdapter(loadData());
        dinoteAdapter.setDinoteAdapterListener(this);
        mBinding.rcvSearchResult.setAdapter(dinoteAdapter);
        mBinding.rcvSearchResult.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void setTypeView() {
        ReDesign.resizeImage(mBinding.imvResultCancel,64,64);

    }
     private List<Dinote> loadData(){
        return DinoteDataBase.getInstance(getActivity()).dinoteDAO().searchAll(search);
     }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_result_cancel:
                mainActivity.loadFragment(new SearchFragment(),Constant.SEARCH_FRAGMENT);
                break;
        }
    }

    @Override
    public void onGotoDetailDinote(Dinote dinote) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SEND_DATA_OBJ_DINOTE, dinote);
        DetailsDinoteFragment detailsDinoteFragment = new DetailsDinoteFragment();
        detailsDinoteFragment.setArguments(bundle);
        mainActivity.loadFragment(detailsDinoteFragment, Constant.DETAIL_FRAGMENT_SEARCH);
    }
}