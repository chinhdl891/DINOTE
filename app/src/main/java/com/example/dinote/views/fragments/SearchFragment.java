package com.example.dinote.views.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.dinote.R;
import com.example.dinote.adapter.HistorySearchAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentSearchBinding;
import com.example.dinote.model.SearchHistory;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.ReDesign;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements View.OnClickListener {
    private HistorySearchAdapter historySearchAdapter;
    private String edtSearch;
    private List<SearchHistory> searchHistoryList;

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
        mBinding.imvSearchCancel.setOnClickListener(this);
    }

    @Override
    protected void setView() {

        mBinding.rcvSearchHistory.setLayoutManager(flexboxLayoutManager());
        historySearchAdapter = new HistorySearchAdapter(searchHistoryList());
        mBinding.rcvSearchHistory.setAdapter(historySearchAdapter);
    }

    @Override
    protected void setTypeView() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imv_search_show) {
            edtSearch = mBinding.editSearchContent.getText().toString();
            if (edtPattern(edtSearch)) {
                new Thread(() -> DinoteDataBase.getInstance(getActivity()).searchDAO().insert(new SearchHistory(System.currentTimeMillis(), edtSearch))).start();
                mainActivity.loadFragment(new ResultSearchFragment(), Constant.RESULT_SEARCH_FRAGMENT);
                searchFragmentListener.onSearch(edtSearch);
            } else {
                Toast.makeText(mContext, R.string.hint_search, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.imv_search_cancel) {
            getActivity().onBackPressed();
        }

    }


    private List<SearchHistory> searchHistoryList() {
        searchHistoryList = DinoteDataBase.getInstance(getActivity()).searchDAO().getListStory();
        return searchHistoryList;
    }

    private FlexboxLayoutManager flexboxLayoutManager() {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        layoutManager.setMaxLine(3);
        return layoutManager;

    }

    private boolean edtPattern(String text) {
        return text.length() > 0;
    }
    private SearchFragmentListener searchFragmentListener;

    public void setSearchFragmentListener(SearchFragmentListener searchFragmentListener) {
        this.searchFragmentListener = searchFragmentListener;
    }

    public interface SearchFragmentListener {

        void onSearch(String search);

    }
}