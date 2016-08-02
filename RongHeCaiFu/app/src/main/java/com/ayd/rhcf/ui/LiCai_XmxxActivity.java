package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.TvInit;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;

/**
 * 理财产品详情-项目信息；
 * created by gqy on 2016/3/1
 */
public class LiCai_XmxxActivity extends BaseActivity implements ClickEventCallBack, PtrCallBack {
    private TextView mTvYhm;
    private TextView mTvXb;
    private TextView mTvYwgc;
    private TextView mTvGzcs;

    private TextView mTvZsxm;
    private TextView mTvCsrq;
    private TextView mTvYwgf;
    private TextView mTvGznx;

    private TextView mTvDybxqContent;

    private TextView mTvDywlx;
    private TextView mTvPgjz;
    private TextView mTvQdsj;
    private TextView mTvSqed;

    private Button mBtnLjtz;
    private PullToRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LiCai_Xmxx_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_li_cai_xmxx;
    }

    private void init() {
        mTvYhm = (TextView) findViewById(R.id.tv_yhm);
        mTvXb = (TextView) findViewById(R.id.tv_xb);
        mTvYwgc = (TextView) findViewById(R.id.tv_ywgc);
        mTvGzcs = (TextView) findViewById(R.id.tv_gzcs);

        mTvZsxm = (TextView) findViewById(R.id.tv_zsxm);
        mTvCsrq = (TextView) findViewById(R.id.tv_csrq);
        mTvYwgf = (TextView) findViewById(R.id.tv_ywgf);
        mTvGznx = (TextView) findViewById(R.id.tv_gznx);

        mTvDybxqContent = (TextView) findViewById(R.id.tv_dybxq_content);

        mTvDywlx = (TextView) findViewById(R.id.tv_dywlx);
        mTvPgjz = (TextView) findViewById(R.id.tv_pgjz);
        mTvQdsj = (TextView) findViewById(R.id.tv_qdsj);
        mTvSqed = (TextView) findViewById(R.id.tv_sqed);

        mBtnLjtz = (Button) findViewById(R.id.btn_ljtz);
        ClickListenerRegister.regist(mBtnLjtz, this);

        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setPullUpEnable(false);
        PtrRefreshRegister.regist(refreshLayout, this);
    }

    public void initTv(String content) {
        String s = String.format(getResources().getString(R.string.text_xmxx_yhm),"");
        TvInit.set(mTvYhm, s);
        TvInit.set(mTvXb, "");
        TvInit.set(mTvYwgc, "");
        TvInit.set(mTvGzcs, "");

        TvInit.set(mTvZsxm, "");
        TvInit.set(mTvCsrq, "");
        TvInit.set(mTvYwgf, "");
        TvInit.set(mTvGznx, "");

        TvInit.set(mTvDybxqContent, "");

        TvInit.set(mTvDywlx, "");
        TvInit.set(mTvPgjz, "");
        TvInit.set(mTvQdsj, "");
        TvInit.set(mTvSqed, "");
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_xmxx);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mBtnLjtz.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("", "");
            readyGoWithBundle(this, QrtzActivity.class, bundle);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
