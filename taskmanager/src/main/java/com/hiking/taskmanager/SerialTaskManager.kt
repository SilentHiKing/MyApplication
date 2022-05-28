package com.hiking.taskmanager

import java.util.concurrent.ConcurrentLinkedQueue

object SerialTaskManager {
    private val queue: ConcurrentLinkedQueue<Runnable> = ConcurrentLinkedQueue<Runnable>()

    @Volatile
    private var currentRunnable: Runnable? = null

    fun equeue(runnable: Runnable?) {
        if (runnable == null) {
            return
        }
        synchronized(queue) {
            if (!queue.contains(runnable)) {
                queue.add(runnable)
            }
        }
        excute()
    }

    fun notifyFinished(runnable: Runnable) {
        if (currentRunnable != runnable) {
            queue.remove(runnable)
            println("notifyFinished : ${if (runnable != null) runnable::class.java.simpleName + "${runnable.hashCode()}" else "null"}")
        }
        queue.remove(currentRunnable)
        currentRunnable = null
        excute()

    }

    private fun excute() {
        if (currentRunnable != null) {
            return
        }
        try {
            currentRunnable = queue.first()
        } catch (e: Exception) {
            println("任务栈无数据")
        } finally {
            currentRunnable?.run()
        }
    }

}
