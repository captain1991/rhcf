package com.ayd.rhcf.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.OwnTextWatcher;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by gqy on 2016/2/29.
 * 充值Fragment；
 */
public class CzActivity extends BaseActivity implements ClickEventCallBack {
    private EditText mEtJe;
    private TextView mTvSjdz;
    private Button mBtnCz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        mEtJe = (EditText) findViewById(R.id.et_je);
        mEtJe.addTextChangedListener(new OwnTextWatcher() {
            @Override
            public void textChange(CharSequence text) {
                String text_ = text.toString().trim();

                LogUtil.i("===========>" + text_);

                //超过小数点后2位；
                if (CommonUtil.verifyMore2NumAfterPoint(text)) {
                    int pointIndex = text_.indexOf(".");
                    mEtJe.setText(text_.substring(0, pointIndex + 3));
                    mEtJe.setSelection(mEtJe.getText().toString().length());
                }
            }
        });
        mTvSjdz = (TextView) findViewById(R.id.tv_sjdz);
        mTvSjdz.setVisibility(View.GONE);
        mBtnCz = (Button) findViewById(R.id.btn_cz);
        ClickListenerRegister.regist(mBtnCz, this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_cz;
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mBtnCz.getId()) {
            exeCz();
        }
    }

    private void exeCz() {
        String je = mEtJe.getText().toString().trim();
        LogUtil.i("je---->>" + je);

        if (CommonUtil.isEmpty(je)) {
            ToastUtil.showToastShort(this, R.string.not_allowed_null);
            return;
        }

        //数据格式的合法性验证；
        if (!CommonUtil.verifyDataFormat(je)) {
            ToastUtil.showToastShort(this, R.string.text_error_data_format);
            return;
        }

        //验证是50的倍数；?????????
        try {
            //余数；
            Double ysNumber = Double.parseDouble(je) % 50;
            if (ysNumber != 0) {
                ToastUtil.showToastShort(this, R.string.text_number_50ys);
                return;
            }
            double zsNumber = Double.parseDouble(je);

            //投资金额不能为0；
            if (zsNumber < 50.00) {
                ToastUtil.showToastShort(this, R.string.text_number_dy50);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToastShort(this, R.string.text_number_50ys);
            return;
        }

//{"device" :"android",
//        "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "m":"trust/recharge/new",
//                    "money":"20",
//                    "token":"f70ec186e2fb7d3a2f3992ef5c95da6b"
//        }}
        showWOrDismissDialog(0,"请稍等。。。");
        Map<String,String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(CzActivity.this,AppConstants.TOKEN,"");
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("m","trust/recharge/new");
        params.put("money",je);
        params.put("token",token);
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID",rhcfApp.session);
        HttpProxy.getDataByPost(CzActivity.this, AppConstants.BASE_URL, AppConstants.CZ_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if(resultCode==101){
                    if(responseData[0] instanceof JSONObject){
                        JSONObject jsonObject = (JSONObject) responseData[0];
                        showWOrDismissDialog(1, "");
                        try {
                            if(jsonObject.getInt("ret")==0){
                                String data = jsonObject.getString("data");
                                Bundle bundle = new Bundle();
                                bundle.putString("form",data);
                                readyGoWithBundle(CzActivity.this, HFTXActivity.class,bundle);

                            }else if (jsonObject.getInt("ret")== -1) {
                                CommonUtil.loginOutTime(CzActivity.this);
                                ToastUtil.showToastShort(CzActivity.this, "登录超时请重新登录");
                                Bundle bundle = new Bundle();
                                readyGoForResultWB(CzActivity.this, LoginActivityNew.class, bundle, 0);
                            } else {
                                ToastUtil.showToastShort(CzActivity.this, jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },0);
    }

    public void showWOrDismissDialog(int reqCode, String msg) {
        WaitingDialog wDialog = WaitingDialog.getInstance().setMsg(msg);
//        0 打开
        if (reqCode == 0) {
            wDialog.show(getSupportFragmentManager(), "WaitingDialog");
        }else {
            wDialog.dismiss();
        }
    }


    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_cz);
    }


    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.CZ_REQ_TAG};
    }
}
