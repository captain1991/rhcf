package com.ayd.rhcf.ui;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.bean.BorrowBean;
import com.ayd.rhcf.bean.TestBean;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.TvInit;
import com.ayd.rhcf.view.AnimProgressBar;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;
import com.ayd.rhcf.view.pulltorefresh.PullableScrollView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 理财产品详情页；
 * created by gqy on 2016/3/1
 */
public class LccpDetailActivity extends BaseActivity implements ClickEventCallBack, PtrCallBack {
    private TextView mTvXmName;

    private TextView mTvXmze;
    private TextView mTvNhsy;

    private AnimProgressBar mProgressbar;
    private TextView mProgressValue;

    private TextView mTvXmqx;
    private TextView mTvHkfs;
    private TextView mTvSysj;
    private TextView mTTSysj;
    private TextView mTvQtje;

    private LinearLayout lltQtje;
    private LinearLayout lltSysj;

    private LinearLayout mPanelXmxx;
    private LinearLayout mPanelFkxx;
    private LinearLayout mPanelTzjl;
    private LinearLayout mPanelHkjh;
    private PullToRefreshLayout refreshLayout;

    private Button mBtnLjtz;
    private ScheduledExecutorService ses;
    private static int sysjTimeIns ; //剩余时间；

    private BorrowBean testBean;

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LccpDetail_REQ_TAG};
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testBean = (BorrowBean) getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE).get(AppConstants.BUNDLE_BEAN);
        init();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    private void updateTimeLabel() {
        sysjTimeIns = sysjTimeIns-1;
        if (sysjTimeIns <= 0) {
            sysjTimeIns = 0;
        }
        CommonUtil.updateTimeLabel(mTvSysj, sysjTimeIns);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_lccp_detail;
    }

    // 进度更新的Handler；
    private Handler progressUpdateHandler = new Handler();

    private void init() {
        if(testBean!=null)
        sysjTimeIns = (int) (Long.parseLong(testBean.getBorrow_end_time())-testBean.getNow_time());
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);
        refreshLayout.setPullUpEnable(false);
        refreshLayout.setPullDownEnable(false);

        mTvXmName = (TextView) findViewById(R.id.tv_xm_name);
        mTvXmze = (TextView) findViewById(R.id.tv_xmze);
        mTvNhsy = (TextView) findViewById(R.id.tv_nhsy);

        mProgressValue = (TextView) findViewById(R.id.progress_value);
        mProgressbar = (AnimProgressBar) findViewById(R.id.progressbar);
        mProgressbar.setTvProgress(mProgressValue);
        if(testBean!=null)
        progressUpdateHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mProgressbar.setCurAnimProgress((int) testBean.getBorrow_account_scale());
            }
        }, 500);

        mTvXmqx = (TextView) findViewById(R.id.tv_xmqx);
        mTvHkfs = (TextView) findViewById(R.id.tv_hkfs);
        mTvSysj = (TextView) findViewById(R.id.tv_sysj);
        mTTSysj = (TextView) findViewById(R.id.tt_sysj);
        mTvQtje = (TextView) findViewById(R.id.tv_qtje);

        lltSysj = (LinearLayout) findViewById(R.id.llt_sysj);
        lltQtje = (LinearLayout) findViewById(R.id.llt_qtje);

        mPanelXmxx = (LinearLayout) findViewById(R.id.panel_xmxx);
        ClickListenerRegister.regist(mPanelXmxx, this);
        mPanelFkxx = (LinearLayout) findViewById(R.id.panel_fkxx);
        ClickListenerRegister.regist(mPanelFkxx, this);
        mPanelFkxx.setVisibility(View.GONE);

        mPanelTzjl = (LinearLayout) findViewById(R.id.panel_tzjl);
        ClickListenerRegister.regist(mPanelTzjl, this);
//        mPanelTzjl.setVisibility(View.INVISIBLE);

        mPanelHkjh = (LinearLayout) findViewById(R.id.panel_hkjh);
        ClickListenerRegister.regist(mPanelHkjh, this);
        mPanelHkjh.setVisibility(View.GONE);

        mBtnLjtz = (Button) findViewById(R.id.btn_ljtz);
        ClickListenerRegister.regist(mBtnLjtz, this);
        updateTimeLabel();
        initTv();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            updateTimeLabel();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
//        有问题
        if(ses == null) {
            ses = Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {

                    // 运行在非UI线程中；
                    handler.sendEmptyMessage(0x1122);
                }
            }, 1, 1, TimeUnit.SECONDS);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void initTv() {
        if(testBean!=null) {
            TvInit.set(mTvXmName, testBean.getName());

            TvInit.set(mTvXmze, testBean.getAccount());
            TvInit.set(mTvNhsy, testBean.getBorrow_apr());
            TvInit.set(mProgressValue, "");

            TvInit.set(mTvXmqx, testBean.getBorrow_period_name());
//            TvInit.set(mTvHkfs, testBean.getBorrow_style()=="month"? "按月付息到期还本":"按天付息");
            TvInit.set(mTvHkfs, testBean.getRepay_type());
            TvInit.set(mTvQtje, testBean.getTender_account_min().equals("0") ? "无限制" : testBean.getTender_account_min()+"元");
            TvInit.set(mTvSysj, "");
//            是否开启进度条 0 不开启
            if (testBean.getCount_down().equals("0")) {
                goneOrShowDjs(View.GONE);
            } else {
                goneOrShowDjs(View.VISIBLE);
            }
        }
    }

    public void goneOrShowDjs(int i){
        lltSysj.setVisibility(i);
        if(i == View.GONE){
            lltQtje.setVisibility(View.VISIBLE);
        }else {
            lltQtje.setVisibility(View.GONE);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mPanelXmxx.getId()) {

            if(testBean==null){
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("borrow_nid",testBean.getBorrow_nid());
            /*判断跳到哪一个标详情页*/
//            1465228800
            if(Long.parseLong(testBean.getTender_time())>=1465228800 ) {
                readyGoWithBundle(this, LiCai_XmxxActivityNew.class, bundle);
            }else {
                readyGoWithBundle(this, LiCai_XmxxActivityOld.class, bundle);
            }
        } else if (viewId == mPanelFkxx.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("", "");
            readyGoWithBundle(this, LiCai_FkxxActivity.class, bundle);
        } else if (viewId == mPanelTzjl.getId()) {
            if(testBean==null){
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putString("borrow_nid", testBean.getBorrow_nid());

            readyGoWithBundle(this, LiCai_TzjlActivity.class, bundle);
        } else if (viewId == mPanelHkjh.getId()) {
            Bundle bundle = new Bundle();
            bundle.putString("", "");
            readyGoWithBundle(this, HkjhActivity.class, bundle);
        } else if (viewId == mBtnLjtz.getId()) {
            if(!SpUtil.getSpBooleanValueByKey(this,AppConstants.ISLOGIN,false)){
                Bundle bundle = new Bundle();
                readyGoWithBundle(this, LoginActivityNew.class, bundle);
            }else {
                Bundle bundle = new Bundle();
                bundle.putString("borrow_nid", testBean.getBorrow_nid());
                readyGoWithBundle(this, QrtzActivity.class, bundle);
            }
        }
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_cpxx);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    protected void onDestroy() {
        if (ses != null && !ses.isShutdown()) {
            ses.shutdown();
        }
        super.onDestroy();
    }
}
