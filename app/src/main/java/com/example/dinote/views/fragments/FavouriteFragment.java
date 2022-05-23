package com.example.dinote.views.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.dinote.R;
import com.example.dinote.adapter.DinoteAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.FragmentFavouriteBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.utils.Constant;

import java.util.List;

public class FavouriteFragment extends BaseFragment<FragmentFavouriteBinding> implements DinoteAdapter.DinoteAdapterListener {
    private List<Dinote> dinoteList;
    private static final String TAG = "FavouriteFragment";


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_favourite;
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
        mBinding.rcvFavoriteDinote.setLayoutManager(new LinearLayoutManager(getActivity()));
        DinoteAdapter dinoteAdapter = new DinoteAdapter();
        dinoteAdapter.setDinoteList(getListDinoteFavorite());
        dinoteAdapter.setDinoteAdapterListener(this);
        mBinding.rcvFavoriteDinote.setAdapter(dinoteAdapter);

    }

    @Override
    protected void setTypeView() {

    }

    private List<Dinote> getListDinoteFavorite() {
        dinoteList = DinoteDataBase.getInstance(getActivity()).dinoteDAO().getAllDinoteFavorite();
        Log.d(TAG, "getListDinoteFavorite: "+dinoteList.size());
        return dinoteList;
    }

    @Override
    public void onGotoDetailDinote(Dinote dinote) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SEND_DATA_OBJ_DINOTE, dinote);
        DetailsDinoteFragment detailsDinoteFragment = new DetailsDinoteFragment();
        detailsDinoteFragment.setArguments(bundle);
        mainActivity.loadFragment(detailsDinoteFragment, Constant.DETAIL_FRAGMENT_LOVE);

    }

}
