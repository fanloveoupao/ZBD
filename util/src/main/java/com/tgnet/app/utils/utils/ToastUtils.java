package com.tgnet.app.utils.utils;

import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tgnet.app.utils.R;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class ToastUtils {

    private ToastUtils() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public static boolean isShow = true;

    private static TextView mTvNewMsgCount;
    private static LinearLayout mLlNewMessage;
    private static View toastView;

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 短时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showShort(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, CharSequence message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public static void showLong(Context context, int message) {
        if (isShow)
            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, CharSequence message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public static void show(Context context, int message, int duration) {
        if (isShow)
            Toast.makeText(context, message, duration).show();
    }

    public static void showNotifierPro(Context context, String message) {
        if (toastView == null)
            toastView = LayoutInflater.from(context).inflate(R.layout.dialog_automatic_dismiss_notifierpro, null);
        mTvNewMsgCount = (TextView) toastView.findViewById(R.id.tv_new_msg_count);
        mLlNewMessage = (LinearLayout) toastView.findViewById(R.id.ll_new_message);
        mTvNewMsgCount.setText(message);
        final Toast toast = new Toast(context);
        TranslateAnimation translate = new TranslateAnimation(0, 0, 0, mLlNewMessage.getLayoutParams().height);
        translate.setDuration(800);
        translate.setFillAfter(true);
        mLlNewMessage.setVisibility(View.VISIBLE);
        mLlNewMessage.startAnimation(translate);
        translate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        TranslateAnimation translate = new TranslateAnimation(0, 0, mLlNewMessage.getLayoutParams().height, 0);
                        translate.setDuration(800);
                        translate.setFillAfter(true);
                        mLlNewMessage.startAnimation(translate);
                        translate.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                mLlNewMessage.setVisibility(View.GONE);
                                toast.cancel();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }, 500);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(toastView);
        toast.show();
    }

}
