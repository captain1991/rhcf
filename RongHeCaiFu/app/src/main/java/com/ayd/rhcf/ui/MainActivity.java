package com.ayd.rhcf.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.Process;
import android.service.chooser.ChooserTargetService;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.AppDownloadService;
import com.ayd.rhcf.AppNotification;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.fragment.AccountFragment;
import com.ayd.rhcf.fragment.LicaiChanpinFragment;
import com.ayd.rhcf.fragment.LicaiFragment;
import com.ayd.rhcf.fragment.MoreFragment;
import com.ayd.rhcf.fragment.ShouyeFragment;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.ayd.rhcf.utils.UmsUtil;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

/**
 * 主页；
 * created by gqy on 2016/2/22
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String FRAGMENT_TAG0 = "FRAGMENT_TAG0";
    private static final String FRAGMENT_TAG1 = "FRAGMENT_TAG1";
    private static final String FRAGMENT_TAG2 = "FRAGMENT_TAG2";
    private static final String FRAGMENT_TAG3 = "FRAGMENT_TAG3";
    public  LinearLayout mPanelShouye;
    public LinearLayout mPanelLicai;
    public LinearLayout mPanelZhanghu;
    public LinearLayout mPanelMore;

    private ShouyeFragment shouyeFragment;
    private LicaiFragment licaiFragment;
    private LicaiChanpinFragment licaiChanpinFragment;
    private AccountFragment accountFragment;
    private MoreFragment moreFragment;

    private ImageView mIvShouye;
    private TextView mTvShouye;
    private ImageView mIvLicai;
    private TextView mTvLicai;

    private ImageView mIvZhanghu;
    private TextView mTvZhanghu;
    private ImageView mIvMore;
    private TextView mTvMore;
    private int curIndex = 0; //当前page的index；

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        LogUtil.i("onCreate===============");
//        Intent intent = getIntent();
//        if (intent!=null&&intent.getBundleExtra(AppConstants.INTENT_BUNDLE)!=null&&intent.getBundleExtra(AppConstants.INTENT_BUNDLE).getBoolean("isReLogin")){
//                    super.onCreate(null);
//        }else {
            super.onCreate(savedInstanceState);
//        }
        init();
        selectTab(0);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    private void init() {
        mPanelShouye = (LinearLayout) findViewById(R.id.panel_shouye);
        mPanelLicai = (LinearLayout) findViewById(R.id.panel_licai);
        mPanelZhanghu = (LinearLayout) findViewById(R.id.panel_zhanghu);
        mPanelMore = (LinearLayout) findViewById(R.id.panel_more);
        mPanelShouye.setOnClickListener(this);
        mPanelLicai.setOnClickListener(this);
        mPanelZhanghu.setOnClickListener(this);
        mPanelMore.setOnClickListener(this);

//        HttpProxy.getDataByGet(this, "FFFFF", null);
//        Map<String, String> map = new HashMap<>();
//        map.put("11", "11_");
//        map.put("22", "22_");
//        map.put("耿奇云", "gqy");
//        HttpProxy.getDataByPost(this, "FFFFF", map);

        mIvShouye = (ImageView) findViewById(R.id.iv_shouye);
        mTvShouye = (TextView) findViewById(R.id.tv_shouye);
        mIvLicai = (ImageView) findViewById(R.id.iv_licai);
        mTvLicai = (TextView) findViewById(R.id.tv_licai);
        mIvZhanghu = (ImageView) findViewById(R.id.iv_zhanghu);
        mTvZhanghu = (TextView) findViewById(R.id.tv_zhanghu);
        mIvMore = (ImageView) findViewById(R.id.iv_more);
        mTvMore = (TextView) findViewById(R.id.tv_more);
        if(!CommonUtil.isConnected(this)){
            ToastUtil.showToastShort(this,"网络未连接");
            return;
        }
//        通知跳到shouryeFragment；
        registChangeRv();

        //检查更新是的BroadcastReceiver；
        registAppUpdateReceiver();
        // 检查新版本；
        AppDownloadService.checkNewVersion(this);

        // 下载补丁文件(用语热更新)；
//        AppDownloadService.downloadFile(this, AppConstants.PATCH_FILE_PATH, AppConstants.PATCH_DOWN_ID);
        // 下载apk文件(如果需要更新的话，这里只是测试)；
//        AppDownloadService.downloadFile(this, AppConstants.APK_FILE_PATH, AppConstants.APP_UPDATE_ID);


        // 测试获取设备的唯一id；
        LogUtil.i("getUUID==>" + CommonUtil.getUUID(this));
//        LogUtil.i("verifyPointAndEn==>" + CommonUtil.verifyPointAndEn("434fd1tertretertertre"));
        CommonUtil.isMobilePhone("14056066230");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        只是在开发测试时评估推送效果，发布打包时将不会出现；
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    private void registAppUpdateReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(AppConstants.CHECK_UPDATE_RECEIVER_ACTION);
        filter.addAction(AppConstants.DOWNLOAD_RECEIVER_ACTION);
        this.registerReceiver(receiver, filter);
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (AppConstants.CHECK_UPDATE_RECEIVER_ACTION.equals(action)) {
                boolean updateFlag = intent.getBooleanExtra("need_update", false);

                LogUtil.i("updateFlag==>" + updateFlag);
                if (updateFlag) {
                    showUpdateDialog("");
                }

            } else if (AppConstants.DOWNLOAD_RECEIVER_ACTION.equals(action)) {

                /*
                 * -1:下载失败；
                 * 0：下载中；
                 * 1：下载完成；
                 */
                int flag = intent.getIntExtra("flag", -1);
                LogUtil.i("下载任务接收到的flag====" + flag);
                switch (flag) {
                    case -1:
                        int task_id = intent.getIntExtra("task_id", -1);
                        if (task_id == AppConstants.APP_UPDATE_ID) {
                            ToastUtil.showToastLong(MainActivity.this, R.string.download_fail);
                        }
                        break;
                    case 0:
                        int rate = intent.getIntExtra("rate", 0);
                        if (rate == 100) {
                            AppNotification.cancelAppUpdateNotification(getApplicationContext(), R.string.app_name + 1);
                        } else {
                            AppNotification.setNotification(getApplicationContext(),
                                    (rate + "%"), R.string.app_name + 1);
                        }
                        break;
                }
            }
        }
    };

    private void registChangeRv(){
        IntentFilter filter = new IntentFilter();
        filter.addAction("changePage");
        registerReceiver(changePageRv,filter);
    }

//    changePage
    private BroadcastReceiver changePageRv = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            selectTab(0);
        }

    };

    /**
     * 更新弹框；
     *
     * @param downloadUrl
     */
    private void showUpdateDialog(String downloadUrl) {
//        if (CommonUtil.verifyHttpUrl(downloadUrl)) {
        NoticeDialog nd = new NoticeDialog();
        nd.setCallBack(new MyEventCallBack() {
            @Override
            public void adapterEventCallBack(Object... args) {
                if (args != null && args.length > 0 && args[0] instanceof Integer) {
                    int position = (int) args[0];
                    if (position == 1) {
                        //升级操作；
                        ToastUtil.showToastShort(MainActivity.this, "开始升级");
                        AppDownloadService.downloadFile(MainActivity.this, AppConstants.APK_FILE_PATH, AppConstants.APP_UPDATE_ID);
                    }
                }
            }
        });

        Spanned spanned = Html.fromHtml("1,update1;<br />2,update2;<br />3,update3;");
        nd.setContent(spanned == null ? "" : spanned.toString());
        nd.setTitle(getString(R.string.text_find_new_version));
        nd.setYesLabel(getString(R.string.text_ljsj));
        nd.setNoLabel(getString(R.string.text_zbsj));
        nd.show(getSupportFragmentManager(), "update_dialog");
//        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panel_shouye:
                if (curIndex != 0) {
                    selectTab(0);
                }
                break;
            case R.id.panel_licai:
                if (curIndex != 1) {
                    selectTab(1);
                }
                break;
            case R.id.panel_zhanghu:
                if (curIndex != 2) {
                    selectTab(2);
                }
                break;
            case R.id.panel_more:
                if (curIndex != 3) {
                    selectTab(3);
                }
//                UmsUtil.openShareBoad(this);
                break;
        }
    }

    /**
     * 页面切换；
     */
    private void selectTab(int index) {
        hideOldFragment(curIndex);
        switch (index) {
            case 0:
                findViewById(R.id.rl_root).setVisibility(View.VISIBLE);
                if (shouyeFragment == null) {
                    shouyeFragment = ShouyeFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            shouyeFragment, FRAGMENT_TAG0).commit();
                }
                getSupportFragmentManager().beginTransaction().show(shouyeFragment).commit();
                titlebar_title.setText(getString(R.string.app_name));
                break;
            case 1:
                findViewById(R.id.rl_root).setVisibility(View.VISIBLE);
                if (licaiChanpinFragment == null) {
                    licaiChanpinFragment = LicaiChanpinFragment.newInstance("","");
//                    licaiFragment = LicaiFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            licaiChanpinFragment, FRAGMENT_TAG1).commit();
                }
                getSupportFragmentManager().beginTransaction().show(licaiChanpinFragment).commit();
                titlebar_title.setText(getString(R.string.text_licai));
                break;
            case 2:
                //隐藏标题栏；
                findViewById(R.id.rl_root).setVisibility(View.GONE);
                Intent intent = new Intent();
                intent.setAction("notifyReLogin");
                sendBroadcast(intent);
                if (accountFragment == null) {
                    accountFragment = AccountFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            accountFragment, FRAGMENT_TAG2).commit();
                }
                getSupportFragmentManager().beginTransaction().show(accountFragment).commit();
                titlebar_title.setText(getString(R.string.text_zhanghu));
                //500ms后执行刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        accountFragment.refreshDataWhenVisible();
                    }
                }, 500);
                break;
            case 3:
                findViewById(R.id.rl_root).setVisibility(View.VISIBLE);

                if (moreFragment == null) {
                    moreFragment = MoreFragment.newInstance("", "");
                    getSupportFragmentManager().beginTransaction().add(R.id.container,
                            moreFragment, FRAGMENT_TAG3).commit();
                }
                getSupportFragmentManager().beginTransaction().show(moreFragment).commit();
                titlebar_title.setText(getString(R.string.text_more));

                //500ms后执行刷新
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moreFragment.refreshDataWhenVisible();
                    }
                }, 500);
                break;
        }
        updateSelectTabBg(index);
        curIndex = index;
    }

    /**
     * 更新选中的tab的背景图；
     *
     * @param index 选中的tab页的下标；
     */
    private void updateSelectTabBg(int index) {
        clearOldSelectTabBg();
        switch (index) {
            case 0:
                mIvShouye.setBackgroundResource(R.drawable.shouye_selected);
                mTvShouye.setTextColor(getResources().getColor(R.color.main_page_tab_select_text_color));
                break;
            case 1:
                mIvLicai.setBackgroundResource(R.drawable.licai_selected);
                mTvLicai.setTextColor(getResources().getColor(R.color.main_page_tab_select_text_color));
                break;
            case 2:
                mIvZhanghu.setBackgroundResource(R.drawable.zhanghu_selected);
                mTvZhanghu.setTextColor(getResources().getColor(R.color.main_page_tab_select_text_color));
                break;
            case 3:
                mIvMore.setBackgroundResource(R.drawable.gengduo_selected);
                mTvMore.setTextColor(getResources().getColor(R.color.main_page_tab_select_text_color));
                break;
        }
    }

    /**
     * 清除旧的背景；
     */
    private void clearOldSelectTabBg() {
        int oldIndex = curIndex;
        switch (oldIndex) {
            case 0:
                mIvShouye.setBackgroundResource(R.drawable.shouye_normal);
                mTvShouye.setTextColor(getResources().getColor(R.color.main_page_tab_normal_text_color));
                break;
            case 1:
                mIvLicai.setBackgroundResource(R.drawable.licai_normal);
                mTvLicai.setTextColor(getResources().getColor(R.color.main_page_tab_normal_text_color));
                break;
            case 2:
                mIvZhanghu.setBackgroundResource(R.drawable.zhanghu_normal);
                mTvZhanghu.setTextColor(getResources().getColor(R.color.main_page_tab_normal_text_color));
                break;
            case 3:
                mIvMore.setBackgroundResource(R.drawable.gengduo_normal);
                mTvMore.setTextColor(getResources().getColor(R.color.main_page_tab_normal_text_color));
                break;
        }
    }

    /**
     * 隐藏掉所有的Fragment；
     *
     * @param oldPageIndex 之前显示的页面；
     */
    private void hideOldFragment(int oldPageIndex) {
        if (oldPageIndex == 0 && shouyeFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(shouyeFragment).commit();
        } else if (oldPageIndex == 1 && licaiChanpinFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(licaiChanpinFragment).commit();
        } else if (oldPageIndex == 2 && accountFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(accountFragment).commit();
        } else if (oldPageIndex == 3 && moreFragment != null) {
            getSupportFragmentManager().beginTransaction().hide(moreFragment).commit();
        }
    }

    /**
     * 清理内存；
     */
    @Override
    protected void onDestroy() {
        if (receiver != null) {
            try {
                this.unregisterReceiver(receiver);
            } catch (Exception e) {
                if (e != null)
                    LogUtil.i(String.valueOf(e.getMessage()));
            }
        }
        //取消所有的请求
        AppDownloadService.stopTask(getApplicationContext());
        super.onDestroy();
    }

    /**
     * 保存用户最新的点击时间；
     */
    private static long TIME_INTERNAL = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {

            if ((System.currentTimeMillis() - TIME_INTERNAL) > 2000) {
                TIME_INTERNAL = System.currentTimeMillis();
                ToastUtil.showToastLong(this, R.string.text_repeat_to_exit);
            } else {
//                String cachePath = getCacheDir().getAbsolutePath();
//                LogUtil.i("cachePath>>>>>"+cachePath);
                //保存友盟统计数据
                MobclickAgent.onKillProcess(this);
                android.os.Process.killProcess(Process.myPid());
                finish();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    @Override
    protected boolean isTitleBarLeftVisible() {
        return false;
    }

    @Override
    protected boolean isTitleBarRightVisible() {
        return false;
    }

    @Override
    protected String getTitleBarTitle() {
        return getString(R.string.text_main_page);
    }

}
