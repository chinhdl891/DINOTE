package com.example.dinote.views.activities;

import android.Manifest;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dinote.R;
import com.example.dinote.adapter.TagAdapter;
import com.example.dinote.databases.DinoteDataBase;
import com.example.dinote.databinding.ActivityMainBinding;
import com.example.dinote.model.Tag;
import com.example.dinote.myshareferences.MySharePreference;
import com.example.dinote.utils.AppUtils;
import com.example.dinote.utils.Constant;
import com.example.dinote.utils.ReDesign;
import com.example.dinote.views.dialogs.ExitAppDialog;
import com.example.dinote.views.fragments.FavouriteFragment;
import com.example.dinote.views.fragments.MainFragment;
import com.example.dinote.views.fragments.ReminderFragment;
import com.example.dinote.views.fragments.ResultSearchFragment;
import com.example.dinote.views.fragments.SearchFragment;
import com.example.dinote.views.fragments.ThemeFragment;
import com.google.android.flexbox.FlexboxLayoutManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentManager fragmentManager;
    private ActivityMainBinding mainBinding;
    private View headerAccBinding;
    private ImageView imvMainOpenTheme, imvMainOpenFavorite, imvMainOpenRate, imvMainTag;
    private LinearLayout lnlMainOpenTheme, lnlMainOpenRate, lnlMainOpenFavorite;
    private ActionBarDrawerToggle toggle;
    private RecyclerView rcvHeadHotTag;
    private TagAdapter tagAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupTheme();
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        checkPermission();
        setupToolbar();
        loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
        onClickMainView();
        headerAccBinding = mainBinding.ngvMainAction.getHeaderView(0);
        initViewHead();
        resizeImage();
        onClickHead();
        setUpRcvTagSuggest();
    }

    private void setUpRcvTagSuggest() {
        tagAdapter = new TagAdapter(getTagListSuggest(), new TagAdapter.TagAdapterListener() {
            @Override
            public void onSendData(Tag tag) {
                mainBinding.drlMain.closeDrawer(GravityCompat.START);
                ResultSearchFragment resultSearchFragment = new ResultSearchFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Constant.KEY_SEARCH,tag.getContentTag());
                resultSearchFragment.setArguments(bundle);
                resultSearchFragment.setArguments(bundle);
                loadFragment(resultSearchFragment, Constant.RESULT_SEARCH_FRAGMENT);

            }
        });
        rcvHeadHotTag.setAdapter(tagAdapter);
        rcvHeadHotTag.setLayoutManager(new FlexboxLayoutManager(this));
    }

    private List<Tag> getTagListSuggest() {
        return DinoteDataBase.getInstance(this).tagDAO().listHotTag();
    }


    private void onClickMainView() {
        mainBinding.imvMainSearch.setOnClickListener(this);
        mainBinding.imvMainNotification.setOnClickListener(this);
    }

    private void setupTheme() {
        UiModeManager uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        int theme = new MySharePreference(this).getDataTheme(ThemeFragment.TAG);
        if (theme == 1) {
            setTheme(com.google.android.material.R.style.Theme_Material3_Dark_NoActionBar);
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        } else {
//            setTheme(com.google.android.material.R.style.Theme_Material3_Light_NoActionBar);
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
        }
    }

    private void setupToolbar() {
        setSupportActionBar(mainBinding.tlbMainAction);
        toggle = new ActionBarDrawerToggle(this, mainBinding.drlMain, mainBinding.tlbMainAction, R.string.open, R.string.close);
        mainBinding.drlMain.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void onClickHead() {
        lnlMainOpenRate.setOnClickListener(this);
        lnlMainOpenFavorite.setOnClickListener(this);
        lnlMainOpenTheme.setOnClickListener(this);
    }

    private void initViewHead() {
        imvMainOpenFavorite = headerAccBinding.findViewById(R.id.imv_head_favorite);
        imvMainOpenRate = headerAccBinding.findViewById(R.id.imv_head_rate);
        imvMainOpenTheme = headerAccBinding.findViewById(R.id.imv_head_theme);
        imvMainTag = headerAccBinding.findViewById(R.id.imv_head_tag);
        lnlMainOpenFavorite = headerAccBinding.findViewById(R.id.lnl_head_open_favorite);
        lnlMainOpenTheme = headerAccBinding.findViewById(R.id.lnl_head_openTheme);
        lnlMainOpenRate = headerAccBinding.findViewById(R.id.lnl_head_open_rate);
        rcvHeadHotTag = headerAccBinding.findViewById(R.id.rcv_head_tag_hot);

    }

    private void resizeImage() {
        ReDesign.resizeImage(imvMainOpenFavorite, 64, 64);
        ReDesign.resizeImage(imvMainOpenRate, 64, 64);
        ReDesign.resizeImage(imvMainTag, 64, 64);
        ReDesign.resizeImage(imvMainOpenTheme, 64, 64);

    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE},
                    Constant.PERMISSION_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onBackPressed() {
        if (mainBinding.drlMain.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drlMain.closeDrawer(GravityCompat.START);
        } else {
            String getTopFragment = getTopFragment().getTag();
            if (getTopFragment != null) {
                switch (getTopFragment) {
                    case Constant.MAIN_FRAGMENT:
                        onShowExitApp();
                        break;
                    case Constant.DETAIL_FRAGMENT_LOVE:
                        loadFragment(new FavouriteFragment(), Constant.FAVORITE_FRAGMENT);
                        break;
                    case Constant.DRAW_FRAGMENT:
                        super.onBackPressed();
                        break;
                    case Constant.CREATE_DINOTE_FRAGMENT:
                    case Constant.THEME_FRAGMENT:
                    case Constant.REMIND_FRAGMENT:
                    case Constant.FAVORITE_FRAGMENT:
                    case Constant.DETAIL_FRAGMENT:
                    case Constant.SEARCH_FRAGMENT:
                        mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                        loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
                        break;
                    case Constant.DETAIL_FRAGMENT_SEARCH:
                    case Constant.RESULT_SEARCH_FRAGMENT:
                        loadFragment(new SearchFragment(), Constant.SEARCH_FRAGMENT);
                        break;
                }
            }
        }
    }

    private void onShowExitApp() {
        ExitAppDialog exitAppDialog = new ExitAppDialog(this);
        exitAppDialog.setExitDialogListener(this::finish);
        exitAppDialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imv_main_search:
                loadFragment(new SearchFragment(), Constant.SEARCH_FRAGMENT);
                break;
            case R.id.imv_main_notification:
                loadFragment(new ReminderFragment(), Constant.REMIND_FRAGMENT);
                break;
            case R.id.lnl_head_open_favorite:
                loadFragment(new FavouriteFragment(), Constant.FAVORITE_FRAGMENT);
                mainBinding.drlMain.closeDrawer(GravityCompat.START);
                break;
            case R.id.lnl_head_open_rate:
                rateApp();
                mainBinding.drlMain.closeDrawer(GravityCompat.START);
                break;
            case R.id.lnl_head_openTheme:
                loadFragment(new ThemeFragment(), Constant.THEME_FRAGMENT);
                mainBinding.drlMain.closeDrawer(GravityCompat.START);
                break;
        }
    }

    private void rateApp() {
        try {
            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } catch (Exception e) {
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id" + getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void loadFragment(Fragment fragment, String tag) {
        AppUtils.hideKeyboard(this);
        if (!tag.equals(Constant.MAIN_FRAGMENT)) {
            mainBinding.drlMain.setEnabled(false);
            mainBinding.tlbMainAction.setVisibility(View.GONE);
        } else {
            mainBinding.drlMain.setEnabled(true);
        }
        switch (tag) {
            case Constant.DRAW_FRAGMENT:
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.frl_main_content, fragment, tag);
                fragmentTransaction.addToBackStack(tag);
                fragmentTransaction.commit();
                break;
            case Constant.MAIN_FRAGMENT:
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                replaceFragment(fragment, tag);
                break;
            default:
                replaceFragment(fragment, tag);
                break;
        }
    }

    private void replaceFragment(Fragment fragment, String tag) {
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frl_main_content, fragment, tag);
        fragmentTransaction.addToBackStack(tag);
        fragmentTransaction.commit();
    }


    public Fragment getTopFragment() {
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constant.PERMISSION_WRITE_EXTERNAL_STORAGE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    if (i == 0) {
                        Toast.makeText(this, R.string.write_debied, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.read_denied, Toast.LENGTH_SHORT).show();
                    }
                    checkPermission();
                }
            }
        }
    }


}