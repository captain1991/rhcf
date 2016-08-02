package com.ayd.rhcf.ui;

import android.app.Activity;
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
import com.ayd.rhcf.adapter.PtggAdapter;
import com.ayd.rhcf.bean.PtggBean;
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
 * Created by gqy on 2016/2/26.
 * 平台公告Fragment；
 */
public class PtggActivity extends BaseActivity implements PtrCallBack, AdapterView.OnItemClickListener {
    private PullToRefreshLayout refreshLayout;
    private ListView mListView;
    private PtggAdapter adapter;
    private List<PtggBean> ptggBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Ptgg_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_ptgg;
    }

    private void init() {
        ptggBeans = new ArrayList<PtggBean>();
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);
        mListView = (ListView) refreshLayout.getPullableView();
        adapter = new PtggAdapter(this);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(this);
        refreshLayout.autoRefresh();

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
        return getString(R.string.text_ptgg);
    }

    @Override
    public void onRefresh() {
        page=1;
        ptggBeans.clear();
        adapter.clear();
        getGGList();
    }


    int page=1;
    int epage = 15;
    private void getGGList() {
//
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "q":"appnew",
//                    "type":"notice",
//                    "site_nid":"preview"
//        }}
        Map<String, String> params = new HashMap<>();
        params.put("push_id", JPushInterface.getRegistrationID(PtggActivity.this));
        params.put("query_site", "user");
        params.put("q", "appnew");
        params.put("type", "notice");
        params.put("page", ""+page);
        params.put("epage", ""+epage);
        params.put("site_nid", "preview");

        HttpProxy.getDataByPost(PtggActivity.this, AppConstants.BASE_URL, AppConstants.MyHfzh_REQ_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if(resultCode == 101){
                    JSONObject resp = (JSONObject) responseData[0];
                    try {
                        if(resp.getInt("code")==0){
                            JSONObject data = resp.getJSONObject("data");
                            JSONArray list = data.getJSONArray("list");
                            Gson mGson = new Gson();
                            List<PtggBean> temp = new ArrayList<PtggBean>();
                            for(int i=0;i<list.length();i++){
                                JSONObject jsonObject = (JSONObject) list.get(i);
                                PtggBean bean = mGson.fromJson(jsonObject.toString(),PtggBean.class);
                                temp.add(bean);
                            }
                            ptggBeans.addAll(temp);
                            adapter.appendList(temp);
                        }else {
                            page--;
                        }
                        refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }, 0);
    }

    @Override
    public void onLoadMore() {
        page++;
        getGGList();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PtggBean ptgg = ptggBeans.get(position);
        ToastUtil.showToastShort(this,""+position);
    }
}
