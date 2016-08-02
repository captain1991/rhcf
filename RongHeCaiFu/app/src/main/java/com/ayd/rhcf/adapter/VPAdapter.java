package com.ayd.rhcf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by gqy on 2016/2/25.
 */
public class VPAdapter extends FragmentPagerAdapter {
    private final FragmentManager mFragmentManager;
    private String[] titles = null;
    private List<Fragment> mListFragments = null;

    public VPAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public void setFragments(List<Fragment> fragments) {
        this.mListFragments = fragments;
    }

    @Override
    public int getCount() {
        return null != mListFragments ? mListFragments.size() : 0;
    }

    @Override
    public Fragment getItem(int index) {
        if (mListFragments != null && index > -1 && mListFragments.size() > index) {
            return mListFragments.get(index);
        } else {
            return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles != null ? titles[position] : "";
    }
}
