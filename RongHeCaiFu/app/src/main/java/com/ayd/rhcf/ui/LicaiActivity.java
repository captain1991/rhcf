package com.ayd.rhcf.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.R;
import com.ayd.rhcf.fragment.CategoryListFragment;
import com.ayd.rhcf.fragment.ZtxmFragment;
import com.ayd.rhcf.fragment.ZzxmFragment;

/**
 * 账户-我的理财；
 * created by gqy on 2016/3/4
 * 没用的activity
 */
public class LicaiActivity extends BaseActivity implements ClickEventCallBack, CategoryListFragment.CategorySwitchCallBack {
    private RelativeLayout mRlXm;
    private TextView mTitlebarTitle;
    private ImageView mIvUpDown;
    private String[] titles = null;
    private Fragment ztxmFragment;//直投；
    private Fragment zzxmFragment;//债转；
    private CategoryListFragment categoryFragment;

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
    protected int getLayoutResId() {
        return R.layout.activity_zh_mylicai;
    }

    private void init() {
        mRlXm = (RelativeLayout) findViewById(R.id.rl_xm);
        ClickListenerRegister.regist(mRlXm, this);
        mTitlebarTitle = (TextView) findViewById(R.id.titlebar_title);
        mIvUpDown = (ImageView) findViewById(R.id.iv_up_down);
        titles = getResources().getStringArray(R.array.zh_my_licai_titles);

        categoryFragment = (CategoryListFragment) getSupportFragmentManager().
                findFragmentById(R.id.categoryFragment);
        categoryFragment.setCategorySwitchCallBack(this);

        hideCategoryFragment();
        setCurFragment(0);
    }

    private void showCategoryFragment() {
        getSupportFragmentManager().beginTransaction().show(categoryFragment).commit();
        indicatorUp();
    }

    private void indicatorUp() {
        mIvUpDown.setBackgroundResource(R.drawable.iv_up);
    }

    private void indicatorDown() {
        mIvUpDown.setBackgroundResource(R.drawable.iv_down);
    }


    /**
     * 隐藏分类列表的Fragment；
     */
    private void hideCategoryFragment() {
        getSupportFragmentManager().beginTransaction().hide(categoryFragment).commit();
        mIvUpDown.setBackgroundResource(R.drawable.iv_down);
    }

    private void setCurFragment(int pageIndex) {
        hideAllFragments();
        switch (pageIndex) {
            case 0:
                if (ztxmFragment == null) {
                    ztxmFragment = ZtxmFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            ztxmFragment, AppConstants.ZTXM_FRAGMENT_TAG).commit();
                }
                getSupportFragmentManager().beginTransaction().show(ztxmFragment).commit();
                break;
            case 1:
                if (zzxmFragment == null) {
                    zzxmFragment = ZzxmFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            zzxmFragment, AppConstants.ZZXM_FRAGMENT_TAG).commit();
                }
                getSupportFragmentManager().beginTransaction().show(zzxmFragment).commit();
                break;
        }
        titlebar_title.setText(titles[pageIndex]);
    }

    private void hideAllFragments() {
        if (ztxmFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(ztxmFragment).commit();
        }
        if (zzxmFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(zzxmFragment).commit();
        }
    }

    /**
     * 项目切换的回调；
     *
     * @param newCategory 0:直投；1：债转；
     */
    @Override
    public void switchCategory(int newCategory) {
        switch (newCategory) {
            case XM_ZT:
                setCurFragment(XM_ZT);
                break;
            case XM_ZZ:
                setCurFragment(XM_ZZ);
                break;
            case NOTHING:
                break;
        }
        indicatorDown();
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (viewId == mRlXm.getId()) {
            if (categoryFragment != null) {
                if (categoryFragment.isHidden()) {
                    showCategoryFragment();
                } else {
                    hideCategoryFragment();
                }
            }
        }
    }
    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.LiCai_REQ_TAG};
    }

    @Override
    protected String getTitleBarTitle() {
        return super.getTitleBarTitle();
    }

    @Override
    protected void titleBarLeftClick() {
        finish();
    }

}
