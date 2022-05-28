package com.h.myapplication;

import android.content.res.Configuration;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.h.myapplication.base.BaseActivity;
import com.h.myapplication.login.interceptor.GitAuthInterceptor;
import com.hiking.common.arouter.ArouterPath;

@Route(path = ArouterPath.PATH_MAIN)
public class MainActivity extends BaseActivity {


    @Override
    public int generateLayoutResID() {
        GitAuthInterceptor.addAuthInterceptor();
//        SingleTest.getInstance("hello");
        return R.layout.activity_main;


    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
