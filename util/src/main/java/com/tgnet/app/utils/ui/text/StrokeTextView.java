package com.tgnet.app.utils.ui.text;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.utils.ScreenUtils;

import java.lang.reflect.Field;

/**
 * Created by lzh on 2017/9/5.
 */

public class StrokeTextView extends TextView {
    private TextPaint mTextPaint;
    private int mTextColor;
    private int mOuterColor;
    private int mStrokeWidth;
    private boolean mDrawSideLine=true;//默认采用描边

    private Context mContext;

    public StrokeTextView(Context context, int mTextColor, int mOuterColor, int mStrokeWidth) {
        super(context);
        this.mContext=context;
        mTextPaint=this.getPaint();
        this.mTextColor = mTextColor;
        this.mOuterColor = mOuterColor;
        this.mStrokeWidth = mStrokeWidth;
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext=context;
        mTextPaint=this.getPaint();
        TypedArray array=context.obtainStyledAttributes(attrs, R.styleable.StrokeTextView);
        this.mTextColor=array.getColor(R.styleable.StrokeTextView_textColor,0xffffff);
        this.mOuterColor=array.getColor(R.styleable.StrokeTextView_outerColor,0xffffff);
        this.mStrokeWidth=array.getInt(R.styleable.StrokeTextView_width,0);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr, int mTextColor, int mOuterColor, int mStrokeWidth) {
        super(context, attrs, defStyleAttr);
        this.mContext=context;
        mTextPaint=this.getPaint();
        this.mTextColor = mTextColor;
        this.mOuterColor = mOuterColor;
        this.mStrokeWidth = mStrokeWidth;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(mDrawSideLine){
            setColor(mOuterColor);
            mTextPaint.setStrokeWidth(ScreenUtils.dp2px(mContext,mStrokeWidth));
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mTextPaint.setFakeBoldText(true);
            super.onDraw(canvas);

            setColor(mTextColor);
            mTextPaint.setStrokeWidth(0);
            mTextPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mTextPaint.setFakeBoldText(false);
        }
        super.onDraw(canvas);
    }

    private void setColor(int color){
        Field textColorField;
        try {
            textColorField=TextView.class.getDeclaredField("mCurTextColor");
            textColorField.setAccessible(true);
            textColorField.set(this,color);
            textColorField.setAccessible(false);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mTextPaint.setColor(color);
    }
}
