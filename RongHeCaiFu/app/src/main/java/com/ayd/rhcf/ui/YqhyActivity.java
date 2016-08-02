package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.WvSettings;

import java.io.File;

/**
 * Created by gqy on 2016/2/26.
 * 邀请好友Fragment；
 * 改为汇付表单信息加载页
 */
public class YqhyActivity extends BaseActivity {
    private ProgressBar mProgressBar;
    private WebView mWebView;
   String body ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("YqhyActivity======onCreate");
//        body = getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE).getString("form");
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Yqhy_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_yqhy;
    }

    private void init() {
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.addJavascriptInterface(this, "jsObject");
        String url = "https://www.douban.com/note/153676755/";
        WvSettings.setting(this, mWebView, new WvSettings.IWvCallBack() {

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
        LogUtil.e(CommonUtil.getDiskCacheDir(this));
//        mWebView.loadData(body, "text/html", "utf-8");
        mWebView.loadUrl("file:///"+CommonUtil.getDiskCacheDir(this)+ File.separator+"huifu.html");
    }

    // 一定要添加注解；4.2之后；
    @JavascriptInterface
    public void jsCall(String arg) {
        LogUtil.i("jsCall调用了");
        ToastUtil.showToastLong(YqhyActivity.this, arg);
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
        return getString(R.string.text_huifu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();// 返回前一个页面
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
