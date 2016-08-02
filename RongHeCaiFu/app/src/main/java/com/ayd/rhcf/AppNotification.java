package com.ayd.rhcf;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Created by gqy on 2016/3/9.
 * 系统通知；
 */
public class AppNotification {
    private static NotificationManager nmc = null;
    private static Notification notification = null;

    public static NotificationManager getNotificationManager(Context context) {
        synchronized (AppNotification.class) {
            if (nmc == null) {
                nmc = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
        }
        return nmc;
    }

    public static void showNotification(Context context) {
        synchronized (AppNotification.class) {
            if (notification == null) {
//                RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
//                        R.layout.notification_layout);
                notification = new NotificationCompat.Builder(context)
                        .setAutoCancel(false)
                        .setContentTitle("title")
                        .setContentText("text")
                        .setSmallIcon(0)   // 一定要添加小图标；
                        .setTicker("")
//                        .setContent()
                        .build();
            }
        }

        getNotificationManager(context).notify(11, notification);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setNotification(Context context, String content, int notificationId) {
        NotificationManager nmcLocal = getNotificationManager(context);
        Notification.Builder builder=null;
        if (builder == null) {
            builder = new Notification.Builder(context.getApplicationContext());
            builder.setSmallIcon(R.drawable.ic_launcher)
                    .setAutoCancel(false)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("正在下载...")
                    .setOngoing(true)   // 禁止滑动删除；
                    .setContentText(content)
                    .setContentIntent(null);
        }
        Notification notification = builder.setContentText(content).build();

        nmcLocal.notify(notificationId, notification);
    }


    /**
     * 取消消息提示
     */
    public static void cancelAppUpdateNotification(Context context, int notificationId) {
        NotificationManager nmcLocal = getNotificationManager(context);
        if (nmcLocal != null) {
            nmcLocal.cancel(notificationId);
        }
    }
}
