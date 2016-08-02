package com.ayd.rhcf.utils;

import android.widget.TextView;

/**
 * Created by gqy on 2016/3/15.
 */
public class TvInit {

    public static void set(TextView tv, String content) {
        if (tv != null) {
            tv.setText(content == null ? "" : content);
        }
    }
}
