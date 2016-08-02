package com.ayd.rhcf;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.text.TextUtils;

import com.alipay.euler.andfix.patch.PatchManager;
import com.ayd.rhcf.bean.User;
import com.ayd.rhcf.ui.JPushMsgReceiver;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.BitmapDisplayConfig;
import com.lidroid.xutils.bitmap.core.BitmapSize;
import com.lidroid.xutils.util.LogUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.utils.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by gqy on 2016/2/22.
 */
public class RhcfApp extends Application {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {

        this.user = user;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        andFixConfig();

        //AppUnCatchExceptionHandler.getInstance().init(this);  //暂时
        xUtilsConfig();
        jPushConfig();

        registSdCardStateReceiver();
        registBootReceiver();
        umengAnalyzeConfig();
        umengShareConfig();
//        getSession();
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {

        this.session = session;
    }

    public  String session;
//    private void getSession(){
//        Map<String,String> map = new HashMap<>();
//        map.put("push_id",JPushInterface.getRegistrationID(this));
//        map.put("query_site","user");
//        map.put("q","indexinfo");
//        map.put("type", "session_id");
//        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.Login_REQ_TAG, map, new HRCallBack() {
//            @Override
//            public void httpResponse(int resultCode, Object... responseData) {
//               JSONObject data = (JSONObject) responseData[0];
//                try {
//                    session = data.getString("data");
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, 0);
//    }

    /**
     * 友盟分享；
     */
    private void umengShareConfig() {
        //微信,朋友圈；
        PlatformConfig.setWeixin(AppConstants.weixin_id, AppConstants.weixin_secret);
        //sina新浪微博
        PlatformConfig.setSinaWeibo(AppConstants.sina_id, AppConstants.sina_secret);
        // QQ,Qzone
        PlatformConfig.setQQZone(AppConstants.qq_id, AppConstants.qq_secret);

        // 关闭分享的Log和Toast
        Log.LOG = false;
        Config.IsToastTip = true;
        //关闭sina的日志输出；
        com.sina.weibo.sdk.utils.LogUtil.disableLog();
    }

    /**
     * 友盟分析，bug跟踪统计；
     */
    private void umengAnalyzeConfig() {
        MobclickAgent.setDebugMode(false);

        //当应用在后台运行超过30秒（默认）再回到前端，被认为是两次统计
        //自定义友盟统计间隔时间 1分钟；
        MobclickAgent.setSessionContinueMillis(60 * 1000);
        MobclickAgent.setCatchUncaughtExceptions(true);
        // 如果开发者自己捕获了错误，需要上传到友盟服务器可以调用reportError方法：
    }

    /**
     * 极光推送配置；
     */
    private void jPushConfig() {
        // 开发者模式可打开，打包前需要关闭；
        JPushInterface.init(this);
        JPushInterface.setDebugMode(true);
    }

    /**
     * 关闭xUtils的log；
     */
    private void xUtilsConfig() {
        LogUtils.allowD = false;
        LogUtils.allowE = false;
        LogUtils.allowI = false;
        LogUtils.allowV = false;
        LogUtils.allowW = false;
        LogUtils.allowWtf = false;
    }

    private static PatchManager patchManager;

    /**
     * 热更新配置；
     * 当apk版本升级，需要把之前patch文件的删除，需要以下操作
     * 删除所有已加载的patch文件
     * mPatchManager.removeAllPatch();
     */
    private void andFixConfig() {
        try {
            if (patchManager == null) {
                patchManager = new PatchManager(this);
            }

            LogUtil.i("patchManager====>" + patchManager);

            if (patchManager != null) {
                patchManager.init(CommonUtil.getAppVersionName(this));
                patchManager.loadPatch();
            }

        } catch (Exception e) { // 异常处理；
            e.printStackTrace();
            if (e != null) {
                LogUtil.i("PatchManager发生异常===>" + String.valueOf(e.getMessage()));
            }
        } catch (Throwable e) {  // 针对AndFix不支持的CPU架构可能出现的UnsatisfiedLinkError错误；
            e.printStackTrace();
            if (e != null) {
                LogUtil.i("PatchManager发生异常===>" + String.valueOf(e.getMessage()));
            }

        }
    }

    /**
     * 执行热更新；
     */
    public void addPatch() {
        if (!CommonUtil.isSDCardAvailable() || patchManager == null) {
            return;
        }

        try {
            String patchPath = CommonUtil.getApatchDownloadPath(this);
            File patchFile = new File(patchPath);

            //文件存在，应用补丁；
            if (patchFile.exists()) {

                LogUtil.i("apatch文件存在");
                patchManager.addPatch(patchPath);
            }

            //复制且加载补丁成功后，删除AndFix拷贝到
            // File目录下的apatch文件夹下的补丁,避免下次重复加载

            // 删除AndFix框架copy的文件；
            boolean result = CommonUtil.deleteSpicificFile(this.getFilesDir().getAbsolutePath() +
                    File.separator + "apatch" + File.separator + AppConstants.APATCH_PATH);
            LogUtil.i("getFilesDir() 目录下的补丁路径" + this.getFilesDir().getAbsolutePath() +
                    File.separator + "apatch" + AppConstants.APATCH_PATH);
            if (result) {
                LogUtil.i("删除getFilesDir() 目录下的补丁成功");
            } else {
                LogUtil.i("删除getFilesDir() 目录下的补丁失败");
            }


            // 删除patch文件下载目录的apatch文件；
            boolean delDownPatchResult = CommonUtil.deleteSpicificFile(CommonUtil.getApatchDownloadPath(this));
            if (delDownPatchResult) {
                LogUtil.i("删除补丁下载目录下的补丁成功");
            } else {
                LogUtil.i("删除补丁下载目录下的补丁失败");
            }

        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i("patch文件应用热更新时发生异常");
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.i("patch文件应用热更新时发生异常");
        }
    }

    /**
     * 删除所有的apatch文件；
     */
    public void deletePatch() {
        if (patchManager != null) {
            patchManager.removeAllPatch();
        }

        CommonUtil.deleteSpicificFile(this.getFilesDir().getAbsolutePath() +
                File.separator + "apatch" + AppConstants.APATCH_PATH);
        CommonUtil.deleteSpicificFile(CommonUtil.getApatchDownloadPath(this));
    }

    private static BitmapUtils bitmapUtils;

    public static BitmapUtils getBitmapUtils(Context context) {
        synchronized (RhcfApp.class) {
            if (bitmapUtils == null) {
                if (CommonUtil.isSDCardAvailable()) {
                    bitmapUtilsConfigWhenHasSDCard(context);
                } else {
                    bitmapUtilsConfigWhenNoSDCard(context);
                }
            }
        }
        return bitmapUtils;
    }

    private static void bitmapUtilsConfigWhenHasSDCard(Context context) {
        BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
        displayConfig.setBitmapMaxSize(new BitmapSize(720, 1280));
        displayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
        String cachePath = CommonUtil.getImageCacheDir(context);

        if (TextUtils.isEmpty(cachePath)) {
            bitmapUtilsConfigWhenNoSDCard(context);
            return;
        }
        // 占用内存最多25%
        bitmapUtils = new BitmapUtils(context, cachePath, 0.25f, 100 * 1024 * 1024);

        bitmapUtils.configThreadPoolSize(3)
                .configDefaultDisplayConfig(displayConfig)
                .configDiskCacheEnabled(true)
                .configMemoryCacheEnabled(true)
                .configDefaultConnectTimeout(15000)
                .configDefaultReadTimeout(15000)
                .configDefaultLoadingImage(R.drawable.empty_photo)
                .configDefaultLoadFailedImage(R.drawable.empty_photo);
    }

    private static void bitmapUtilsConfigWhenNoSDCard(Context context) {
        BitmapDisplayConfig displayConfig = new BitmapDisplayConfig();
        displayConfig.setBitmapMaxSize(new BitmapSize(720, 1280));
        displayConfig.setBitmapConfig(Bitmap.Config.RGB_565);
        // 占用内存最多25%
        bitmapUtils = new BitmapUtils(context);

        bitmapUtils.configThreadPoolSize(3)
                .configDefaultDisplayConfig(displayConfig)
                .configDiskCacheEnabled(false)
                .configMemoryCacheEnabled(true)
                .configDefaultConnectTimeout(15000)
                .configDefaultReadTimeout(15000)
                .configDefaultLoadingImage(R.drawable.empty_photo)
                .configDefaultLoadFailedImage(R.drawable.empty_photo);
    }

    /**
     * 注册Sd卡卸载和可用的Receiver；
     * 主要用于 BitmapUtils的缓存；
     */
    private void registSdCardStateReceiver() {
        IntentFilter sdCardFilter = new IntentFilter();
        sdCardFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);

        sdCardFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
        sdCardFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
        sdCardFilter.addAction(Intent.ACTION_MEDIA_SHARED); // 被共享；
        sdCardFilter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);// sd卡已经从sd卡插槽拔出，但是挂载点还没解除
        sdCardFilter.addAction(Intent.ACTION_MEDIA_SCANNER_STARTED);// 开始扫描
        sdCardFilter.addAction(Intent.ACTION_MEDIA_SCANNER_FINISHED);// 扫描完成

        registerReceiver(sdCardStateReceiver, sdCardFilter);
    }

    private BroadcastReceiver sdCardStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //sd卡挂载；
            if (Intent.ACTION_MEDIA_MOUNTED.equals(intent.getAction())) {
                bitmapUtilsConfigWhenHasSDCard(RhcfApp.this);
                LogUtil.i("SD卡可用了");
            } else {
                bitmapUtilsConfigWhenNoSDCard(RhcfApp.this);
                LogUtil.i("SD卡不可用了");
            }
        }
    };
    private BroadcastReceiver bootReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            LogUtil.i("程序安装完成了");
            deletePatch();
        }
    };

    /**
     * 注册应用安装成功的广播；
     * 一般用于软件升级时的版本替换；
     */
    private void registBootReceiver() {
        IntentFilter bootFilter = new IntentFilter();
        bootFilter.addAction(Intent.ACTION_PACKAGE_ADDED);
        registerReceiver(bootReceiver, bootFilter);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();

        if (sdCardStateReceiver != null) {
            unregisterReceiver(sdCardStateReceiver);
        }
        if (bootReceiver != null) {
            unregisterReceiver(bootReceiver);
        }
    }
}
