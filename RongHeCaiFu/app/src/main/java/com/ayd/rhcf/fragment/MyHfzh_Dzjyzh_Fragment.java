package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.ui.LoginActivityNew;
import com.ayd.rhcf.ui.WaitingDialog;
import com.ayd.rhcf.ui.YqhyActivity;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 账户-我的汇付账号-电子交易账户Fragment；
 * created by gqy on 2016/3/8
 */
public class MyHfzh_Dzjyzh_Fragment extends BaseLazyFragment implements PtrCallBack, View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private PullToRefreshLayout mRefreshLayout;
    private TextView mTvHftxh;
    private TextView mTvZje;
    private TextView mTvKyje;
    private TextView mTvDjje;
    private TextView mTvNotice;

    public static MyHfzh_Dzjyzh_Fragment newInstance(String param1, String param2) {
        MyHfzh_Dzjyzh_Fragment fragment = new MyHfzh_Dzjyzh_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyHfzh_Dzjyzh_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myhfzh_dzjyzh, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setPullUpEnable(false);
        PtrRefreshRegister.regist(mRefreshLayout, this);
        mTvHftxh = (TextView) view.findViewById(R.id.tv_hftxh);
        mTvZje = (TextView) view.findViewById(R.id.tv_zje);
        mTvKyje = (TextView) view.findViewById(R.id.tv_kyje);
        mTvDjje = (TextView) view.findViewById(R.id.tv_djje);
        mTvNotice = (TextView) view.findViewById(R.id.tv_notice);
        mTvNotice.setVisibility(View.GONE);
        mTvNotice.setOnClickListener(this);
        mTvNotice.setMovementMethod(new LinkMovementMethod());
        mRefreshLayout.autoRefresh();
    }
//获取汇付账户信息
    private void getdata() {
//        user&m=trust/account
        Map<String, String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(activity, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(activity));
        params.put("query_site", "user");
        params.put("m", "trust/account");
        params.put("token", token);
        HttpProxy.getDataByPost(activity, AppConstants.BASE_URL, AppConstants.MyHfzh_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                mRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {

                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if (resp.getInt("ret") == 0) {
                                JSONObject data = resp.getJSONObject("data");
                                mTvHftxh.setText(data.getString("trust_userid"));
                                mTvZje.setText(data.getString("total"));
                                mTvKyje.setText(data.getString("balance"));
                                mTvDjje.setText(data.getString("frost"));
//                                mTvNotice.setText(Html.fromHtml("您的账户中尚有" + data.getString("balance_old") + "元未同步余额，请点击" +
//                                        "<a href='http://www.baidu.com'>立即同步</a>" + "，同步手续费由平台支付"));
                            } else if(resp.getInt("ret") == -1) {
                                CommonUtil.loginOutTime(activity);
                                ToastUtil.showToastShort(activity, "登录超时请重新登录");
                                readyGo(activity, LoginActivityNew.class);
                            }else {
                                ToastUtil.showToastShort(activity, resp.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        }, 0);

    }

//    资金同步
    public void tongBu(){
//        /weixin/?user&m=trust/adjust_account/adjust
        WaitingDialog.getInstance().show(getChildFragmentManager(),"");
        Map<String, String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(activity, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(activity));
        params.put("query_site", "user");
        params.put("m", "trust/adjust_account/adjust");
        params.put("token", token);
        HttpProxy.getDataByPost(activity, AppConstants.BASE_URL, AppConstants.MyHfzh_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if (resp.getInt("ret") == 0) {

                            } else if (resp.getInt("ret") == -1) {
                                CommonUtil.loginOutTime(activity);
                                ToastUtil.showToastShort(activity, "登录超时请重新登录");
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
        },0);
    }

    /**
     * 第一次显示，联网加载数据；
     */
    @Override
    protected void onFirstUserVisible() {
//        refreshLayout.autoRefresh();
        LogUtil.i("onFirstUserVisible调用了");
    }

    /*获取下拉刷新时的数据*/
    @Override
    public void onRefresh() {
        getdata();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onClick(View v) {
//        tongBu();
    }
}
