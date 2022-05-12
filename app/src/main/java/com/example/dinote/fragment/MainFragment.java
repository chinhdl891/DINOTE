package com.example.dinote.fragment;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.example.dinote.activity.MainActivity;
import com.example.dinote.R;
import com.example.dinote.adapter.PhotoAdapter;
import com.example.dinote.base.BaseFragment;
import com.example.dinote.databinding.MainFragmentBinding;
import com.example.dinote.utils.Constant;
import com.example.dinote.viewmodel.MainViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainFragment extends BaseFragment<MainFragmentBinding> implements View.OnClickListener {
    private ViewPager vpgMainFragment;
    private PhotoAdapter photoAdapter;
    private CircleImageView circleImageView;
    private MainActivity mainActivity;

    @Override
    protected int getLayoutResource() {
        return R.layout.main_fragment;
    }

    private MainViewModel viewModel;

    @Override
    protected void initViews(View rootView) {
        mainActivity = (MainActivity) getActivity();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        vpgMainFragment = rootView.findViewById(R.id.vpg_main_fragment);
        vpgMainFragment.setPageMargin(50);
        photoAdapter = new PhotoAdapter(mContext, viewModel.image);
        vpgMainFragment.setAdapter(photoAdapter);
        circleImageView = rootView.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(this);


    }

    @Override
    protected void resizeViews() {

    }

    @Override
    protected void onClickViews() {

    }

    @Override
    protected void setView() {

    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.profile_image) {
            mainActivity.loadFragment(new CreateDinoteFragment(), Constant.CREATE_DINOTE_FRAGMENT);
            mainActivity.getTopFragment();
        }
    }
}
