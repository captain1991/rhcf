package com.ayd.rhcf.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;

/**
 * Created by gqy on 2016/2/27.
 * 公告新闻Fragment；
 */
public class GgxwFragment extends Fragment implements PtrCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private PullToRefreshLayout mRefreshLayout;
    private ListView mListView;

    public static GgxwFragment newInstance(String param1, String param2) {
        GgxwFragment fragment = new GgxwFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public GgxwFragment() {
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
        View view = inflater.inflate(R.layout.fragment_ggxw, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        mRefreshLayout.setPullUpEnable(false);
        PtrRefreshRegister.regist(mRefreshLayout, this);
        mListView = (ListView) mRefreshLayout.getPullableView();
        mListView.setAdapter(null);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }
}
