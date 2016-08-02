package com.ayd.rhcf;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


/**
 * App后台服务；
 * created by gqy on 2016/3/8
 */
public class AppDownloadService extends IntentService {

    private static final String CHECK_UPDATE_TAG = "CHECK_UPDATE_TAG";
    private static final int CHECK_UPDATE_RESULT_CODE = 100;

    public AppDownloadService() {
        super("AppDownloadService");
    }

    /**
     * 检查新版本；
     *
     * @param context
     */
    public static void checkNewVersion(Context context) {
        Intent intent = new Intent(context, AppDownloadService.class);
        intent.setAction(AppConstants.CHECK_UPDATE_SERVICE_ACTION);
        context.startService(intent);
    }

    /**
     * 下载新版本；
     *
     * @param context
     * @param downloadTaskId 下载任务的id；
     *                       用于区分不同任务；可以是
     *                       {AppConstants.APP_UPDATE_ID,AppConstants.PATCH_DOWN_ID};
     */
    public static void downloadFile(Context context, String downloadUrl, int downloadTaskId) {
        Intent intent = new Intent(context, AppDownloadService.class);
        intent.setAction(AppConstants.DOWNLOAD_SERVICE_ACTION);
        intent.putExtra("downloadUrl", downloadUrl);
        intent.putExtra("task_id", downloadTaskId);
        context.startService(intent);
    }

    /**
     * 停止服务；
     */
    public static void stopTask(Context context) {
        if (context != null) {
            Intent intent = new Intent(context, AppDownloadService.class);
            context.stopService(intent);
            LogUtil.i("AppDownloadService服务停止了==");
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (AppConstants.CHECK_UPDATE_SERVICE_ACTION.equals(action)) {
                checkUpdate();
            } else if (AppConstants.DOWNLOAD_SERVICE_ACTION.equals(action)) {
                String url = intent.getStringExtra("downloadUrl");
                int task_id = intent.getIntExtra("task_id", -1);
                downloadFile(url, task_id);
            }
        }
    }


    /**
     * http://music.baidu.com/cms/BaiduMusic-danqu.apk
     * -1:下载失败；
     * 0：下载中；
     * 1：下载完成；
     *
     * @param downloadUrl
     * @param task_id
     */
    private void downloadFile(String downloadUrl, int task_id) {
        if (task_id == -1) {
            return;
        }

        String targetFilePath = "";
        //======app更新；
        if (task_id == AppConstants.APP_UPDATE_ID) {

            String apkDownloadDir = CommonUtil.getApkDownloadPath(getApplicationContext());
            if (!TextUtils.isEmpty(apkDownloadDir)) {
                targetFilePath = apkDownloadDir + File.separator + "rhcf.apk";
            } else {
                //提示下载失败；
                downLoadError(task_id);
                return;
            }

            LogUtil.i("下载目录====" + targetFilePath);
        }
        //======补丁更新；
        else if (task_id == AppConstants.PATCH_DOWN_ID) {
            String aPatchDownPath = CommonUtil.getApatchDownloadPath(getApplicationContext());
            if (CommonUtil.isNotEmpty(aPatchDownPath)) {
                targetFilePath = aPatchDownPath;
            }
        }

        if (CommonUtil.isEmpty(targetFilePath)) {
            return;
        }

        //6.0以上；
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
            downByHttpUrlConnection(downloadUrl, targetFilePath, task_id);
        }
        //6.0以下，采用xutil下载；
        else {
            downByXUtil(downloadUrl, targetFilePath, task_id);
        }
    }

    /**
     * 通过xUtils下载；
     *
     * @param downloadUrl
     * @param targetFilePath
     * @param task_id
     */
    public void downByXUtil(String downloadUrl, String targetFilePath, final int task_id) {

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.download(downloadUrl, targetFilePath,
                true,//有未完成的下载任务是否接着下载
                task_id == AppConstants.APP_UPDATE_ID ? true : false,//是否使用检测到的文件名(热更新文件名是固定)
                new RequestCallBack<File>() {

                    /**
                     * 下载完成；
                     * @param responseInfo
                     */
                    @Override
                    public void onSuccess(ResponseInfo<File> responseInfo) {
                        LogUtil.i("===文件下载完成==下载目录为==>" + responseInfo.result.getPath());
//                        RhcfApp app = (RhcfApp) getApplication();
//                        app.deletePatch();

                        if (task_id == AppConstants.APP_UPDATE_ID) {
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setDataAndType(Uri.fromFile(responseInfo.result), "application/vnd.android.package-archive");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        } else if (task_id == AppConstants.PATCH_DOWN_ID) {
                            //应用插件；
                            RhcfApp app = (RhcfApp) getApplication();
                            app.addPatch();
                        }
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                        LogUtil.i("总大小=" + total + ",已下载=" + current);

                        //app更新时才通知显示进度条；
                        if (task_id == AppConstants.APP_UPDATE_ID &&
                                total > 0 && current > 0 && current / total <= 1) {
                            sendCurDownloadRate(total, current);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        LogUtil.i("onFailure============" + error.getMessage() + "msg==" + msg);
                        //提示下载失败；
                        downLoadError(task_id);
                    }
                });
    }

    /**
     * 通过HttpUrlConnection下载；
     *
     * @param downloadUrl
     * @param targetFilePath
     * @param task_id
     */
    public void downByHttpUrlConnection(String downloadUrl, String targetFilePath, int task_id) {
        FileOutputStream fos = null;
        InputStream is = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(downloadUrl);

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(20000);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(20000);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                LogUtil.i("====urlConnection连接成功了");

                is = urlConnection.getInputStream();
                fos = new FileOutputStream(new File(targetFilePath));

                // 100KB缓存；
                byte[] buffer = new byte[100 * 1024];
                int total = urlConnection.getContentLength();
                int count;
                int current = 0;
                while ((count = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, count);
                    current += count;
                    sendCurDownloadRate(total, current);
                }
                //下载完成安装apk或进行热更新
                if (task_id == AppConstants.APP_UPDATE_ID) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(new File(targetFilePath)), "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else if (task_id == AppConstants.PATCH_DOWN_ID) {
                    //应用插件；
                    RhcfApp app = (RhcfApp) getApplication();
                    app.addPatch();
                }
            } else {
                downLoadError(task_id);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            downLoadError(task_id);
            LogUtil.i("===========下载地址有误");
        } catch (IOException e) {
            e.printStackTrace();
            LogUtil.i("===========下载文件时，发生IO异常");
            downLoadError(task_id);

        } finally {
            try {
                if (is != null) {
                    is.close();
                }

                if (urlConnection != null) {
                    // 断开连接；
                    urlConnection.disconnect();
                }

                if (fos != null) {
                    fos.flush();
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发送下载进度；
     *
     * @param total
     * @param current
     */
    private void sendCurDownloadRate(long total, long current) {
        if (total > 0 && current > 0 && current / total <= 1) {
            LogUtil.i("======total=" + total + ",current=" + current);

            int rate = (int) (current * 100 / total);

            //避免频繁发送消息；
            if (rate % 2 == 0) {
                Intent intent = new Intent();
                intent.setAction(AppConstants.DOWNLOAD_RECEIVER_ACTION);
                intent.putExtra("flag", 0);
                intent.putExtra("rate", rate);
                sendBroadcast(intent);
            }
        }
    }


    /**
     * 提示下载失败；
     *
     * @param task_id 任务id；
     */
    private void downLoadError(int task_id) {
        Intent intent = new Intent();
        intent.setAction(AppConstants.DOWNLOAD_RECEIVER_ACTION);
        intent.putExtra("flag", -1);
        intent.putExtra("task_id", task_id);
        sendBroadcast(intent);
    }

    /**
     * 异步检查更新；
     */

    private void checkUpdate() {
        //先取消掉之前所有的相同请求；
        HttpProxy.cancelSpecificRequest(getApplicationContext(), CHECK_UPDATE_TAG);
        String url = "";
        Map<String, String> map = new HashMap<>();

        Intent intent = new Intent();
        intent.setAction(AppConstants.CHECK_UPDATE_RECEIVER_ACTION);
        intent.putExtra("need_update", true);
        sendBroadcast(intent);

//        HttpProxy.getDataByGet(getApplicationContext(), url, CHECK_UPDATE_TAG, map, new HttpResponseCallBack() {
//            @Override
//            public void httpResponse(int resultCode, Object... responseData) {
//
//                if (resultCode == 200) {
//                    Intent intent = new Intent();
//                    intent.setAction(AppConstants.CHECK_UPDATE_RECEIVER_ACTION);
//                    intent.putExtra("need_update", true);
//                    sendBroadcast(intent);
//                }
//            }
//        }, CHECK_UPDATE_RESULT_CODE);
    }
}
