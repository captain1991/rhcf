package com.ayd.rhcf.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.HRCallBack;
import com.ayd.rhcf.HttpProxy;
import com.ayd.rhcf.R;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.bean.User;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.ayd.rhcf.utils.SpUtil;
import com.ayd.rhcf.utils.ToastUtil;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * 闪屏页；
 * created by gqy on 2016/2/22
 */
public class SplashActivity extends BaseActivity {

    private ViewPager viewPager;
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewPager = (ViewPager) findViewById(R.id.guid_vp);
//        测试先默认为false 等会改
        Boolean isFirst = SpUtil.getSpBooleanValueByKey(this, "isFirst_Start", false);
        if (isFirst){

            SpUtil.save2SpBoolean(this,"isFirst_Start",false);
            doGuide();
        }else {
            viewPager.setVisibility(View.GONE);
            if(!CommonUtil.isConnected(this)){
                ToastUtil.showToastLong(this,"网络异常,请检查网络");
            }else {
                getSession();
            }
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startAc();
                }
            }, 1000);
        }


    }

    private void getSession(){
        Map<String,String> map = new HashMap<>();
        map.put("push_id",JPushInterface.getRegistrationID(this));
        map.put("query_site", "user");
        map.put("q","indexinfo");
        map.put("type", "session_id");
        HttpProxy.getDataByPost(this, AppConstants.BASE_URL, AppConstants.Splash_REQ_TAG, map, new HRCallBack() {
            @Override
            public void httpResponse(int resultCode, Object... responseData) {
                if(resultCode==101) {
                    JSONObject data = (JSONObject) responseData[0];

                    try {
                        if(data.getInt("code")==0) {
                            String session = data.getString("data");
                            RhcfApp app = (RhcfApp) getApplication();
                            app.setSession(session);
                            LogUtil.d("getSession=====splash");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, 0);

    }

    @Override
    protected String[] getReqTagList() {
        return new String[]{AppConstants.Splash_REQ_TAG};
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }


    @Override
    protected Activity getUmengAnalyzeContext() {
        return this;
    }

    public void getChannel_id(){
        String channel = getApplicationInfo().metaData.getString("UMENG_CHANNEL");
        if(channel!=null&&!channel.equals("")){

        }
    }

    public void startAc(){
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void doGuide() {
        MyPagerAdapter adapter = new MyPagerAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {

            private static final float MIN_SCALE = 0.85f;
            private static final float MIN_ALPHA = 0.5f;

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void transformPage(View page, float position) {

                int pageWidth = page.getWidth();
                int pageHeight = page.getHeight();

                if (position < -1) { // [-Infinity,-1)
                    page.setAlpha(0);

                } else if (position <= 1) { // [-1,1]
                    // Modify the default slide transition to shrink the page as well
                    float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                    float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                    float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                    if (position < 0) {
                        page.setTranslationX(horzMargin - vertMargin / 2);
//                        page.setTranslationX(-pageWidth*(1-position));
                    } else {
                        page.setTranslationX(-horzMargin + vertMargin / 2);
//                        page.setTranslationX(pageWidth*(1-position));
                    }

                    // Scale the page down (between MIN_SCALE and 1)
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);

                    // Fade the page relative to its size.
                    page.setAlpha(MIN_ALPHA +
                            (scaleFactor - MIN_SCALE) /
                                    (1 - MIN_SCALE) * (1 - MIN_ALPHA));

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    page.setAlpha(0);
                }
            }

        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//                最后一张图片左划进入应用
                if(position==imageViewsList.size()-1){
                    viewPager.setOnTouchListener(new View.OnTouchListener() {
                        float startX;
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            switch (event.getAction()){
                                case MotionEvent.ACTION_DOWN:
                                    startX = event.getRawX();
                                    break;
                                case MotionEvent.ACTION_MOVE:
                                    float moveX = event.getRawX()-startX;
                                    if(moveX<-10){
                                        startAc();
                                    }
                                    break;
                                default:
                                    return false;
                            }
                            return false;
                        }
                    });
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    private List<ImageView> imageViewsList;

    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(imageViewsList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(imageViewsList.get(position));
            return imageViewsList.get(position);
        }

        @Override
        public int getCount() {
            return imageViewsList == null ? 0 : imageViewsList.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }
}
