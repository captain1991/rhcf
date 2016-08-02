package com.ayd.rhcf.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.ClickListenerRegister;
import com.ayd.rhcf.R;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import java.lang.ref.WeakReference;
import java.util.Map;

/**
 * Created by gqy on 2016/3/29.
 * 分享；
 * 使用方法：直接调用UmsUtil.openShareBoad(Activity context)即可；
 */
public class UmsUtil {
    static WeakReference<Activity> activityRef = null;

    static final SHARE_MEDIA[] platfomlist = new SHARE_MEDIA[]{
            SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,
            SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.SINA, SHARE_MEDIA.SMS
    };

    public static void openShareBoad(Activity context) {
        if (context == null) {
            return;
        }

        // 自定义分享对话框；
        ProgressDialog dialog = new ProgressDialog(context, R.style.dialog_style);
        dialog.setMessage("正在分享，请稍后...");
        Config.dialog = dialog;

        activityRef = new WeakReference<Activity>(context);

        new ShareAction(activityRef.get()).setDisplayList(platfomlist)
                .setShareboardclickCallback(new ShareBoardlistener() {
                    @Override
                    public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                        startShare(share_media);
                    }
                }).open();
    }

    /**
     * 调用本地应用分享
     *
     * @param platfom 分享的平台；
     */
    private static void startShare(SHARE_MEDIA platfom) {


        if (platfom == SHARE_MEDIA.WEIXIN) {// 微信；

        } else if (platfom == SHARE_MEDIA.WEIXIN_CIRCLE) {// 朋友圈；

        } else if (platfom == SHARE_MEDIA.QQ) {

        } else if (platfom == SHARE_MEDIA.QZONE) {// qq空间；

        } else if (platfom == SHARE_MEDIA.SINA) { // 新浪；

        } else if (platfom == SHARE_MEDIA.SMS) { // 短信；

        }

        LogUtil.i("分享platfom==>" + platfom.toString());

        if (activityRef == null || activityRef.get() == null) {
            return;
        }

        new ShareAction(activityRef.get())
                .setPlatform(platfom)
                .withText("邀请码")
                .withTitle("融和贷")
                .withMedia(new UMImage(activityRef.get(),R.drawable.ronghedai_logo))
                .withTargetUrl("http://www.ronghedai.com")
//                .withMedia(new UMImage(context,null))
                .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        LogUtil.i("分享result==>" + share_media.toString());
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        if (activityRef != null && activityRef.get() != null) {
                            ToastUtil.showToastShort(activityRef.get(), R.string.share_fail);
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        if (activityRef != null && activityRef.get() != null) {
                            ToastUtil.showToastShort(activityRef.get(), R.string.share_cancel);
                        }
                    }
                })
                .share();
    }

    public static void goShouquan(Activity context,SHARE_MEDIA share_media) {
        UMShareAPI mShareAPI;
        activityRef = new WeakReference<Activity>(context);
        mShareAPI = UMShareAPI.get(activityRef.get());
//        SHARE_MEDIA share_media = SHARE_MEDIA.QQ;
        mShareAPI.doOauthVerify(activityRef.get(), share_media, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                Toast.makeText(activityRef.get(), " 授权成功啦", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {

            }
        });
    }

    public static void showShareDialog(Activity context){
        activityRef = new WeakReference<Activity>(context);
        final Dialog shareDialog = new Dialog(activityRef.get(),R.style.dialog_yhk_style);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_share,null);
        shareDialog.setContentView(view);
//        Window window = shareDialog.getWindow();
//        WindowManager.LayoutParams wl = window.getAttributes();
//        wl.width = CommonUtil.getScreenWidth(context) - CommonUtil.dip2px(context, 50);
//        window.setAttributes(wl);
        shareDialog.show();
        initView(view, new ClickEventCallBack() {
            @Override
            public void clickEventCallBack(int viewId) {
                switch (viewId) {
                    case R.id.iv_qq:
                        startShare(SHARE_MEDIA.QQ);
                        break;
                    case R.id.iv_wx:
                        startShare(SHARE_MEDIA.WEIXIN);
                        break;
                    case R.id.iv_sina:
                        startShare(SHARE_MEDIA.SINA);
                        break;
                    case R.id.iv_qqzone:
                        startShare(SHARE_MEDIA.QZONE);
                        break;
                    case R.id.iv_wxfriend:
                        startShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                        break;
                    case R.id.iv_xx:
                        startShare(SHARE_MEDIA.SMS);
                        break;
                    case R.id.btn_cancel:
                        shareDialog.dismiss();
                        break;
                    default:
                        return;
                }
            }
        });
    }

    public static void initView(View view,ClickEventCallBack clickEventCallBack){
        ImageView iv_qq = (ImageView) view.findViewById(R.id.iv_qq);
        ImageView iv_wx = (ImageView) view.findViewById(R.id.iv_wx);
        ImageView iv_sina = (ImageView) view.findViewById(R.id.iv_sina);
        ImageView iv_qqzone = (ImageView) view.findViewById(R.id.iv_qqzone);
        ImageView iv_wxfriend = (ImageView) view.findViewById(R.id.iv_wxfriend);
        ImageView iv_xx = (ImageView) view.findViewById(R.id.iv_xx);
        Button button = (Button) view.findViewById(R.id.btn_cancel);
        ClickListenerRegister.regist(iv_qq,clickEventCallBack);
        ClickListenerRegister.regist(iv_wx,clickEventCallBack);
        ClickListenerRegister.regist(iv_sina,clickEventCallBack);
        ClickListenerRegister.regist(iv_qqzone,clickEventCallBack);
        ClickListenerRegister.regist(iv_wxfriend,clickEventCallBack);
        ClickListenerRegister.regist(iv_xx,clickEventCallBack);
        ClickListenerRegister.regist(button,clickEventCallBack);
    }

}
