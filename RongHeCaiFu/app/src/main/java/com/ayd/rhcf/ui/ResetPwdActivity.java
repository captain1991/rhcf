package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 密码重置；
 * created by gqy on 2016/2/23
 */
public class ResetPwdActivity extends BaseActivity implements ClickEventCallBack {
    private EditText mEtNewPwd1;
    private EditText mEtNewPwd2;
    private Button mBtnOk;
    private EditText et_yzm;
    private Button btn_get_yzm;
    private String phoneNumber;
    private String tupianYzm;
    private View content_layout;
    private ProgressBar progressBar;
    private TextView tv_yy_yzm;
    private ScheduledExecutorService se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        暂时没有用
//        SMSSDK.initSDK(this, "131bc1c8fe31c", "6fe2b44d94eb570daf197c527a6283f2");
//        SMSSDK.registerEventHandler(mhandler);
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE);
        phoneNumber = bundle.getString(AppConstants.BUNDLE_PHONE);
        tupianYzm = bundle.getString(AppConstants.VALICODE);
        LogUtil.e(phoneNumber);
        LogUtil.e(tupianYzm);
        se = Executors.newSingleThreadScheduledExecutor();

        assignViews();

        se.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
             timHandler.sendEmptyMessage(1);
            }
        }, 0, 1, TimeUnit.SECONDS);

    }

    Handler timHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if(time<=0){
                time = 60;
                btn_get_yzm.setText("获取验证码");
                btn_get_yzm.setClickable(true);
                if(se!=null&&!se.isShutdown()){
                    se.shutdown();
                }
            }else {
                btn_get_yzm.setClickable(false);
                btn_get_yzm.setText(time + "秒后重新获取");
            }
        }
    };

    int time = 60;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_reset_pwd;
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.ResetPwd_REQ_TAG,AppConstants.ResetPwd_GETCODE_TAG,AppConstants.ResetPwd_VERIFY_CODE_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    private void assignViews() {
        content_layout = findViewById(R.id.content_layout);
        mEtNewPwd1 = (EditText) findViewById(R.id.et_new_pwd1);
        mEtNewPwd2 = (EditText) findViewById(R.id.et_new_pwd2);
        mBtnOk = (Button) findViewById(R.id.btn_ok);
        ClickListenerRegister.regist(mBtnOk, this);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        btn_get_yzm = (Button) findViewById(R.id.btn_get_yzm);
        ClickListenerRegister.regist(btn_get_yzm, this);
        progressBar= (ProgressBar) findViewById(R.id.submit_progressbar);
        tv_yy_yzm = (TextView) findViewById(R.id.tv_yy_yzm);
        tv_yy_yzm.setVisibility(View.GONE);
//        tv_yy_yzm.setText(Html.fromHtml("如果未收到短信，您可以<font color=\"#3b80b2\">" + "获取语音验证码" + "</font>"));
        ClickListenerRegister.regist(tv_yy_yzm, this);
    }


    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == R.id.btn_ok) {
            String newPwd1 = mEtNewPwd1.getText().toString().trim();
            String newPwd2 = mEtNewPwd2.getText().toString().trim();
            String yzmString = et_yzm.getText().toString().trim();
            LogUtil.i("newPwd1==>" + newPwd1 + ",newPwd2=>" + newPwd2);

            if (CommonUtil.isEmpty(newPwd1) || CommonUtil.isEmpty(newPwd2)||CommonUtil.isEmpty(yzmString)) {
                ToastUtil.showToastShort(this, R.string.pwd_not_null);
                return;
            }
//            progressBar.setVisibility(View.VISIBLE);
//
            verifyCode();

        }else if(viewId == R.id.btn_get_yzm){
//            重新获取验证码
            readyGo(ResetPwdActivity.this,FindBackPwdActivity.class);
            finish();
        }else if(viewId == R.id.tv_yy_yzm){
        }
    }


    private void submitPwd() {
        // 提交；
        readyGo(this,LoginActivityNew.class);
    }

//    验证手机验证码
    private void verifyCode(){
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "q":"getpwd",
//                    "type":"phone_code",
//                    "phonevalid":"htsdot",
//                    "phone":"15212414880"
//        }}
        waitingDialog(0,"请稍等...");
        String yzmString = et_yzm.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("q","getpwd");
        params.put("type","phone_code");
        params.put("phonevalid",yzmString);
        params.put("phone",phoneNumber);
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID", rhcfApp.session);
        HttpProxy.getDataByPost(this,AppConstants.BASE_URL,AppConstants.ResetPwd_VERIFY_CODE_TAG,params,new HRCallBack(){
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                waitingDialog(1,"");
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if(resp.getInt("code") == 0){
//                                短信验证码验证通过
                                resetPWD();
//                                evHandler.sendEmptyMessage(1);
                            }else {
//                                evHandler.sendEmptyMessage(0);
                                ToastUtil.showToastShort(ResetPwdActivity.this,resp.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },0);

    }
//重置密码
    private void resetPWD(){
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "q":"getpwd",
//                    "type":"changepwd",
//                    "password":"12345aa",
//                    "comfirm_password":"12345aa",
//                    "userid":"346"
//        }}
        String newPwd1 = mEtNewPwd1.getText().toString().trim();
        String newPwd2 = mEtNewPwd2.getText().toString().trim();
        Map<String, String> params = new HashMap<>();
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("q","getpwd");
        params.put("type","changepwd");
        params.put("password",newPwd1);
        params.put("comfirm_password",newPwd2);
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID", rhcfApp.session);
        HttpProxy.getDataByPost(this,AppConstants.BASE_URL,AppConstants.ResetPwd_REQ_TAG,params,new HRCallBack(){
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                waitingDialog(1,"");
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if(resp.getInt("code") == 0){
//                                修改成功
                                readyGo(ResetPwdActivity.this, LoginActivityNew.class);
                                finish();
                            }else {
                                ToastUtil.showToastShort(ResetPwdActivity.this,resp.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },0);

    }

    public void waitingDialog(int code,String msg){
        WaitingDialog waitingDialog = WaitingDialog.getInstance().setMsg(msg);
        if(code == 0) {
            waitingDialog.show(getSupportFragmentManager(),"waitingDialog");
        }else {
            waitingDialog.dismiss();
        }
    }

//发送手机验证码
//    private void getPhoneCode(){
//        Map<String, String> params = new HashMap<>();
//        params.put("push_id", JPushInterface.getRegistrationID(this));
//        params.put("query_site","user");
//        params.put("q","getpwd");
//        params.put("type","get_code");
//        params.put("valicode","6584");
//        params.put("phone",phoneNumber);
//        RhcfApp rhcfApp = (RhcfApp) getApplication();
//        params.put("PHPSESSID", rhcfApp.session);
//        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.ResetPwd_GETCODE_TAG, params, new HRCallBack() {
//            @Override
//            public void httpResponse(int resultCode, Object... responseData) {
//                if (resultCode == 101) {
//                    if (responseData[0] instanceof JSONObject) {
//                        JSONObject resp = (JSONObject) responseData[0];
//                        try {
//                            if(resp.getInt("code")==0){
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }, 0);
//        }

        @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_reset_pwd);
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        SMSSDK.unregisterEventHandler(mhandler);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }


    Handler evHandler = new EveHandler(this);

    static class EveHandler extends Handler{
        WeakReference<ResetPwdActivity> activityWeakReference;

        public EveHandler(ResetPwdActivity activity) {
            activityWeakReference = new WeakReference<ResetPwdActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            ResetPwdActivity resetPwdActivity = activityWeakReference.get();
//            resetPwdActivity.progressBar.setVisibility(View.GONE);
        }
    }

}
