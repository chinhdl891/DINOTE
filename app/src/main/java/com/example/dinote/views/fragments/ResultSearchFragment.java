package com.example.dinote.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private List<Dinote> dinoteList;
    private int offset = 0;
    private boolean isLoadMore;
    private int totalItemSearch = 0;
    private static final String TAG = "ResultSearchFragment";
    private RecyclerView.LayoutManager mLayoutManager;


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
    protected void setUpData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            search = bundle.getString(Constant.KEY_SEARCH);
        }
        totalItemSearch = DinoteDataBase.getInstance(mContext).dinoteDAO().getTotalSearch(search);
        Log.e(TAG, "setUpData: " + totalItemSearch);
        dinoteList = DinoteDataBase.getInstance(mContext).dinoteDAO().searchAll(search, Constant.LIMIT_SEARCH, 0);
        dinoteAdapter = new DinoteAdapter(dinoteList);
        dinoteAdapter.setDinoteAdapterListener(this);
        mBinding.rcvSearchResult.setAdapter(dinoteAdapter);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.rcvSearchResult.setLayoutManager(mLayoutManager);
        mBinding.rcvSearchResult.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoadMore) {
                    if (((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition() == dinoteList.size() - 1) {
                        isLoadMore = true;
                        loadData();
                    }
                }

            }
        });
        mBinding.tvSearchResultContent.setText(search);
    }

    @Override
    protected void setTypeView() {
        ReDesign.resizeImage(mBinding.imvResultCancel, 64, 64);

    }

    private int page = 0;

    private void loadData() {
        offset += 50;
        if (offset >= totalItemSearch) {
            Toast.makeText(mContext, R.string.item_end_dinote, Toast.LENGTH_SHORT).show();
            isLoadMore = true;
        } else {
            List<Dinote> dinoteListNew = DinoteDataBase.getInstance(getActivity()).dinoteDAO().searchAll(search, Constant.LIMIT_SEARCH, offset);
            dinoteList.addAll(dinoteListNew);
            dinoteAdapter.notifyItemRangeInserted(offset, dinoteListNew.size());
            isLoadMore = false;
            page++;
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_result_cancel:
                mainActivity.loadFragment(new SearchFragment(), Constant.SEARCH_FRAGMENT);
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