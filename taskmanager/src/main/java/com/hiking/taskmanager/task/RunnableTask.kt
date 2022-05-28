package com.hiking.taskmanager.task

import com.hiking.taskmanager.SerialTaskManager

interface RunnableTask : Runnable {
    fun runFinished() {
        SerialTaskManager.notifyFinished(this)
    }
}