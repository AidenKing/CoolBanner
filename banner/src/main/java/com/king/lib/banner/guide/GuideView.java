package com.king.lib.banner.guide;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;

import com.king.lib.banner.R;

/**
 * Desc:
 *
 * @author：Jing Yang
 * @date: 2019/3/4 10:00
 */
public class GuideView extends View {

    private int guideNormalColor;
    private int guideFocusColor;
    private int guideAsTextAtNum;
    private int guideTextColor;
    private int guidePointSize;
    private int guideTextSize;
    private int guideTextGravity = Gravity.RIGHT;

    private int pointNumber;

    private int mFocusIndex;

    private Paint mPaint;

    public GuideView(Context context) {
        super(context);
        init(null);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public GuideView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        mPaint = new Paint();
        if (attrs == null) {
            guideNormalColor = Color.WHITE;
            guideFocusColor = Color.parseColor("#fe4e4e");
            guideAsTextAtNum = 11;
            guideTextColor = Color.WHITE;
            guidePointSize = dp2px(10);
            guideTextSize = dp2px(12);
        }
        else {
            TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs
                    , R.styleable.GuideView, 0, 0);
            guideNormalColor = a.getColor(R.styleable.GuideView_guideNormalColor, Color.WHITE);
            guideFocusColor = a.getColor(R.styleable.GuideView_guideFocusColor, Color.parseColor("#fe4e4e"));
            guideAsTextAtNum = a.getInteger(R.styleable.GuideView_guideAsTextAtNum, 11);
            guideTextColor = a.getColor(R.styleable.GuideView_guideTextColor, Color.WHITE);
            guidePointSize = a.getDimensionPixelSize(R.styleable.GuideView_guidePointSize, dp2px(10));
            guideTextSize = a.getDimensionPixelSize(R.styleable.GuideView_guideTextSize, dp2px(12));
        }
    }

    public int dp2px(float dp){
        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return Math.round(px);
    }

    public void setPointNumber(int pointNumber) {
        this.pointNumber = pointNumber;
        invalidate();
    }

    public void setFocusIndex(int index) {
        mFocusIndex = index;
        invalidate();
    }

    public void setGuideTextGravity(int guideTextGravity) {
        this.guideTextGravity = guideTextGravity;
        invalidate();
    }

    public void setGuideAsTextAtNum(int guideAsTextAtNum) {
        this.guideAsTextAtNum = guideAsTextAtNum;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (pointNumber > 0) {
            if (pointNumber >= guideAsTextAtNum) {
                drawGuideText(canvas);
            }
            else {
                drawPoint(canvas);
            }
        }
        super.onDraw(canvas);
    }

    private void drawGuideText(Canvas canvas) {
        mPaint.setColor(guideTextColor);
        mPaint.setTextSize(guideTextSize);
        String text = mFocusIndex + "/" + pointNumber;
        Rect rect = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), rect);

        float xPos;
        if (guideTextGravity == Gravity.LEFT) {
            mPaint.setTextAlign(Paint.Align.LEFT);
            xPos = dp2px(24);
        }
        else if (guideTextGravity == Gravity.RIGHT) {
            mPaint.setTextAlign(Paint.Align.RIGHT);
            xPos = getWidth() - dp2px(24);
        }
        // center
        else {
            mPaint.setTextAlign(Paint.Align.CENTER);
            xPos = getWidth() / 2;
        }

        // 纵向居中
        // y对应的横线并不是文字的下边界，而是基准线Baseline
        //计算baseline
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float distance=(fontMetrics.bottom - fontMetrics.top)/2 - fontMetrics.bottom;
        float baseline = getHeight() / 2 + distance;
        canvas.drawText(text, xPos, baseline, mPaint);
        mPaint.reset();
    }

    private void drawPoint(Canvas canvas) {
        int margin = dp2px(5);
        // 纵向居中
        float cy = getHeight() / 2;
        int totalWidth = pointNumber * guidePointSize + (pointNumber - 1) * margin;
        // 横向居中
        int startOffset = (getWidth() - totalWidth) / 2;

        mPaint.setAntiAlias(true);
        for (int i = 0; i < pointNumber; i ++) {
            int cx = startOffset + i * guidePointSize + i * margin;
            if (i == mFocusIndex) {
                mPaint.setColor(guideFocusColor);
                mPaint.setStyle(Paint.Style.FILL);
                mPaint.setStrokeWidth(0);
                canvas.drawCircle(cx, cy, guidePointSize / 2, mPaint);
            }
            mPaint.setColor(guideNormalColor);
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeWidth(2);
            canvas.drawCircle(cx, cy, guidePointSize / 2, mPaint);
        }
    }
}
