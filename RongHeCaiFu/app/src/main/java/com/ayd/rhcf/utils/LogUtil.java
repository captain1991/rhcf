package com.ayd.rhcf.utils;

/**
 * Created by Stone on 2015/4/23.
 */

import android.util.Log;

public class LogUtil {
    public static final String LOG_TAG = "RongHeCaiFu";

    public static final int NONE = 0;
    public static final int ERROR_ONLY = 1;
    public static final int ERROR_WARN = 2;
    public static final int ERROR_WARN_INFO = 3;
    public static final int ERROR_WARN_INFO_DEBUG = 4;

    /**
     * 日志输出开关
     */
    private static final int LOGGING_LEVEL = ERROR_WARN_INFO_DEBUG;
//    private static final int LOGGING_LEVEL = ERROR_ONLY;

    // Errors + warnings + info + debug (default)
    public static void e(String msg) {
        if (LOGGING_LEVEL >= ERROR_ONLY)
            Log.e(LOG_TAG, msg);
    }

    public static void e(String msg, Throwable e) {
        if (LOGGING_LEVEL >= ERROR_ONLY)
            Log.e(LOG_TAG, msg, e);
    }

    public static void w(String msg) {
        if (LOGGING_LEVEL >= ERROR_WARN)
            Log.w(LOG_TAG, msg);
    }

    public static void w(String msg, Throwable e) {
        if (LOGGING_LEVEL >= ERROR_WARN)
            Log.w(LOG_TAG, msg, e);
    }

    public static void i(String msg) {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO)
            Log.i(LOG_TAG, msg);
    }

    public static void i(String msg, Throwable e) {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO)
            Log.i(LOG_TAG, msg, e);
    }

    public static void d(String msg) {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO_DEBUG)
            Log.d(LOG_TAG, msg);
    }

    public static void d(String msg, Throwable e) {
        if (LOGGING_LEVEL >= ERROR_WARN_INFO_DEBUG)
            Log.d(LOG_TAG, msg, e);
    }
}

