package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.TzjlAdapter;
import com.ayd.rhcf.bean.HkjhBean;
import com.ayd.rhcf.bean.TestBean;
import com.ayd.rhcf.bean.TzjlBean;
import com.ayd.rhcf.utils.LogUtil;
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
 * 理财产品详情-投资记录；
 * created by gqy on 2016/3/2
 */
public class LiCai_TzjlActivity extends BaseActivity implements ClickEventCallBack,
        PtrCallBack, AdapterView.OnItemClickListener, HRCallBack {
    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private TzjlAdapter adapter;
    private String borrow_nid;
    private Gson mGson;
    private List<HkjhBean> tzjlBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        borrow_nid = getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE).getString("borrow_nid");
        mGson = new Gson();
        init();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LiCai_Tzjl_REQ_TAG};
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_li_cai_tzjl;
    }

    private void init() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);

        refreshListView = (ListView) refreshLayout.getPullableView();
        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);
        adapter = new TzjlAdapter(this);

        refreshListView.setAdapter(adapter);
        refreshListView.setOnItemClickListener(this);
        //首次自动刷新；
        refreshLayout.autoRefresh();
//        initJsonData();
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_tzjl);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    public void clickEventCallBack(int viewId) {
//        if (viewId == mBtnLjtz.getId()) {
//            ToastUtil.showToastShort(this, "投资");
//        }
    }

    boolean isRefreshing;
    int page = 1;

    @Override
    public void onRefresh() {
        page = 1;
        isRefreshing = true;
        adapter.clear();
        initJsonData();
    }

    public void initJsonData() {
        if (borrow_nid != null) {
            Map<String, String> map = new HashMap<>();
            map.put("push_id", JPushInterface.getRegistrationID(this));
            map.put("query_site", "user");
            map.put("q", "appnew");
            map.put("borrow_nid", borrow_nid);
            map.put("type", "get_tender_list");
            map.put("page", "" + page);
            map.put("epage", "15");
            HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.LiCai_Tzjl_REQ_TAG, map, this, 0);
        }
    }

    boolean isLoading;

    @Override
    public void onLoadMore() {
        page++;
        isLoading = true;
        initJsonData();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    JSONObject reqData;

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        if (isLoading) {
            isLoading = false;
            refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
        if (isRefreshing) {
            isRefreshing = false;
            refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
        if (resultCode == 101) {
            reqData = (JSONObject) responseData[0];
            try {
                if (reqData.getInt("code") == 0) {
                    if (reqData.has("data")) {
                        try {
                            JSONArray items = reqData.getJSONObject("data").getJSONArray("list");
                            tzjlBeans = new ArrayList<>();
                            for (int i = 0; i < items.length(); i++) {
                                HkjhBean tzjlBean;
                                JSONObject item = (JSONObject) items.get(i);
                                tzjlBean = mGson.fromJson(item.toString(), HkjhBean.class);
                                tzjlBeans.add(tzjlBean);
                            }
                            ToastUtil.showToastShort(LiCai_TzjlActivity.this, reqData.getString("msg"));
                            adapter.appendDataList(tzjlBeans);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    //最后一页
                    try {
                        if (reqData.getInt("total_page") < page) {
                            ToastUtil.showToastShort(LiCai_TzjlActivity.this, "没有更多了");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    ToastUtil.showToastShort(LiCai_TzjlActivity.this, reqData.getString("msg"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
