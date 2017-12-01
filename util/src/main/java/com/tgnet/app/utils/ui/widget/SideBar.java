package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.tgnet.app.utils.R;
import com.tgnet.app.utils.utils.TypedValueUtil;
import com.tgnet.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by fan-gk on 2017/4/27.
 */


public class SideBar extends Button {
    public final static char SEARCH_CHAR = Character.MIN_VALUE;
    public final static char STAR_CHAR = '★';
    public final static char OTHER_CHAR = '#';

    // 触摸事件
    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    // 26个字母
    public static char[] FULL_INDEXS = {SEARCH_CHAR, STAR_CHAR, 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I',
            'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', OTHER_CHAR};


    private char[] indexs;
    private int choose = -1;// 选中
    private Paint paint = new Paint();
    private Handler handler;
    private int interval;

    private TextView mTextDialog;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SideBar(Context context) {
        super(context);
        init();
    }

    private void init() {
        indexs = FULL_INDEXS;
        handler = new Handler();
    }

    public void setIndexs(boolean includeSearch, Set<Character> indexs) {
        List<Character> ins = new ArrayList<>();
        if (indexs != null) {
            if (includeSearch)
                ins.add(SEARCH_CHAR);
            for (int i = 1; i < FULL_INDEXS.length; i++) {
                if (indexs.contains(FULL_INDEXS[i])) {
                    ins.add(FULL_INDEXS[i]);
                }
            }
        }
        char[] r = new char[ins.size()];
        for (int i = 0; i < ins.size(); i++) {
            r[i] = ins.get(i) == null ? OTHER_CHAR : ins.get(i).charValue();
        }
        this.indexs = r;
        handler.post(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        });
    }

    /**
     * 重写这个方法
     */
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int height = getHeight();
        int width = getWidth();
        if (indexs.length == 0)
            return;
        interval = Math.min(height / indexs.length, TypedValueUtil.fromDip(35));

        int textSize = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 14, getContext()
                        .getApplicationContext().getResources()
                        .getDisplayMetrics());
        paint.setAntiAlias(true);

        int size = indexs.length;

        for (int i = 0; i < size; i++) {
            if (indexs[i] == SEARCH_CHAR) {
                Bitmap bm_star = BitmapFactory.decodeResource(getResources(),
                        R.drawable.icon_search_gray);
                float xPo = width / 2 - bm_star.getWidth() / 2;
                float yPo = interval - bm_star.getHeight();
                canvas.drawBitmap(bm_star, xPo, yPo, paint);
            } else {
                String v = String.valueOf(indexs[i]);
                // 抗锯齿
                paint.setAntiAlias(true);
                paint.setTypeface(Typeface.DEFAULT);
                paint.setTextSize(textSize);
                paint.setColor(Color.parseColor("#6a737d"));
                float xPos = width / 2 - (paint.measureText(v) / 2);
                float yPos = interval * i + interval;
                canvas.drawText(v, xPos, yPos, paint);
            }
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();// 点击y坐标
        final int oldChoose = choose;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        final int c = (int) Math.floor(y / interval);

        switch (action) {
            case MotionEvent.ACTION_UP:
                setBackground(new ColorDrawable(0x00000000));
                choose = -1;//
                invalidate();
                if (mTextDialog != null) {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                setBackgroundResource(R.drawable.sidebar_background);
                if (oldChoose != c) {
                    if (c >= 0 && c < indexs.length) {
                        final String s = String.valueOf(indexs[c]);
                        if (listener != null) {
                            listener.onTouchingLetterChanged(s);
                        }
                        if (mTextDialog != null && !StringUtil.isNullOrEmpty(s)) {
                            mTextDialog.setText(s);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        choose = c;
                    }
                }
                break;
        }
        return true;
    }

    /**
     * 向外公开的方法
     *
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(
            OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /**
     * 接口
     *
     * @author coder
     */
    public interface OnTouchingLetterChangedListener {
        void onTouchingLetterChanged(String s);
    }
}


