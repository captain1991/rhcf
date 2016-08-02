package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.ayd.rhcf.R;
import com.ayd.rhcf.adapter.VPAdapter;
import com.ayd.rhcf.fragment.Zzxm_Kzr_Fragment;
import com.ayd.rhcf.fragment.Zzxm_Yjs_Fragment;
import com.ayd.rhcf.view.PagerSlidingTabStrip;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by yxd on 2016/5/6.
 * 代替licaiActivity
 */
public class ZhaizhuanActivity extends BaseActivity {
    private PagerSlidingTabStrip mVpIndicator;
    private ViewPager mViewPager;
    private VPAdapter vpAdapter = null;
    private List<Fragment> fragments = null;
    private String[] titles = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zzxm;
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_zzxm);
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

    private void init() {
        titles = getResources().getStringArray(R.array.zzxm_vp_titles);
        if (fragments == null) {
            fragments = new ArrayList<>();
        }
        fragments.add(Zzxm_Kzr_Fragment.newInstance("", ""));
        fragments.add(Zzxm_Kzr_Fragment.newInstance("", ""));
        fragments.add(Zzxm_Yjs_Fragment.newInstance("", ""));

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
}
