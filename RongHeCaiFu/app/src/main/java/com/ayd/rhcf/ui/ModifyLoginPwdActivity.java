package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
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
 * 修改登录密码；
 * created by gqy on 2016/2/27
 */
public class ModifyLoginPwdActivity extends BaseActivity implements ClickEventCallBack {
    private EditText mEtOldPwd;
    private EditText mEtNewPwd1;
    private EditText mEtNewPwd2;
    private Button mBtnSure;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.ModifyLoginPwd_REQ_TAG};
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_modify_login_pwd;
    }


    private void init() {
        mEtOldPwd = (EditText) findViewById(R.id.et_old_pwd);
        mEtNewPwd1 = (EditText) findViewById(R.id.et_new_pwd1);
        mEtNewPwd2 = (EditText) findViewById(R.id.et_new_pwd2);
        progressBar = (ProgressBar) findViewById(R.id.submit_progressbar);
        mBtnSure = (Button) findViewById(R.id.btn_sure);
        ClickListenerRegister.regist(mBtnSure, this);
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mBtnSure.getId()) {
            exe();
        }
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    private void exe() {
        String oldPwd = mEtOldPwd.getText().toString().trim();
        String newPwd1 = mEtNewPwd1.getText().toString().trim();
        String newPwd2 = mEtNewPwd2.getText().toString().trim();

        LogUtil.i("oldPwd--->" + oldPwd + ",newPwd1--->" + newPwd1 + ",newPwd2--->" + newPwd2);

        if (CommonUtil.isEmpty(oldPwd) || CommonUtil.isEmpty(newPwd1) || CommonUtil.isEmpty(newPwd2)) {
            ToastUtil.showToastShort(ModifyLoginPwdActivity.this, R.string.not_allowed_null);
            return;
        }

        if (!CommonUtil.verifyPointAndEn(oldPwd) || !CommonUtil.verifyPointAndEn(newPwd1)
                || !CommonUtil.verifyPointAndEn(newPwd2)) {
            ToastUtil.showToastShort(ModifyLoginPwdActivity.this, R.string.text_pwd_allow_chars);
            return;
        }

        if (!CommonUtil.verifyLen(oldPwd, 6) || !CommonUtil.verifyLen(newPwd1, 6)
                || !CommonUtil.verifyLen(newPwd2, 6)) {

            ToastUtil.showToastShort(ModifyLoginPwdActivity.this, R.string.text_verify_len);
            return;
        }

        if (!newPwd1.equals(newPwd2)) {
            ToastUtil.showToastShort(ModifyLoginPwdActivity.this, R.string.text_pwd_not_same);
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "m":"users/userpwd",
//                    "token":"30df57276bf44da2311d88f4e0c023eb",
//                    "password_old":"12345a",
//                    "new_pwd":"12345aa"
//        }}

        Map<String,String> params = new HashMap<>();
        params.put( "push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site","user");
        params.put("m","users/userpwd");
        String token = SpUtil.getSpStringValueByKey(this,AppConstants.TOKEN,"");
        params.put("token",token);
        params.put("password_old",oldPwd);
        params.put("new_pwd",newPwd1);
        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.ModifyLoginPwd_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if(resultCode ==101){
                    if(responseData[0] instanceof JSONObject){
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if(resp.getInt("code")==0){
                                ToastUtil.showToastShort(ModifyLoginPwdActivity.this,"更改密码成功");
                                finish();
                            }else if(resp.getInt("code")==-1){
                                CommonUtil.loginOutTime(ModifyLoginPwdActivity.this);
                                ToastUtil.showToastShort(ModifyLoginPwdActivity.this, "登录超时请重新登录");
                                Bundle bundle = new Bundle();
                                readyGoWithBundle(ModifyLoginPwdActivity.this, LoginActivityNew.class, bundle);
                            }else {
                                ToastUtil.showToastShort(ModifyLoginPwdActivity.this,resp.getString("msg"));
                            }
                            progressBar.setVisibility(View.GONE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },0);

    }


    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_setting_login_pwd);
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

}
