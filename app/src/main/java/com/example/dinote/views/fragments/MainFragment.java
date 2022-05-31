package com.example.dinote.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.dinote.R;
import com.example.dinote.adapter.DinoteAdapter;
import com.example.dinote.adapter.PhotoAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.MainFragmentBinding;
import com.example.dinote.model.Dinote;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.ReDesign;
import com.example.dinote.viewmodel.MainViewModel;
import com.example.dinote.views.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainFragment extends BaseFragment<MainFragmentBinding> implements View.OnClickListener, DinoteAdapter.DinoteAdapterListener {
    private static final String TAG = "MainFragment";
    public static final int NUM_ITEM_LOAD_MORE = 50;
    private ViewPager vpgMainFragment;
    private PhotoAdapter photoAdapter;
    private MainActivity mainActivity;
    private List<Dinote> dinoteList;
    private MainViewModel viewModel;
    private int[] photoModelList;
    private Timer mTimer;
    private DinoteAdapter mDinoteAdapter;
    private int mOffset = 0;
    private boolean isCanLoadMore = false;
    private RecyclerView.LayoutManager mLayoutManager;
    private int totalItem;


    @Override
    protected int getLayoutResource() {
        return R.layout.main_fragment;
    }


    @Override
    protected void initViews(View rootView) {

        mainActivity = (MainActivity) getActivity();
        vpgMainFragment = rootView.findViewById(R.id.vpg_main_fragment);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mBinding.rcvMainDinote.setLayoutManager(mLayoutManager);

    }

    @Override
    protected void setUpData() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        photoModelList = viewModel.image;
        vpgMainFragment.setPageMargin(50);
        photoAdapter = new PhotoAdapter(mContext, photoModelList);
        vpgMainFragment.setAdapter(photoAdapter);
        autoNextAds();

        dinoteList = new ArrayList<>();
        totalItem = DinoteDataBase.getInstance(getActivity()).dinoteDAO().getTotalItemCount();
        dinoteList.addAll(DinoteDataBase.getInstance(getActivity()).dinoteDAO().getAllDinote(NUM_ITEM_LOAD_MORE, mOffset));
        mDinoteAdapter = new DinoteAdapter(dinoteList);
        mDinoteAdapter.setDinoteAdapterListener(this);
        mBinding.rcvMainDinote.setAdapter(mDinoteAdapter);
        mBinding.rcvMainDinote.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                if (((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition() == (dinoteList.size() - 1) && !isCanLoadMore) {
                    isCanLoadMore = true;
                    loadData();
                }
            }
        });
    }


    private void loadData() {

        if (mOffset - 50 >= totalItem) {
            isCanLoadMore = true;
        } else {
            mOffset += NUM_ITEM_LOAD_MORE;
            List<Dinote> newDataList = DinoteDataBase.getInstance(getActivity()).dinoteDAO().getAllDinote(NUM_ITEM_LOAD_MORE, mOffset);
            dinoteList.addAll(newDataList);
            mDinoteAdapter.notifyItemRangeInserted(mOffset, newDataList.size());
            isCanLoadMore = false;
        }

    }


    private void autoNextAds() {
        if (photoModelList == null || photoModelList.length == 0 || vpgMainFragment == null) {
            return;
        }

        if (mTimer == null) {
            mTimer = new Timer();

        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem = vpgMainFragment.getCurrentItem();
                        int totalItem = photoModelList.length - 1;
                        if (currentItem < totalItem) {
                            currentItem++;
                            vpgMainFragment.setCurrentItem(currentItem);
                        } else {
                            vpgMainFragment.setCurrentItem(0);
                        }
                    }
                });
            }
        }, 500, 3000);

    }


    @Override
    protected void resizeViews() {
        ReDesign.resizeImage(mBinding.bgMainBackground, 128, 128);
        ReDesign.resizeImage(mBinding.imvMainCreateDinote, 80, 80);
    }

    @Override
    protected void onClickViews() {
        mBinding.bgMainBackground.setOnClickListener(this);
        mBinding.imvMainCreateDinote.setOnClickListener(this);
        mBinding.bgMainBackground.setOnClickListener(this);

    }


    @Override
    protected void setTypeView() {

    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.bg_main_background:
            case R.id.imv_main_create_dinote:
                mainActivity.loadFragment(new CreateDinoteFragment(), Constant.CREATE_DINOTE_FRAGMENT);
                mainActivity.getTopFragment();
                break;
        }

    }

    @Override
    public void onGotoDetailDinote(Dinote dinote) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.SEND_DATA_OBJ_DINOTE, dinote);
        DetailsDinoteFragment detailsDinoteFragment = new DetailsDinoteFragment();
        detailsDinoteFragment.setArguments(bundle);
        mainActivity.loadFragment(detailsDinoteFragment, Constant.DETAIL_FRAGMENT);

    }

    @Override
    public void onStop() {
        super.onStop();
        mTimer.cancel();
        Log.e(TAG, "onStop: " );
    }

}
