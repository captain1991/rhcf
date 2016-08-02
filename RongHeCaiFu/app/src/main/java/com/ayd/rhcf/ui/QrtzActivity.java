package com.ayd.rhcf.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.style.TtsSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.OwnTextWatcher;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.TvInit;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;

/**
 * 确认投资；
 * created by gqy on 2016/3/2
 */
public class QrtzActivity extends BaseActivity implements ClickEventCallBack {
    private TextView mTvYjzsy;
    private EditText mEtTzje;
    private TextView mTvKtje;
    private TextView mTvZhye;
    private TextView mTvCz;
    private Button mBtnCz;
    ScheduledExecutorService se;
    private String borrow_nid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        borrow_nid = getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE).getString("borrow_nid");
        registMyReciver();
        init();
        se = Executors.newSingleThreadScheduledExecutor();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Qrtz_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_qrtz;
    }

    private void init() {
        mTvYjzsy = (TextView) findViewById(R.id.tv_yjzsy);
        mEtTzje = (EditText) findViewById(R.id.et_tzje);

        mEtTzje.addTextChangedListener(new OwnTextWatcher() {
            @Override
            public void textChange(CharSequence text) {
                String text_ = text.toString().trim();

                LogUtil.i("===========>" + text_);

                //超过小数点后2位；
                if (CommonUtil.verifyMore2NumAfterPoint(text)) {
                    int pointIndex = text_.indexOf(".");
                    mEtTzje.setText(text_.substring(0, pointIndex + 3));
                    mEtTzje.setSelection(mEtTzje.getText().toString().length());
                }
                setYjsy();
            }
        });

        mTvKtje = (TextView) findViewById(R.id.tv_ktje);
        mTvZhye = (TextView) findViewById(R.id.tv_zhye);
        mTvCz = (TextView) findViewById(R.id.tv_cz);
        //添加下划线；
        mTvCz.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mBtnCz = (Button) findViewById(R.id.btn_cz);
        ClickListenerRegister.regist(mBtnCz, this);
        getToubiaoqian();
    }

    private void setYjsy() {
        String je = mEtTzje.getText().toString().trim();
        if (je == null || je.equals("")) {
            je = "0";
        }
        ije = Float.parseFloat(je);
        sy = (float) ((float) (nh / 100) * ije * qx / 12f);
        TvInit.set(mTvYjzsy, "" + sy);
    }

    String ktje = "";
    String zhye = "";

    private void initContent(String content) {
        String yjzsy = "";
        String ktje = getString(R.string.text_ssf_hint);
        String zhye = getString(R.string.text_ssf_hint);

        if (CommonUtil.isNotEmpty(content)) {
            yjzsy = "";
            ktje = "";
        }
        TvInit.set(mTvYjzsy, yjzsy);
        TvInit.set(mTvKtje, ktje);
        TvInit.set(mTvZhye, zhye);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.qztz);
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (mBtnCz.getId() == viewId) {
            exeTz();
        }
    }

    public void showWOrDismissDialog(int reqCode, String msg) {
        WaitingDialog wDialog = WaitingDialog.getInstance().setMsg(msg);
//        0 打开
        if (reqCode == 0) {
            wDialog.show(getSupportFragmentManager(), "WaitingDialog");
        } else {
            wDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (reciver != null) {
            unregisterReceiver(reciver);
        }
        if (se != null && !se.isShutdown()) {
            se.shutdown();
        }
    }

    private void exeTz() {
        Boolean islogin = SpUtil.getSpBooleanValueByKey(QrtzActivity.this,AppConstants.ISLOGIN,false);
        if (!islogin){
            ToastUtil.showToastShort(QrtzActivity.this, "请登录");
            Bundle bundle = new Bundle();
            readyGoWithBundle(QrtzActivity.this, LoginActivityNew.class, bundle);
            return;
        }
        String je = mEtTzje.getText().toString().trim();
        LogUtil.i("je---->>" + je);

        if (CommonUtil.isEmpty(je)) {
            ToastUtil.showToastShort(this, "请输入投资金额");
            return;
        }

        //数据格式的合法性验证；
        if (!CommonUtil.verifyDataFormat(je)) {
            ToastUtil.showToastShort(this, R.string.text_error_data_format);
            return;
        }

        if (CommonUtil.verifyNoZero(je)) {
            ToastUtil.showToastShort(this, R.string.text_number_dy0);
            return;
        }

        double kt = 0.0;
        if (!ktje.equals("")) {
            kt = Double.parseDouble(ktje);
        }

        double zh = 0.0;
        if (!zhye.equals("")) {
            zh = Double.parseDouble(zhye);
        }

        if (je == null || je.equals("")) {
            je = "0";
        }

        double jeDo = Double.parseDouble(je);

        if ( kt < jeDo||zh < jeDo) {
            ToastUtil.showToastLong(this, "请输入小于可投金额或账户余额的数值");
            return;
        }

        // 通过；
        toubiao();
//        final NoticeDialog noticeDialog = new NoticeDialog();
//        noticeDialog.setContent(Html.fromHtml("<br/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "无操作5s后返回投资页"));
//        Spanned spanned = Html.fromHtml("<font color=\"#5baff8\">恭喜投资成功!</font>");
//        noticeDialog.setTitle(spanned);
//        noticeDialog.setNoLabel("取消");
//        Spanned spanned1 = Html.fromHtml("<font color=\"#5baff8\">投资记录</font>");
//        noticeDialog.setYesLabel(spanned1);
//        noticeDialog.show(getSupportFragmentManager(), "qrtz_tag");
//        se.schedule(new Runnable() {
//            @Override
//            public void run() {
//                noticeDialog.dismiss();
//            }
//        }, 5, TimeUnit.SECONDS);
//        noticeDialog.setCallBack(new MyEventCallBack() {
//            @Override
//            public void adapterEventCallBack(Object... args) {
//                if (args != null && args.length > 0 && args[0] instanceof Integer) {
//                    int position = (int) args[0];
//                    if (position == 0) {
//                    } else if (position == 1) {
////                        投资记录
//                        Bundle bundle = new Bundle();
//                        bundle.putString("borrow_nid", borrow_nid);
//                        readyGoWithBundle(QrtzActivity.this, LiCai_TzjlActivity.class, bundle);
//                    }
//                }
//            }
//        });
    }

    float ije = 0;
    Double qx = 0D;
    float nh = 0;
    float sy = 0;

    private void getToubiaoqian() {

        Map<String, String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(QrtzActivity.this, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(QrtzActivity.this));
        params.put("query_site", "user");
        params.put("q", "appnew_ol");
        params.put("borrow_nid", borrow_nid);
        params.put("type", "get_tender_info");
        params.put("token", token);
        HttpProxy.getDataByPost(QrtzActivity.this, AppConstants.BASE_URL, AppConstants.Qrtz_REQ_TQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {

                        JSONObject resp = (JSONObject) responseData[0];
                        try {
//                            "borrow_type": "vouch", // 标类型  day 为天标 其他为月标
//                                    "borrow_period": "3",// 标类型  标期限
//                                    "borrow_account_wait": 2000, // 剩余可投金额
//                                    "borrow_apr": "10.00", // 年化率
//                                    "balance": 0 // 可用余额

                            if (resp.getInt("code") == 0) {

                                JSONObject data = resp.getJSONObject("data");

//                                月标
                                if (data.getString("borrow_type").equals("vouch")) {
                                    qx = data.getDouble("borrow_period");
                                } else {
                                    qx = 1d;
                                }
                                nh = (float) data.getDouble("borrow_apr");
                                ktje = data.getString("borrow_account_wait");
                                zhye = data.getString("balance");

                                TvInit.set(mTvKtje, data.getString("borrow_account_wait"));
                                TvInit.set(mTvZhye, data.getString("balance"));

                                setYjsy();

                            } else if (resp.getInt("code") == -1) {
                                CommonUtil.loginOutTime(QrtzActivity.this);
                                ToastUtil.showToastShort(QrtzActivity.this, "登录超时请重新登录");
                                Bundle bundle = new Bundle();
                                readyGoWithBundle(QrtzActivity.this, LoginActivityNew.class, bundle);
                            } else {
                                ToastUtil.showToastShort(QrtzActivity.this, resp.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }


            }
        }, 0);
    }

    private void toubiao() {
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "m":"trust/tender/new",
//                    "borrow_nid":"20160720001",
//                    "account":"200",
//                    "token":"f70ec186e2fb7d3a2f3992ef5c95da6b"
//        }}
        showWOrDismissDialog(0, "请稍等。。。。");
        String je = mEtTzje.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(QrtzActivity.this, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(QrtzActivity.this));
        params.put("query_site", "user");
        params.put("m", "trust/tender/new");
        params.put("borrow_nid", borrow_nid);
        params.put("account", je);
        params.put("token", token);
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID",rhcfApp.session);
        if (token.equals("")) {
            CommonUtil.loginOutTime(this);
            ToastUtil.showToastShort(QrtzActivity.this, "登录超时请重新登录");
            Bundle bundle = new Bundle();
            readyGoWithBundle(QrtzActivity.this, LoginActivityNew.class, bundle);
            showWOrDismissDialog(1, "");
            return;
        }
        HttpProxy.getDataByPost(QrtzActivity.this, AppConstants.BASE_URL, AppConstants.Qrtz_REQ_TQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                showWOrDismissDialog(1, "");
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
//                        关闭dialog
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if (resp.getInt("ret") == 0) {
                                String data = resp.getString("data");
                                Bundle bundle = new Bundle();
                                bundle.putString("form", data);
                                readyGoWithBundle(QrtzActivity.this, HFTXActivity.class, bundle);
                            } else if (resp.getInt("ret") == -1) {
                                CommonUtil.loginOutTime(QrtzActivity.this);
                                ToastUtil.showToastShort(QrtzActivity.this, "登录超时请重新登录");
                                Bundle bundle = new Bundle();
                                readyGoWithBundle(QrtzActivity.this, LoginActivityNew.class, bundle);
                            } else {
                                ToastUtil.showToastShort(QrtzActivity.this, resp.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 0);
    }

    MyReciver reciver;

    private void registMyReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("notifyReLogin");
        reciver = new MyReciver();
        registerReceiver(reciver, filter);
    }

    class MyReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            getToubiaoqian();
        }
    }


}
