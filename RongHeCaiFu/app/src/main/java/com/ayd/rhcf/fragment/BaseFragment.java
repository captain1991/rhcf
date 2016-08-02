package com.ayd.rhcf.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.utils.LogUtil;

import java.util.Arrays;

/**
 * Created by gqy on 2016/2/26.
 * Fragment基类；
 */
public class BaseFragment extends Fragment {
    protected ImageView titlebar_left;
    protected TextView titlebar_title;
    protected TextView titlebar_right;
    protected Activity activity;//依附的上下文；

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void readyGo(Activity context, Class claz) {
        Intent intent = new Intent(context, claz);
        context.startActivity(intent);
    }

    protected void readyGoThenFinish(Activity context, Class claz){
        Intent intent = new Intent(context, claz);
        context.startActivity(intent);
        context.finish();
    }

    protected void readyGoForResult(Activity context, Class claz, int reqCode) {
        Intent intent = new Intent(context, claz);
        startActivityForResult(intent, reqCode);
    }

    protected void readyGoWithBundle(Activity context, Class claz, Bundle bundle) {
        Intent intent = new Intent(context, claz);
        if (bundle != null) {
            intent.putExtra(AppConstants.INTENT_BUNDLE, bundle);
        }
        context.startActivity(intent);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (activity != null) {
            cancelReqByTag();
            activity = null;
        }
    }

    /**
     * 此处取消网络请求；
     */
    protected void cancelReqByTag() {
        String[] reqTagList = getReqTagList();
        if (activity != null && reqTagList != null && reqTagList.length > 0) {
            for (String req_tag : reqTagList) {
                HttpProxy.cancelSpecificRequest(activity, req_tag);
            }
            LogUtil.i("取消的tag-list=====>" + Arrays.toString(reqTagList));
        }
    }

    protected String[] getReqTagList() {
        return null;
    }

}
