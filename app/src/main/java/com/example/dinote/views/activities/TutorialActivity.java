package com.example.dinote.views.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import com.example.dinote.R;
import com.example.dinote.adapter.TutorialAdapter;
import com.example.dinote.databinding.ActivityTutorialBinding;
import com.example.dinote.myshareferences.MyDataLocal;

public class TutorialActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityTutorialBinding mBinding;
    private TutorialAdapter tutorialAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_tutorial);
        mBinding.tvTutorialSkip.setVisibility(View.INVISIBLE);
        if (MyDataLocal.getIsFirstInstall()) {
            gotoMain();
        } else {
            setUpViewpager();
            onClick();
            setUpScroll();
        }
    }

    private void onClick() {
        mBinding.tvTutorialContinue.setOnClickListener(this);
        mBinding.tvTutorialSkip.setOnClickListener(this);
        mBinding.btnTutorialGotoMain.setOnClickListener(this);
    }

    private void setUpScroll() {
        mBinding.vpgTutorialSlide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (position > 0 && position < 3) {
                    mBinding.tvTutorialSkip.setVisibility(View.VISIBLE);
                    mBinding.lnlTutorialGotoMain.setVisibility(View.GONE);
                    mBinding.clTutorialSlide.setVisibility(View.VISIBLE);
                } else if (position == 0) {
                    mBinding.tvTutorialSkip.setVisibility(View.GONE);
                    mBinding.lnlTutorialGotoMain.setVisibility(View.GONE);
                    mBinding.clTutorialSlide.setVisibility(View.VISIBLE);
                } else {
                    mBinding.lnlTutorialGotoMain.setVisibility(View.VISIBLE);
                    mBinding.clTutorialSlide.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setUpViewpager() {
        mBinding.vpgTutorialSlide.setOffscreenPageLimit(4);
        tutorialAdapter = new TutorialAdapter(this);
        mBinding.vpgTutorialSlide.setAdapter(tutorialAdapter);
        mBinding.vpgTutorialSlide.setCurrentItem(0);
        mBinding.dotTutorialFragment.setViewPager(mBinding.vpgTutorialSlide);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_tutorial_skip:
            case R.id.btn_tutorial_goto_main:
                gotoMain();
                break;
            case R.id.tv_tutorial_continue:
                int itemPage = mBinding.vpgTutorialSlide.getCurrentItem();
                if (itemPage >= 0 && itemPage < 3) {
                    itemPage++;
                    mBinding.vpgTutorialSlide.setCurrentItem(itemPage);
                }
                break;
        }
    }

    private void gotoMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}