package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.WvSettings;

/**
 * Created by yxd on 2016/5/5.
 */
public class AnquanActivity extends BaseActivity {
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
        return R.layout.activity_aqbz;
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
        String url = "http://www.ronghedai.com/curity/index.html";
        mWebView.loadUrl(url);
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
        return getString(R.string.text_aqbz);
    }
}

