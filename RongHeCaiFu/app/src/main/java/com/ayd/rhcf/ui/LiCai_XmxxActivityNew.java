package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.adapter.ShxxAdapter;
import com.ayd.rhcf.bean.BorrowDetailBean;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;
import com.ayd.rhcf.view.pulltorefresh.PullableScrollView;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import libcore.io.DiskLruCache;
import libcore.io.DiskLruUtils;

/**
 * 理财产品详情-项目信息；
 * 标详情页模板2
 * Created by yxd on 2016/5/5.
 */
public class LiCai_XmxxActivityNew extends BaseActivity implements ClickEventCallBack, ViewPager.OnPageChangeListener, PtrCallBack, HRCallBack {
    private PullToRefreshLayout refreshLayout;
    private PullableScrollView pullableScrollView;
    private ViewPager pager;
    private List<View> qzxx_views;
    private MyPageAdapter adapter;
    private ImageView iv_previous;
    private ImageView iv_next;
    private int pagerCurrentPosition = 0;
    private GridView gridView;
    private ShxxAdapter shxxAdapter;
    private TextView type_title;
    private TextView xxjs_title;
    private TextView gzzk_title;
    private TextView zcjs_title;
    private TextView jsyt_title;
    private TextView xxjs_content;
    private TextView gzzk_content;
    private TextView zcjs_content;
    private TextView jsyt_content;
    private TextView fkbz_hzjgsh;
    private TextView fkbz_rhd;
    private TextView fkbz_dyw;
    private TextView agree_account;
    private JSONObject xmxxData;
    private String borrow_nid;
    private Gson mGson;
    private DiskLruCache diskLruCache;
    File cacheDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        borrow_nid = getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE).getString("borrow_nid");
        LogUtil.e(borrow_nid + "borrow_nid");
        mGson = new Gson();
//        缓存目录
//        String cacheDirPath = CommonUtil.getDiskCacheDir(LiCai_XmxxActivityNew.this);
//        File cacheDir = new File(cacheDirPath+File.separator+"biao");
        cacheDir = DiskLruUtils.getDiskCacheDir(LiCai_XmxxActivityNew.this, "biao");

        if (!cacheDir.exists()) {
            cacheDir.mkdir();
        }

        try {
            diskLruCache = DiskLruCache.open(cacheDir, CommonUtil.getAppVersionCode(LiCai_XmxxActivityNew.this), 1, 1024 * 1024 * 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        initViews();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_xmxx);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LiCai_Xmxx_REQ_TAG};
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_li_cai_xmxxnew;
    }

    public void initViews() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        PtrRefreshRegister.regist(refreshLayout, this);
        pullableScrollView = (PullableScrollView) refreshLayout.getPullableView();
        refreshLayout.setPullUpEnable(false);
//        自动刷新
//        refreshLayout.autoRefresh();
        qzxx_views = new ArrayList<View>();
//
//        initData();
        pager = (ViewPager) findViewById(R.id.qzxx_viewpager);
        adapter = new MyPageAdapter();
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(this);
        iv_previous = (ImageView) findViewById(R.id.iv_previous);
        ClickListenerRegister.regist(iv_previous, this);
        iv_next = (ImageView) findViewById(R.id.iv_next);
        ClickListenerRegister.regist(iv_next, this);
        gridView = (GridView) findViewById(R.id.gv_shxx);
        shxxAdapter = new ShxxAdapter(this);
        gridView.setAdapter(shxxAdapter);

        type_title = (TextView) findViewById(R.id.type_title);
        xxjs_title = (TextView) findViewById(R.id.xxjs_title);
        gzzk_title = (TextView) findViewById(R.id.jzzk_title);
        zcjs_title = (TextView) findViewById(R.id.zcjs_title);
        jsyt_title = (TextView) findViewById(R.id.jkyt_title);

        xxjs_content = (TextView) findViewById(R.id.xxjs_content);
        gzzk_content = (TextView) findViewById(R.id.gzzk_content);
        zcjs_content = (TextView) findViewById(R.id.zcjs_content);
        jsyt_content = (TextView) findViewById(R.id.jsyt_content);
//        风控步骤
        fkbz_hzjgsh = (TextView) findViewById(R.id.fkbz_hzjg);
        fkbz_rhd = (TextView) findViewById(R.id.fkbz_rhdsh);
        fkbz_dyw = (TextView) findViewById(R.id.fkbz_dyw);

        agree_account = (TextView) findViewById(R.id.agree_account);
        checkCache();

    }

    public void saveCache(final String s) {
        if (diskLruCache == null) {
            return;
        }
        new Runnable() {
            @Override
            public void run() {
                try {
                    String key = DiskLruUtils.hashKeyForDisk(borrow_nid);
                    LogUtil.e("saveCache===========" + key);
                    DiskLruCache.Editor editor = diskLruCache.edit(key);
                    OutputStream os = editor.newOutputStream(0);
                    if (dLBorrowToStream(s, os)) {
                        editor.commit();
                    } else {
                        editor.abort();
                    }
                    diskLruCache.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.run();

    }

    public boolean dLBorrowToStream(String s, OutputStream os) {

        byte[] bytes = s.getBytes();
        BufferedOutputStream bos = new BufferedOutputStream(os,bytes.length);
        try {

            bos.write(bytes);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /*缓存策略*/
    public void checkCache() {

        String key = DiskLruUtils.hashKeyForDisk(borrow_nid);
        try {
            File[] files = cacheDir.listFiles();
            if (files.length <= 0) {
                refreshLayout.autoRefresh();
                return;
            }
            DiskLruCache.Snapshot snapShot = diskLruCache.get(key);
//          有缓存就加载缓存
            if (snapShot != null) {
                String jst = snapShot.getString(0);
                JSONObject jsonObject = new JSONObject(jst);
                setData(jsonObject);
            } else {
                refreshLayout.autoRefresh();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void initData() {
        if (borrow_nid == null) {
            return;
        }

        Map<String, String> map = new HashMap<>();
        map.put("push_id", JPushInterface.getRegistrationID(this));
        map.put("query_site", "user");
        map.put("q", "appnew");
        map.put("type", "get_borrow_info");
        map.put("borrow_nid", borrow_nid);
        HttpProxy.getDataByPost(LiCai_XmxxActivityNew.this, AppConstants.BASE_URL, AppConstants.LiCai_Xmxx_REQ_TAG, map, this, 1);
//        HttpProxy.getDataByGet(LiCai_XmxxActivityNew.this,AppConstants.BASE_URL, AppConstants.LiCai_Xmxx_REQ_TAG,map,this,1);


    }

    @Override
    public void clickEventCallBack(int viewId) {
        switch (viewId) {
            case R.id.iv_previous:
                pager.setCurrentItem(pagerCurrentPosition == 0 ? qzxx_views.size() - 1 : pagerCurrentPosition - 1);
                break;
            case R.id.iv_next:
                pagerCurrentPosition = pagerCurrentPosition == qzxx_views.size() - 1 ? 0 : pagerCurrentPosition + 1;
                pager.setCurrentItem(pagerCurrentPosition);
                break;
            default:
                return;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        pagerCurrentPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onRefresh() {
//        checkCache();
        initData();
//        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void onLoadMore() {
        initData();
        refreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
    }

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        if (resultCode == 101) {
            xmxxData = (JSONObject) responseData[0];
//            dealData(xmxxData);
            saveCache(xmxxData.toString());
            setData(xmxxData);
        }else{
        }
    }

    private void setData(JSONObject data) {
        BorrowDetailBean detailBean = mGson.fromJson(data.toString(), BorrowDetailBean.class);
        if (detailBean == null) {
            return;
        }
        BorrowDetailBean.DataBean dataBean = detailBean.getData();
        if (dataBean == null) {
            return;
        }
        String viewType = dataBean.getView_type();
        if (viewType == null) {
            return;
        }
//        3企业，2个人，1债转
        if (viewType.equals("3")) {
            type_title.setText("借款企业信息");
            xxjs_title.setText("企业名称：");
            gzzk_title.setText("企业法人信息：");
            zcjs_title.setText("资产介绍：");
        } else if (viewType.equals("2")) {
            type_title.setText("借款人信息");
            fkbz_hzjgsh.setVisibility(View.GONE);
        }
        BorrowDetailBean.DataBean.UserinfoBean userinfoBean = detailBean.getData().getUserinfo();
        if (userinfoBean == null) {
            return;
        }
        xxjs_content.setText(userinfoBean.getRealname());
        gzzk_content.setText(userinfoBean.getWork_city());
        zcjs_content.setText(userinfoBean.getIs_house());
        jsyt_content.setText(userinfoBean.getIs_car());
        fkbz_dyw.setText("抵押物:" + dataBean.getBorrow_pawn_description());
        fkbz_rhd.setText("融和贷审核:" + dataBean.getUserinfo().getCompany_position());
        agree_account.setText("同意放款" + CommonUtil.jeFormat(Double.parseDouble(userinfoBean.getWork_year())) + "元");
        List<String> shxx = new ArrayList<>();
        BorrowDetailBean.DataBean.UserinfoBean.CompanyTypeBean typeBean =
                detailBean.getData().getUserinfo().getCompany_type();
        if (typeBean.getCldj() != null && !typeBean.getCldj().equals("")) {
            shxx.add("cldj");
        }
        if (typeBean.getCldjfp() != null && !typeBean.getCldjfp().equals("")) {
            shxx.add("cldjfp");
        }
        if (typeBean.getCljq() != null && !typeBean.getCljq().equals("")) {
            shxx.add("cljq");
        }
        if (typeBean.getClpg() != null && !typeBean.getClpg().equals("")) {
            shxx.add("getClpg");
        }
        if (typeBean.getClsy() != null && !typeBean.getClsy().equals("")) {
            shxx.add("clsy");
        }
        if (typeBean.getFcz() != null && !typeBean.getFcz().equals("")) {
            shxx.add("fcz");
        }
        if (typeBean.getFwsd() != null && !typeBean.getFwsd().equals("")) {
            shxx.add("fwsd");
        }
        if (typeBean.getGtz() != null && !typeBean.getGtz().equals("")) {
            shxx.add("gtz");
        }
        if (typeBean.getGzzm() != null && !typeBean.getGzzm().equals("")) {
            shxx.add("gzzm");
        }
        if (typeBean.getHkb() != null && !typeBean.getHkb().equals("")) {
            shxx.add("hkb");
        }
        if (typeBean.getJhz() != null && !typeBean.getJhz().equals("")) {
            shxx.add("jhz");
        }
        if (typeBean.getQsdc() != null && !typeBean.getQsdc().equals("")) {
            shxx.add("qsdc");
        }
        if (typeBean.getSfz() != null && !typeBean.getSfz().equals("")) {
            shxx.add("sfz");
        }
        if (typeBean.getSrzm() != null && !typeBean.getSrzm().equals("")) {
            shxx.add("srzm");
        }
        if (typeBean.getXsz() != null && !typeBean.getXsz().equals("")) {
            shxx.add("xsz");
        }
        if (typeBean.getYhls() != null && !typeBean.getYhls().equals("")) {
            shxx.add("yhls");
        }
        if (typeBean.getZxbg() != null && !typeBean.getZxbg().equals("")) {
            shxx.add("zxbg");
        }

        shxxAdapter.setStrings(shxx);
        List<String> urlList = detailBean.getData().getUpfiles_pic();
        addvpImage(urlList);
    }

    /*
    * 底部viewPage添加图片
    * */
    public void addvpImage(List<String> urls) {
        BitmapUtils bitmapUtils = RhcfApp.getBitmapUtils(this
                .getApplicationContext());
        for (String url : urls) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            bitmapUtils.display(imageView, AppConstants.BASE_URL + url);
            qzxx_views.add(imageView);
        }

        //==============================测试数据
//        ImageView imageView = new ImageView(getApplicationContext());
//        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
////        imageView.setImageResource(R.drawable.cpxqforeground);
//        bitmapUtils.display(imageView,"http://pic.youngt.com/static/team/2015/0204/14230151388959.jpg");
//
//        ImageView imageView1 = new ImageView(getApplicationContext());
//        imageView1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
////        imageView.setImageResource(R.drawable.cpxqforeground);
//        bitmapUtils.display(imageView1,"http://pic.youngt.com/static/team/2015/0204/14230151388959.jpg");

//        ImageView imageView1 = new ImageView(this);
//        imageView1.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        imageView1.setImageResource(R.drawable.cpxqforeground);
//
//        ImageView imageView2 = new ImageView(this);
//        imageView2.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        imageView2.setImageResource(R.drawable.cpxqforeground);
//
//        ImageView imageView3 = new ImageView(this);
//        imageView3.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
//        imageView3.setImageResource(R.drawable.cpxqforeground);

//        qzxx_views.add(imageView2);
//        qzxx_views.add(imageView3);
        adapter.notifyDataSetChanged();
    }

    private String type;

    public void dealData(JSONObject data) {
        try {
            type = data.getJSONObject("data").getJSONObject("pawn_info").getString("view_type");
            JSONObject riskObject = data.getJSONObject("data").getJSONObject("risk");

            if (type.equals("3")) {
                type_title.setText("借款企业信息");
            } else {
                type_title.setText("借款人信息");
            }

            xxjs_content.setText(riskObject.getString("company_position"));
            gzzk_content.setText(riskObject.getString("work_city"));
            zcjs_content.setText(riskObject.getString("is_house"));
            jsyt_content.setText(riskObject.getString("is_car"));
            String[] shxx = riskObject.getString("company_type").split(",");
//            shxxAdapter.setStrings(shxx);

            fkbz_rhd.setText("融和贷审核:" + riskObject.getString("ronghe_check"));
            fkbz_dyw.setText("抵押物:" + data.getJSONObject("data").getJSONObject("pawn_info").getString("borrow_pawn_description"));

            agree_account.setText("同意放款" + CommonUtil.jeFormat(Double.parseDouble(riskObject.getString("work_year"))) + "元");

            BitmapUtils bitmapUtils = RhcfApp.getBitmapUtils(this
                    .getApplicationContext());
            JSONArray img_paths = data.getJSONObject("data").getJSONArray("img_path");
            if (img_paths != null && img_paths.length() > 0) {
                for (int i = 0; i < img_paths.length(); i++) {
                    ImageView imageView = new ImageView(getApplicationContext());
                    imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    LogUtil.i("LiCai_xmxx_img>>>>" + img_paths.get(i));
                    bitmapUtils.display(imageView, AppConstants.BASE_URL_RONGHE + img_paths.get(i));
                    qzxx_views.add(imageView);
                }
                adapter.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class MyPageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return qzxx_views.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(qzxx_views.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(qzxx_views.get(position));
            return qzxx_views.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (diskLruCache != null) {
            try {
                diskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
