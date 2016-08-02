package com.ayd.rhcf.ui;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.HkjhListAdapter;
import com.ayd.rhcf.bean.HkjhBean;
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
 * Created by gqy on 2016/2/29.
 * 回款计划；
 */
public class HkjhActivity extends BaseActivity implements PtrCallBack, HRCallBack, AdapterView.OnItemClickListener {
    private PullToRefreshLayout refreshLayout;
    private ListView mListView;
    private HkjhListAdapter adapter;
    private Gson mGson;
    private List<HkjhBean> hkjhBeanList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hkjhBeanList = new ArrayList<>();
        mGson = new Gson();
        registMyReciver();
        init();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_hkjh;
    }

    private void init() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);
        mListView = (ListView) refreshLayout.getPullableView();
        mListView.setOnItemClickListener(this);
        adapter = new HkjhListAdapter(this);
        mListView.setAdapter(adapter);
        refreshLayout.autoRefresh();
    }

//    {“device” :”android”,
//        “version”:”1.0”,
//        “data”:{
//        “push_id”:”sfsaa”,
//        “query_site”:”user”,
//        “q”:”appnew_ol”,
//        “type”:”get_tender_list”,
//        “page”:”1”,
//        “epage”:”3”,
//        “token”:”111”
//    }}

    private int page = 1;
    private void initData(){
        Map<String,String> param = new HashMap<>();
        param.put("push_id", JPushInterface.getRegistrationID(this));
        param.put("query_site","user");
        param.put("type","get_tender_list");
        param.put("q","appnew_ol");
        param.put("page",""+page);
        param.put("epage", "10");
        String token = SpUtil.getSpStringValueByKey(this,AppConstants.TOKEN,"");
        LogUtil.d("token<<<<fromSp<<<<"+token);
        param.put("token",token);
        HttpProxy.getDataByPost(this,AppConstants.BASE_URL,AppConstants.HKJH_REQ_TAG,param,this,0);
    }

    @Override
    public void onRefresh() {
        page = 1;
        adapter.clear();
        hkjhBeanList.clear();
        initData();
    }

    @Override
    public void onLoadMore() {
        page++;
        initData();
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
    protected String getTitleBarTitle() {
        return getString(R.string.text_hkjh);
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.HKJH_REQ_TAG};
    }

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        if(resultCode==101){
            if(responseData[0] instanceof JSONObject){
                JSONObject jsonData = (JSONObject) responseData[0];
                try {
                    if (jsonData.getInt("code")==0) {
                        JSONObject data = jsonData.getJSONObject("data");
                        if (data.has("list")) {
                            List<HkjhBean> temp = new ArrayList<>();
                            JSONArray list = data.getJSONArray("list");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject jsonHkjh = (JSONObject) list.get(i);
                                HkjhBean hkjh = mGson.fromJson(jsonHkjh.toString(), HkjhBean.class);
                                temp.add(hkjh);
                            }
                            hkjhBeanList.addAll(temp);
                            adapter.appendDataList(temp);
                        }
                    }else if (jsonData.getInt("code")==-1) {
                        CommonUtil.loginOutTime(this);
                        ToastUtil.showToastShort(HkjhActivity.this, "登录超时请重新登录");
                        Bundle bundle = new Bundle();
                        readyGoWithBundle(HkjhActivity.this, LoginActivityNew.class,bundle);
                    }else {
                        ToastUtil.showToastShort(this,jsonData.getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            stopLoadAndfresh();
        }
    }

    public void stopLoadAndfresh(){
        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    private MyReciver reciver;
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(reciver!=null){
            unregisterReceiver(reciver);
        }
    }
}
