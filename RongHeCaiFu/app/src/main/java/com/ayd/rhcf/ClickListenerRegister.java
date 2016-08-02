package com.ayd.rhcf;

import android.view.View;

/**
 * Created by gqy on 2016/2/26.
 * 点击事件注册器；
 */
public class ClickListenerRegister {

    /**
     * 注册监听；
     * @param clickView
     * @param eventCallBack
     */
    public static void regist(final View clickView, final ClickEventCallBack eventCallBack) {
        if (clickView != null) {
            clickView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (eventCallBack != null) {
                        eventCallBack.clickEventCallBack(clickView.getId());
                    }
                }
            });
        }
    }
}
