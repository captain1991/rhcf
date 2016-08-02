package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.LicaiCpAdapter;
import com.ayd.rhcf.bean.BannarBean;
import com.ayd.rhcf.bean.BorrowBean;
import com.ayd.rhcf.bean.TestBean;
import com.ayd.rhcf.ui.AnquanActivity;
import com.ayd.rhcf.ui.GywmActivity;
import com.ayd.rhcf.ui.LccpDetailActivity;
import com.ayd.rhcf.ui.PtggActivity;
import com.ayd.rhcf.ui.SyjsActivity;
import com.ayd.rhcf.ui.YqhyActivity;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.UmsUtil;
import com.ayd.rhcf.view.SlideShowView;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;
import com.ayd.rhcf.view.pulltorefresh.PullableListView;
import com.google.gson.Gson;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;

/**
 * 首页Fragment；
 * created by gqy on 2016/2/23
 */
public class ShouyeFragment extends BaseFragment implements PtrCallBack, ClickEventCallBack,
        AdapterView.OnItemClickListener, HRCallBack, MyEventCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private LinearLayout mPanelGywm;
    private LinearLayout mPanelPtgg;
    private LinearLayout mPanelYqhy;
    private LinearLayout mPanelSyjs;
    private PullToRefreshLayout refreshLayout;
    private ListView refreshListView;
    private SlideShowView mSlideShowView;
    private LicaiCpAdapter madapter = null;
    private ScheduledExecutorService ses;
    private Gson mGson;
    public long server_time;
    private List<BorrowBean> beans;
    private BannarBean bannarBean;

    public static ShouyeFragment newInstance(String param1, String param2) {
        ShouyeFragment fragment = new ShouyeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ShouyeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_shouye, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mGson = new Gson();
        mSlideShowView = (SlideShowView) view.findViewById(R.id.slideShowView);
        initSlideShowView();
        mSlideShowView.setEventCallback(this);
        mPanelGywm = (LinearLayout) view.findViewById(R.id.panel_gywm);
        mPanelPtgg = (LinearLayout) view.findViewById(R.id.panel_ptgg);
        mPanelYqhy = (LinearLayout) view.findViewById(R.id.panel_yqhy);
        mPanelSyjs = (LinearLayout) view.findViewById(R.id.panel_syjs);
        ClickListenerRegister.regist(mPanelGywm, this);
        ClickListenerRegister.regist(mPanelPtgg, this);
        ClickListenerRegister.regist(mPanelYqhy, this);
        ClickListenerRegister.regist(mPanelSyjs, this);

        refreshLayout = (PullToRefreshLayout) view.findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);
        refreshLayout.setPullUpEnable(false);

        refreshListView = (ListView) refreshLayout.getPullableView();
        refreshListView.setFooterDividersEnabled(false);
        refreshListView.setHeaderDividersEnabled(false);
        madapter = new LicaiCpAdapter(activity);
        refreshListView.setAdapter(madapter);
        refreshListView.setOnItemClickListener(this);
        if(ses == null) {
//    更新时间用的
            ses = Executors.newSingleThreadScheduledExecutor();
//        if(ses.isShutdown()) {
            ses.scheduleAtFixedRate(new UpdateAdapterTask(), 1, 1, TimeUnit.SECONDS);
//        }
        }
        //首次自动刷新；
        refreshLayout.autoRefresh();
        initData();
    }


    JSONObject shouyeData ;
    private void initData() {
//banner
        HashMap<String,String> param1 = new HashMap<>();
        param1.put("query_site","user");
        param1.put("q","appnew");
        param1.put("type","get_index_banner");
        HttpProxy.getDataByPost(getContext(), AppConstants.BASE_URL, AppConstants.SHOUYE_REQBN_TAG, param1, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if(responseData[0] instanceof JSONObject){
                    JSONObject imgeJson = (JSONObject) responseData[0];
                    bannarBean = mGson.fromJson(imgeJson.toString(),BannarBean.class);
                    shouYeHandler.sendEmptyMessage(MSG_WHAT_BANNAR_RESPONSE);
                }
            }
        }, 0);
         /*====================================测试数据*/
//        List<TestBean> beans = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            TestBean bean = new TestBean();
//            beans.add(bean);
//        }
//        adapter.appendDataList(beans);
//        adapter.notifyDataSetChanged();
        /*=========================================*/
    }


    private Handler shouYeHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what==MSG_WHAT) {
//                if (adapter != null) {
//                    List<TestBean> dataList = adapter.getDataList();
                    if (beans != null && beans.size() > 0) {
                        for (BorrowBean borrowBean:beans){
                          long endTime =  (Long.parseLong(borrowBean.getBorrow_end_time())) ;
                            endTime = endTime-1;
                            borrowBean.setBorrow_end_time("" + endTime);
                        }
//                        adapter.scheduleTime();
                    }
//                }
            }
            else if(msg.what==MSG_WHAT_RESPONSE){
                if (shouyeData.has("data")){
                    beans=new ArrayList<>();
                    try {
                        JSONObject data = (JSONObject) shouyeData.get("data");
                        if(data.has("list")){
                            JSONArray list = (JSONArray) data.get("list");
                            LogUtil.i("list长度>>>>>"+list.length());
                            for(int i = 0;i<list.length();i++){

                                JSONObject borrowJson = (JSONObject) list.get(i);
                                BorrowBean borrowBean = mGson.fromJson(borrowJson.toString(),BorrowBean.class);
                                beans.add(borrowBean);
                            }
                            madapter.appendBorrowList(beans);
                            refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }else if(msg.what==MSG_WHAT_BANNAR_RESPONSE) {
                try {


                    List<String> urlList = new ArrayList<>();
                    for (int i = 0; i < bannarBean.getData().getActiv_list().size(); i++) {
                        urlList.add("http://img1.imgtn.bdimg.com/it/u=3573202674,1863124697&fm=21&gp=0.jpg");
//                urlList.add(AppConstants.BASE_URL+bannarBean.getData().getActiv_list().get(i).getScroll_pic_url());
                    }
//                urlList.add("http://img1.imgtn.bdimg.com/it/u=3573202674,1863124697&fm=21&gp=0.jpg");
//                urlList.add("http://img1.imgtn.bdimg.com/it/u=3573202674,1863124697&fm=21&gp=0.jpg");
//                urlList.add("http://img1.imgtn.bdimg.com/it/u=3573202674,1863124697&fm=21&gp=0.jpg");
                    mSlideShowView.setImageDataList(urlList);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    };


    private static final int MSG_WHAT = 0x1111;
    private static final int MSG_WHAT_RESPONSE = 0x1112;
    private static final int MSG_WHAT_BANNAR_RESPONSE = 0x1113;

//    更新时间用的
    private class UpdateAdapterTask implements Runnable {
        @Override
        public void run() {
            shouYeHandler.sendEmptyMessage(MSG_WHAT);
        }
    }

    private void initSlideShowView() {
        ViewGroup.LayoutParams vpLp = mSlideShowView.getLayoutParams();
        vpLp.height = (int) (0.5 + CommonUtil.getScreenWidth(activity) / AppConstants.VP_IMG_WH_SCALE);
        mSlideShowView.setLayoutParams(vpLp);


    }

    @Override
    public void onRefresh() {
        HashMap<String,String> param = new HashMap<>();
        String push_id = JPushInterface.getRegistrationID(getContext());
        param.put("push_id",push_id);
        param.put("query_site","user");
        param.put("q","appnew");
        param.put("type", "get_index_list");
        HttpProxy.getDataByPost(getContext(), AppConstants.BASE_URL, AppConstants.SHOUYE_REQ_TAG, param, this, 1);
    }

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        if(resultCode == 101) {
            shouyeData = (JSONObject) responseData[0];
            shouYeHandler.sendEmptyMessage(MSG_WHAT_RESPONSE);
        }
    }

    @Override
    public void onLoadMore() {
        refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (mPanelGywm.getId() == viewId) {
            if(CommonUtil.isConnected(getActivity())){
                readyGo(activity, GywmActivity.class);
            }else {
                ToastUtil.showToastShort(getActivity(),"无网络，请稍后重试");
            }
        } else if (mPanelPtgg.getId() == viewId) {
//            UmsUtil.goShouquan(getActivity(), SHARE_MEDIA.QQ);
            readyGo(activity, PtggActivity.class);
        } else if (mPanelYqhy.getId() == viewId) {
//            readyGo(activity, YqhyActivity.class);
//            UmsUtil.openShareBoad(getActivity());
            final float scale = getActivity().getResources().getDisplayMetrics().density;
            LogUtil.e("density===scale="+scale);
            UmsUtil.showShareDialog(getActivity());
        } else if (mPanelSyjs.getId() == viewId) {
//            readyGo(activity, SyjsActivity.class);
            if(CommonUtil.isConnected(getActivity())){
                readyGo(activity, AnquanActivity.class);
            }else {
                ToastUtil.showToastShort(getActivity(),"无网络，请稍后重试");
            }
        }else if (viewId==mSlideShowView.getId()){
            int posi = mSlideShowView.getCurrentItem();
            LogUtil.i("mSlideShowView>>>>click>>>"+posi);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LogUtil.i("onItemClick>>>>position");
        Bundle bundle = new Bundle();
        bundle.putSerializable(AppConstants.BUNDLE_BEAN,beans.get(position));
        bundle.putString("", "");
        readyGoWithBundle(activity, LccpDetailActivity.class, bundle);
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.SHOUYE_REQ_TAG};
    }

    @Override
    public void onDestroy() {
        if (ses != null && !ses.isShutdown()) {
            ses.shutdown();
        }
        mSlideShowView.stopPlay();
        super.onDestroy();
    }

    @Override
    public void adapterEventCallBack(Object... args) {
        if(args[0] instanceof Integer){
            LogUtil.i("Bannar点击======="+args[0]);
        }
    }
}
