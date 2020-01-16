package com.hiking.common.util;


import android.text.TextUtils;
import android.util.Printer;

public class BlockLogPrinter implements Printer {
    TimeWatcher watcher = new TimeWatcher(BlockLogPrinter.class.getSimpleName());

    @Override
    public void println(String x) {
        if (!TextUtils.isEmpty(x)) {
            if (x.startsWith(">>>>> Dispatching to")) {
                watcher.start();
            } else if (x.startsWith("<<<<< Finished to")) {
                watcher.stop();
                if (watcher.getTotalTimeAsMillis() > 16) {
                    TLog.e(watcher.getTotalTimeAsMillis() + x);
                }
            }

        }
    }
}
