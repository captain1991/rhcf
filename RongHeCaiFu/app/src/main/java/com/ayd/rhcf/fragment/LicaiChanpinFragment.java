package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.LicaiCpAdapter;
import com.ayd.rhcf.bean.BorrowBean;
import com.ayd.rhcf.ui.LccpDetailActivity;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 理财-理财产品-Fragment；
 * created by gqy on 2016/2/23
 */
public class LicaiChanpinFragment extends BaseLazyFragment implements PtrCallBack,
        AdapterView.OnItemClickListener, HRCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private LicaiCpAdapter madapter;
    private static final int MSG_WHAT = 0x1112;
    private static final int MSG_WHAT_RESPONSE = 0x1113;
    private int page = 1;
    private int totalPage;
    private Gson mGson;
    private List<BorrowBean> beans;
    private ScheduledExecutorService ses;

    public static LicaiChanpinFragment newInstance(String param1, String param2) {
        LicaiChanpinFragment fragment = new LicaiChanpinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LicaiChanpinFragment() {
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
        mGson = new Gson();
        beans = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_licai_chanpin, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshListView = (ListView) refreshLayout.getPullableView();
        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);
        refreshListView.setOnItemClickListener(this);
        madapter = new LicaiCpAdapter(activity);
        refreshListView.setAdapter(madapter);
        PtrRefreshRegister.regist(refreshLayout, this);
        refreshLayout.autoRefresh();
        if (ses == null) {
            ses = Executors.newSingleThreadScheduledExecutor();
            ses.scheduleAtFixedRate(new UpdateAdapterTask(), 1, 1, TimeUnit.SECONDS);
        }
    }

    JSONObject LicaiData;

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        if (resultCode == 101) {
            if (responseData != null && responseData.length != 0)
                LicaiData = (JSONObject) responseData[0];
            liCaiHandler.sendEmptyMessage(MSG_WHAT_RESPONSE);
        } else if (resultCode == 102) {
            if (isLoading) {
                page--;
            }
        }
        stopLoadAndRefresh();
    }

    class Mhandler extends Handler {
        //
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_WHAT_RESPONSE) {
                if (LicaiData.has("data")) {

                    List<BorrowBean> temp = new ArrayList<>();
                    try {
                        JSONObject data = (JSONObject) LicaiData.get("data");
                        //页数
                        totalPage = data.getInt("total_page");
//                        每页的item数
                        int epage = data.getInt("epage");
                        if (data.has("list")) {
                            JSONArray list = (JSONArray) data.get("list");
                            for (int i = 0; i < list.length(); i++) {
                                JSONObject jsonObject = (JSONObject) list.get(i);
                                BorrowBean borrowBean = mGson.fromJson(jsonObject.toString(), BorrowBean.class);
                                temp.add(borrowBean);
                            }
                            beans.addAll(temp);
                            madapter.appendBorrowList(temp);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private class UpdateAdapterTask implements Runnable {
        @Override
        public void run() {
            timeHandler.sendEmptyMessage(MSG_WHAT);
        }
    }

    private Handler timeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == MSG_WHAT) {
                if (beans != null && beans.size() > 0) {
                    for (BorrowBean borrowBean : beans) {
//
                        if (!borrowBean.getCount_down().equals("0")) {
                            long endTime = (Long.parseLong(borrowBean.getBorrow_end_time()));
                            endTime = endTime - 1;
                            borrowBean.setBorrow_end_time("" + endTime);
                        }
                    }
                }
            }
        }
    };

    private Handler liCaiHandler = new Mhandler();

    private void initData() {

        if (totalPage != 0) {
            if (page > totalPage) {
                ToastUtil.showToastShort(activity, "没有更多了");
                refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                return;
            }
        }
        Map<String, String> map = new HashMap<String, String>();
        map.put("query_site", "user");
        map.put("q", "appnew");
        map.put("type", "get_borrow_list");
        map.put("page", page + "");
        map.put("epage", "5");
        HttpProxy.getDataByPost(getActivity(), AppConstants.BASE_URL, AppConstants.LICAI_LCCP_REQ_TAG, map, this, 0);

    }

    /**
     * 第一次显示，联网加载数据；
     */
    @Override
    protected void onFirstUserVisible() {
        LogUtil.i("onFirstUserVisible调用了");
    }

    private boolean isRefreshing = false;

    /*获取下拉刷新时的数据*/
    @Override
    public void onRefresh() {
        LogUtil.i("onRefresh调用了");
        isRefreshing = true;
        page = 1;
        beans.clear();
        madapter.clear();
        initData();
    }

    private boolean isLoading = false;

    @Override
    public void onLoadMore() {
        isLoading = true;
        page++;
        initData();
    }

    public void stopLoadAndRefresh() {
        if (isLoading) {
            isLoading = false;
            refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
        }
        if (isRefreshing) {
            isRefreshing = false;
            refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.BUNDLE_BEAN, beans.get(position));
        bundle.putString("", "");
        readyGoWithBundle(getActivity(), LccpDetailActivity.class, bundle);
        LogUtil.d("positionTouch>>>>>" + position);
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LICAI_LCCP_REQ_TAG};
    }

    @Override
    public void onDestroy() {
        if (ses != null && !ses.isShutdown()) {
            ses.shutdown();
        }
        super.onDestroy();
    }
}
