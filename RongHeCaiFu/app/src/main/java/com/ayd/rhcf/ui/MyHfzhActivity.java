package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.VPAdapter;
import com.ayd.rhcf.fragment.LicaiChanpinFragment;
import com.ayd.rhcf.fragment.LicaiZqzrFragment;
import com.ayd.rhcf.fragment.MyHfzh_Bdyhk_Fragment;
import com.ayd.rhcf.fragment.MyHfzh_Dzjyzh_Fragment;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.view.PagerSlidingTabStrip;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 我的汇付账号；
 * created by gqy on 2016/3/7
 */
public class MyHfzhActivity extends BaseActivity implements HRCallBack {
    private PagerSlidingTabStrip mVpIndicator;
    private ViewPager mViewPager;
    private VPAdapter vpAdapter = null;

    private String[] titles = null;
    private List<Fragment> fragments = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.MyHfzh_REQ_TAG};
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_my_hfzh;
    }

    private void init() {
        titles = getResources().getStringArray(R.array.hfzh_manage_titles);

        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.add(MyHfzh_Dzjyzh_Fragment.newInstance("", ""));
        fragments.add(MyHfzh_Bdyhk_Fragment.newInstance("", ""));

        mVpIndicator = (PagerSlidingTabStrip) findViewById(R.id.vpIndicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        vpAdapter = new VPAdapter(getSupportFragmentManager());

        vpAdapter.setTitles(titles);
        vpAdapter.setFragments(fragments);
        mViewPager.setAdapter(vpAdapter);

        try {
            mVpIndicator.setViewPager(mViewPager);
            mVpIndicator.setOnPageChangeListener(pageChangeListener);
        } catch (IllegalStateException e) {

        }
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position == 0) {
                setTitlebarRightVisiblity(false);
            } else if (position == 1) {
                setTitlebarRightVisiblity(true);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    /**
     * 添加银行卡；
     */
    @Override
    protected void titleBarRightClick() {
        showWOrDismissDialog(0,"请稍等...");
        Map<String,String> params = new HashMap<>();
        String token = SpUtil.getSpStringValueByKey(this,AppConstants.TOKEN,"");
        params.put("push_id", JPushInterface.getRegistrationID(this));
        params.put("query_site", "user");
        params.put("m", "trust/bank/bindbank");
        params.put("token", token);
//        params.put("PHPSESSID",app.session);
        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.MyHfzh_REQ_TAG, params, this, 0);
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_myhfzh);
    }

    @Override
    protected String getTitleBarRightText() {
        return getString(R.string.text_tj);
    }

    public void showWOrDismissDialog(int reqCode, String msg) {
        WaitingDialog wDialog = WaitingDialog.getInstance().setMsg(msg);
//        0 打开
        if (reqCode == 0) {
            wDialog.show(getSupportFragmentManager(), "WaitingDialog");
        }else {
            wDialog.dismiss();
        }
    }

    @Override
    public void httpResponse(int resultCode, Object... responseData) {
        showWOrDismissDialog(1,"");
        if(resultCode==101){
            if(responseData[0] instanceof JSONObject){
                JSONObject resp = (JSONObject) responseData[0];
                try {
                    if (resp.getInt("ret") == 0) {
                        String data = resp.getString("data");
//                        String cachePath = CommonUtil.getDiskCacheDir(MyHfzhActivity.this);
//                        File file = new File(cachePath+File.separator+"huifu.html");
//                        if(file.exists()){
//                            file.delete();
//                        }
//                        file = new File(cachePath+File.separator+"huifu.html");
//                        FileOutputStream fos = new FileOutputStream(file);
//                        fos.write(data.getBytes());
                        Bundle bundle = new Bundle();
                        bundle.putString("form",data);
                        readyGoWithBundle(MyHfzhActivity.this, HFTXActivity.class, bundle);

                    }else if(resp.getInt("ret") == -1){
//                        登录超时重新denglu
                        CommonUtil.loginOutTime(this);
                        ToastUtil.showToastShort(MyHfzhActivity.this, "登录超时请重新登录");
                        Bundle bundle = new Bundle();
                        readyGoWithBundle(MyHfzhActivity.this, LoginActivityNew.class, bundle);
                    }
                    else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
