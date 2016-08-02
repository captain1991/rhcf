package com.ayd.rhcf.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.bean.ClassEvent;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.OwnTextWatcher;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by yxd on 2016/5/4.
 */
public class LoginActivityNew extends BaseActivity implements View.OnClickListener, HRCallBack {
    private EditText uname;
    private EditText pwd;
    private ImageView iv_clear;
    private ImageView iv_eye;
    private Button btn_login;
    private TextView ljzc;
    private TextView wjmm;
    private LinearLayout lt_txyzm;
    private ImageView iv_txyzm;
    private EditText et_txyzm;
//    是否有验证码
    private boolean isValicode;
    private RhcfApp app;
    Bundle bundle;//根据有没有bundle判断登录后如何跳转
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (RhcfApp) getApplication();
        bundle = getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE);

        initViews();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_loginnew;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_login);
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Login_REQ_TAG};
    }

    //    @Override
//    protected String getTitleBarRightText() {
//        return getString(R.string.text_regist);
//    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected void titleBarRightClick() {
        readyGo(this, RegistrationActivity.class);
    }

    private void initViews() {
        uname = (EditText) findViewById(R.id.et_uname);
        pwd = (EditText) findViewById(R.id.et_pwd);
        iv_clear = (ImageView) findViewById(R.id.iv_clear);
        iv_eye = (ImageView) findViewById(R.id.iv_eye);
        iv_eye.setTag(R.drawable.eye1);
        iv_clear.setVisibility(View.INVISIBLE);
        iv_clear.setOnClickListener(this);
        uname.addTextChangedListener(new OwnTextWatcher() {
            @Override
            public void textChange(CharSequence s) {
                if (s != null && CommonUtil.isNotEmpty(s.toString())) {
                    iv_clear.setVisibility(View.VISIBLE);
                } else {
                    iv_clear.setVisibility(View.INVISIBLE);
                }
            }
        });
        iv_eye.setOnClickListener(this);

        btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
        ljzc = (TextView) findViewById(R.id.tv_ljzc);
        ljzc.setOnClickListener(this);
        wjmm = (TextView) findViewById(R.id.tv_wjmm);
        wjmm.setOnClickListener(this);
        lt_txyzm = (LinearLayout) findViewById(R.id.lt_txyzm);
        iv_txyzm = (ImageView) findViewById(R.id.iv_txyzm);
        et_txyzm = (EditText) findViewById(R.id.et_txyzm);
        iv_txyzm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_eye:
                String pwdtext = pwd.getText().toString().trim();
//                if (CommonUtil.isNotEmpty(pwdtext)) {
                    changePwdState();
//                }
                break;
            case R.id.iv_clear:
                uname.setText("");
                break;
            case R.id.btn_login:
                login();
                break;
            case R.id.tv_ljzc:
                readyGo(this, RegistrationActivity.class);
                break;
            case R.id.tv_wjmm:
                readyGo(this, FindBackPwdActivity.class);
                break;
            case R.id.iv_txyzm:
                showTxYzm();
                break;
            default:
                return;
        }
    }

    public void changePwdState() {
        int id = (Integer) iv_eye.getTag();

        if (id == R.drawable.eye1) {
            pwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            iv_eye.setBackgroundResource(R.drawable.eye2);
            iv_eye.setTag(R.drawable.eye2);
        } else {
            pwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            iv_eye.setBackgroundResource(R.drawable.eye1);
            iv_eye.setTag(R.drawable.eye1);
        }
        CharSequence charSequence = pwd.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    /**
     * 登录；
     */
    private void login() {

        if (!CommonUtil.isConnected(this)) {
            ToastUtil.showToastShort(this, "没有网络请稍候重试");
            return;
        }

        String unametext = uname.getText().toString().trim();
        String pwdtext = pwd.getText().toString().trim();

        LogUtil.i("uname==>" + unametext + ",pwd=>" + pwdtext);

        if (CommonUtil.isEmpty(unametext)) {
            ToastUtil.showToastShort(this, R.string.uname_not_null);
            return;
        }
        if (CommonUtil.isEmpty(pwdtext)) {
            ToastUtil.showToastShort(this, R.string.pwd_not_null);
            return;
        }
        // 执行登录；
        showWOrDismissDialog(0,"正在登录。。。");
        Map<String, String> param = new HashMap<>();
        String push_id = JPushInterface.getRegistrationID(this);
        LogUtil.d("push_id======" + push_id);
        param.put("query_site", "user");
        param.put("q", "login");
        param.put("password", pwdtext);
//        先写死
        param.put("UUID", CommonUtil.getUUID(this));
        param.put("username", unametext);
//        验证码
        if(isValicode){
            param.put("valicode", et_txyzm.getText().toString());
        }
        param.put("pushid", "3");
        param.put("handpass", "handpass");
        RhcfApp rhcfApp = (RhcfApp) getApplication();

        param.put("PHPSESSID",rhcfApp.session);
        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.Login_REQ_TAG, param, this, 0);

    }

    //    输错两次显示验证码
    private void showTxYzm() {
        //获取验证码
        Map<String, String> params = new HashMap<>();
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("q","valicode");
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID", rhcfApp.session);
        HttpProxy.getImageByPost(this, AppConstants.BASE_URL, AppConstants.Login_REQ_VALICODE_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof Bitmap) {
                        iv_txyzm.setImageBitmap((Bitmap) responseData[0]);
                    }
                }
            }
        }, 0);
        lt_txyzm.setVisibility(View.VISIBLE);
        isValicode = true;
    }


    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        if (resultCode == 101) {
            if (responseData[0] instanceof JSONObject) {
                showWOrDismissDialog(1,"");
                JSONObject jsonData = (JSONObject) responseData[0];
                try {
//                    登录成功
                    if (jsonData.getString("code").equals("0")) {
                        if (jsonData.has("data")) {
                            JSONObject loginData = jsonData.getJSONObject("data");
                            String token = loginData.getString("token");
                            LogUtil.d("token===get=====" + token);
                            String invite = jsonData.getString("invite");
                            LogUtil.d("邀请码===invite=====" + token);
                            SpUtil.save2SpString(this, AppConstants.INVITE, invite);
                            SpUtil.save2SpString(this, AppConstants.TOKEN, token);
                            SpUtil.save2SpBoolean(this, AppConstants.ISLOGIN, true);
                            String unametext = uname.getText().toString().trim();
                            SpUtil.save2SpString(this, AppConstants.USERNAME, unametext);
//                            EventBus.getDefault().post("yes");
                            if(bundle!=null){
                                LogUtil.d("has =============== bundle");
                                setResult(0);
                                finish();
                            }else {
                                LogUtil.d("has =============== bundle");
                                readyGo(LoginActivityNew.this,MainActivity.class);
                            }
                            Intent intent = new Intent();
                            intent.setAction("notifyReLogin");
                            sendBroadcast(intent);

                        }
                    } else if (jsonData.getString("code").equals("100")) {
                        ToastUtil.showToastShort(this, jsonData.getString("msg"));
                        LogUtil.d("100==============");
                    }else if(jsonData.getString("code").equals("101")){
                        ToastUtil.showToastShort(this, jsonData.getString("msg"));
                        LogUtil.d("101==============");
//                        延迟一秒获取验证码
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                showTxYzm();
                            }
                        },500);

                    }else {
                        ToastUtil.showToastShort(this, jsonData.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        cancelReqByTag();
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
}
