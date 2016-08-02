package com.ayd.rhcf.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by gqy on 2016/3/29.
 * 带动画的进度条；
 */
public class AnimProgressBar extends ProgressBar {
    private Context c;
    private TextView mProgressValue;

    public AnimProgressBar(Context context) {
        this(context, null);
    }

    public AnimProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.c = context;
    }

    private int totalProgress = 100; // 总进度；
    private int curProgress = 0; //当前进度；

    private ObjectAnimator mAnimator;

    /**
     * 设置当前的动画进度：
     *
     * @param progress
     */
    public void setCurAnimProgress(int progress) {
        if (curProgress < 0) {
            curProgress = 0;
        }

        if (curProgress > totalProgress) {
            curProgress = totalProgress;
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            this.setProgress(curProgress);

        } else {
            if (mAnimator == null) {

                mAnimator = ObjectAnimator.ofInt(this, "curProgress", 0, progress);
                mAnimator.setDuration(1000);
                mAnimator.setInterpolator(new DecelerateInterpolator());

                mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                            curProgress = (int) animation.getAnimatedValue();
                            setProgress(curProgress);
                            mProgressValue.setText(curProgress + "%");
                        }
                    }
                });
            }
            mAnimator.start();
        }
    }

    /**
     * 联动的TextView；
     * @param mProgressValue
     */
    public void setTvProgress(TextView mProgressValue) {
        this.mProgressValue = mProgressValue;
    }
}
