package com.ayd.rhcf;

import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;

/**
 * 刷新注册器；
 * Created by gqy on 2016/2/26.
 */
public class PtrRefreshRegister {
    /**
     * 注册；
     *
     * @param refreshLayout
     * @param refreshCallBack
     */
    public static void regist(PullToRefreshLayout refreshLayout, final PtrCallBack refreshCallBack) {
        if (refreshLayout != null) {
            refreshLayout.setOnPullListener(new PullToRefreshLayout.OnPullListener() {
                @Override
                public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
                    if (refreshCallBack != null) {
                        refreshCallBack.onRefresh();
                    }
                }

                @Override
                public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {
                    if (refreshCallBack != null) {
                        refreshCallBack.onLoadMore();
                    }
                }
            });
        }
    }
}
