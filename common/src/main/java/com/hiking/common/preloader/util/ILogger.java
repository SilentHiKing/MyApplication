package com.hiking.common.preloader.util;


public interface ILogger {

    void showLog(boolean isShowLog);

    void debug(String message);
    void debug(String tag, String message);

    void info(String message);
    void info(String tag, String message);

    void warning(String message);
    void warning(String tag, String message);

    void error(String message);
    void error(String tag, String message);

    void throwable(Throwable th);

    String getDefaultTag();
}
