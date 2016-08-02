package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.WvSettings;

/**
 * Created by gqy on 2016/2/26.
 * 关于我们；
 */
public class GywmActivity extends BaseActivity {
    private ProgressBar mProgressBar;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gywm;
    }

    private void init() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (WebView) findViewById(R.id.webView);
        WvSettings.setting(this,mWebView, new WvSettings.IWvCallBack() {

                    @Override
                    public void onProgressChanged(int newProgress) {
                        if (newProgress == 100) {
                            mProgressBar.setVisibility(View.GONE);
                        } else {
                            mProgressBar.setVisibility(View.VISIBLE);
                            mProgressBar.setProgress(newProgress);
                        }
                    }
                }
        );

        //此方法用在4.2以上系统并加上@JavascriptInterface注解时才具有安全性
//        mWebView.addJavascriptInterface(new JsCall(), "jsObject");
        String url = "http://www.ronghedai.com/aboutat/index.html";
//        mWebView.loadUrl("file:///android_asset/jstest1.html");
        mWebView.loadUrl(url);
    }
    Handler mHandler = new Handler(){};
    public class JsCall {

        // 一定要添加注解；4.2之后；
        @android.webkit.JavascriptInterface
        public void jsCall(String arg) {
            ToastUtil.showToastLong(GywmActivity.this, arg);
            LogUtil.i("jsCall调用了");
            mHandler.post(new Runnable() {
                public void run() {
                    // 此处调用 HTML 中的javaScript 函数
//                    mWebView.loadUrl("javaScript:changeText()");
                    LogUtil.i("handler调用了");
                }
            });
        }
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_gywm);
    }
}
