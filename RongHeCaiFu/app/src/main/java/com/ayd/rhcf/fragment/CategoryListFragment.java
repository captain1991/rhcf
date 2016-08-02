package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.R;

/**
 * Created by gqy on 2016/3/4.
 * 我的理财-点击直投项目弹出的灰色Fragment
 */
public class CategoryListFragment extends Fragment implements ClickEventCallBack {
    private LinearLayout mPanelZtxm;
    private LinearLayout mPanelZzxm;
    private View mFootView;
    private CategorySwitchCallBack categorySwitchCallBack;


    public void setCategorySwitchCallBack(CategorySwitchCallBack categorySwitchCallBack) {
        this.categorySwitchCallBack = categorySwitchCallBack;
    }

    /**
     * 分类切换的回调；
     */
    public interface CategorySwitchCallBack {
        int XM_ZT = 0;
        int XM_ZZ = 1;
        int NOTHING = -1;

        /**
         * @param newCategory 0:直投；1：债转；
         */
        public void switchCategory(int newCategory);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);
        init(view);
        return view;
    }

    private void init(View view) {
        mPanelZtxm = (LinearLayout) view.findViewById(R.id.panel_ztxm);
        ClickListenerRegister.regist(mPanelZtxm, this);
        mPanelZzxm = (LinearLayout) view.findViewById(R.id.panel_zzxm);
        ClickListenerRegister.regist(mPanelZzxm, this);

        mFootView = view.findViewById(R.id.footView);
        ClickListenerRegister.regist(mFootView, this);
    }

    @Override
    public void clickEventCallBack(int viewId) {
        if (mPanelZtxm.getId() == viewId) {
            if (categorySwitchCallBack != null) {
                categorySwitchCallBack.switchCategory(CategorySwitchCallBack.XM_ZT);
            }
            hideThisFragment();
        } else if (mPanelZzxm.getId() == viewId) {
            if (categorySwitchCallBack != null) {
                categorySwitchCallBack.switchCategory(CategorySwitchCallBack.XM_ZZ);
            }
            hideThisFragment();
        } else if (mFootView.getId() == viewId) {
            if (categorySwitchCallBack != null) {
                categorySwitchCallBack.switchCategory(CategorySwitchCallBack.NOTHING);
            }
            hideThisFragment();
        }
    }

    private void hideThisFragment() {
        getChildFragmentManager().beginTransaction().hide(this).commit();
    }
}
