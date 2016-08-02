package com.ayd.rhcf;

import android.content.Context;
import android.os.Looper;

import com.ayd.rhcf.utils.LogUtil;

/**
 * Created by gqy on 2016/2/26.
 */
public class AppUnCatchExceptionHandler implements Thread.UncaughtExceptionHandler {
    private static AppUnCatchExceptionHandler exceptionHandler;
    private static Object obj = new Object();

    private AppUnCatchExceptionHandler() {

    }

    public static AppUnCatchExceptionHandler getInstance() {
        if (exceptionHandler == null) {
            synchronized (obj) {
                exceptionHandler = new AppUnCatchExceptionHandler();
            }
        }
        return exceptionHandler;
    }

    public void init(Context context) {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        catchException(ex);
    }

    /**
     * 抓获异常；
     * @param ex
     * @return
     */
    private boolean catchException(Throwable ex) {
        if (ex == null)
            return false;
        final StackTraceElement[] stack = ex.getStackTrace();
        final String message = ex.getMessage();
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                LogUtil.e("不好意思，我在你前面抓到错误了。message = " + message);
                Looper.loop();
            }

        }.start();
        return true;
    }
}
