package com.example.dinote.views.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dinote.R;
import com.example.dinote.databinding.ActivityMainBinding;
import com.example.dinote.model.Motion;
import com.example.dinote.utils.Constant;
import com.example.dinote.views.dialogs.SavedDialog;
import com.example.dinote.views.fragments.CreateDinoteFragment;
import com.example.dinote.views.fragments.MainFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, CreateDinoteFragment.CreateDinoteListener {


    private FragmentManager fragmentManager;
    private Motion mMotion;
    public static int LAYOUT_WIDTH = 0;
    public static int LAYOUT_HEIGHT = 0;
    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        getInfoDisplay();

        setSupportActionBar(mainBinding.tlbMainAction);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainBinding.drlMain, mainBinding.tlbMainAction, R.string.open, R.string.close);
        mainBinding.drlMain.addDrawerListener(toggle);
        toggle.syncState();
        MainFragment fragment = new MainFragment();
        loadFragment(fragment, Constant.MAIN_FRAGMENT);


    }

    private void getInfoDisplay() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        LAYOUT_HEIGHT = displayMetrics.heightPixels;
        LAYOUT_WIDTH = displayMetrics.widthPixels;
    }


    @Override
    public void onBackPressed() {
        if (mainBinding.drlMain.isDrawerOpen(GravityCompat.START)) {
            mainBinding.drlMain.closeDrawer(GravityCompat.START);
        } else {
            if (getTopFragment().getTag().equals(Constant.CREATE_DINOTE_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);

                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
            } else
                super.onBackPressed();
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()) {
            case R.id.item_menu_export:
                break;
            case R.id.item_menu_lock_app:
                break;
            case R.id.item_menu_love:
                break;
            case R.id.item_menu_rate:
                break;
            case R.id.item_menu_remind:
                break;
            case R.id.item_menu_tag:
                break;
            case R.id.item_menu_theme:
                break;

        }

        return true;
    }

    public void loadFragment(Fragment fragment, String tag) {
        if (!tag.equals(Constant.MAIN_FRAGMENT)) {
            mainBinding.tlbMainAction.setVisibility(View.GONE);
        }
        if (tag.equals(Constant.DRAW_FRAGMENT)) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frl_main_content, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else {

            if (fragment instanceof CreateDinoteFragment) {
                ((CreateDinoteFragment) fragment).setCreateDinoteListener(this);
            }

            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frl_main_content, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        }
    }

    public Fragment getTopFragment() {
        int index = getSupportFragmentManager().getBackStackEntryCount() - 1;
        FragmentManager.BackStackEntry backEntry = getSupportFragmentManager().getBackStackEntryAt(index);
        String tag = backEntry.getName();
        return getSupportFragmentManager().findFragmentByTag(tag);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && resultCode == Activity.RESULT_OK) {
            Motion motion = (Motion) data.getSerializableExtra("obj_motion");
//            Motion motion = getArguments().getParcelable("obj_emoji");
//            CreateDinoteFragment createDinoteFragment = new CreateDinoteFragment();
//            Bundle bundle = new Bundle();
//            bundle.putSerializable("obj_emoji",motion);
//            createDinoteFragment.setArguments(bundle);
//            loadFragment(createDinoteFragment,Constant.CREATE_DINOTE_FRAGMENT);
//            int sizeFragment = getSupportFragmentManager().getFragments().size();
//            Fragment f = getSupportFragmentManager().getFragments().get(sizeFragment - 1);
//            if (f != null && f instanceof CreateDinoteFragment){
//                ((CreateDinoteFragment) f).handleEmotion(motion);
//            }

        }

    }


    @Override
    public void onShowSaveComplete() {
        SavedDialog dialog = new SavedDialog(this);
        dialog.setCallbackSaveDialog(() -> {

        });
        dialog.show();
    }

}