package com.h.myapplication;

import com.h.myapplication.base.BaseActivity;
import com.h.myapplication.test.SingleTest;

public class MainActivity extends BaseActivity {


    @Override
    public int generateLayoutResID() {
//        SingleTest.getInstance("hello");
        return R.layout.activity_main;

    }


}
