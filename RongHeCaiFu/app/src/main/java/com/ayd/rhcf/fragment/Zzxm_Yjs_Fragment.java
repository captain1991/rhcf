package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.ZzxmAdapter;
import com.ayd.rhcf.adapter.ZzxmYjsAdapter;
import com.ayd.rhcf.ui.ZqxxActivity;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;

/**
 * 我的理财-债转项目-已结束-Fragment；
 * created by gqy on 2016/3/11
 */
public class Zzxm_Yjs_Fragment extends BaseLazyFragment implements PtrCallBack,
        MyEventCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private ZzxmYjsAdapter adapter;

    public static Zzxm_Yjs_Fragment newInstance(String param1, String param2) {
        Zzxm_Yjs_Fragment fragment = new Zzxm_Yjs_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Zzxm_Yjs_Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_zyxm_tbz, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshListView = (ListView) view.findViewById(R.id.pullableListView);
        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);
//        View empty = view.findViewById(R.id.empty_view_zz);
//        refreshListView.setEmptyView(empty);
        adapter = new ZzxmYjsAdapter(getActivity());

        adapter.setAadapterEventCallBack(this);

        refreshListView.setAdapter(adapter);
        PtrRefreshRegister.regist(refreshLayout, this);
    }

    /**
     * 适配器item点击操作的回调；
     */
    @Override
    public void adapterEventCallBack(Object... args) {
        if (args != null && args.length > 0) {
            if (args[0] instanceof Integer) {
                int position = (int) args[0];
//                 adapter.getDataList().get(position)
                Bundle bundle = new Bundle();
                bundle.putString("", "");
                readyGoWithBundle(getActivity(), ZqxxActivity.class, bundle);
            }
        }
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
    }

    @Override
    public void onLoadMore() {

    }
}
