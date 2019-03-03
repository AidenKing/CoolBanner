package com.king.lib.banner;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

import java.lang.reflect.Field;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2019/3/1 9:31
 */
public class CoolBanner extends ViewPager {

    /**
     * 是否循环
     */
    private boolean isLoop = true;

    private int duration = 5000;

    private boolean enableSwitch = true;

    private PageController controller;

    public CoolBanner(@NonNull Context context) {
        super(context);
        init(null);
    }

    public CoolBanner(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        controller = new PageController(this);
        if (attrs != null) {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs
                    , R.styleable.CoolBanner, 0, 0);
            duration = a.getInteger(R.styleable.CoolBanner_switchDuration, 5000);
        }
        try {
            // 通过class文件获取mScroller属性
            Field mField = ViewPager.class.getDeclaredField("mScroller");
            mField.setAccessible(true);
            FixedSpeedScroller mScroller = new FixedSpeedScroller(getContext(), new AccelerateInterpolator());
            mScroller.setScrollDuration(500);
            mField.set(this, mScroller);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setAdapter(@Nullable PagerAdapter adapter) {
        super.setAdapter(adapter);
        controller.onAdapterSet();
    }

    public void setLoop(boolean loop) {
        isLoop = loop;
    }

    public boolean isLoop() {
        return isLoop;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void startAutoPlay() {
        controller.startAutoPlay();
    }

    public void stopAutoPlay() {
        controller.stopAutoPlay();
    }

    public void setEnableSwitch(boolean enableSwitch) {
        this.enableSwitch = enableSwitch;
    }

    public boolean isEnableSwitch() {
        return enableSwitch;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (enableSwitch) {
            return super.onInterceptTouchEvent(ev);
        }
        else {
            return false;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (enableSwitch) {
            return super.onTouchEvent(ev);
        }
        else {
            return false;
        }
    }

    /**
     * 当有touch事件时，重置计时
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // 必须在dispatchTouchEvent处理，因为这里onTouchEvent里接收不到ACTION_DOWN事件
        if (enableSwitch) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                case MotionEvent.ACTION_MOVE:
                    controller.resetTimer();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    break;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}
