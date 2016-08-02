package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.R;

import java.util.List;

/**
 * 我的理财-债转项目-操作-债权信息；
 * created by gqy on 2016/3/3
 */
public class ZqxxActivity extends BaseActivity implements ClickEventCallBack {
    private TextView mTvZrbd;
    private TextView mTvZgzrjg;
    private TextView mTvSxf;
    private EditText mTvZrje;
    private EditText mEtYzm;
    private Button mBtnGetYzm;
    private Button mBtnLjzr;
    private TextView mTvWxts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Zqxx_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_zqxx;
    }

    private void init() {
        mTvZrbd = (TextView) findViewById(R.id.tv_zrbd);
        mTvZgzrjg = (TextView) findViewById(R.id.tv_zgzrjg);
        mTvSxf = (TextView) findViewById(R.id.tv_sxf);
        mTvZrje = (EditText) findViewById(R.id.tv_zrje);
        mEtYzm = (EditText) findViewById(R.id.et_yzm);
        mBtnGetYzm = (Button) findViewById(R.id.btn_get_yzm);
        mBtnLjzr = (Button) findViewById(R.id.btn_ljzr);
        mTvWxts = (TextView) findViewById(R.id.tv_wxts);

        mTvWxts.setText(Html.fromHtml("<b>" + getString(R.string.text_wxts_title) +
                "</b><br/>&nbsp;&nbsp;&nbsp;&nbsp;" + getString(R.string.text_wxts_content1) +
                "<br/>&nbsp;&nbsp;&nbsp;&nbsp;"
                + getString(R.string.text_wxts_content2)));
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (mBtnGetYzm.getId() == viewId) {

        } else if (mBtnLjzr.getId() == viewId) {

        }
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_zqxx);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }
}
