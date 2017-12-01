package com.tgnet.app.utils.ui.widget.tgmessagecircle;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PixelFormat;
import android.graphics.PointF;
import android.graphics.drawable.AnimationDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.tgnet.app.utils.R;


/**
 * 描述:拖拽控件View的触摸监听 (处理拖拽爆炸效果)
 * Created by Xialm on 2017-08-10.
 */

public class OnTgMessageCircleTouchListener implements View.OnTouchListener, TgMessageCircleView.OnMessageBubbleViewDragListener {
    private final View mOriginalView; //原来的,真实的那个View控件
    private final OnViewDragDisappearListener mDisappearListener;
    private TgMessageCircleView mTgMessageCircleView; //我们实现贝塞尔的View
    private final WindowManager mWindowManager;
    private WindowManager.LayoutParams mParams;
    private Context mContext;
    private FrameLayout mBombLayout;
    private ImageView mBombView;

    public OnTgMessageCircleTouchListener(View view, Context context, OnViewDragDisappearListener disappearListener) {
        this.mOriginalView = view;
        this.mDisappearListener = disappearListener;
        mContext = context;
        mTgMessageCircleView = new TgMessageCircleView(context);
        mTgMessageCircleView.setOnMessageBubbleViewDragListener(this);
        mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE; //TYPE_TOAST7.0是有问题的
        mParams.format = PixelFormat.RGBA_8888; //背景要透明.否则会黑屏
        mBombLayout = new FrameLayout(mContext);
        mBombView = new ImageView(mContext);
        mBombView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mBombLayout.addView(mBombView);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mOriginalView.getParent().requestDisallowInterceptTouchEvent(true);
                // 搞一个原始View的快照,并添加WindowManger中
                mWindowManager.addView(mTgMessageCircleView, mParams);

                // 初始化贝塞尔View的中心点 在原始View的中线点
                // 获取原始View在屏幕中的坐标
                int[] location = new int[2];
                mOriginalView.getLocationOnScreen(location); //默认获取的是View左上角在屏幕上的坐标(y坐标包含状态栏的高度)
                mTgMessageCircleView.initPoint(location[0] + mOriginalView.getWidth() / 2, location[1] + mOriginalView.getHeight() / 2 - TgMessageUtils.getStatusBarHeight(mContext));

                // 为什么不设置左上角呢? 拖拽时贝塞尔曲线会连到左上角 不美观

                // 给贝塞尔View一张原始View的快照
                Bitmap copyBitmap = getCopyBitmapFromView(mOriginalView);
                // 给拖拽的消息设置一张原始View的快照
                mTgMessageCircleView.setDragBitmap(copyBitmap);

                // 已经绘制过后再把原来的隐藏掉
                //mOriginalView.setVisibility(View.INVISIBLE);  // 放到下面,一拖动就隐藏

                break;
            case MotionEvent.ACTION_MOVE:
                // 解决一点击View出现闪动的bug(一旦拖动在隐藏)
                if (mOriginalView.getVisibility() == View.VISIBLE) {
                    mOriginalView.setVisibility(View.INVISIBLE);
                }
                mTgMessageCircleView.updateDragPointLocation(event.getRawX(), event.getRawY() - TgMessageUtils.getStatusBarHeight(mContext)); // 同样要减去状态栏高度
                break;
            case MotionEvent.ACTION_UP:
                mOriginalView.getParent().requestDisallowInterceptTouchEvent(false);
                mTgMessageCircleView.handleActionUP();
                break;
        }
        return true;
    }

    private Bitmap getCopyBitmapFromView(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    /**
     * 拖拽的View消失时的监听方法
     *
     * @param pointF
     */
    @Override
    public void onViewDragDisappear(PointF pointF) {
        // 移除消息气泡贝塞尔View,同时添加一个爆炸的View动画(帧动画)
        mWindowManager.removeView(mTgMessageCircleView);
        mWindowManager.addView(mBombLayout, mParams);
        mBombView.setBackgroundResource(R.drawable.circle_bmob_anim);

        AnimationDrawable bombDrawable = (AnimationDrawable) mBombView.getBackground();
        // 矫正爆炸时,位置偏下的问题
        mBombView.setX(pointF.x - bombDrawable.getIntrinsicWidth() / 2);
        mBombView.setY(pointF.y - bombDrawable.getIntrinsicHeight() / 2);
        bombDrawable.start();

        mBombView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 动画执行完毕,把爆炸布局及时从WindowManager移除
                mWindowManager.removeView(mBombLayout);

                if (mDisappearListener != null) {
                    mDisappearListener.onDisappear(mOriginalView);
                }

            }
        }, getAnimationTotalTime(bombDrawable));
    }

    private long getAnimationTotalTime(AnimationDrawable animationDrawable) {
        long time = 0;
        int framesNum = animationDrawable.getNumberOfFrames();
        for (int i = 0; i < framesNum; i++) {
            time += animationDrawable.getDuration(i);
        }
        return time;
    }

    /**
     * 松手后,拖拽View消失,原来的View重新显示的监听方法
     */
    @Override
    public void onViewDragRestore() {
        mWindowManager.removeView(mTgMessageCircleView);
        mOriginalView.setVisibility(View.VISIBLE);
    }

    /**
     * 真正的处理View的消失的监听
     */
    public interface OnViewDragDisappearListener {
        /**
         * 原始View消失的监听
         *
         * @param originalView 原始的View
         */
        void onDisappear(View originalView);
    }

}
