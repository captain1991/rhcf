package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * created by gqy on 2016/2/26
 * 意见反馈；
 */
public class YjfkActivity extends BaseActivity implements ClickEventCallBack, HRCallBack {
    private TextView mTvKfrx;
    private Button mBtnSend;
    private EditText mEtFk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Yjfk_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    private void init() {
        mEtFk = (EditText) findViewById(R.id.et_fk);
        mTvKfrx = (TextView) findViewById(R.id.tv_kfrx);
        ClickListenerRegister.regist(mTvKfrx, this);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        ClickListenerRegister.regist(mBtnSend,this);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_yjfk;
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
        return getString(R.string.text_yjfk);
    }

    public void sendMessage(){
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "q":"userinfo",
//                    "token":"30df57276bf44da2311d88f4e0c023eb",
//                    "type":"advice",
//                    "advice":"qwer"
//        }}
        waitDialog(0,"正在提交");
        String qwer = mEtFk.getText().toString().trim();

        Map<String,String> param = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(this,AppConstants.TOKEN,"");
        param.put("push_id", JPushInterface.getRegistrationID(this));
        param.put("query_site","user");
        param.put("q","userinfo");
        param.put("token",token);
        param.put("type","advice");
        param.put("advice",qwer);
        HttpProxy.getDataByPost(this,AppConstants.BASE_URL,AppConstants.Yjfk_REQ_TAG,param,this,0);

    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mTvKfrx.getId()) {
            CommonUtil.dialNumber(this, getString(R.string.text_kfrx_number));
        } else if (viewId == mBtnSend.getId()) {
            sendMessage();
        }
    }

    public void waitDialog(int code,String msg){
        WaitingDialog dialog = WaitingDialog.getInstance();
        dialog.setMsg(msg);
        if(code==0){
            dialog.show(getSupportFragmentManager(),"waiting_dialg");
        }else {
            dialog.dismiss();
        }
    }

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        if(resultCode==101){
            waitDialog(1,"");
            if(responseData[0] instanceof JSONObject){
                JSONObject resp = (JSONObject) responseData[0];
                try {
                    if(resp.getInt("code")==0){
                        mEtFk.setText("");
                        ToastUtil.showToastShort(this, resp.getString("msg"));
                    }else if (resp.getInt("code")==-1) {
                        CommonUtil.loginOutTime(this);
                        ToastUtil.showToastShort(YjfkActivity.this, "登录超时请重新登录");
                        Bundle bundle = new Bundle();
                        readyGoWithBundle(YjfkActivity.this, LoginActivityNew.class,bundle);
                    }else {
                        ToastUtil.showToastShort(this,resp.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
