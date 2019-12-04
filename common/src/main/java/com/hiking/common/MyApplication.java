package com.hiking.common;

import android.app.Application;

import com.hiking.common.skin.SkinManager;


public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.init(this);
    }
}
