package com.tgnet.app.utils.ui.widget.statusbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.utils.TypedValueUtil;
import com.tgnet.log.LoggerResolver;

/**
 * Created by fan-gk on 2017/8/15.
 */

public class GradientToolBar extends Toolbar {

    public static final int STRAT_BLUE = 0xffb629;
    public static final int END_BLUE = 0xff9c00;
    private Paint mPaint;
    private float windowWidth;
    private int height;
    private boolean mWhite;

    public GradientToolBar(Context context) {
        this(context, null);
    }

    public GradientToolBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public GradientToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        windowWidth = wm.getDefaultDisplay().getWidth();//获取屏幕宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (!mWhite) {
            for (int i = 1; i <= windowWidth; i++) {
                // 设置画笔颜色为自定义颜色
                mPaint.setColor((Integer) evaluateColor(Math.pow(i / windowWidth, 2), STRAT_BLUE, END_BLUE));
                canvas.drawRect(i - 1, 0, i, height, mPaint);
            }
        } else {
            int statusBarHeight = StatusBarUtil.getStatusBarHeight(getContext());
            if (height < statusBarHeight)
                LoggerResolver.getInstance().error("tgnet", "the height of toolbar must taller than stausbar ");
            mPaint.setColor(ContextCompat.getColor(getContext(), R.color.transparency_10));
            canvas.drawRect(0, 0, windowWidth, statusBarHeight, mPaint);
        }

    }

    public void setBackgroundWhite() {
        mWhite = true;
        invalidate();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
    }

    /**
     * 颜色变化过度
     *
     * @param fraction
     * @param startValue
     * @param endValue
     * @return
     */
    public Object evaluateColor(double fraction, Object startValue, Object endValue) {
        int startInt = (Integer) startValue;
        int startA = (startInt >> 24) & 0xff;
        int startR = (startInt >> 16) & 0xff;
        int startG = (startInt >> 8) & 0xff;
        int startB = startInt & 0xff;

        int endInt = (Integer) endValue;
        int endA = (endInt >> 24) & 0xff;
        int endR = (endInt >> 16) & 0xff;
        int endG = (endInt >> 8) & 0xff;
        int endB = endInt & 0xff;

        return (startA + (int) (fraction * (endA - startA))) << 24 |
                (startR + (int) (fraction * (endR - startR))) << 16 |
                (startG + (int) (fraction * (endG - startG))) << 8 |
                (startB + (int) (fraction * (endB - startB)));
    }
}
