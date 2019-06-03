package com.hiking.common.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * Log工具，根据build任务决定log级别
 * 只有ERROR级别的log才会写入文件
 * 默认路径为com.pptv.tvsports.feedback.AppLogManager#cacheLog
 *
 * @author menglu
 */
public final class TLog {

    private static final String DEFAULT_TAG = "Hello";


    private TLog() {
    }

    public static void v(String content) {
        String tag = generateTag();
        v(tag, content);
    }

    public static void v(String tag, String content) {
        v(tag, content, null);
    }

    public static void v(String tag, String content, Throwable tr) {
        if (getTagLevel() <= Log.VERBOSE) {
            Log.v(generateTag(tag), content, tr);
        }
    }

    public static void d(String content) {
        d(generateTag(), content);
    }

    public static void d(String tag, String content) {
        d(tag, content, null);
    }

    public static void d(String tag, String content, Throwable tr) {
        if (getTagLevel() <= Log.DEBUG) {
            Log.d(generateTag(tag), content, tr);
        }
    }

    public static void i(String content) {
        i(generateTag(), content);
    }

    public static void i(String tag, String content) {
        i(tag, content, null);
    }

    public static void i(String tag, String content, Throwable tr) {
        if (getTagLevel() <= Log.INFO) {
            Log.i(generateTag(tag), content, tr);
        }
    }

    public static void w(String content) {
        w(null, content);
    }

    public static void w(String tag, String content) {
        w(tag, content, null);
    }

    public static void w(String tag, String content, Throwable tr) {
        if (getTagLevel() <= Log.WARN) {
            Log.w(generateTag(tag), content, tr);
        }
    }

    public static void e(String content) {
        e(generateTag(), content);
    }

    public static void e(String tag, String content) {
        e(tag, content, null);
    }

    public static void e(String tag, String content, Throwable tr) {
        if (getTagLevel() <= Log.ERROR) {
            Log.e(generateTag(tag), content, tr);
        }
    }


    public static String generateTag(String tag) {
        return TextUtils.isEmpty(tag) ? DEFAULT_TAG : (DEFAULT_TAG + ":" + tag);

    }


    private static String generateTag() {
        // 根据log级别来决定是否要获取tag
        if (getTagLevel() == Log.ERROR) {
            return "";
        }
        StackTraceElement caller = new Throwable().getStackTrace()[2];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
        return tag;
    }

    private static int getTagLevel() {
        return Log.VERBOSE;
    }

}
