package com.ayd.rhcf;

import android.support.v4.view.ViewPager;

/**
 * Created by gqy on 2016/3/8.
 */
public class PclListenerRegister {

    interface PclCallBack {
         void callBack(int position);
    }

    public void regist(ViewPager viewPager, final PclCallBack callBack) {
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (callBack != null) {
                    callBack.callBack(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
