package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.MyBdyhkAdapter;
import com.ayd.rhcf.bean.BankBean;
import com.ayd.rhcf.ui.LoginActivityNew;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 账户-我的汇付账号-绑定银行卡Fragment；
 * created by gqy on 2016/3/8
 */
public class MyHfzh_Bdyhk_Fragment extends BaseLazyFragment implements PtrCallBack,
        MyEventCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private MyBdyhkAdapter adapter;
    private List<BankBean> bankBeans;

    public static MyHfzh_Bdyhk_Fragment newInstance(String param1, String param2) {
        MyHfzh_Bdyhk_Fragment fragment = new MyHfzh_Bdyhk_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MyHfzh_Bdyhk_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankBeans = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myhfzh_bdyhk, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshListView = (ListView) refreshLayout.getPullableView();
        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);
        refreshLayout.setPullDownEnable(true);
        refreshLayout.setPullUpEnable(false);
        adapter = new MyBdyhkAdapter(getActivity());
        adapter.setAadapterEventCallBack(this);
        refreshListView.setAdapter(adapter);
        PtrRefreshRegister.regist(refreshLayout, this);
        refreshLayout.autoRefresh();
    }

    private void getBankList(){
        Map<String,String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(activity, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(activity));
        params.put("query_site", "user");
        params.put("m", "account/bank/getlist");
        params.put("token", token);

        HttpProxy.getDataByPost(activity, AppConstants.BASE_URL, AppConstants.MyHfzh_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if (resp.getInt("ret") == 0) {
                                JSONArray list = resp.getJSONArray("data");
                                List<BankBean> temp = new ArrayList<BankBean>();
                                for (int i = 0; i < list.length(); i++) {
                                    JSONObject object = (JSONObject) list.get(i);
                                    Gson mGson = new Gson();
                                    BankBean bankBean = mGson.fromJson(object.toString(), BankBean.class);
                                    temp.add(bankBean);
                                }

                                adapter.appendDataList(temp);
                                bankBeans.addAll(temp);
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
        }, 0);
    }

    @Override
    public void adapterEventCallBack(Object... args) {
        int position = (int) args[0];
        deleItem(position);

    }

    public void deleItem(int position){
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "m":"trust/bank/remove",
//                    "id ":"20",
//                    "token":"f70ec186e2fb7d3a2f3992ef5c95da6b"
//        }}
        Map<String,String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(activity, AppConstants.TOKEN, "");
        BankBean bean = bankBeans.get(position);
        String id = bean.getId();
        params.put("push_id", JPushInterface.getRegistrationID(activity));
        params.put("query_site", "user");
        params.put("m", "trust/bank/remove");
        params.put("id",id);
        params.put("token", token);
        HttpProxy.getDataByPost(activity, AppConstants.BASE_URL, AppConstants.MyHfzh_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode == 101) {
                    if (responseData[0] instanceof JSONObject) {
                        JSONObject resp = (JSONObject) responseData[0];
                        try {
                            if (resp.getInt("code") == 0) {
                                ToastUtil.showToastShort(activity,"删除成功");
                                bankBeans.clear();
                                adapter.clear();
                                getBankList();
                            }else if (resp.getInt("code") == -1) {
                                CommonUtil.loginOutTime(activity);
                                ToastUtil.showToastShort(activity, "登录超时请重新登录");
                                readyGo(activity, LoginActivityNew.class);
                            } else {
                                ToastUtil.showToastShort(activity, resp.getString("msg"));
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
        getBankList();
    }

    @Override
    public void onLoadMore() {

    }

}
