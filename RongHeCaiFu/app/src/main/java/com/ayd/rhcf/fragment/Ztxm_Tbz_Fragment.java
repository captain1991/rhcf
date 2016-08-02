package com.ayd.rhcf.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.ZtxmAdapter;
import com.ayd.rhcf.bean.HkjhBean;
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


/**
 * 我的理财-直投项目-Fragment；
 * created by gqy on 2016/3/3
 */
public class Ztxm_Tbz_Fragment extends BaseLazyFragment implements PtrCallBack{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private ZtxmAdapter adapter;
    private List<HkjhBean> hkjhBeanList;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    private int page=1;
    private int epage;
    Boolean isLoading = true;

    public static Ztxm_Tbz_Fragment newInstance(String param1, String param2) {
        Ztxm_Tbz_Fragment fragment = new Ztxm_Tbz_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Ztxm_Tbz_Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hkjhBeanList = new ArrayList<>();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zyxm_tbz, container, false);
        assignViews(view);
        return view;
    }

    private void assignViews(View view) {
        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshListView = (ListView) refreshLayout.getPullableView();
        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);
        adapter = new ZtxmAdapter(getActivity());
//        View empty = view.findViewById(R.id.empty_view);
//        refreshListView.setEmptyView(empty);
        refreshListView.setAdapter(adapter);
        PtrRefreshRegister.regist(refreshLayout, this);
    }


    /**
     * 第一次显示，联网加载数据；
     */
    @Override
    protected void onFirstUserVisible() {
        setCurrent();
        refreshLayout.autoRefresh();
    }

    @Override
    protected void onUserVisible() {
        LogUtil.i("Ztxm_onUserVisible调用了");
        long currentTime = System.currentTimeMillis();
        long oldTime = preferences.getLong(AppConstants.ZTXM_REFRESH_TIME, 0);
        if (currentTime-oldTime>6000){
            refreshLayout.autoRefresh();
        }
        setCurrent();
    }

    /*获取下拉刷新时的数据*/
    @Override
    public void onRefresh() {
        page = 1;
        adapter.clear();
        hkjhBeanList.clear();
        initData();
        LogUtil.i("Ztxm_onRefresh调用了");
    }

    private void initData(){
        Map<String,String> params = new HashMap<>();
//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "q":"userinfo",
//                    "type":"get_tender_list",
//                    "token":"30df57276bf44da2311d88f4e0c023eb"
//        }}

//        {"device" :"android",
//                "version":"1.0",
//                "data":{
//            "push_id":"sfsaa",
//                    "query_site":"user",
//                    "q":"appnew_ol",
//                    "type":"get_tender_list",
//        status_type",mParam1
//                    "page":"1",
//                    "epage":"3",
//                    "token":"111"
//        }}

        params.put( "push_id","sfsaa");
        params.put( "query_site","user");
        params.put( "q","userinfo");
        params.put( "page",""+page);
//        end/tender/recover
        params.put( "type","get_tender_list");
        params.put( "status_type",mParam1);
        String token = SpUtil.getSpStringValueByKey(activity,AppConstants.TOKEN,"");
        params.put("token",token);
        HttpProxy.getDataByPost(activity, AppConstants.BASE_URL, AppConstants.ZTXM_FRAGMENT_TAG, params, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                if (resultCode==101){
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
                                        Gson mGson = new Gson();
                                        HkjhBean hkjh = mGson.fromJson(jsonHkjh.toString(), HkjhBean.class);
                                        temp.add(hkjh);
                                    }
                                    hkjhBeanList.addAll(temp);
                                    adapter.appendDataList(temp);
                                    try {
                                        if(data.getInt("total_page")<=page){
                                            ToastUtil.showToastShort(activity,"没有更多了");
                                        }
                                    }catch (Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            }else if (jsonData.getInt("code")== -1) {
                                CommonUtil.loginOutTime(activity);
                                ToastUtil.showToastShort(activity, "登录超时请重新登录");
                                Bundle bundle = new Bundle();
                                readyGoWithBundle(activity, LoginActivityNew.class ,bundle);
                            } else {
                                ToastUtil.showToastShort(getActivity(), jsonData.getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }else if(resultCode==102){
                    if(isLoading){
                        isLoading = false;
                        page--;
                    }
                }

            }
        },0);

//        list = new ArrayList<>();
//        String s = "0";
//        list.add(s);
//        s = "1";
//        list.add(s);
//        s = "2";
//        list.add(s);
//        s = "3";
//        list.add(s);
//        adapter.appendDataList(list);
    }

    @Override
    public void onLoadMore() {
        isLoading = true;
        page++;
        initData();
    }



    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.ZTXM_FRAGMENT_TAG};
    }

    private void setCurrent(){
        preferences = getActivity().getSharedPreferences("ztxm", Context.MODE_PRIVATE);
        editor = preferences.edit();
        long currentTime = System.currentTimeMillis();
        editor.putLong(AppConstants.ZTXM_REFRESH_TIME, currentTime);
        editor.commit();
    }
}
