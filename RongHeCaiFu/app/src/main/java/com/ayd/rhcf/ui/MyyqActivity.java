package com.ayd.rhcf.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.MyYqAdapter;
import com.ayd.rhcf.bean.Friend;
import com.ayd.rhcf.bean.User;
import com.ayd.rhcf.utils.CommonUtil;
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
 * Created by gqy on 2016/2/29.
 * 我的邀请Fragment；
 */
public class MyyqActivity extends BaseActivity implements PtrCallBack, AdapterView.OnItemClickListener {
    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private MyYqAdapter adapter;
    private View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registMyReciver();
        init();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_myyq;
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Myyq_REQ_TAG};
    }

    private void init() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);

        refreshListView = (ListView) refreshLayout.getPullableView();
//        headerView = LayoutInflater.from(this).inflate(R.layout.headerview_myyq, null);
//
//        refreshListView.addHeaderView(headerView);

        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);
        adapter = new MyYqAdapter(this);
        refreshListView.setAdapter(adapter);
        refreshListView.setOnItemClickListener(this);
        //首次自动刷新；
        refreshLayout.autoRefresh();

    }

    private int page;
    private void getYaoqing(){
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "m":"users/myfriend/list",
//                    "token":"30df57276bf44da2311d88f4e0c023eb",
//                    "page":"1",
//                    "epage":"2"
//        }}
        Map<String, String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(this, AppConstants.TOKEN, "");
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site", "user");
        params.put("m","users/myfriend/list");
        params.put("token", token);
        params.put("page", ""+page);
        params.put("epage", "10");
        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, "account_req_tag", params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if (resultCode==101){
                    if(responseData[0] instanceof JSONObject){
                        JSONObject resp = (JSONObject) responseData[0];

                            try {
                                if(resp.getInt("code")==0) {
                                    JSONObject data = resp.getJSONObject("data");
                                    JSONArray list = data.getJSONArray("list");
                                    List<Friend> friends = new ArrayList<Friend>();
                                    if(list.length() == 0){
                                        ToastUtil.showToastShort(MyyqActivity.this,"没有更多数据了");
                                    }
                                    for (int i = 0; i < list.length(); i++) {
                                        JSONObject jsonFriend = (JSONObject) list.get(i);
                                        Gson mGson = new Gson();
                                        Friend friend = mGson.fromJson(jsonFriend.toString(), Friend.class);
                                        friends.add(friend);
                                    }
                                    adapter.appendDataList(friends);
                                }else if(resp.getInt("code")== -1){
                                    CommonUtil.loginOutTime(MyyqActivity.this);
                                    ToastUtil.showToastShort(MyyqActivity.this, "登录超时请重新登录");
                                    Bundle bundle = new Bundle();
                                    readyGoWithBundle(MyyqActivity.this, LoginActivityNew.class, bundle);
                                }else {
                                    ToastUtil.showToastShort(MyyqActivity.this,resp.getString("msg"));
                                }
                                refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                }
            }
        }, 0);

    }

    @Override
    public void onRefresh() {
        page = 1;
        adapter.clear();
        getYaoqing();
    }

    @Override
    public void onLoadMore() {
        page++;
        getYaoqing();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
    MyReciver reciver;
    private void registMyReciver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("notifyReLogin");
        reciver = new MyReciver();
        registerReceiver(reciver, filter);
    }

    class MyReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshLayout.autoRefresh();
        }
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
        return getString(R.string.text_myyq);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reciver!=null) {
            unregisterReceiver(reciver);
        }
    }
}
