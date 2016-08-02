package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.TvInit;
import com.ayd.rhcf.view.PfProgressView;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;

/**
 * 理财产品详情-风控信息；
 * created by gqy on 2016/3/2
 */
public class LiCai_FkxxActivity extends BaseActivity implements ClickEventCallBack,
        PtrCallBack, HRCallBack {
    private TextView mTvLevel;
    private TextView mTvXyfValue;
    private TextView mTvLevel2;

    private PfProgressView pfProgressView;

    private TextView mTvJbxx;
    private TextView mTvXyzk;
    private TextView mTvDywpg;

    private TextView mTvJjnl;
    private TextView mTvWwdy;
    private TextView mTvZyqk;

    private TextView mTextRhdscContent;
    private Button mBtnLjtz;
    private PullToRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_li_cai_fkxx;
    }

    private void init() {
        mTvLevel = (TextView) findViewById(R.id.tv_level);
        mTvXyfValue = (TextView) findViewById(R.id.tv_xyf_value);
        mTvLevel2 = (TextView) findViewById(R.id.tv_level2);

        pfProgressView = (PfProgressView) findViewById(R.id.rl_pf);
        pfProgressView.setAnimProgress(25);

        mTvJbxx = (TextView) findViewById(R.id.tv_jbxx);
        mTvXyzk = (TextView) findViewById(R.id.tv_xyzk);
        mTvDywpg = (TextView) findViewById(R.id.tv_dywpg);

        mTvJjnl = (TextView) findViewById(R.id.tv_jjnl);
        mTvWwdy = (TextView) findViewById(R.id.tv_wwdy);
        mTvZyqk = (TextView) findViewById(R.id.tv_zyqk);

        mTextRhdscContent = (TextView) findViewById(R.id.text_rhdsc_content);

        mBtnLjtz = (Button) findViewById(R.id.btn_ljtz);
        ClickListenerRegister.regist(mBtnLjtz, this);
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setPullUpEnable(false);

        PtrRefreshRegister.regist(refreshLayout, this);
    }

    public void initTv(String content) {
        TvInit.set(mTvLevel, "");
        TvInit.set(mTvXyfValue, "");

        TvInit.set(mTvLevel2, "");

        TvInit.set(mTvJbxx, "");
        TvInit.set(mTvXyzk, "");

        TvInit.set(mTvDywpg, "");
        TvInit.set(mTvJjnl, "");
        TvInit.set(mTvWwdy, "");
        TvInit.set(mTvZyqk, "");

        TvInit.set(mTextRhdscContent, "");
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_fkxx);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mBtnLjtz.getId()) {
//            Bundle bundle = new Bundle();
//            bundle.putString("", "");
//            readyGoWithBundle(this, QrtzActivity.class, bundle);

            pfProgressView.setAnimProgress(15);
        }
    }
    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }
    @Override
    public void onRefresh() {
        //自动刷新；
//        refreshLayout.autoRefresh();
//        HttpProxy.getDataByGet(this, "", "", null, this, 110);
    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 响应；
     *
     * @param resultCode
     * @param responseData
     */
    @Override
    public void httpResponse(int resultCode, Object... responseData) {

    }
}
