package com.example.dinote.views.fragments;

import android.view.View;

import com.example.dinote.R;
import com.example.dinote.adapter.HistorySearchAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentSearchBinding;
import com.example.dinote.model.SearchHistory;
import com.example.dinote.utils.ReDesign;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements View.OnClickListener {
    private HistorySearchAdapter historySearchAdapter;
    private String edtSearch;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_search;
    }

    @Override
    protected void initViews(View rootView) {

    }

    @Override
    protected void resizeViews() {
        ReDesign.resizeImage(mBinding.imvSearchCancel, 64, 64);
        ReDesign.resizeImage(mBinding.imvSearchShow, 64, 64);

    }

    @Override
    protected void onClickViews() {
        mBinding.imvSearchShow.setOnClickListener(this);
    }

    @Override
    protected void setView() {

    }

    @Override
    protected void setTypeView() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imv_search_show) {
            edtSearch = mBinding.editSearchContent.getText().toString();
            if (edtPattern(edtSearch)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DinoteDataBase.getInstance(getActivity()).searchDAO().insert(new SearchHistory(System.currentTimeMillis(), edtSearch));
                    }
                }).start();
            }
        }
    }

    private boolean edtPattern(String text) {
        return text.length() > 0;
    }
    pr
}