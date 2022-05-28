package com.h.myapplication.test_taskmanager

import android.content.Context
import com.hiking.taskmanager.chain.Chain
import com.hiking.taskmanager.dialog.DialogInterceptor


object DialogTest {

    fun show(c: Context) {
        Chain.create(3)
            .addInterceptor(DialogInterceptor(ADialog(c)))
            .addInterceptor(DialogInterceptor(BDialog(c)))
            .addInterceptor(DialogInterceptor(CDialog(c)))
            .build().process()
    }
}