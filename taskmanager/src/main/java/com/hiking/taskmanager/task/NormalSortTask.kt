package com.hiking.taskmanager.dialog

import com.hiking.taskmanager.task.RunnableTask

abstract class NormalSerialTask : RunnableTask {
    override fun run() {
        excute()
        runFinished()
    }

    abstract fun excute()
}