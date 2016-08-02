package com.ayd.rhcf.ui;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
import android.widget.Button;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.TvInit;

import java.util.concurrent.TimeUnit;

/**
 * 购买债券；
 * Created by gqy on 2016/3/2.
 */
public class GmzqActivity extends BaseActivity implements ClickEventCallBack {
    private TextView mTvZqzz;
    private TextView mTvGmjj;
    private TextView mTvKyje;
    private TextView mTvCz;
    private Button mBtnGm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    public void initTv(String content) {
        TvInit.set(mTvZqzz, "");
        TvInit.set(mTvGmjj, "");
        TvInit.set(mTvKyje, "");
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_gmzq;
    }

    private void init() {
        mTvZqzz = (TextView) findViewById(R.id.tv_zqzz);
        mTvGmjj = (TextView) findViewById(R.id.tv_gmjj);
        mTvKyje = (TextView) findViewById(R.id.tv_kyje);
        mTvCz = (TextView) findViewById(R.id.tv_cz);
        mTvCz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        ClickListenerRegister.regist(mTvCz, this);

        mBtnGm = (Button) findViewById(R.id.btn_gm);
        ClickListenerRegister.regist(mBtnGm, this);
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mTvCz.getId()) {
            ToastUtil.showToastShort(this, "充值");

//           投资成功后显示
            final NoticeDialog noticeDialog = new NoticeDialog();
            noticeDialog.setContent(Html.fromHtml("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "无操作5s后返回投资页"));
            Spanned spanned = Html.fromHtml("<font color=\"#5baff8\">恭喜投资成功!</font>");
            noticeDialog.setTitle(spanned);
            noticeDialog.setNoLabel("取消");
            Spanned spanned1 = Html.fromHtml("<font color=\"#5baff8\">投资记录</font>");
            noticeDialog.setYesLabel(spanned1);
            noticeDialog.show(getSupportFragmentManager(), "qrtz_tag");
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    noticeDialog.dismiss();
                }
            }, 5000);
            noticeDialog.setCallBack(new MyEventCallBack() {
                @Override
                public void adapterEventCallBack(Object... args) {
                    if (args != null && args.length > 0 && args[0] instanceof Integer) {
                        int position = (int) args[0];
                        if (position == 0) {
                        } else if (position == 1) {
//                        投资记录
                            Bundle bundle = new Bundle();
                            bundle.putString("", "");
                            readyGoWithBundle(GmzqActivity.this, LiCai_TzjlActivity.class, bundle);
                        }
                    }
                }
            });

        } else if (viewId == mBtnGm.getId()) {
            ToastUtil.showToastShort(this, "购买");


        }
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_gmzq);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.GMZQ_REQ_TAG};
    }

}
