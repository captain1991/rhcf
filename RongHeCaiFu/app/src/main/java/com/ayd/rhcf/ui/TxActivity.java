package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.bean.BankBean;
import com.ayd.rhcf.bean.User;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.OwnTextWatcher;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

public class TxActivity extends BaseActivity implements ClickEventCallBack {
    private LinearLayout mPanelYhk;
    private TextView mTvYhkh;

    private EditText mEtTxje;

    private TextView mTvSsf;
    private TextView mTvKyye;
    private Button mBtnLktx;
    private RadioGroup mGroup_tx;
    private User user;
    private String id ;//银行卡在数据库的位置
    private List<BankBean> bankBeans;
    private int psi = 0;//被点击的位置
    private String cash_channel="general";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankBeans = new ArrayList<>();
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Tx_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_tx;
    }

    private void init() {
        mPanelYhk = (LinearLayout) findViewById(R.id.panel_yhk);
        ClickListenerRegister.regist(mPanelYhk, this);
        mTvYhkh = (TextView) findViewById(R.id.tv_yhkh);
        mGroup_tx = (RadioGroup) findViewById(R.id.group_tx);
        mGroup_tx.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.tx_t0:
                        cash_channel = "fast";
                        break;
                    case R.id.tx_t1:
                        cash_channel = "general";
                        break;
                    default:
                        return;
                }
            }
        });

        mEtTxje = (EditText) findViewById(R.id.et_txje);
        mEtTxje.addTextChangedListener(new OwnTextWatcher() {
            @Override
            public void textChange(CharSequence text) {
                String text_ = text.toString().trim();

                LogUtil.i("===========>" + text_);

                //超过小数点后2位；
                if (CommonUtil.verifyMore2NumAfterPoint(text)) {
                    int pointIndex = text_.indexOf(".");
                    mEtTxje.setText(text_.substring(0, pointIndex + 3));
                    mEtTxje.setSelection(mEtTxje.getText().toString().length());
                }
            }
        });

        mTvSsf = (TextView) findViewById(R.id.tv_ssf);
        mTvKyye = (TextView) findViewById(R.id.tv_kyye);


        mBtnLktx = (Button) findViewById(R.id.btn_lktx);
        ClickListenerRegister.regist(mBtnLktx, this);
        getBankList();
    }

    private static final int NET_TX_GETYHK_RESULT_CODE = 0;
    private static final int NET_TX_LKTX_RESULT_CODE = 1;

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mPanelYhk.getId()) {//银行卡；
//            获取银行卡列表
//            showWDialog(AppConstants.TX_GETYHK_REQ_CODE, getString(R.string.text_getting_yhk_list));
//            显示列表dialog
//            TxActivity.this.httpResponse(NET_TX_GETYHK_RESULT_CODE);

        } else if (viewId == mBtnLktx.getId()) { //立即提现；
            exeTx();
        }
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

    /**
     * 对话框被取消的回调；
     * 取消数据请求；
     */
    public void dialogCanceled(int resultCode) {
        switch (resultCode) {
            case AppConstants.DIALOG_TX_GETYHK_RESULT_CODE://获取银行卡信息请求取消；

                break;
            case AppConstants.DIALOG_TX_LKTX_RESULT_CODE://立刻提现信息请求取消；

                break;
        }
    }

    //只有点击银行卡时调用
    String[] array;
    public void httpResponse(int resultCode, Object... responseData) {
        switch (resultCode) {
            case NET_TX_GETYHK_RESULT_CODE:
                if(bankBeans == null || bankBeans.size() == 0){
                    ToastUtil.showToastShort(TxActivity.this,"没有绑定的银行卡");
                    return;
                }
                ShowListStrDialog dialog = new ShowListStrDialog();
                dialog.setItems(array, new ShowListStrDialog.DialogItemClick() {
                    @Override
                    public void itemClick(int position) {
                        psi = position;
                        id = bankBeans.get(position).getId();
                    }
                }).show(getSupportFragmentManager(), "YHK_DIALOG_TAG");

                break;
            case NET_TX_LKTX_RESULT_CODE:

                break;
        }
    }

//    获取银行卡列表
    private void getBankList(){
        Map<String,String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(TxActivity.this, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(TxActivity.this));
        params.put("query_site", "user");
        params.put("m", "account/bank/getlist");
        params.put("token", token);

        HttpProxy.getDataByPost(TxActivity.this, AppConstants.BASE_URL, AppConstants.Tx_REQ_BANKLIST_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if (resp.getInt("ret") == 0) {
                                JSONArray list = resp.getJSONArray("data");
//                                array = new String[list.length()];
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject object = (JSONObject) list.get(i);
                                    Gson mGson = new Gson();
                                    BankBean bankBean = mGson.fromJson(object.toString(), BankBean.class);
//                                    卡号
//                                    array[i] = bankBean.getAccount();
                                    bankBeans.add(bankBean);
                                }
                                initView();
                            }else if (resp.getInt("ret") == -1) {
                                CommonUtil.loginOutTime(TxActivity.this);
                                ToastUtil.showToastShort(TxActivity.this, "登录超时请重新登录");
                                Bundle bundle = new Bundle();
                                readyGoWithBundle(TxActivity.this, LoginActivityNew.class,bundle);
                            }else {
                                ToastUtil.showToastShort(TxActivity.this,resp.getString("msg"));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }, 0);
    }

    private void initView(){
        RhcfApp app = (RhcfApp) getApplication();
        user = app.getUser();
        if(user!=null){
            mTvKyye.setText(String.format(getString(R.string.text_kyye_hint), user.getCapital_balance()));
            if(bankBeans==null||bankBeans.size()==0){
                return;
            }
            String cardid = bankBeans.get(psi).getAccount();
            if(cardid!=null&&!cardid.equals("")){
                mTvYhkh.setText(cardid);
            }
        }

    }

    /**
     * 执行提现；
     */
    private void exeTx() {
        String je = mEtTxje.getText().toString().trim();
        LogUtil.i("je---->>" + je);

        if (CommonUtil.isEmpty(je)) {
            ToastUtil.showToastShort(this, "请输入提现金额");
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

        showWOrDismissDialog(0, getString(R.string.text_zztj));

        Map<String,String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(TxActivity.this, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site", "user");
        params.put("m", "trust/cash/new");
        LogUtil.d("tixian===========id=" + id);
        params.put("id", id);
        params.put("money", je);
//        general 普通  fast  快速提现
        params.put("cash_channel",cash_channel);
        params.put("money",je);
        params.put("token",token);
        RhcfApp rhcfApp = (RhcfApp) getApplication();
        params.put("PHPSESSID",rhcfApp.session);
        HttpProxy.getDataByPost(TxActivity.this, AppConstants.BASE_URL, AppConstants.Tx_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        showWOrDismissDialog(1,"");
                        JSONObject jsonObject = (JSONObject) responseData[0];
                        try {
                            if (jsonObject.getInt("ret") == 0) {
                                String data = jsonObject.getString("form");
                                Bundle bundle = new Bundle();
                                bundle.putString("form",data);
                                readyGoWithBundle(TxActivity.this, HFTXActivity.class, bundle);
                            } else if (jsonObject.getInt("ret")== -1) {
                                CommonUtil.loginOutTime(TxActivity.this);
                                ToastUtil.showToastShort(TxActivity.this, "登录超时请重新登录");
                                Bundle bundle = new Bundle();
                                readyGoWithBundle(TxActivity.this, LoginActivityNew.class,bundle);
                            }else {
                                ToastUtil.showToastShort(TxActivity.this, jsonObject.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      /*  catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                }
            }
        }, 0);
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
        return getString(R.string.text_tx);
    }
}
