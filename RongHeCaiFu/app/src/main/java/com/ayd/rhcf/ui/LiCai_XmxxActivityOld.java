package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.PtrCallBack;
import com.ayd.rhcf.PtrRefreshRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.adapter.XmxxPicAdapter;
import com.ayd.rhcf.bean.BorrowBean;
import com.ayd.rhcf.bean.OldBorrowBean;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.view.NoScrollListView;
import com.ayd.rhcf.view.pulltorefresh.PullToRefreshLayout;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 理财产品详情-项目信息；
 * 标详情页模板1
 * Created by yxd on 2016/6/7.
 */
public class LiCai_XmxxActivityOld extends BaseActivity implements HRCallBack, PtrCallBack {

    private PullToRefreshLayout refreshLayout;
    private ListView listView;
    private XmxxPicAdapter adapter;
    private LinearLayout layout;
    private String borrow_nid;
    private JSONObject xmxxData;
    private TextView third;
    private TextView name;
    private TextView sex;
    private TextView birth;
    private TextView marry;
    private TextView xueli;
    private TextView work_city;
    private TextView house;
    private TextView car;
    private TextView work_year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        borrow_nid = getIntent().getBundleExtra(AppConstants.INTENT_BUNDLE).getString("borrow_nid");
        initView();
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
    protected int getLayoutResId() {
        return R.layout.activity_li_cai_xmxxold;
    }

    public void initView() {
        refreshLayout = (PullToRefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setPullUpEnable(false);
        refreshLayout.setPullDownEnable(true);
        PtrRefreshRegister.regist(refreshLayout, this);
        layout = (LinearLayout) findViewById(R.id.content_layout);
        third = (TextView) findViewById(R.id.tv_third);
        name = (TextView) findViewById(R.id.real_name);
        sex = (TextView) findViewById(R.id.sex);
        birth = (TextView) findViewById(R.id.birth);
        marry = (TextView) findViewById(R.id.marry);
        xueli = (TextView) findViewById(R.id.xueli);
        work_city = (TextView) findViewById(R.id.work_city);
        house = (TextView) findViewById(R.id.house);
        car = (TextView) findViewById(R.id.car);
        work_year = (TextView) findViewById(R.id.work_year);
//        dywlx = (TextView) findViewById(R.id.dywlx);
//        pgjz = (TextView) findViewById(R.id.pgjz);
//        tv_qdsj = (TextView) findViewById(R.id.tv_qdsj);
//        tv_sqed = (TextView) findViewById(R.id.tv_sqed);
//        qkms = (TextView) findViewById(R.id.qkms);
        refreshLayout.autoRefresh();

    }

    public void initData() {
        Map<String, String> map = new HashMap<>();
        map.put("push_id", JPushInterface.getRegistrationID(this));
        map.put("query_site", "user");
        map.put("q", "appnew");
        map.put("type", "get_borrow_info");
        map.put("borrow_nid", borrow_nid);
        HttpProxy.getDataByPost(LiCai_XmxxActivityOld.this, AppConstants.BASE_URL, AppConstants.LiCai_Xmxx_REQ_TAG, map, this, 1);

    }

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        refreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
        if (resultCode == 101) {
            xmxxData = (JSONObject) responseData[0];
            dealData();
        }
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LiCai_XmxxOld_REQ_TAG};
    }

    public void dealData() {
        Gson mGson = new Gson();
        OldBorrowBean borrowBean = null;
        try{
            borrowBean = mGson.fromJson(xmxxData.toString(), OldBorrowBean.class);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (borrowBean == null) {
            return;
        }
        if (borrowBean.getCode().equals("0")) {
            OldBorrowBean.DataBean dataBean = borrowBean.getData();
            if (dataBean == null) {
                return;
            }
            third.setText(borrowBean.getData().getRonghe_third());
            name.setText("姓名：" + borrowBean.getData().getUser_info().getRealname());
            sex.setText("性别：" + borrowBean.getData().getUser_info().getSex());
            birth.setText("出生：" + borrowBean.getData().getUser_info().getBirthday());
            marry.setText("婚否：" + borrowBean.getData().getUser_info().getMarry());
            xueli.setText("学历：" + borrowBean.getData().getUser_info().getDegree());
            work_city.setText("工作城市：" + borrowBean.getData().getUser_info().getWork_city());
            house.setText("有无购房：" + borrowBean.getData().getUser_info().getIs_house());
            car.setText("有无购车：" + borrowBean.getData().getUser_info().getIs_car());
            work_year.setText("工作年限：" + borrowBean.getData().getUser_info().getWork_year());
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
//                Date date = new Date(Long.parseLong(""));
//                String time = format.format(date);

            List<String> urls = borrowBean.getData().getUpfiles_pic();
            if (urls==null){
                return;
            }
            BitmapUtils bitmapUtils = RhcfApp.getBitmapUtils(this
                    .getApplicationContext());
            for (int i = 0; i < urls.size(); i++) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_xmxx_pic, null);
                ImageView imageView = (ImageView) view.findViewById(R.id.xmxx_old_img);
//                    http://www.ronghedai.com/dyupfiles/images/2016-06/23/0_admin_upload_1466667409650.jpg
                LogUtil.i("LiCai_xmxx_old_img>>>>" + urls.get(i));
                bitmapUtils.display(imageView, AppConstants.BASE_URL + urls.get(i));
                layout.addView(view);
            }
        } else {
            ToastUtil.showToastShort(this, borrowBean.getMsg());
        }
    }

    @Override
    public void onRefresh() {
        initData();
    }

    @Override
    public void onLoadMore() {

    }
}
