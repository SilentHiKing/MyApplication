package com.hiking.taskmanager.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle

open class BaseDialog(context: Context) : AlertDialog(context), IDialog {
    override fun show1() {
        this.show()
    }

    override fun setDismissListener(listener: () -> Unit) {
        setOnDismissListener() {
            listener.invoke()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val density = context.resources.displayMetrics.density
        window?.attributes?.width =
            context.resources.displayMetrics.widthPixels - (20 * density * 2).toInt()
        window?.attributes?.height = context.resources.displayMetrics.heightPixels- (50 * density * 2).toInt()

    }


}