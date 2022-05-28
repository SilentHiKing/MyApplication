package com.h.myapplication.test_taskmanager

import android.content.Context
import android.os.Bundle
import android.view.View
import com.h.myapplication.R
import com.hiking.taskmanager.dialog.BaseDialog

class CDialog(context: Context) : BaseDialog(context), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_c)
        findViewById<View>(R.id.tv_confirm)?.setOnClickListener(this)
        findViewById<View>(R.id.tv_cancel)?.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        dismiss()
    }




}