package com.ayd.rhcf.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.StatusBarCompat;
import com.umeng.analytics.MobclickAgent;

import java.util.Arrays;

/**
 * Created by gqy on 2016/2/22.
 * base;
 */
public abstract class BaseActivity extends FragmentActivity {
    protected ImageView titlebar_left;
    protected TextView titlebar_title;
    protected TextView titlebar_right;

    /**
     * 获取友盟分析的Activity上下文
     *
     * @return
     */
    protected Activity getUmengAnalyzeContext() {
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        StatusBarCompat.compat(this, Color.parseColor("#0992C4"));
        initTitleBar();
    }

    /**
     * Android 6.0的权限申请的回调；暂时不用；
     * 由ActivityCompact.requestPermissions(@NonNull String[] permissions, int requestCode)请求权限发起；
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getUmengAnalyzeContext() != null)
            MobclickAgent.onResume(getUmengAnalyzeContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getUmengAnalyzeContext() != null)
            MobclickAgent.onPause(getUmengAnalyzeContext());
    }

    /**
     * 初始化titlebar；
     */
    private void initTitleBar() {
        titlebar_left = (ImageView) findViewById(R.id.titlebar_left);
        titlebar_title = (TextView) findViewById(R.id.titlebar_title);
        titlebar_right = (TextView) findViewById(R.id.titlebar_right);

        if (titlebar_left != null) {
            if (isTitleBarLeftVisible()) {
                titlebar_left.setVisibility(View.VISIBLE);
                titlebar_left.setImageResource(0);
                titlebar_left.setOnClickListener(leftClickListener);
            } else {
                titlebar_left.setVisibility(View.GONE);
            }
        }

        if (titlebar_right != null) {
            setTitlebarRightVisiblity(isTitleBarRightVisible());
        }

        if (titlebar_title != null) {
            titlebar_title.getPaint().setFakeBoldText(true);
            titlebar_title.setText(getTitleBarTitle());
        }

        if (titlebar_right != null) {
            titlebar_right.setText(getTitleBarRightText());
        }
    }

    /**
     * 设置titlebar右侧的TextView的可见性；
     *
     * @param isVisible
     */
    protected void setTitlebarRightVisiblity(boolean isVisible) {
        if (isVisible) {
            titlebar_right.setVisibility(View.VISIBLE);
            titlebar_right.setOnClickListener(rightClickListener);
        } else {
            titlebar_right.setVisibility(View.GONE);
        }
    }

    /**
     * 获取资源id；
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 设置titlebar的title；
     */
    protected String getTitleBarTitle() {
        return "";
    }

    /**
     * 设置titlebar右侧的TextView的内容；
     */
    protected String getTitleBarRightText() {
        return "";
    }

    /**
     * titlebar的左侧是否可见；
     *
     * @return
     */
    protected boolean isTitleBarLeftVisible() {
        return true;
    }

    /**
     * titlebar的右侧是否可见；
     *
     * @return
     */
    protected boolean isTitleBarRightVisible() {
        return true;
    }

    /**
     * titlebar左侧view点击监听；
     */
    protected View.OnClickListener leftClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            titleBarLeftClick();
        }
    };

    /**
     * titlebar右侧view点击监听；
     */
    protected View.OnClickListener rightClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            titleBarRightClick();
        }
    };

    /**
     * 执行titlebar左侧view点击事件；
     */
    protected void titleBarLeftClick() {

    }

    /**
     * 执行titlebar右侧view点击事件；
     */
    protected void titleBarRightClick() {

    }


    /**
     * Activity跳转并finish；
     */
    protected void readyGoThenFinish(Activity context, Class claz) {
        Intent intent = new Intent(context, claz);
        context.startActivity(intent);
        context.finish();
    }

    /**
     * Activity跳转不finish；
     */
    protected void readyGo(Activity context, Class claz) {
        Intent intent = new Intent(context, claz);
        context.startActivity(intent);
    }

    /**
     * Activity跳转并返回result；
     */
    protected void readyGoForResult(Activity context, Class claz, int reqCode) {
        Intent intent = new Intent(context, claz);
        startActivityForResult(intent, reqCode);
    }
    /**
     * Activity跳转并返回result并附带Bundle参数；
     */
    protected void readyGoForResultWB(Activity context, Class claz,Bundle bundle, int reqCode) {
        Intent intent = new Intent(context, claz);
        if (bundle != null) {
            intent.putExtra(AppConstants.INTENT_BUNDLE, bundle);
        }
        startActivityForResult(intent, reqCode);
    }

    /**
     * Activity跳转并附带Bundle参数；
     */
    protected void readyGoWithBundle(Activity context, Class claz, Bundle bundle) {
        Intent intent = new Intent(context, claz);
        if (bundle != null) {
            intent.putExtra(AppConstants.INTENT_BUNDLE, bundle);
        }
        context.startActivity(intent);
    }

    /**
     * Activity销毁之前取消网络请求；
     */
    @Override
    protected void onDestroy() {
        cancelReqByTag();
        super.onDestroy();
    }

    /**
     * 此处取消网络请求；
     */
    protected void cancelReqByTag() {
        String[] reqTagList = getReqTagList();
        if (reqTagList != null && reqTagList.length > 0) {
            for (String req_tag : reqTagList) {
                HttpProxy.cancelSpecificRequest(this, req_tag);
            }
            LogUtil.i("取消的tag-list=====>" + Arrays.toString(reqTagList));
        }
    }

    /**
     * 获得请求的标签列表；
     * 要取消的请求的所有tag都在这里定义；
     * 子类需要重写；
     *
     * @return
     */
    protected String[] getReqTagList() {
        return null;
    }
}
