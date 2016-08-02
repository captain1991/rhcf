package com.ayd.rhcf.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.JyjlListAdapter;
import com.ayd.rhcf.bean.JyjlBean;
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
 * 交易记录t；
 */
public class JyjlActivity extends BaseActivity implements PtrCallBack, HRCallBack {
    private PullToRefreshLayout refreshLayout;
    private ListView mListView;
    private JyjlListAdapter adapter;
    private TextView tv_wh;
    private int page = 1;
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

    private void init() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);
        refreshLayout.setPullDownEnable(false);
        refreshLayout.setPullUpEnable(true);
        mListView = (ListView) refreshLayout.getPullableView();
        adapter = new JyjlListAdapter(this);
        mListView.setAdapter(adapter);
        tv_wh = (TextView) findViewById(R.id.tv_wh);
        tv_wh.setText("余额(元)");
        refreshLayout.autoRefresh();
    }

//    {"device" :"android",
//    "version":"1.0",
//            "data":{
//        "push_id":"sfsaa",
//                "query_site":"user",
//                "q":"userinfo",
//                "type":"account_log",
//                "token":"f70ec186e2fb7d3a2f3992ef5c95da6b"
//    }}


    private void initData(){
        Map<String,String> param = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(JyjlActivity.this,AppConstants.TOKEN,"");
        param.put("push_id",JPushInterface.getRegistrationID(JyjlActivity.this));
        param.put("query_site","user");
        param.put("q","userinfo");
        param.put("type","account_log");
        param.put("page",""+page);
        param.put("epage","5");
        param.put("token",token);

        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.JYJL_REQ_TAG, param, this, 0);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_jyjl;
    }

    @Override
    public void onRefresh() {
        page=1;
        adapter.clear();
        initData();
    }

    @Override
    public void onLoadMore() {
        page++;
        initData();
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
        return getString(R.string.text_jyjl);
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.JYJL_REQ_TAG};
    }

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        if(resultCode==101){
            if(responseData[0] instanceof JSONObject){
                JSONObject resp = (JSONObject) responseData[0];
                try {
                    if(resp.getInt("ret")==0){
                        JSONObject data = resp.getJSONObject("data");
                        JSONArray list = data.getJSONArray("list");
                        List<JyjlBean> jyjlBeans = new ArrayList<>();
                        for (int i = 0;i<list.length();i++){
                            Gson mGson = new Gson();
                            JyjlBean bean = null;
                            JSONObject object =(JSONObject) list.get(i);
                            bean = mGson.fromJson(object.toString(),JyjlBean.class);
                            jyjlBeans.add(bean);
                        }
                        adapter.appendDataList(jyjlBeans);
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    }else if(resp.getInt("ret") == -1){
                        CommonUtil.loginOutTime(this);
                        ToastUtil.showToastShort(JyjlActivity.this, "登录超时请重新登录");
                        Bundle bundle = new Bundle();
                        readyGoWithBundle(JyjlActivity.this, LoginActivityNew.class, bundle);
                    } else {
                        ToastUtil.showToastShort(this, resp.getString("msg"));
                    }
                    refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                    refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
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
    protected void onDestroy() {
        super.onDestroy();
        if(reciver!=null){
            unregisterReceiver(reciver);
        }
    }
}
