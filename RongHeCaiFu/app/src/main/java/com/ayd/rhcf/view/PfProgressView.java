package com.ayd.rhcf.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

import com.ayd.rhcf.utils.CommonUtil;
import com.ayd.rhcf.utils.LogUtil;

/**
 * Created by gqy on 2016/3/23.
 * 评分环形进度条；
 * 总分1000分，根据当前的最大评分显示不同的进度；
 * 25个刻度，每个刻度为40分；
 */
public class PfProgressView extends LinearLayout {
    private Context c;

    public PfProgressView(Context context) {
        this(context, null);
    }

    public PfProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c = context;
        init();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public PfProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = context;
        init();
    }

    private float viewWidth = 0f;
    private float viewHeight = 0f;

    //内测圆环的半径；
    private float circleRadius = 0f;

    // 画刻度时的宽度；
    private float kdWidth = 0f;

    // 刻度的长度；
    private float kdHeight = 0f;

    private float max_degree = 360;

    // 环形色带的颜色数组；
    private int[] colors = {
            Color.parseColor("#0093dd"),
            Color.parseColor("#75c5f0"),
            Color.parseColor("#afd13e"),

            Color.parseColor("#f8c300"),
            Color.parseColor("#fff500"),
            Color.parseColor("#ef9a48"),

            Color.parseColor("#84c225")
    };

    // 刻度默认颜色；
    private int kdDefaultColor = Color.parseColor("#dededd");

    // 刻度颜色；
    private int kdColor = Color.parseColor("#0093dd");

    private ObjectAnimator mAnimator;

    // 总份数；
    private float totalProgress = 25;

    private int zf = 1000; //总分；
    private float everyFs = zf / totalProgress; // 每个刻度的分数；

    //当前的刻度（份数）；
    private float curProgress = 0f;

    //每次旋转的角度数；
    private float rotateAngle = max_degree / totalProgress;

    private Paint cirlePaint = null;

    private void init() {
        circleRadius = CommonUtil.getScreenWidth(c) / 6.0f;
        kdWidth = CommonUtil.dip2px(c, 6);
        kdHeight = CommonUtil.dip2px(c, 28);

        //不加kdWidth也是可以的，这里稍微增大view的宽高；
        viewWidth = circleRadius * 2 + 2 * kdHeight + kdWidth;

        LogUtil.i("viewWidth==>" + viewWidth);
        viewHeight = viewWidth;

        cirlePaint = new Paint();
        cirlePaint.setAntiAlias(true);
        cirlePaint.setStyle(Paint.Style.STROKE);
        cirlePaint.setStrokeWidth(kdWidth);

//        xyf_text_size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
//                c.getResources().getDisplayMetrics());
//        xyf_value_text_size = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16,
//                c.getResources().getDisplayMetrics());
//
//        textPaint = new Paint();
//        textPaint.setAntiAlias(true);
//        textPaint.setTextAlign(Paint.Align.CENTER);
//        textPaint.setTextSize(xyf_text_size);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
//        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension((int) viewWidth, (int) viewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        // 坐标系平移至中心位置；
        canvas.translate(viewWidth / 2, viewHeight / 2);

        //默认从中心点的顶部开始绘制；
        canvas.rotate(-180);

        // 循环绘制刻度；
        for (int i = 0; i < totalProgress; i++) {
            canvas.save();
            // 坐标系旋转；
            canvas.rotate(i * rotateAngle);

            // 设置颜色；curProgress从1开始；
            if (curProgress > 0 && i <= curProgress - 1) {
                LogUtil.i("onDraw调用了" + curProgress);
                // 此处根据不同的角度，设置不同的颜色值；
//                if (true) {
//                }else if (true){
//
//                }
                if(0<=i&&i<=3.5){
                    cirlePaint.setColor(colors[0]);
                }else if(3.5<i&&i<=7){
                    cirlePaint.setColor(colors[1]);
                }else if(7<i&&i<=10.5){
                    cirlePaint.setColor(colors[2]);
                }else if(10.5<i&&i<=14){
                    cirlePaint.setColor(colors[3]);
                }else if(14<i&&i<=17.5){
                    cirlePaint.setColor(colors[4]);
                }else if(17.5<i&&i<=21){
                    cirlePaint.setColor(colors[5]);
                }else if(21<i&&i<=25){
                    cirlePaint.setColor(colors[6]);
                }

//                cirlePaint.setColor(kdColor);
            } else {
                cirlePaint.setColor(kdDefaultColor);
            }

            canvas.drawLine(0, circleRadius, 0, circleRadius + kdHeight, cirlePaint);
            canvas.restore();
        }
        canvas.restore();
        cirlePaint.setColor(kdDefaultColor);

        // 开始绘制文本；
//        textPaint.setTextSize(xyf_text_size);
//        canvas.drawText("信用分", viewWidth / 2.0f, viewHeight / 2.0f, textPaint);
    }

    /**
     * 添加环形动画；
     *
     * @param progress 范围0 —— totalProgress；
     */
    public void setAnimProgress(float progress) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return;
        }
        if (progress < 0) {
            progress = 0;
        }

        if (progress > totalProgress) {
            progress = totalProgress;
        }

        if (mAnimator == null || !mAnimator.isRunning()) {
            mAnimator = ObjectAnimator.ofFloat(this, "curProgress", 0f, progress);
            mAnimator.setDuration(1000);
            mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

            mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {

                    //>=API 11;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                        PfProgressView.this.curProgress = (float) valueAnimator.getAnimatedValue();
                        LogUtil.i("addUpdateListener调用了" + curProgress);
                        invalidate();
                    }
                }
            });
            mAnimator.start();
        }
    }

}
