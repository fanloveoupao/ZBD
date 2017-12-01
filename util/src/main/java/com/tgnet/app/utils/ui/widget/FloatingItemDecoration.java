package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Dimension;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;


import com.tgnet.app.utils.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by -- on 2017/1/13.
 * 头部悬浮的效果，只支持LinearLayoutManager垂直滑动的情况
 */

public class FloatingItemDecoration extends RecyclerView.ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;
    private int dividerHeight;
    private int dividerWidth;
    private Map<Integer, String> keys = new HashMap<>();
    private int mTitleHeight;
    private Paint mTextPaint;
    private Paint mBackgroundPaint;
    private float mTextHeight;
    private float mTextBaselineOffset;
    private Context mContext;
    private int marginLeft = 0;
    private int marginRight=0;



    /**
     * 滚动列表的时候是否一直显示悬浮头部
     */
    private boolean showFloatingHeaderOnScrolling = true;
    private boolean drawBorderTop = false;
    private boolean drawBorderBottom = false;

    public FloatingItemDecoration(Context context) {
        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        this.dividerHeight = mDivider.getIntrinsicHeight();
        this.dividerWidth = mDivider.getIntrinsicWidth();
        init(context);
    }

    public void setMarginLeft(int left) {
        this.marginLeft = left;
    }
    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }
    /**
     * 自定义分割线
     *
     * @param context
     * @param drawableId 分割线图片
     */
    public FloatingItemDecoration(Context context, @DrawableRes int drawableId) {
        mDivider = ContextCompat.getDrawable(context, drawableId);
        this.dividerHeight = mDivider.getIntrinsicHeight();
        this.dividerWidth = mDivider.getIntrinsicWidth();
        init(context);
    }

    /**
     * 自定义分割线
     * 也可以使用{@link Canvas#drawRect(float, float, float, float, Paint)}或者{@link Canvas#drawText(String, float, float, Paint)}等等
     * 结合{@link Paint}去绘制各式各样的分割线
     *
     * @param context
     * @param color         整型颜色值，非资源id
     * @param dividerWidth  单位为dp
     * @param dividerHeight 单位为dp
     */
    public FloatingItemDecoration(Context context, @ColorInt int color, @Dimension float dividerWidth,
                                  @Dimension float dividerHeight) {
        mDivider = new ColorDrawable(color);
        this.dividerWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dividerWidth, context.getResources().getDisplayMetrics());
        this.dividerHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dividerHeight, context.getResources().getDisplayMetrics());
        init(context);
    }

    public void setText(int size, int color) {
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size,
                mContext.getResources().getDisplayMetrics()));
        mTextPaint.setColor(ContextCompat.getColor(mContext, color));
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();//字体属性测量
        mTextHeight = fm.bottom - fm.top;//计算文字高度
        mTextBaselineOffset = fm.bottom;
    }
    public void setBackground( int color) {
        mBackgroundPaint.setColor(ContextCompat.getColor(mContext, color));
    }
    private void init(Context mContext) {
        this.mContext = mContext;
        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);//设置抗锯齿
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14,
                mContext.getResources().getDisplayMetrics()));
        mTextPaint.setColor(ContextCompat.getColor(mContext, R.color.gray_666666));
        Paint.FontMetrics fm = mTextPaint.getFontMetrics();//字体属性测量
        mTextHeight = fm.bottom - fm.top;//计算文字高度
        mTextBaselineOffset = fm.bottom;

        mBackgroundPaint = new Paint();
        mBackgroundPaint.setAntiAlias(true);
        mBackgroundPaint.setColor(ContextCompat.getColor(mContext, R.color.gray_f5f5f5));
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVertical(c, parent);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        //onDrawOver方法的实现，该方法主要用来在RecyclerView最上层绘制，这样当列表滚动的时候，
        // 可以绘制标题栏，这样就看上去就一直悬浮在页面顶部，主要难点就是去判断上下两组标题栏的碰撞
        if (!showFloatingHeaderOnScrolling) {//控制是否需要悬浮头部的效果。如果不需要则直接返回，只有分组列表的效果
            return;
        }
        int firstVisiblePos = ((LinearLayoutManager) parent.getLayoutManager())
                .findFirstVisibleItemPosition();
        if (firstVisiblePos == RecyclerView.NO_POSITION) {
            return;
        }
        String title = getTitle(firstVisiblePos);
        if (TextUtils.isEmpty(title)) {
            return;
        }
        boolean flag = false;
        //getTitle方法主要是用来获取每个item的title，碰撞的检测主要是根据当前第一个可见元素和下一个元素进行对比
        //如果这两个元素所在的title不一样，就说明是属于不同的分组，标题栏即将进行碰撞了
        if (getTitle(firstVisiblePos + 1) != null && !title.equals(getTitle(firstVisiblePos + 1))) {
            //说明是当前组最后一个元素，但不一定碰撞了
            View child = parent.findViewHolderForAdapterPosition(firstVisiblePos).itemView;
            if (child.getTop() + child.getMeasuredHeight() < mTitleHeight) {
                //进一步检测碰撞
                //根据item的高度和标题栏的高度结合item的getTop值来共同判断，这样就可以去移动画布，
                // 造成标题栏被下一个挤上去的效果，同时需要在下面还原移动过的画布。
                c.save();//保存画布当前的状态
                flag = true;
                c.translate(0, child.getTop() + child.getMeasuredHeight() - mTitleHeight);//负的代表向上
            }
        }
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = parent.getPaddingTop();
        int bottom = top + mTitleHeight;
        c.drawRect(left, top, right, bottom, mBackgroundPaint);
        float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLeft,
                mContext.getResources().getDisplayMetrics());
        float y = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;//计算文字baseLine
        c.drawText(title, x, y, mTextPaint);
        if (flag) {
            //还原画布为初始状态
            c.restore();
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildViewHolder(view).getAdapterPosition();
//        int childCount = parent.getAdapter().getItemCount();

        //根据当前view的position判断需要在上方留出分割线空隙还是标题栏头部空隙
        if (keys.containsKey(pos)) {//留出头部偏移
            outRect.set(0, mTitleHeight, 0, 0);
        } else {
            int left = 0, top = dividerHeight, right = 0, bottom = dividerHeight;
            boolean isFirstRaw = isFirstRaw(pos);
            //垂直滚动线性布局
            if (isFirstRaw && drawBorderTop) {
                top = dividerHeight;
            }
            outRect.set(left, top, right, bottom);
        }
    }

    /**
     * *如果该位置没有，则往前循环去查找标题，
     * 找到说明该位置属于该分组
     *
     * @param position
     * @return
     */
    private String getTitle(int position) {
        while (position >= 0) {
            if (keys.containsKey(position)) {
                return keys.get(position);
            }
            position--;
        }
        return null;
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        //绘制具体的分割线和标题栏
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int top = 0;
        int bottom = 0;
        int allChildCount = parent.getAdapter().getItemCount();

        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (!keys.containsKey(params.getViewLayoutPosition())) {
                float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginLeft,
                        mContext.getResources().getDisplayMetrics());
                float r= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, marginRight,
                        mContext.getResources().getDisplayMetrics());
                if (drawBorderTop) {
                    //加上第一条
                    if (isFirstRaw(params.getViewLayoutPosition())) {
                        top = child.getTop() - params.topMargin - dividerHeight;
                        bottom = top + dividerHeight;
                        mDivider.setBounds(left, top, right-(int)r, bottom);
                        mDivider.draw(c);
                    }
                } else {
                    if ( //isFirstRaw(params.getViewLayoutPosition()) ||
                            (isLastRaw(params.getViewLayoutPosition(), allChildCount)
                                    && !drawBorderBottom)) {
                        continue;
                    }
                }
                if (keys.size() == 0) {
                    top = child.getBottom() + params.bottomMargin;
                    bottom = top + dividerHeight;
                    mDivider.setBounds((int) x, top,right-(int)r, bottom);
                    mDivider.draw(c);
                } else {
                    //画普通分割线
                    top = child.getTop() - params.topMargin - dividerHeight;
                    bottom = top + dividerHeight;
                    mDivider.setBounds((int) x, top,right-(int)r, bottom);
                    mDivider.draw(c);
                }
            } else {
                //画头部
                top = child.getTop() - params.topMargin - mTitleHeight;
                bottom = top + mTitleHeight;
                c.drawRect(left, top, right, bottom, mBackgroundPaint);
                float x = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,
                        mContext.getResources().getDisplayMetrics());
                float y = bottom - (mTitleHeight - mTextHeight) / 2 - mTextBaselineOffset;//计算文字baseLine
                c.drawText(keys.get(params.getViewLayoutPosition()), x, y, mTextPaint);
            }
        }
    }

    public void setShowFloatingHeaderOnScrolling(boolean showFloatingHeaderOnScrolling) {
        this.showFloatingHeaderOnScrolling = showFloatingHeaderOnScrolling;
    }

    public void setKeys(Map<Integer, String> keys) {
        if (keys == null) {
            this.keys.clear();
        } else {
            this.keys.putAll(keys);
        }
    }

    public void setDrawBorderTop(boolean drawBorderTop) {
        this.drawBorderTop = drawBorderTop;
    }

    public void setDrawBorderBottom(boolean drawBorderBottom) {
        this.drawBorderBottom = drawBorderBottom;
    }

    public void setmTitleHeight(int titleHeight) {
        this.mTitleHeight = titleHeight;
    }

    private boolean isFirstRaw(int pos) {
        if (pos == 0) {
            return true;
        }
        return false;
    }

    private boolean isLastRaw(int pos, int childCount) {
        if (pos == childCount - 1) {
            return true;
        }
        return false;
    }

}
