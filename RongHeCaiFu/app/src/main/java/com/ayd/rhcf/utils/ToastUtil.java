package com.ayd.rhcf.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by gqy on 2016/2/23.
 */
public class ToastUtil {

    public static void showToastShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void showToastShort(Context context, int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_SHORT).show();
    }

    public static void showToastLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    public static void showToastLong(Context context, int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_LONG).show();
    }
}
