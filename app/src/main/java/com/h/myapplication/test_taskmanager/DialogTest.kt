package com.h.myapplication.test_taskmanager

import android.content.Context
import com.hiking.common.util.TLog
import com.hiking.taskmanager.SerialTaskManager
import com.hiking.taskmanager.chain.Chain
import com.hiking.taskmanager.chain.ChainTask
import com.hiking.taskmanager.dialog.DialogInterceptor
import com.hiking.taskmanager.dialog.NormalSerialTask


object DialogTest {

    fun show(c: Context) {
        val task0 = object : NormalSerialTask(){
            override fun excute() {
                TLog.d("task0")
            }
        }
        val task = Chain.Companion.create(3)
            .addInterceptor(DialogInterceptor(ADialog(c)))
            .addInterceptor(DialogInterceptor(BDialog(c)))
            .addInterceptor(DialogInterceptor(CDialog(c)))
            .build(ChainTask::class.java)
        val task1 = Chain.Companion.create(3)
            .addInterceptor(DialogInterceptor(CDialog(c)))
            .addInterceptor(DialogInterceptor(BDialog(c)))
            .addInterceptor(DialogInterceptor(ADialog(c)))
            .build(ChainTask::class.java)
        val task2 = object : NormalSerialTask(){
            override fun excute() {
               TLog.d("task2")
            }
        }
        SerialTaskManager.equeue(task0)
        SerialTaskManager.equeue(task)
        SerialTaskManager.equeue(task1)
        SerialTaskManager.equeue(task2)

    }
}