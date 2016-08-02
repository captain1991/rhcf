package com.ayd.rhcf.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayd.rhcf.AppConstants;
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
 * 注册页；
 * created by gqy on 2016/2/23
 */
public class RegistrationActivity extends BaseActivity implements View.OnClickListener {
    private EditText mEtUname;
    private EditText mEtYzm;
    private EditText mEtPwd;
    private EditText mEtYqm;
    private EditText mETTxYzm;

    private Button mBtnGetYzm;
    private Button mBtnRegist;
    private CheckBox mCheckbox;
    private TextView tv_notice_sub1;
    private TextView tv_notice_sub2;

    private ImageView mIvClear;
    private ImageView mIvEye;
    private View pg_layout;
    private ImageView iv_txyzm;
    private EditText et_txyzm;
    RhcfApp rhcfApp;
    private ScheduledExecutorService se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SMSSDK.initSDK(this, "131bc1c8fe31c", "6fe2b44d94eb570daf197c527a6283f2");
        SMSSDK.registerEventHandler(handler);

        rhcfApp = (RhcfApp) getApplication();
        se = Executors.newSingleThreadScheduledExecutor();

        assignViews();
    }
    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Registration_REQ_TAG,AppConstants.Registration_REQ_YZM_TAG,AppConstants.Registration_REQ_VALICODE_TAG};
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_registration;
    }

    private void assignViews() {
        mEtUname = (EditText) findViewById(R.id.et_uname);
        mEtUname.addTextChangedListener(textWatcher);

        mEtYzm = (EditText) findViewById(R.id.et_yzm);
        mETTxYzm = (EditText) findViewById(R.id.et_txyzm);
        mEtYqm = (EditText) findViewById(R.id.et_yqm);
        mBtnGetYzm = (Button) findViewById(R.id.btn_get_yzm);
        mBtnGetYzm.setOnClickListener(this);
        mEtPwd = (EditText) findViewById(R.id.et_pwd);

        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvClear.setVisibility(View.INVISIBLE);
        mIvClear.setOnClickListener(this);

        mIvEye = (ImageView) findViewById(R.id.iv_eye);
        mIvEye.setTag(R.drawable.eye1);
        mIvEye.setOnClickListener(this);

        mBtnRegist = (Button) findViewById(R.id.btn_regist);
        mBtnRegist.setOnClickListener(this);
        mCheckbox = (CheckBox) findViewById(R.id.checkbox);
        tv_notice_sub1 = (TextView) findViewById(R.id.tv_notice_sub1);
        tv_notice_sub2 = (TextView) findViewById(R.id.tv_notice_sub2);

        tv_notice_sub1.setOnClickListener(this);
        tv_notice_sub2.setOnClickListener(this);
        pg_layout = findViewById(R.id.progress_layout);
//        图形验证码
        iv_txyzm = (ImageView) findViewById(R.id.iv_txyzm);
        iv_txyzm.setOnClickListener(this);
        et_txyzm = (EditText) findViewById(R.id.et_txyzm);
        getImageValiCode();
    }

    public void getImageValiCode(){
        Map<String, String> params = new HashMap<>();
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("q","valicode");
        params.put("PHPSESSID",rhcfApp.session);
        HttpProxy.getImageByPost(this, AppConstants.BASE_URL, AppConstants.Registration_REQ_VALICODE_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof Bitmap) {
                        iv_txyzm.setImageBitmap((Bitmap) responseData[0]);
                    }
                }
            }
        }, 0);
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

    int time = 60;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if(time<0){
                if (!se.isShutdown()) {
                    se.shutdown();
                }
                time = 60;
                mBtnGetYzm.setClickable(true);
                mBtnGetYzm.setText(getString(R.string.text_get_yzm));
            }else {
                mBtnGetYzm.setText(time + "秒后重新获取");
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_yzm:
                if(!CommonUtil.isConnected(this)){
                    ToastUtil.showToastShort(this,"没有网络请稍候重试");
                    return;
                }

                String phoneName = mEtUname.getText().toString().trim();
                if(!CommonUtil.isMobilePhone(phoneName)){
                    ToastUtil.showToastShort(this, "请输入正确的手机号");
                    return;
                }
//                SMSSDK.getVerificationCode("86",phoneName);
//                改为获取语音验证码
//                SMSSDK.getVoiceVerifyCode("86",phoneName);
                mBtnGetYzm.setClickable(false);
                se.scheduleAtFixedRate(new Runnable() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(0);
                    }
                }, 0, 1, TimeUnit.SECONDS);
                //        短信验证码
                String pc = et_txyzm.getText().toString().trim();
                Map<String,String> params = new HashMap<>();
                params.put("push_id", JPushInterface.getRegistrationID(this));
                params.put("query_site","user");
                params.put("q","reg");
                params.put("valicode",pc);
                params.put("type","send_code");
                params.put("phone",phoneName);
                params.put("PHPSESSID",rhcfApp.session);
                HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.Registration_REQ_YZM_TAG, params, new HRCallBack() {
                    @Override
                    public void httpResponse(int resultCode, Object... responseData) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if(resp.getInt("code")!=0){
                                ToastUtil.showToastShort(RegistrationActivity.this,resp.getString("msg"));
                            }
                            LogUtil.d("获取短信验证码<<<<<<<<<<<"+resp.getString("msg"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },0);
                break;
            case R.id.btn_regist:

                if(!CommonUtil.isConnected(this)){
                    ToastUtil.showToastShort(this,"没有网络请稍候重试");
                    return;
                }

                if(!mCheckbox.isChecked()){
                    ToastUtil.showToastShort(this,"请同意协议");
                    return;
                }

//                SMSSDK.submitVerificationCode("86",p,c);
                setRegist();
                break;
            case R.id.tv_notice_sub1:
                mCheckbox.setChecked(!mCheckbox.isChecked());
                break;
            case R.id.tv_notice_sub2:
                ToastUtil.showToastShort(this, "协议");
                break;
            case R.id.iv_clear:
                mEtUname.setText("");
                break;
            case R.id.iv_eye:
                updateEyeState();
                break;
            case R.id.iv_txyzm:
                getImageValiCode();
                break;
        }
    }

    private void setRegist(){
        showWOrDismissDialog(0,"请稍等...");
        String p = mEtUname.getText().toString().trim();
//        短信验证码
        String c = mEtYzm.getText().toString().trim();
        String pwd = mEtPwd.getText().toString().trim();
        String yqm = mEtYqm.getText().toString().trim();
        Map<String,String> param = new HashMap<>();
        param.put("push_id", JPushInterface.getRegistrationID(this));
        param.put("query_site","user");
        param.put("q","reg");
        param.put("phone",p);
//        用户名为电话号码
        param.put("username",p);
        param.put("password",pwd);
//        服务器端需要验证密码客户端没有
        param.put("confirm_password",pwd);
        param.put("phonecode",c);
        param.put("PHPSESSID", rhcfApp.session);
        if(yqm!=null&&!yqm.equals("")){
            param.put("invite_userid",yqm);
        }
        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.Registration_REQ_TAG, param, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                JSONObject resp = (JSONObject) responseData[0];
                showWOrDismissDialog(1,"");
                try {
                    if (resp.getInt("code")!=0){
                        ToastUtil.showToastShort(RegistrationActivity.this,"短信已发送请注意查收");
                    }else {
                        readyGo(RegistrationActivity.this,LoginActivityNew.class);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, 0);
    }

//    注册成功后直接登录
    private void login(){

    }

    /**
     * 更新眼睛的状态和密码的显示和隐藏；
     */
    private void updateEyeState() {
        int id = (Integer) mIvEye.getTag();
        if (id == R.drawable.eye1) {
            //显示密码；
            mEtPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            mIvEye.setBackgroundResource(R.drawable.eye2);
            mIvEye.setTag(R.drawable.eye2);
        } else {
            mEtPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            mIvEye.setBackgroundResource(R.drawable.eye1);
            mIvEye.setTag(R.drawable.eye1);
        }
        CharSequence charSequence = mEtPwd.getText();
        if (charSequence instanceof Spannable) {
            Spannable spanText = (Spannable) charSequence;
            Selection.setSelection(spanText, charSequence.length());
        }
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s != null && CommonUtil.isNotEmpty(s.toString())) {
                mIvClear.setVisibility(View.VISIBLE);
            } else {
                mIvClear.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void regist(){
//   号码是否已被注册，是提示去登录
        //注册成功登录；
//        保存用户
//        SpUtil.save2SpString();
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_regist2);
    }

    @Override
    protected String getTitleBarRightText() {
        return getString(R.string.text_has_account);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected void titleBarRightClick() {
        readyGoThenFinish(this, LoginActivityNew.class);
    }

    @Override
    protected void onDestroy() {
        SMSSDK.unregisterEventHandler(handler);
        if(!se.isShutdown()){
            se.shutdown();
        }
        super.onDestroy();
    }

    private EventHandler handler = new EventHandler(){

        @Override
        public void afterEvent(int i, int i1, Object o) {

            if(i1 == SMSSDK.RESULT_COMPLETE){
                if (i == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    LogUtil.e("获取验证码");
                }else if (i == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    LogUtil.e("提交验证码成功");
                    //注册
                }
            }else if (i1 == SMSSDK.RESULT_ERROR) {
                try {
                    Throwable throwable = (Throwable) o;
                    throwable.printStackTrace();
                    JSONObject object = new JSONObject(throwable.getMessage());
                    String des = object.optString("detail");//错误描述
                    int status = object.optInt("status");//错误代码
                    if (status > 0 && !TextUtils.isEmpty(des)) {
                        Message message = new Message();
                        Bundle bundle = new Bundle();
                        bundle.putString("detail",des);
                        message.what = 1;
                        message.setData(bundle);
                        evHandler.sendMessage(message);
                        /*验证码错误额外处理，两小时内只提供三次验证码
                        * 468表示验证码错误，467表示校验验证码请求频繁，466表示校验的验证码为空
                        * */
                        if (status==468||status==467||status==466){

                        }
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
    };

    Handler evHandler = new EveHandler(this);

    static class EveHandler extends Handler{
        WeakReference<RegistrationActivity> activityWeakReference;

        public EveHandler(RegistrationActivity activity) {
            activityWeakReference = new WeakReference<RegistrationActivity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activityWeakReference.get().pg_layout.setVisibility(View.GONE);
            RegistrationActivity resetPwdActivity = activityWeakReference.get();
            if(msg.what==1) {
                Bundle bundle = msg.getData();
                Toast.makeText(activityWeakReference.get(), bundle.getString("detail"), Toast.LENGTH_SHORT).show();
            }
        }
    }

}
