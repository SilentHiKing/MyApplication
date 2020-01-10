package com.hiking.common;

import android.app.Application;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hiking.common.skin.SkinManager;
import com.hiking.common.util.TLog;

import androidx.multidex.MultiDex;


public class MyApplication extends Application {


    public static Application sContext;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        TLog.d("density=" + this.getResources().getDisplayMetrics().density);
        TLog.d("xdpi=" + this.getResources().getDisplayMetrics().xdpi);
        TLog.d("widthPixels=" + this.getResources().getDisplayMetrics().widthPixels);
//        AutoSize.initCompatMultiProcess(this);
        SkinManager.init(this);
        initArouter();
    }

    private void initArouter() {
        if (TLog.isDebug()) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this); // As early as possible, it is recommended to initialize in the Application
    }
}
