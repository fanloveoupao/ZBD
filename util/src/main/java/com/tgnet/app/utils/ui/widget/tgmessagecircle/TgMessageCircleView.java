package com.tgnet.app.utils.ui.widget.tgmessagecircle;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * 描述:自定义可拖拽消息气泡View
 * Created by Xialm on 2017-08-08.
 */

public class TgMessageCircleView extends View {
    // 两个实心圆--根据点的坐标来绘制圆
    private PointF mDragPoint, mFixationPoint;
    private Paint mPaint;
    private int mDragRadius = 9; // 拖拽圆半径

    // 固定圆最大半径(初始半径)/半径的最小值
    private int mFixationRadiusMax = 7;
    private int mFixationRadiusMin = 4;
    private int mFixationRadius;
    private Bitmap mDragBitmap; // 原始View的快照
    private static int mDragColor = Color.parseColor("#fd424d");
    /*
    实现思路:
        1.手指按下的时候,绘制出两个圆(固定圆和拖拽圆)
            固定圆的圆心位置固定,但是半径可发生变化
            拖拽圆的圆心可变,半径固定
        2.手指拖动的时候,不断更新拖拽圆的位置(不断的绘制),
            同时改变固定圆的圆心大小(两个圆越近,固定圆半径越大;两圆越远,固定圆的半径越小;
            两圆距离超过一定值时,固定圆消失不见即不在绘制)
     */

    public TgMessageCircleView(Context context) {
        this(context, null);
    }

    public TgMessageCircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TgMessageCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPaint.setColor(mDragColor);
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);

        mDragRadius = dip2px(mDragRadius);
        mFixationRadiusMax = dip2px(mFixationRadiusMax);
        mFixationRadiusMin = dip2px(mFixationRadiusMin);
    }

    public void updateDragPointLocation(float moveX, float moveY) {
        mDragPoint.x = moveX;
        mDragPoint.y = moveY;
        invalidate();
    }

    /**
     * 初始化点
     */
    public void initPoint(float downX, float downY) {
        mFixationPoint = new PointF(downX, downY);
        mDragPoint = new PointF(downX, downY);
        invalidate(); // 绘制
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mFixationPoint == null || mDragPoint == null) {
            return;
        }

        // 画两个圆: 固定圆有一个初始化大小,而且随着两圆距离的增大而变小,小到一定程度就不见了(不画了)

        // 拖拽View的快照 大小不变,位置跟随手指移动
        canvas.drawCircle(mDragPoint.x, mDragPoint.y, mDragRadius, mPaint);


        // 绘制贝塞尔曲线 如果两圆拖拽到一定距离,固定圆消失的同时不再绘制贝塞尔曲线
        Path besaierPath = getBesaierPath();

        if (besaierPath != null) {
            // 固定圆半径可变 当拖拽在一定距离时才去绘制,超过一定距离就不在绘制
            canvas.drawCircle(mFixationPoint.x, mFixationPoint.y, mFixationRadius, mPaint);
            canvas.drawPath(besaierPath, mPaint);
        }

        if (mDragBitmap != null) {
            canvas.drawBitmap(mDragBitmap, mDragPoint.x - mDragBitmap.getWidth() / 2, mDragPoint.y - mDragBitmap.getHeight() / 2, null); // 从左上角开始绘制
        }
    }

    /**
     * 获取贝塞尔曲线路径
     */
    private Path getBesaierPath() {
        double distance = getPointsDistance(mDragPoint, mFixationPoint);

        // 随着拖拽的距离变化,逐渐改变固定圆的半径
        mFixationRadius = (int) (mFixationRadiusMax - distance / 18);
        if (mFixationRadius < mFixationRadiusMin) {
            // 超过一定距离  贝塞尔曲线和固定圆都不要绘制了
            return null;
        }

        Path besaierPath = new Path();

        // 求角a
        double angleA = Math.atan((mDragPoint.y - mFixationPoint.y) / (mDragPoint.x - mFixationPoint.x));

        float P0x = (float) (mFixationPoint.x + mFixationRadius * Math.sin(angleA));
        float P0y = (float) (mFixationPoint.y - mFixationRadius * Math.cos(angleA));

        float P3x = (float) (mFixationPoint.x - mFixationRadius * Math.sin(angleA));
        float P3y = (float) (mFixationPoint.y + mFixationRadius * Math.cos(angleA));

        float P1x = (float) (mDragPoint.x + mDragRadius * Math.sin(angleA));
        float P1y = (float) (mDragPoint.y - mDragRadius * Math.cos(angleA));

        float P2x = (float) (mDragPoint.x - mDragRadius * Math.sin(angleA));
        float P2y = (float) (mDragPoint.y + mDragRadius * Math.cos(angleA));


        // 拼接 贝塞尔曲线路径
        // 移动到我们的起始点,否则默认从(0,0)开始
        besaierPath.moveTo(P0x, P0y);
        // 求控制点坐标,我们取两圆圆心为控制点(如果取黄金比例0.618是比较好的)
        PointF controlPoint = getControlPoint();
        // 画第一条 前两个参数为控制点坐标  后两个参数为终点坐标
        besaierPath.quadTo(controlPoint.x, controlPoint.y, P1x, P1y);
        besaierPath.lineTo(P2x, P2y);
        besaierPath.quadTo(controlPoint.x, controlPoint.y, P3x, P3y);
        besaierPath.close();

        return besaierPath;
    }

    /**
     * 获取控制点
     */
    private PointF getControlPoint() {
        return new PointF((mDragPoint.x + mFixationPoint.x) / 2, (mDragPoint.y + mFixationPoint.y) / 2);
    }

    /**
     * 获取两个点之间的距离(勾股定理)
     */
    private double getPointsDistance(PointF point1, PointF point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) + (point1.y - point2.y) * (point1.y - point2.y));
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    public static void bindMessageView(View view, OnTgMessageCircleTouchListener.OnViewDragDisappearListener disappearListener) {
        view.setOnTouchListener(new OnTgMessageCircleTouchListener(view, view.getContext(), disappearListener));
    }

    public static void bindMessageView(View view, int dragColor, OnTgMessageCircleTouchListener.OnViewDragDisappearListener disappearListener) {
        mDragColor = dragColor;
        view.setOnTouchListener(new OnTgMessageCircleTouchListener(view, view.getContext(), disappearListener));
    }

    public void setDragBitmap(Bitmap dragBitmap) {
        this.mDragBitmap = dragBitmap;
    }

    /**
     * 处理松手逻辑:
     * 松手时,如果距离超过一定值,则显示爆炸效果;
     * 如果没有超过一定值,则显示回弹效果
     */
    public void handleActionUP() {
        if (mFixationRadius < mFixationRadiusMin) {
            // 显示爆炸效果
            if (dragListener != null) {
                dragListener.onViewDragDisappear(mDragPoint);
            }

        } else {
            // 显示回弹效果 属性动画
            ValueAnimator animator = ObjectAnimator.ofFloat(1f);
            animator.setDuration(300);

            final PointF startPoint = new PointF(mDragPoint.x, mDragPoint.y);
            final PointF endPoint = new PointF(mFixationPoint.x, mFixationPoint.y);

            animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    float percent = (float) animation.getAnimatedValue();
                    //PointF pointF = TgMessageUtils.getPointByPercent(mDragPoint,mFixationPoint,percent);
                    // 这个起始点和结束点,不能这样传值(这样传值的话,起点和终点一直在发生变化),应该转的是固定点和刚开始放手时的坐标的点
                    PointF pointF = TgMessageUtils.getPointByPercent(startPoint, endPoint, percent);
                    updateDragPointLocation(pointF.x, pointF.y);
                }
            });
            animator.setInterpolator(new OvershootInterpolator(4f));
            animator.start();
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    // 当动画结束的时候,重新让View可拖动
                    if (dragListener != null) {
                        dragListener.onViewDragRestore();
                    }
                }
            });


        }
    }

    ///////////////////////////////////////////////////////////////////////////
    // 自定义拖拽爆炸控件View消失和重置的监听
    ///////////////////////////////////////////////////////////////////////////
    private OnMessageBubbleViewDragListener dragListener;

    public void setOnMessageBubbleViewDragListener(OnMessageBubbleViewDragListener dragListener) {
        this.dragListener = dragListener;
    }

    public interface OnMessageBubbleViewDragListener {
        void onViewDragDisappear(PointF pointF);

        void onViewDragRestore();
    }

}
