package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Scroller;
import android.widget.TextView;


public class WheelView extends ViewGroup implements OnGestureListener {

    private BaseAdapter adapter;

    private int itemH = 0;
    private int selection;

    public static int SNAP_VELOCITY = 300; // 最小的滑动速率

    private Scroller mScroller;
    private Context mContext;
    private GestureDetector detector;
    private boolean isUp = true;

    private String drawText = null;

    private OnSelectItemListener listener;

    public WheelView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mScroller = new Scroller(context);
        mContext = context;
        detector = new GestureDetector(this);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);

            v.layout(0, i * v.getMeasuredHeight(), getWidth(),
                    (i + 1) * v.getMeasuredHeight());
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            child.measure(width, height);
        }
    }

    public void setDrawText(String drawText) {

        this.drawText = drawText;
    }

    public void setAdapter(BaseAdapter adapter) {

        if (null == adapter)
            return;

        removeAllViews();

        this.adapter = adapter;

        for (int i = 0; i < adapter.getCount(); i++) {
            addView(adapter.getView(i, null, this));
        }

        View listItem = adapter.getView(0, null, this);
        listItem.measure(0, 0);

        itemH = listItem.getMeasuredHeight();

        int totalHeight = itemH * 5;
        this.getLayoutParams().height = totalHeight;

        requestLayout();

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (0 == itemH)
            return;

        int top = itemH * 2 + getScrollY();

        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));

        Rect rect = new Rect(0, top, getWidth(), top + itemH);

        canvas.drawRect(rect, paint);

        if (null != drawText) {

            paint.setColor(Color.parseColor("#ff8800"));
            paint.setTextSize(30);
            paint.setTextAlign(Align.CENTER);

            FontMetrics fm = paint.getFontMetrics();

            int baseline = (int) (rect.top
                    + (rect.bottom - rect.top - fm.bottom + fm.top) / 2 - fm.top);

            canvas.drawText(drawText, getWidth() / 2 + 70, baseline, paint);

        }

        if (null != mScroller && mScroller.isFinished() && isUp) {
            selection = top / itemH;
            if (null != listener)
                listener.onSelectItem((String) adapter.getItem(selection));
            setSelectionItemText(selection);
        }

    }

    private void setSelectionItemText(int selection) {
//        int count=getChildCount();
////        if(0<selection&&selection < count+1) {
////            TextView tvCurrent = (TextView) getChildAt(selection).findViewById(R.id.tv_item);
////            TextView tvPre = (TextView) getChildAt(selection-1).findViewById(R.id.tv_item);
////            TextView tvNext = (TextView) getChildAt(selection+1).findViewById(R.id.tv_item);
////            tvCurrent.setTextColor(Color.parseColor("#ff5500"));
////            tvPre.setTextColor(Color.parseColor("#333333"));
////            tvNext.setTextColor(Color.parseColor("#333333"));
////        }
////        else if(selection == 0) {
////            TextView tvCurrent = (TextView) getChildAt(selection).findViewById(R.id.tv_item);
////            TextView tvNext = (TextView) getChildAt(selection+1).findViewById(R.id.tv_item);
////            tvCurrent.setTextColor(Color.parseColor("#ff5500"));
////            tvNext.setTextColor(Color.parseColor("#333333"));
////        }
////        else if(selection==count+1){
////            TextView tvCurrent = (TextView) getChildAt(selection).findViewById(R.id.tv_item);
////            TextView tvPre = (TextView) getChildAt(selection-1).findViewById(R.id.tv_item);
////            tvCurrent.setTextColor(Color.parseColor("#ff5500"));
////            tvPre.setTextColor(Color.parseColor("#333333"));
////        }
//        int color=Color.parseColor("#333333");
//        ((TextView) getChildAt(selection).findViewById(1)).setTextColor(Color.WHITE);
//        if(selection<count+2){
//            ((TextView) getChildAt(selection+1).findViewById(1)).setTextColor(color);
//        }
//        if(selection<count+1){
//            ((TextView) getChildAt(selection+2).findViewById(1)).setTextColor(color);
//        }
//        if(selection>0) {
//            ((TextView) getChildAt(selection - 1).findViewById(1)).setTextColor(color);
//        }
//        if(selection>1) {
//            ((TextView) getChildAt(selection - 2).findViewById(1)).setTextColor(color);
//        }
        for (int i = 0; i < getChildCount(); i++) {
            TextView tv = (TextView) getChildAt(i).findViewById(1);
            if (i == selection) {
                tv.setTextColor(Color.parseColor("#ff8800"));
            } else {
                tv.setTextColor(Color.parseColor("#999999"));
            }
        }

    }

    public void setDefaultItem(int index, String item) {

        try {
            int scrollY = itemH * (index);
            scrollTo(0, scrollY);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setDefaultItem(String item) {

        try {
            for (int i = 0; i < adapter.getCount(); i++) {
                String value = (String) adapter.getItem(i);

                if (value.equals(item)) {

                    int scrollY = itemH * (i - 2);

                    scrollTo(0, scrollY);

                    break;
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                if (mScroller != null) {
                    if (!mScroller.isFinished()) {
                        mScroller.abortAnimation();
                    }
                }
                isUp = false;

                break;
            case MotionEvent.ACTION_MOVE:

                setSelectionItemText(-1);

                break;
            case MotionEvent.ACTION_UP:

                isUp = true;

                if (mScroller.isFinished()) {

                    mScroller.abortAnimation();

                    int scrollY = getScrollY();

                    int deta = scrollY % itemH;
                    if (deta >= itemH / 2)
                        deta = itemH - deta;
                    else
                        deta = -deta;

                    mScroller.startScroll(0, scrollY, 0, deta);

                    computeScroll();
                }

                break;
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return this.detector.onTouchEvent(event);
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {

            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());

            if (mScroller.isFinished()) {

                int finalY = mScroller.getFinalY();
                if (finalY != 0 && finalY % itemH != 0) {

                    int deta = finalY % itemH;

                    if (deta <= itemH / 2)
                        deta = -deta;
                    else
                        deta = itemH - deta;

                    mScroller.startScroll(0, finalY, 0, deta);
                }
            }
            postInvalidate();
        }
    }

    @Override
    public boolean onDown(MotionEvent e) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
                            float distanceY) {

        if (!mScroller.isFinished())
            return false;

        int detaY = (int) distanceY; // 每次滑动应该移动的距离

        int tempY = getScrollY() + detaY;

        if (tempY < 0)
            detaY = 0;

        if (tempY > itemH * (getChildCount() - 5))
            return false;

        scrollBy(0, detaY);

        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                           float velocityY) {

        int slow = -(int) velocityY * 3 / 4;

        int scrollY = getScrollY();

        mScroller.fling(0, scrollY, 0, slow, 0, 0, 0,
                itemH * (adapter.getCount() - 5));

        computeScroll();

        return false;
    }

    public void setOnSelectItemListener(OnSelectItemListener listener) {

        this.listener = listener;
    }

    public interface OnSelectItemListener {

        public void onSelectItem(String item);
    }
}
