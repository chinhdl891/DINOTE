package com.example.dinote.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.dinote.databinding.ActivityMainBinding;

public abstract class BaseActivity  extends AppCompatActivity {
   public ActivityMainBinding mainBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this,getLayOut());
    }

    protected abstract int getLayOut();
    protected abstract void onClick();

}
