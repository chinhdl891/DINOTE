package com.example.dinote.views.fragments;

import android.graphics.Color;
import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.dinote.R;
import com.example.dinote.adapter.ThemeAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.FragmentThemeBinding;
import com.example.dinote.myshareferences.MySharePreference;
import com.example.dinote.utils.ReDesign;

public class ThemeFragment extends BaseFragment<FragmentThemeBinding> implements View.OnClickListener {
    private ViewPager vpgThemeFragment;
    private ThemeAdapter themeAdapter;
    public static final String TAG = "ThemeFragment";


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_theme;
    }

    @Override
    protected void initViews(View rootView) {
        int theme = new MySharePreference(getActivity()).getDataTheme(ThemeFragment.TAG);
        vpgThemeFragment = rootView.findViewById(R.id.vpg_theme_change);
        vpgThemeFragment.setPageMargin(50);
        themeAdapter = new ThemeAdapter(mContext, images());
        vpgThemeFragment.setAdapter(themeAdapter);
        if (theme == 1) {
            vpgThemeFragment.setCurrentItem(theme);
        }
        vpgThemeFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    mBinding.ctlTheme.setBackgroundColor(Color.WHITE);
                } else {
                    mBinding.ctlTheme.setBackgroundColor(Color.BLACK);
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                MySharePreference mySharePreference = new MySharePreference(getContext());
                mySharePreference.pushThemeValue(TAG, position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mBinding.dot.setViewPager(vpgThemeFragment);

    }

    @Override
    protected void resizeViews() {
        ReDesign.resizeImage(vpgThemeFragment, 596, 668);
    }

    @Override
    protected void onClickViews() {
        mBinding.imvThemeCancel.setOnClickListener(this);
        mBinding.btnThemeChange.setOnClickListener(this);
    }

    @Override
    protected void setUpData() {

    }

    @Override
    protected void setTypeView() {

    }

    private int[] images() {
        int[] image = {
                R.drawable.imv_interface_light,
                R.drawable.imv_interface_night
        };
        return image;

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_theme_cancel:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                break;
            case R.id.btn_theme_change:
                if (getActivity() != null) {
                    getActivity().recreate();
                }

                break;
        }
    }
}
