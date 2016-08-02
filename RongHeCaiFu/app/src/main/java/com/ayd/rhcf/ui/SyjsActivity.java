package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.R;
/**
 * Created by gqy on 2016/2/26.
 * 收益计算Fragment；
 */
public class SyjsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }
    @Override
    protected String[] getReqTagList() {
        return null;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_syjs;
    }

    private void init() {

    }
    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
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
        return getString(R.string.text_syjs);
    }
}
