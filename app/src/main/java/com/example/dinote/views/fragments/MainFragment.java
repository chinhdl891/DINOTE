package com.example.dinote.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
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

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends BaseFragment<MainFragmentBinding> implements View.OnClickListener, DinoteAdapter.DinoteAdapterListener {
    private ViewPager vpgMainFragment;
    private PhotoAdapter photoAdapter;
    private CircleImageView circleImageView;
    private MainActivity mainActivity;
    private List<Dinote> dinoteList;
    private MainViewModel viewModel;
    private int[] photoModelList;
    private Timer mTimer;
    int i = 0;
    private static final String TAG = "MainFragment";

    @Override
    protected int getLayoutResource() {
        return R.layout.main_fragment;
    }


    @Override
    protected void initViews(View rootView) {

        mainActivity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        vpgMainFragment = rootView.findViewById(R.id.vpg_main_fragment);
        vpgMainFragment.setPageMargin(50);
        photoModelList = viewModel.image;
        photoAdapter = new PhotoAdapter(mContext, photoModelList);
        vpgMainFragment.setAdapter(photoAdapter);
        autoNextAds();
        mBinding.bgMainBackground.setOnClickListener(this);
        mBinding.rcvMainDinote.setLayoutManager(new LinearLayoutManager(mContext));
        DinoteAdapter dinoteAdapter = new DinoteAdapter();
        mBinding.rcvMainDinote.setAdapter(dinoteAdapter);
        dinoteAdapter.setDinoteList(getListDinote());
        dinoteAdapter.setDinoteAdapterListener(this);




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

    private List<Dinote> getListDinote() {
        dinoteList = DinoteDataBase.getInstance(getActivity()).dinoteDAO().getAllDinote();
        return dinoteList;
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

    }

    @Override
    protected void setView() {

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


}
