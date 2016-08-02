package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.VPAdapter;
import com.ayd.rhcf.fragment.Ztxm_Tbz_Fragment;
import com.ayd.rhcf.fragment.Zzxm_Kzr_Fragment;
import com.ayd.rhcf.fragment.Zzxm_Yjs_Fragment;
import com.ayd.rhcf.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxd on 2016/5/6.
 * 我的理财
 * 代替licaiActivity
 */
public class TouziActivity extends BaseActivity {
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
    private void init() {
        titles = getResources().getStringArray(R.array.ztxm_vp_titles);

        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.add(Ztxm_Tbz_Fragment.newInstance("tender", ""));//投标中
        fragments.add(Ztxm_Tbz_Fragment.newInstance("recover", ""));//回收中
        fragments.add(Ztxm_Tbz_Fragment.newInstance("end", ""));//已结束

        mVpIndicator = (PagerSlidingTabStrip) findViewById(R.id.vpIndicator);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        vpAdapter = new VPAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(3);
        vpAdapter.setTitles(titles);
        vpAdapter.setFragments(fragments);
        mViewPager.setAdapter(vpAdapter);

        try {
            mVpIndicator.setViewPager(mViewPager);
        } catch (IllegalStateException e) {

        }
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }
    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_ztxm;
    }
    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_ztxm);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }
}
