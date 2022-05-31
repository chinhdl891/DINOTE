package com.example.dinote.views.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.UiModeManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.example.dinote.myshareferences.MyDataLocal;
import com.example.dinote.myshareferences.MySharePreference;
import com.example.dinote.reciver.RemindReceiver;
import com.example.dinote.utils.Constant;
import com.example.dinote.views.dialogs.ExitAppDialog;
import com.example.dinote.views.fragments.CreateDinoteFragment;
import com.example.dinote.views.fragments.FavouriteFragment;
import com.example.dinote.views.fragments.MainFragment;
import com.example.dinote.views.fragments.ReminderFragment;
import com.example.dinote.views.fragments.SearchFragment;
import com.example.dinote.views.fragments.ThemeFragment;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, CreateDinoteFragment.CreateDinoteListener, View.OnClickListener, ExitAppDialog.ExitDialogListener {

    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager;
    private Motion mMotion;
    public static int LAYOUT_WIDTH = 0;
    public static int LAYOUT_HEIGHT = 0;
    private ActivityMainBinding mainBinding;
    private PendingIntent pendingIntent;
    private UiModeManager uiModeManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermission();
        createChanelID();
        uiModeManager = (UiModeManager) getSystemService(Context.UI_MODE_SERVICE);
        if (!MyDataLocal.getIsFirstInstall()) {
            MyDataLocal.setInstalled();
            long timeDefault = Constant.defaultCalendar();
            Intent intent = new Intent(this, RemindReceiver.class);
            MyDataLocal.setTimeRemind(timeDefault);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                pendingIntent = PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_IMMUTABLE);
            } else {
                pendingIntent = PendingIntent.getBroadcast(this, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            int type = AlarmManager.RTC_WAKEUP;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(type, timeDefault, pendingIntent);
            } else {
                alarmManager.set(type, timeDefault, pendingIntent);
            }
        }

        int theme = new MySharePreference(this).getDataTheme(ThemeFragment.TAG);
        if (theme == 1) {
            setTheme(com.google.android.material.R.style.Theme_Material3_Dark_NoActionBar);
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_YES);
        } else {
            setTheme(com.google.android.material.R.style.Theme_Material3_Light_NoActionBar);
            uiModeManager.setNightMode(UiModeManager.MODE_NIGHT_NO);
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
        mainBinding.imvMainSearch.setOnClickListener(this);
        mainBinding.imvMainNotification.setOnClickListener(this);


    }

    private void createChanelID() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.dinote_chanel);
            String des = getString(R.string.remind_dinote);
            int importance = NotificationManagerCompat.IMPORTANCE_HIGH;
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel(getString(R.string.dinote_id), name, (int) importance);
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
            String getTopFragment = getTopFragment().getTag();
            if (getTopFragment.equals(Constant.MAIN_FRAGMENT)) {
                onShowExitApp();
            } else if (getTopFragment.equals(Constant.CREATE_DINOTE_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);

            } else if (getTopFragment.equals(Constant.DETAIL_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);

            } else if (getTopFragment.equals(Constant.DETAIL_FRAGMENT_LOVE)) {
                loadFragment(new FavouriteFragment(), Constant.FAVORITE_FRAGMENT);

            } else if (getTopFragment.equals(Constant.FAVORITE_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);

            } else if (getTopFragment.equals(Constant.DRAW_FRAGMENT)) {
                super.onBackPressed();
            } else if (getTopFragment.equals(Constant.THEME_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
            } else if (getTopFragment.equals(Constant.REMIND_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
            } else if (getTopFragment.equals(Constant.SEARCH_FRAGMENT)) {
                mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
                loadFragment(new MainFragment(), Constant.MAIN_FRAGMENT);
            } else if (getTopFragment.equals(Constant.DETAIL_FRAGMENT_SEARCH)) {
                loadFragment(new SearchFragment(), Constant.SEARCH_FRAGMENT);
            }
        }
    }

    private void onShowExitApp() {
        ExitAppDialog exitAppDialog = new ExitAppDialog(this);
        exitAppDialog.setExitDialogListener(this);
        exitAppDialog.show();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        switch (item.getItemId()) {

            case R.id.item_menu_favorite:
                fragment = new FavouriteFragment();
                loadFragment(fragment, Constant.FAVORITE_FRAGMENT);
                break;
            case R.id.item_menu_rate:
                rateApp();
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

    private void rateApp() {
        try {
            Uri uri = Uri.parse("market://details?id="+getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        } catch (Exception e) {
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id" +getPackageName());
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

    public void loadFragment(Fragment fragment, String tag) {
        hideKeyboard(this);
        if (!tag.equals(Constant.MAIN_FRAGMENT)) {
            mainBinding.tlbMainAction.setVisibility(View.GONE);
        }
        if (tag.equals(Constant.DRAW_FRAGMENT)) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frl_main_content, fragment, tag);
            fragmentTransaction.addToBackStack(tag);
            fragmentTransaction.commit();
        } else if (tag.equals(Constant.MAIN_FRAGMENT)) {
            mainBinding.tlbMainAction.setVisibility(View.VISIBLE);
            replaceFragment(fragment, tag);
        } else {
            if (fragment instanceof CreateDinoteFragment) {
                ((CreateDinoteFragment) fragment).setCreateDinoteListener(this);
            }
            replaceFragment(fragment, tag);
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
                        Toast.makeText(this, R.string.write_debied, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, R.string.read_denied, Toast.LENGTH_SHORT).show();
                    }
                    checkPermission();
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.imv_main_search) {
            loadFragment(new SearchFragment(), Constant.SEARCH_FRAGMENT);
        } else if (view.getId() == R.id.imv_main_notification) {
            loadFragment(new ReminderFragment(), Constant.REMIND_FRAGMENT);
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public void onExit() {
        this.finish();
    }
}