package com.ayd.rhcf.fragment;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.R;
import com.ayd.rhcf.ui.GrzxActivity;
import com.ayd.rhcf.ui.GywmActivity;
import com.ayd.rhcf.ui.LoginActivityNew;
import com.ayd.rhcf.ui.NoticeDialog;
import com.ayd.rhcf.ui.PtggActivity;
import com.ayd.rhcf.ui.YjfkActivity;
import com.ayd.rhcf.ui.YqhyActivity;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.lidroid.xutils.BitmapUtils;

import java.io.File;
import java.io.IOException;

import cn.jpush.android.api.JPushInterface;
import libcore.io.DiskLruCache;
import libcore.io.DiskLruUtils;

/**
 * 更多Fragment；
 * created by gqy on 2016/2/23
 */
public class MoreFragment extends BaseFragment implements ClickEventCallBack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private LinearLayout mPanelGgxw;
    private LinearLayout mPanelYqhyzjl;

    private LinearLayout mPanelGrzx;
    private LinearLayout mPanelGywm;
    private LinearLayout mPanelKfrx;
    private LinearLayout mPanelYjfk;

    private LinearLayout mPanelXxss;
    private LinearLayout mPanelQchc;
    private ToggleButton mXxToggle;
    private LinearLayout mPanelBbgx;

    private TextView mTvCacheSize;
    private TextView mTvNewVersion;

    private boolean isViewCreated = false;

    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MoreFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        init(view);
        return view;
    }

    private File cacehDir = null;
    private File downloadDir = null;

    private void init(View view) {
        if (CommonUtil.isSDCardAvailable()) {
            cacehDir = new File(CommonUtil.getImageCacheDir(activity));
            downloadDir = new File(CommonUtil.getApkDownloadPath(activity));
        }

        if (view != null) {
//            mPanelGgxw = (LinearLayout) view.findViewById(R.id.panel_ggxw);
//            ClickListenerRegister.regist(mPanelGgxw, this);
//            mPanelYqhyzjl = (LinearLayout) view.findViewById(R.id.panel_yqhyzjl);
//            ClickListenerRegister.regist(mPanelYqhyzjl, this);
//
//            mPanelGrzx = (LinearLayout) view.findViewById(R.id.panel_grzx);
//            ClickListenerRegister.regist(mPanelGrzx, this);
//            mPanelGywm = (LinearLayout) view.findViewById(R.id.panel_gywm);
//            ClickListenerRegister.regist(mPanelGywm, this);
            mPanelKfrx = (LinearLayout) view.findViewById(R.id.panel_kfrx);
            ClickListenerRegister.regist(mPanelKfrx, this);
            mPanelYjfk = (LinearLayout) view.findViewById(R.id.panel_yjfk);
            ClickListenerRegister.regist(mPanelYjfk, this);

            mPanelXxss = (LinearLayout) view.findViewById(R.id.panel_xxss);
            ClickListenerRegister.regist(mPanelXxss, this);

            mXxToggle = (ToggleButton) view.findViewById(R.id.xx_toggle);
            mXxToggle.setChecked(SpUtil.getSpBooleanValueByKey(activity,
                    AppConstants.TOGGLE_RECV_MSG, true));

            mXxToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    toggleJPushState(b);
                    SpUtil.save2SpBoolean(activity, AppConstants.TOGGLE_RECV_MSG, b);
                }
            });
            mPanelQchc = (LinearLayout) view.findViewById(R.id.panel_qchc);
            ClickListenerRegister.regist(mPanelQchc, this);
            mPanelBbgx = (LinearLayout) view.findViewById(R.id.panel_bbgx);
            ClickListenerRegister.regist(mPanelBbgx, this);

            mTvCacheSize = (TextView) view.findViewById(R.id.tv_cache_size);
            mTvNewVersion = (TextView) view.findViewById(R.id.tv_new_version);
        }
    }

    /*
     * 推送开关；
     */
    private void toggleJPushState(boolean state) {
        if (activity == null) {
            return;
        }
        //1.5.2 以上版本支持。判断停止状态；
        if (state && JPushInterface.isPushStopped(activity.getApplicationContext())) {
            // 开启；
            JPushInterface.resumePush(activity.getApplicationContext());
        } else if (!state && !JPushInterface.isPushStopped(activity.getApplicationContext())) {
            // 关闭；
            JPushInterface.stopPush(activity.getApplicationContext());
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
    }

    /**
     * 刷新缓存容量；
     * 检查新版本；？？？
     */
    DiskLruCache diskLruCache = null;
    public void refreshDataWhenVisible() {
        if (isViewCreated) {
//            下载的文件
            long downloadsize = CommonUtil.getDirSize(downloadDir);
//            图片缓存
            long imagesize = CommonUtil.getDirSize(cacehDir);
            File file = DiskLruUtils.getDiskCacheDir(activity,"biao");
            if(!file.exists()){
                file.mkdir();
            }
            long biaoCache = 0;
            try {
                diskLruCache = DiskLruCache.open(file,CommonUtil.getAppVersionCode(activity),1,5*1024*1024);
                if(diskLruCache != null) {
                    biaoCache = diskLruCache.size();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            long cacehsize = downloadsize+imagesize+biaoCache;
            mTvCacheSize.setText(CommonUtil.getDataWithUnit(cacehsize));
            mTvNewVersion.setText("当前版本"+CommonUtil.getAppVersionName(getActivity()));
        }
    }

    @Override
    public void clickEventCallBack(int viewId) {
//        if (viewId == mPanelGgxw.getId()) {
//            readyGo(activity, PtggActivity.class);
//            return;
//        } else if (viewId == R.id.panel_yqhyzjl) {
//            readyGo(activity, YqhyActivity.class);
//            return;
//        } else if (viewId == mPanelGrzx.getId()) {
//            //判断登录；
//            readyGo(activity, GrzxActivity.class);
//            return;
//        } else if (viewId == mPanelGywm.getId()) {
//            readyGo(activity, GywmActivity.class);
//            return;
//        } else
        if (viewId == mPanelYjfk.getId()) {
            boolean isLogin = SpUtil.getSpBooleanValueByKey(activity,AppConstants.ISLOGIN,false);
            if (!isLogin){
                readyGo(activity, LoginActivityNew.class);
                return;
            }
            readyGo(activity, YjfkActivity.class);
            return;
        } else if (viewId == mPanelKfrx.getId()) {//客服热线；
            CommonUtil.dialNumber(activity, getString(R.string.text_kfrx_number));
            return;
        } else if (viewId == mPanelXxss.getId()) {//接受推送；
            mXxToggle.setChecked(!mXxToggle.isChecked());
            return;
        } else if (viewId == mPanelQchc.getId()) { //清除缓存；
            clearCache();
            return;
        } else if (viewId == mPanelBbgx.getId()) {//版本更新；
            checkNewVersion();
            return;
        }
    }

    /**
     * 检查新版本；
     */
    private void checkNewVersion() {
        //AppDownloadService.checkNewVersion(activity);
        showUpdateDialog("");
    }

    private void showUpdateDialog(String downloadUrl) {
//        if (CommonUtil.verifyHttpUrl(downloadUrl)) {
        NoticeDialog nd = new NoticeDialog();
        nd.setYesLabel(activity.getString(R.string.text_yes))
                .setNoLabel(activity.getString(R.string.text_qx));

        nd.setCallBack(new MyEventCallBack() {
            @Override
            public void adapterEventCallBack(Object... args) {
                if (args != null && args.length > 0 && args[0] instanceof Integer) {
                    int position = (int) args[0];
                    if (position == 1) {
                        //升级操作；
                        ToastUtil.showToastShort(activity, "开始升级");
                    }
                }
            }
        });
        nd.setTitle(getString(R.string.text_find_new_version));
        Spanned spanned = Html.fromHtml("1,update1;<br />2,update2;<br />3,update3;");
        nd.setContent(spanned == null ? "" : spanned.toString());

        nd.show(getChildFragmentManager(), "update_dialog");
//        }
    }

    private void clearCache() {
        if (activity != null) {
            if (!CommonUtil.isSDCardAvailable()) {
                ToastUtil.showToastShort(activity, R.string.no_sdcard);
            } else {
//                删除缓存标文件
                try {
                    if(diskLruCache!=null) {
                        diskLruCache.delete();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
//              删除图片缓存
                if (cacehDir == null) {
                    cacehDir = new File(CommonUtil.getImageCacheDir(activity));
                }
//                删除下载缓存
                if(downloadDir == null){
                    downloadDir = new File(CommonUtil.getApkDownloadPath(activity));
                }
                LogUtil.d("下载地址" + downloadDir.getAbsolutePath());
                CommonUtil.clearCacheDir(cacehDir);
                CommonUtil.clearCacheDir(downloadDir);
                BitmapUtils bitmapUtils = RhcfApp.getBitmapUtils(activity);
                if (bitmapUtils != null) {
                    bitmapUtils.clearMemoryCache();
                    bitmapUtils.clearDiskCache();
                }
                mTvCacheSize.setText("0KB");
                ToastUtil.showToastShort(activity, R.string.clear_cache_success);
            }
        }
    }

    /**
     * 版本更新的request tag；
     *
     * @return
     */
    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.UPDATE_VERSION_REQ_TAG};
    }
}
