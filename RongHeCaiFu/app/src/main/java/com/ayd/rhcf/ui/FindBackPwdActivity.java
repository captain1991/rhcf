package com.ayd.rhcf.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

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
import com.ayd.rhcf.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 找回密码；
 * created by gqy on 2016/2/23
 */
public class FindBackPwdActivity extends BaseActivity implements ClickEventCallBack {
    private EditText mEtPhoneNum;
    private EditText mEtYzm;
    private Button mBtnNext;
    private ImageView mIvClear;
    private ImageView mIvYzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assignViews();
        getValiCode();

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_find_pwd_back;
    }

    private void assignViews() {
        mEtPhoneNum = (EditText) findViewById(R.id.et_phonenumber);
        mEtPhoneNum.addTextChangedListener(textWatcher);

        mIvClear = (ImageView) findViewById(R.id.iv_clear);
        mIvClear.setVisibility(View.INVISIBLE);
        ClickListenerRegister.regist(mIvClear, this);

        mEtYzm = (EditText) findViewById(R.id.et_yzm);
        mIvYzm = (ImageView) findViewById(R.id.iv_yzm);
        mBtnNext = (Button) findViewById(R.id.btn_next);
        ClickListenerRegister.regist(mBtnNext, this);
        ClickListenerRegister.regist(mIvYzm,this);
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == R.id.iv_clear) {
            mEtPhoneNum.setText("");
        } else if (viewId == R.id.btn_next) {
            nextStep();
        }else if (viewId == R.id.iv_yzm){
            getValiCode();
        }

    }

    /**
     * 下一步；
     */
    private void nextStep() {
        final String phoneNum = mEtPhoneNum.getText().toString().trim();
        String yzm = mEtYzm.getText().toString().trim();

        LogUtil.i("phoneNum==>" + phoneNum + ",yzm=>" + yzm);

        if (CommonUtil.isEmpty(phoneNum)) {
            ToastUtil.showToastShort(this, R.string.sjh_not_null);
            return;
        }
        // 手机号段验证；
        if (!CommonUtil.isMobilePhone(phoneNum)) {
            ToastUtil.showToastShort(this, R.string.sjh_format_error);
            return;
        }
        if (CommonUtil.isEmpty(yzm)) {
            ToastUtil.showToastShort(this, R.string.yzm_not_null);
            return;
        }
        getPhoneCode(phoneNum,yzm);

//        Map<String, String> params = new HashMap<>();
//        params.put("push_id", JPushInterface.getRegistrationID(this));
//        params.put("query_site","user");
//        params.put("q","indexinfo");
//        params.put("valicode",yzm);
//        params.put("type","check_imgcode");
//        RhcfApp rhcfApp = (RhcfApp) getApplication();
//        params.put("PHPSESSID", rhcfApp.session);
//        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.FINDBACKPWD_REQ_TAG, params, new HRCallBack() {
//            @Override
//            public void httpResponse(int resultCode, Object... responseData) {
//                if (resultCode == 101) {
//                    if (responseData[0] instanceof JSONObject) {
//                        JSONObject resp = (JSONObject) responseData[0];
//                        try {
//                            if (resp.getInt("code") == 0) {
//                                Bundle bundle = new Bundle();
//                                bundle.putString(AppConstants.BUNDLE_PHONE, phoneNum);
//                                readyGoWithBundle(FindBackPwdActivity.this, ResetPwdActivity.class, bundle);
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }
//            }
//        }, 0);
    }

    private void getPhoneCode(final String phoneNum,final String yzm){
        waitingDialog(0,"正在发送短信，请注意查收");
        Map<String, String> params = new HashMap<>();
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("q","getpwd");
        params.put("type","get_code");
        params.put("valicode",yzm);
        params.put("phone",phoneNum);
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID", rhcfApp.session);
        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.ResetPwd_GETCODE_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                waitingDialog(1,"");
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if(resp.getInt("code")==0){
                                Bundle bundle = new Bundle();
                                bundle.putString(AppConstants.BUNDLE_PHONE, phoneNum);
                                bundle.putString(AppConstants.VALICODE,yzm);
                                readyGoWithBundle(FindBackPwdActivity.this, ResetPwdActivity.class, bundle);
                                finish();
                            }else {
                                ToastUtil.showToastShort(FindBackPwdActivity.this,resp.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 0);
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
////            获取验证码
//            if(CommonUtil.isMobilePhone(s.toString().trim())){
//                getValiCode();
//            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void getValiCode(){

        Map<String, String> params = new HashMap<>();
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("q","valicode");
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID", rhcfApp.session);
//        params.put("phone",phoneNum);
        HttpProxy.getImageByPost(this, AppConstants.BASE_URL, AppConstants.FindBack_REQ_VALICODE_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof Bitmap) {
                        mIvYzm.setImageBitmap((Bitmap) responseData[0]);
                    }
                }
            }
        }, 0);
    }

    public void waitingDialog(int code,String msg){
        WaitingDialog dialog = WaitingDialog.getInstance();
        dialog.setMsg(msg);
        if(code == 0){
            dialog.show(getSupportFragmentManager(),msg);
        }else {
            dialog.dismiss();
        }
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_findback_pwd);
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
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.FINDBACKPWD_REQ_TAG, AppConstants.FindBack_REQ_VALICODE_TAG};
    }

}
