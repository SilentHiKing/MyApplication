package com.hiking.common;

import android.app.Application;

import com.hiking.common.skin.SkinManager;


public class MyApplication extends Application {


    public static Application sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        SkinManager.init(this);
    }
}
