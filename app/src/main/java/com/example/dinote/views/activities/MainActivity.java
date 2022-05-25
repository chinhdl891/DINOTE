package com.example.dinote.views.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dinote.R;
import com.example.dinote.databinding.ActivityMainBinding;
import com.example.dinote.model.Motion;
import com.example.dinote.myshareferences.MySharePreference;
import com.example.dinote.utils.Constant;
import com.example.dinote.views.dialogs.ExitAppDialog;
import com.example.dinote.views.fragments.CreateDinoteFragment;
import com.example.dinote.views.fragments.FavouriteFragment;
import com.example.dinote.views.fragments.MainFragment;
import com.example.dinote.views.fragments.ReminderFragment;
import com.example.dinote.views.fragments.ThemeFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, CreateDinoteFragment.CreateDinoteListener {

    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private Motion mMotion;
    public static int LAYOUT_WIDTH = 0;
    public static int LAYOUT_HEIGHT = 0;
    private ActivityMainBinding mainBinding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        createChanelID();
        int theme = new MySharePreference(this).getData(ThemeFragment.TAG);
        if (theme == 1) {
            setTheme(com.google.android.material.R.style.Theme_Material3_Dark_NoActionBar);
        } else {
            setTheme(com.google.android.material.R.style.Theme_Material3_Light_NoActionBar);
        }
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        getInfoDisplay();
        setSupportActionBar(mainBinding.tlbMainAction);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mainBinding.drlMain, mainBinding.tlbMainAction, R.string.open, R.string.close);
        mainBinding.drlMain.addDrawerListener(toggle);
        toggle.syncState();
        mainBinding.ngvMainAction.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        MainFragment fragment = new MainFragment();
        loadFragment(fragment, Constant.MAIN_FRAGMENT);


    }

    private void createChanelID() {
        if (Build.VERSION.SDK_INT >Build.VERSION_CODES.O) {
            CharSequence name = "Dinote Chanel";
            String des  = "Chanel remind write dinote";
            int importance = NotificationManagerCompat.IMPORTANCE_HIGH;
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("dinoteId",name ,(int) importance);
            channel.setDescription(des);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, Constant.PERMISSION_WRITE_EXTERNAL_STORAGE);
        }
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

            if (getTopFragment().getTag().equals(Constant.MAIN_FRAGMENT)) {
                onShowExitApp();
            } else if (getTopFragment().getTag().equals(Constant.CREATE_DINOTE_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);

            } else if (getTopFragment().getTag().equals(Constant.DETAIL_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);

            } else if (getTopFragment().getTag().equals(Constant.DETAIL_FRAGMENT_LOVE)) {
                loadFragment(new FavouriteFragment(), Constant.FAVORITE_FRAGMENT);

            } else if (getTopFragment().getTag().equals(Constant.FAVORITE_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);

            } else if (getTopFragment().getTag().equals(Constant.DRAW_FRAGMENT)) {
                super.onBackPressed();
            } else if (getTopFragment().getTag().equals(Constant.THEME_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
            } else if (getTopFragment().getTag().equals(Constant.REMIND_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
            }


        }


    }

    private void onShowExitApp() {
        ExitAppDialog exitAppDialog = new ExitAppDialog(this);
        exitAppDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {

            case R.id.item_menu_export:
                break;
            case R.id.item_menu_lock_app:
                break;
            case R.id.item_menu_favorite:
                fragment = new FavouriteFragment();
                loadFragment(fragment, Constant.FAVORITE_FRAGMENT);
                break;
            case R.id.item_menu_rate:
                break;
            case R.id.item_menu_remind:
                loadFragment(new ReminderFragment(), Constant.REMIND_FRAGMENT);
                break;
            case R.id.item_menu_tag:
                break;
            case R.id.item_menu_theme:
                fragment = new ThemeFragment();
                loadFragment(fragment, Constant.THEME_FRAGMENT);
                break;

        }
        mainBinding.drlMain.closeDrawer(GravityCompat.START);
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
//        SavedDialog dialog = new SavedDialog(this);
//        dialog.setCallbackSaveDialog(() -> {
//
//        });
//        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constant.PERMISSION_WRITE_EXTERNAL_STORAGE) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    if (i == 0) {
                        Toast.makeText(this, "Write permission denied", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Read permission denied", Toast.LENGTH_SHORT).show();
                    }
                    checkPermission();
                }
            }
        }
    }
}