package com.ayd.rhcf.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ayd.rhcf.AppConstants;
import com.ayd.rhcf.ClickEventCallBack;
import com.ayd.rhcf.MyEventCallBack;
import com.ayd.rhcf.RhcfApp;
import com.ayd.rhcf.R;
import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;
import com.lidroid.xutils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ViewPager图片轮播View；
 * Created by gqy on 2016/2/24.
 * 使用方法；调用 setImageDataList(List<String> imagesUrlsArg)即可；
 */
public class SlideShowView extends FrameLayout {
    //轮播任务开始时的时间间隔；
    private final static int INITIAL_DELAY = 5;
    //自动轮播的时间间隔
    private final static int PERIOD = 5;

    //自动轮播启用开关
    private boolean isAutoPlay = true;

    //轮播图列表的url
    private List<String> imagesUrls;

    //放轮播图片的ImageView 的list
    private List<ImageView> imageViewsList;
    private ViewPager viewPager;
    private LinearLayout panel_dots;
    //当前轮播页
    private int currentItem = 0;
    //定时任务
    private ScheduledExecutorService scheduledExecutorService;
    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            viewPager.setCurrentItem(currentItem);
        }
    };

    public SlideShowView(Context context) {
        this(context, null);
    }

    public SlideShowView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlideShowView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initUI(context);
    }

    /**
     * 开始轮播图
     */
    private void startPlay() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new SlideShowTask(),
                INITIAL_DELAY, PERIOD, TimeUnit.SECONDS);
    }

    /**
     * 停止轮播图切换
     */
    public void stopPlay() {
        scheduledExecutorService.shutdown();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        panel_dots = (LinearLayout) findViewById(R.id.panel_dots);
        panel_dots.removeAllViews();
    }

    /**
     * 初始化UI;
     */
    private void initUI(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_slideshow, this, true);
    }

    /**
     * 注入图片数据；
     */
    public void setImageDataList(List<String> imagesUrlsArg) {
        imagesUrls = imagesUrlsArg;
        if (imagesUrls == null || imagesUrls.size() == 0) {
            return;
        }

        if (imageViewsList == null) {
            imageViewsList = new ArrayList<ImageView>();
        }
        initImageViewList();
        initDotViewList();

        MyPagerAdapter pagerAdapter = new MyPagerAdapter();
        viewPager.setAdapter(pagerAdapter);
        MyPageChangeListener pageChangeListener = new MyPageChangeListener();
        viewPager.setOnPageChangeListener(pageChangeListener);
        pageChangeListener.onPageSelected(0);

        if (isAutoPlay) {
            startPlay();
        }
    }
    private MyEventCallBack callBack;
    public void setEventCallback(MyEventCallBack c){
        callBack = c;
    }

    /**
     * 初始化ImageView列表；
     */
    private void initImageViewList() {
        for (int i = 0; i < imagesUrls.size(); i++) {
            ImageView imageView = new ImageView(getContext());

            //宽高等比例缩放；
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewsList.add(imageView);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.adapterEventCallBack(currentItem);
                }
            });
            String url = imagesUrls.get(i);
            if (CommonUtil.isNotEmpty(url) && url.startsWith(AppConstants.HTTP_PREFIX)) {
                BitmapUtils bitmapUtils = RhcfApp.getBitmapUtils(getContext()
                        .getApplicationContext());
                if (bitmapUtils != null) {
                    bitmapUtils.display(imageView, url);
                }
            }
        }
    }

    /**
     * 初始化指示器列表；
     */
    private void initDotViewList() {
        panel_dots.removeAllViews();
        //指示器的宽和高；
        int width = CommonUtil.dip2px(getContext(), 8);
        View dotView;
        for (int i = 0; i < imagesUrls.size(); i++) {
            dotView = LayoutInflater.from(getContext()).inflate(R.layout.dot_view, null);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(width, width);

            //非首个View添加左边距；
            if (i > 0) {
                lp.leftMargin = CommonUtil.dip2px(getContext(), 5);
            }
            dotView.setLayoutParams(lp);
            panel_dots.addView(dotView, i);
        }
    }

    public int getCurrentItem(){
        return currentItem;
    }

    /**
     * 填充ViewPager的页面适配器
     *
     * @author caizhiming
     */
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

    /**
     * ViewPager的监听器
     * 当ViewPager中页面的状态发生改变时调用
     *
     * @author caizhiming
     */
    private class MyPageChangeListener implements ViewPager.OnPageChangeListener {
//        boolean isAutoPlay = false;

        @Override
        public void onPageScrollStateChanged(int arg0) {
            switch (arg0) {
                case ViewPager.SCROLL_STATE_DRAGGING:// 手势拖动中；
                    isAutoPlay = false;
                    break;
                case ViewPager.SCROLL_STATE_SETTLING:// 界面切换中
                    isAutoPlay = false;
                    break;
                case ViewPager.SCROLL_STATE_IDLE:// 滑动结束，即切换完毕或者加载完毕
                    isAutoPlay = true;
                    break;
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int pos) {
            currentItem = pos;
            isAutoPlay = true;

            for (int i = 0; i < imagesUrls.size(); i++) {
                //选中页面的dot；
                if (i == pos) {
                    panel_dots.getChildAt(i).setBackgroundResource(R.drawable.yuan2);
                } else {
                    panel_dots.getChildAt(i).setBackgroundResource(R.drawable.yuan1);
                }
            }
        }
    }

    /**
     * 执行轮播图切换任务
     */
    private class SlideShowTask implements Runnable {

        @Override
        public void run() {

            if (isAutoPlay) {
                synchronized (viewPager) {
                    currentItem = (currentItem + 1) % imageViewsList.size();
                    handler.obtainMessage().sendToTarget();
                }
            }
        }
    }

}
