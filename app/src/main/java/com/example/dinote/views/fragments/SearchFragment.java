package com.example.dinote.views.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dinote.R;
import com.example.dinote.adapter.DinoteAdapter;
import com.example.dinote.adapter.HistorySearchAdapter;
import com.example.dinote.adapter.TagAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentSearchBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.model.SearchHistory;
import com.example.dinote.model.Tag;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.ReDesign;
import com.google.android.flexbox.AlignItems;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;

import java.util.List;

public class SearchFragment extends BaseFragment<FragmentSearchBinding> implements View.OnClickListener, HistorySearchAdapter.HistorySearchAdapterListener, TagAdapter.TagAdapterListener, TextWatcher, DinoteAdapter.DinoteAdapterListener {
    private HistorySearchAdapter historySearchAdapter;
    private TagAdapter tagAdapter;
    private String edtSearch;
    private List<SearchHistory> searchHistoryList;
    private DinoteAdapter dinoteAdapter;
    private boolean isShow;
    private Handler handler;
    private List<Dinote> dinoteListSuggest;

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
        mBinding.tvSearchDelete.setOnClickListener(this);
        mBinding.btnSearchShowMore.setOnClickListener(this);
        mBinding.editSearchContent.addTextChangedListener(this);
    }

    @Override
    protected void setUpData() {

        mBinding.rcvSearchHistory.setLayoutManager(flexboxLayoutManager());
        historySearchAdapter = new HistorySearchAdapter(searchHistoryList());
        historySearchAdapter.setHistorySearchAdapterListener(this);
        mBinding.rcvSearchHistory.setAdapter(historySearchAdapter);
        mBinding.rcvSearchTagHot.setLayoutManager(flexboxLayoutManager());
        tagAdapter = new TagAdapter(getHotTag(), this);
        mBinding.rcvSearchTagHot.setAdapter(tagAdapter);

    }

    @Override
    protected void setTypeView() {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imv_search_show) {
            edtSearch = mBinding.editSearchContent.getText().toString().trim();
            if (edtPattern(edtSearch)) {
                if (DinoteDataBase.getInstance(getActivity()).searchDAO().numSearch(edtSearch) <= 0) {
                    new Thread(() -> DinoteDataBase.getInstance(getActivity()).searchDAO().insert(new SearchHistory(edtSearch))).start();
                }
                ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.KEY_SEARCH, edtSearch);
                resultSearchFragment.setArguments(bundle);
                mainActivity.loadFragment(resultSearchFragment, Constant.RESULT_SEARCH_FRAGMENT);

            } else {
                Toast.makeText(mContext, R.string.hint_search, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.imv_search_cancel) {
            getActivity().onBackPressed();
        } else if (view.getId() == R.id.tv_search_delete) {
            historySearchAdapter.clear();
            deleteSearchHistory();
        } else if (view.getId() == R.id.btn_search_show_more) {
            showMoreHistory(isShow);
        }

    }

    private void showMoreHistory(boolean show) {
        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(getActivity());
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        layoutManager.setAlignItems(AlignItems.FLEX_START);
        if (!show) {
            layoutManager.setMaxLine(10);
            mBinding.btnSearchShowMore.setText(R.string.autumn);
        } else {
            layoutManager.setMaxLine(3);
            mBinding.btnSearchShowMore.setText(R.string.show_more);
        }
        mBinding.rcvSearchHistory.setLayoutManager(layoutManager);
        isShow = !isShow;
    }

    private void deleteSearchHistory() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                DinoteDataBase.getInstance(getActivity()).searchDAO().delete();
            }
        }).start();
    }


    private List<SearchHistory> searchHistoryList() {
        searchHistoryList = DinoteDataBase.getInstance(getActivity()).searchDAO().getListStory();
        return searchHistoryList;
    }

    private List<Tag> getHotTag() {
        return DinoteDataBase.getInstance(getActivity()).tagDAO().listHotTag();
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

    @Override
    public void onSendData(SearchHistory searchHistory) {
        mBinding.editSearchContent.setText(searchHistory.getContentSearch());

    }

    private void gotoSearchHistoryByHasTag(String contentSearch) {
        ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.KEY_SEARCH, contentSearch);
        resultSearchFragment.setArguments(bundle);
        mainActivity.loadFragment(resultSearchFragment, Constant.RESULT_SEARCH_FRAGMENT);

    }

    @Override
    public void onSendData(Tag tag) {
        gotoSearchHistoryByHasTag(tag.getContentTag());
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    private static final String TAG = "SearchFragment";
    private void goToSearchSuggest(String search) {
        getListSuggest(search);
        if (dinoteListSuggest.size() == 0) {
            mBinding.lnlSearchEmpty.setVisibility(View.VISIBLE);
            mBinding.rltSearchSuggest.setVisibility(View.GONE);
        } else {
            if (mBinding.rcvSearchSuggest.getVisibility() == View.GONE) {
                mBinding.rcvSearchSuggest.setVisibility(View.VISIBLE);
                Log.e(TAG, "goToSearchSuggest: ");
            }
            dinoteAdapter = new DinoteAdapter(dinoteListSuggest);
            dinoteAdapter.setDinoteAdapterListener(this);
            mBinding.rcvSearchSuggest.setLayoutManager(new LinearLayoutManager(getActivity()));
            mBinding.rcvSearchSuggest.setAdapter(dinoteAdapter);
            Log.e(TAG, "adapter: ");
        }

    }

    private List<Dinote> getListSuggest(String search) {
        return dinoteListSuggest = DinoteDataBase.getInstance(getActivity()).dinoteDAO().searchList(search);
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable.toString().trim().length() > 0) {
            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    goToSearchSuggest(edtSearch.toString());
                }
            },2000);
        } else {

        }
    }

    @Override
    public void onGotoDetailDinote(Dinote dinote) {
        DetailsDinoteFragment detailsDinoteFragment = new DetailsDinoteFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SEND_DATA_OBJ_DINOTE, dinote);
        detailsDinoteFragment.setArguments(bundle);
        mainActivity.loadFragment(detailsDinoteFragment, Constant.DETAIL_FRAGMENT);
    }
}