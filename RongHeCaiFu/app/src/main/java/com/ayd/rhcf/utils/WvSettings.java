package com.ayd.rhcf.utils;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.ui.NoticeDialog;
import com.ayd.rhcf.ui.WaitingDialog;

/**
 * Created by gqy on 2016/3/14.
 */
public class WvSettings {

    public static abstract class IWvCallBack {
        public abstract void onProgressChanged(int newProgress);
    }

    public static void setting(final FragmentActivity activity,final WebView mWebView, final IWvCallBack callBack) {
        mWebView.setInitialScale(100);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //设置WebView可触摸放大缩小
        webSettings.setBuiltInZoomControls(true);
        //支持缩放
        webSettings.setSupportZoom(true);

        webSettings.setDefaultTextEncodingName("utf-8");
//      webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
        webSettings.setLoadWithOverviewMode(true);//自适应屏幕：

        webSettings.setAllowFileAccess(true);
//        webSettings.setAppCacheEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setDatabaseEnabled(true);

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return false;
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
               LogUtil.e("jsAlert===========");
                NoticeDialog dialog = new NoticeDialog();
                dialog.setContent(message);
                dialog.setYesLabel("确定");
                dialog.setNoLabel("返回");
                dialog.show(activity.getSupportFragmentManager(), "webView_notify");
                dialog.setCallBack(new MyEventCallBack() {
                    @Override
                    public void adapterEventCallBack(Object... args) {

                        if(args[0] instanceof Integer){
//                        No
                            if(args[0] == 0){
                            }else {
//                        yes
                            }
                            activity.finish();
                        }
                    }
                });
                return true;
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (callBack != null) {
                    callBack.onProgressChanged(newProgress);
                }
            }
        });
    }
}
