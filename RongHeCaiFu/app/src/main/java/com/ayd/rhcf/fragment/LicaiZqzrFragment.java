package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.LicaiZqzrAdapter;
import com.ayd.rhcf.bean.TestBean;
import com.ayd.rhcf.ui.GmzqActivity;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 理财-债券转让-Fragment；
 * created by gqy on 2016/2/23
 * 暂时不用
 */
public class LicaiZqzrFragment extends BaseLazyFragment implements PtrCallBack, MyEventCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private LicaiZqzrAdapter adapter;
    private ScheduledExecutorService ses;

    public static LicaiZqzrFragment newInstance(String param1, String param2) {
        LicaiZqzrFragment fragment = new LicaiZqzrFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LicaiZqzrFragment() {
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
        View view = inflater.inflate(R.layout.fragment_licai_zqzr, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshListView = (ListView) refreshLayout.getPullableView();
        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);

        adapter = new LicaiZqzrAdapter(activity);
        adapter.setEventCallBack(this);
        refreshListView.setAdapter(adapter);

        PtrRefreshRegister.regist(refreshLayout, this);

        ses = Executors.newSingleThreadScheduledExecutor();
        ses.scheduleAtFixedRate(new UpdateAdapterTask(), 1, 1, TimeUnit.SECONDS);

        initData();
    }

    private Handler shouYeHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (adapter != null) {
                List<TestBean> dataList = adapter.getDataList();
                if (dataList != null && dataList.size() > 0) {
                    adapter.scheduleTime();
                }
            }
        }
    };
    private static final int MSG_WHAT = 0x1113;

    private class UpdateAdapterTask implements Runnable {
        @Override
        public void run() {
            shouYeHandler.sendEmptyMessage(MSG_WHAT);
        }
    }

    private void initData() {
         /*====================================测试数据*/
        List<TestBean> beans = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            TestBean bean = new TestBean();
            beans.add(bean);
        }
        adapter.appendDataList(beans);
        adapter.notifyDataSetChanged();
        /*=========================================*/
    }

    /**
     * 第一次显示，联网加载数据；
     */
    @Override
    protected void onFirstUserVisible() {
//        refreshLayout.autoRefresh();
    }

    @Override
    public void onDestroy() {
        if (ses != null && !ses.isShutdown()) {
            ses.shutdown();
        }
        super.onDestroy();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LICAI_ZQZR_REQ_TAG};
    }

    /*
        * 手势下拉刷新；
        * */
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    /**
     * 适配器点击事件回调；
     *
     * @param args 回传的参数列表；
     */
    @Override
    public void adapterEventCallBack(Object... args) {
        if (args != null && args.length > 0) {
            if (args[0] instanceof Integer) {
                int position = (int) args[0];
//                adapter.getDataList().get(position)
                // 取得相应的item数据；
                Bundle bundle = new Bundle();
                bundle.putString("", "");
                readyGoWithBundle(activity, GmzqActivity.class, bundle);
            }
        }
    }
}
