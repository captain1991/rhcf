package com.ayd.rhcf.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.bean.ClassEvent;
import com.ayd.rhcf.bean.User;
import com.ayd.rhcf.ui.CzActivity;
import com.ayd.rhcf.ui.GrzxActivity;
import com.ayd.rhcf.ui.HFTXActivity;
import com.ayd.rhcf.ui.HkjhActivity;
import com.ayd.rhcf.ui.JyjlActivity;
import com.ayd.rhcf.ui.LicaiActivity;
import com.ayd.rhcf.ui.LoginActivity;
import com.ayd.rhcf.ui.LoginActivityNew;
import com.ayd.rhcf.ui.MainActivity;
import com.ayd.rhcf.ui.MyHfzhActivity;
import com.ayd.rhcf.ui.MyyqActivity;
import com.ayd.rhcf.ui.TouziActivity;
import com.ayd.rhcf.ui.TxActivity;
import com.ayd.rhcf.ui.WaitingDialog;
import com.ayd.rhcf.ui.WktHftgDialog;
import com.ayd.rhcf.ui.YqhyActivity;
import com.ayd.rhcf.ui.ZhaizhuanActivity;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.TvInit;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 账户Fragment；
 * created by gqy on 2016/2/23
 */
public class AccountFragment extends BaseFragment implements ClickEventCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private ImageView mIvThumb;
    private TextView mTvUname;

    private TextView mTvZzc;
    private TextView mTvKyye;
    private TextView mTvLjsy;

    private Button mBtnCz;
    private Button mBtnTx;

    private LinearLayout mPanelMyhfzh;
    private LinearLayout mPanelMylc;
    private LinearLayout mPanelzqzr;
    private LinearLayout mPanelHkjh;
    private LinearLayout mPanelJyjl;
    private LinearLayout mPanelMyyq;

    private WktHftgDialog dialog = null;
    private boolean isViewCreated = false;
    private User user;
    private RhcfApp app;

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public AccountFragment() {
    }

    public void initTv(String content) {
        TvInit.set(mTvUname, "");
        TvInit.set(mTvZzc, "");
        TvInit.set(mTvKyye, "");
        TvInit.set(mTvLjsy, "");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = (RhcfApp) activity.getApplication();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        if (view != null) {
            mIvThumb = (ImageView) view.findViewById(R.id.iv_thumb);
            ClickListenerRegister.regist(mIvThumb, this);

            mTvUname = (TextView) view.findViewById(R.id.tv_uname);
            ClickListenerRegister.regist(mTvUname, this);


            mTvZzc = (TextView) view.findViewById(R.id.tv_zzc);
            mTvKyye = (TextView) view.findViewById(R.id.tv_kyye);
            mTvLjsy = (TextView) view.findViewById(R.id.tv_ljsy);

            mBtnCz = (Button) view.findViewById(R.id.btn_cz);
            ClickListenerRegister.regist(mBtnCz, this);
            mBtnTx = (Button) view.findViewById(R.id.btn_tx);
            ClickListenerRegister.regist(mBtnTx, this);

            mPanelMyhfzh = (LinearLayout) view.findViewById(R.id.panel_myhfzh);
            ClickListenerRegister.regist(mPanelMyhfzh, this);
            mPanelMylc = (LinearLayout) view.findViewById(R.id.panel_mylc);
            ClickListenerRegister.regist(mPanelMylc, this);
            mPanelzqzr = (LinearLayout) view.findViewById(R.id.panel_zqzr);
            ClickListenerRegister.regist(mPanelzqzr, this);
//            没有债转
            mPanelzqzr.setVisibility(View.GONE);
            mPanelHkjh = (LinearLayout) view.findViewById(R.id.panel_hkjh);
            ClickListenerRegister.regist(mPanelHkjh, this);
            mPanelJyjl = (LinearLayout) view.findViewById(R.id.panel_jyjl);
            ClickListenerRegister.regist(mPanelJyjl, this);
            mPanelMyyq = (LinearLayout) view.findViewById(R.id.panel_myyq);
            ClickListenerRegister.regist(mPanelMyyq, this);
            setData();
        }

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        EventBus.getDefault().register(this);
        registMyReciver();
        isViewCreated = true;
    }


    private void setData() {
        isLogin = SpUtil.getSpBooleanValueByKey(activity, AppConstants.ISLOGIN, false);
        String name = SpUtil.getSpStringValueByKey(getActivity(), AppConstants.USERNAME, "");
        if (isLogin && !name.equals("")) {
            mTvUname.setText(name);
        } else {
            mTvUname.setText("未登录");
        }

        if(lOut) {
//      退出登录
            initViewLogout();
            lOut = false;
            return;
        }

        if(!isLogin){
            ToastUtil.showToastLong(activity, "请登录");
            readyGo(activity, LoginActivityNew.class);
            return;
        }

        getUserInfo();
    }

    public void refreshDataWhenVisible() {
        if (isViewCreated) {
            //获取资产数据；

        }
    }

    private void getUserInfo() {
        if (!CommonUtil.isConnected(activity)) {
            ToastUtil.showToastShort(activity, "网络异常");
            return;
        }
        Map<String, String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(activity, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(activity));
        params.put("query_site", "user");
        params.put("q", "userinfo");
        params.put("type", "userinfo");
        params.put("token", token);
        HttpProxy.getDataByPost(activity, AppConstants.BASE_URL, "account_req_tag", params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if (resp.getInt("code") == 0) {
                                Gson gson = new Gson();
                                JSONObject data = resp.getJSONObject("data");
                                user = gson.fromJson(data.toString(), User.class);
                                app.setUser(user);
                                initView();
                            } else if (resp.getInt("code") == -1) {

//                                登录超时或者未登录
                                   CommonUtil.loginOutTime(activity);
                                   ToastUtil.showToastLong(activity, "请登录");
                                   readyGo(activity, LoginActivityNew.class);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, 0);
    }

    private void initView() {

        isLogin = true;
        if (user != null) {
            String thum_url = user.getAvatar_url();
            if (thum_url != null && !thum_url.equals("")) {
                BitmapUtils bitmapUtils = RhcfApp.getBitmapUtils(activity);
                bitmapUtils.display(mIvThumb, thum_url);
            }
            mTvUname.setText(user.getUsername());
            mTvZzc.setText("" + user.getCapital_total());
            mTvKyye.setText("" + user.getCapital_balance());
            if (user.getRecover_yes_profit() != null) {
//                累计收益只有利息的
                mTvLjsy.setText("" + user.getTender_interest_yes());
            }
        }
    }

    private void initViewLogout() {

//      登录超时改为状态改为未登录
        isLogin = false;
        if (user != null) {
            String thum_url = user.getAvatar_url();
            if (thum_url != null && !thum_url.equals("")) {
                BitmapUtils bitmapUtils = RhcfApp.getBitmapUtils(activity);
                bitmapUtils.display(mIvThumb, thum_url);
            }
            mTvUname.setText("未登录");
            mTvZzc.setText("---");
            mTvKyye.setText("---");
            mTvLjsy.setText("---");
        }
    }

    Boolean isLogin;

    @Override
    public void clickEventCallBack(int viewId) {
        Bundle bundle = new Bundle();
        isLogin = SpUtil.getSpBooleanValueByKey(activity,AppConstants.ISLOGIN,false);
        if(!isLogin){
            readyGo(activity, LoginActivityNew.class);
            return;
        }
        if (mTvUname.getId() == viewId) {
            //判断登录；
            if (isLogin) {
                readyGo(activity, GrzxActivity.class);
            } else {
                readyGo(activity, LoginActivityNew.class);
            }
        } else if (mIvThumb.getId() == viewId) {
            if (isLogin) {
                readyGo(activity, GrzxActivity.class);
            } else {
                readyGo(activity, LoginActivityNew.class);
            }
        } else if (mBtnCz.getId() == viewId) {
            if(user!=null) {
                if (user.getTrust() == 0) {
                    ToastUtil.showToastShort(activity, "请开通汇付帐号");
                    return;
                }
//            充值
                readyGo(activity, CzActivity.class);
            }
        } else if (mBtnTx.getId() == viewId) {
            if(user!=null) {
                if (user.getTrust() == 0) {
                    ToastUtil.showToastShort(activity, "请开通汇付帐号");
                    return;
                }
                readyGo(activity, TxActivity.class);
            }
        } else if (mPanelMyhfzh.getId() == viewId) {
//            if (!isLogin) {
//                ToastUtil.showToastShort(activity, "请先登录");
//                return;
//            }
            //未开通汇付托管账户；0未开通
            if (user.getTrust() == 0) {
                showWktHftgDialog();
            } else {
                readyGo(activity, MyHfzhActivity.class);
            }
        } else if (mPanelMylc.getId() == viewId) {
            bundle.putString("", "");
//            readyGoWithBundle(activity, LicaiActivity.class, bundle);
            readyGoWithBundle(activity, TouziActivity.class, bundle);
        } else if (mPanelzqzr.getId() == viewId) {
//            bundle.putString("", "");
//            readyGoWithBundle(activity, LicaiActivity.class, bundle);
//            readyGoWithBundle(activity, ZhaizhuanActivity.class, bundle);
        } else if (mPanelHkjh.getId() == viewId) {
            bundle.putString("", "");
            readyGoWithBundle(activity, HkjhActivity.class, bundle);
        } else if (mPanelJyjl.getId() == viewId) {
            readyGo(activity, JyjlActivity.class);
        } else if (mPanelMyyq.getId() == viewId) {
            readyGo(activity, MyyqActivity.class);
        }
    }

    private void showWktHftgDialog() {
        if (dialog == null) {
            dialog = new WktHftgDialog();
        }
        dialog.setCallBack(new MyEventCallBack() {
            @Override
            public void adapterEventCallBack(Object... args) {
                int index = (int) args[0];
                if (dialog != null) {
                    dialog.dismiss();
                }
                if (index == 1) { //去开通；
                    String name = dialog.getXm();
                    String sfz = dialog.getSfz();
//                    绑定托管，开通汇付
//                    {"device" :"android",
//                            "version":"1.0",
//                            "data":{
//                        "push_id":"sfsaa",
//                                "query_site":"user",
//                                "m":"trust/reg/bind",
//                                "realname":"王显",
//                                "card_id":"340122198903014074",
//                                "token":"f70ec186e2fb7d3a2f3992ef5c95da6b"
//                    }}
                    waitingDialog(0,"请稍等...");
                    Map<String, String> params = new HashMap<String, String>();
                    String token = SpUtil.getSpStringValueByKey(activity, AppConstants.TOKEN, "");
                    params.put("push_id", JPushInterface.getRegistrationID(activity));
                    params.put("query_site", "user");
                    params.put("m", "trust/reg/bind");
                    params.put("realname", name);
                    params.put("card_id", sfz);
                    params.put("token", token);
                    params.put("PHPSESSID",app.session);
                    HttpProxy.getDataByPost(activity, AppConstants.BASE_URL, "account_req_tuofu_tag", params, new HRCallBack() {

                        @Override
                        public void httpResponse(int resultCode, Object... responseData) {
                            if (resultCode == 101) {
                                if (responseData[0] instanceof JSONObject) {
                                    waitingDialog(1,"");
                                    JSONObject resp = (JSONObject) responseData[0];
                                    try {
                                        if (resp.getInt("ret") == 0) {
                                            String data = resp.getString("data");

                                            Bundle bundle = new Bundle();
                                            bundle.putString("form",data);
                                            bundle.putString("tag","AccountFragment");
                                            readyGoWithBundle(activity, HFTXActivity.class, bundle);

                                        }else if (resp.getInt("ret") == -1) {
                                            CommonUtil.loginOutTime(activity);
                                            ToastUtil.showToastShort(activity, "请重新登录");
                                            readyGo(activity, LoginActivityNew.class);
                                        } else {
                                            ToastUtil.showToastShort(activity,resp.getString("msg"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }
                    }, 0);
//                    readyGo(activity, MyHfzhActivity.class);
                }
            }
        });
//        dialog.setTargetFragment(this, AppConstants.OPEN_WKTHFZH_FRAGMENT_REQ_CODE);
        dialog.show(getChildFragmentManager(), AppConstants.WKTHFZF_FRAGMENT_TAG);
    }


    @Override
    protected String[] getReqTagList() {
        return new String[]{"account_req_tag", "account_req_tuofu_tag"};
    }

    private void waitingDialog(int code,String msg){
        WaitingDialog waitingDialog = WaitingDialog.getInstance();
        waitingDialog.setMsg(msg);

        if(code==0){
            waitingDialog.show(getChildFragmentManager(),"");
        }else {
            waitingDialog.dismiss();
        }
    }

    private MyReciver reciver;
    private void registMyReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("notifyReLogin");
        filter.addAction("notifyout");
        reciver = new MyReciver();
        activity.registerReceiver(reciver, filter);
    }
boolean lOut;
    class MyReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("notifyout")){
//                在个人中心退出登录时发的广播
//                lOut防止进入登录页
                lOut=true;
            }
            setData();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(reciver!=null){
            activity.unregisterReceiver(reciver);
        }

    }
}
