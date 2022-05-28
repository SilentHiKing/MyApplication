package com.hiking.taskmanager.chain

import com.hiking.taskmanager.task.RunnableTask

class ChainTask(interceptors: MutableList<Interceptor>) : Chain(interceptors), RunnableTask {

    override fun run() {
        this.process()
    }

    override fun chainEnd() {
        super.chainEnd()
        runFinished()
    }
}