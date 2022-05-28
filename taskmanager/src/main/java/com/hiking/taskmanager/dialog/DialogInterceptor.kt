package com.hiking.taskmanager.dialog

import com.hiking.taskmanager.chain.Chain
import com.hiking.taskmanager.chain.Interceptor

class DialogInterceptor(val dialog: IDialog) : Interceptor {
    lateinit var mChain: Chain
    override fun intercept(chain: Chain) {
        this.mChain = chain

        dialog.setDismissListener() {
            chain.process()
        }
        dialog.show1()
    }

}

interface IDialog {
    open fun show1()

    open fun setDismissListener(listener: () -> Unit)
}