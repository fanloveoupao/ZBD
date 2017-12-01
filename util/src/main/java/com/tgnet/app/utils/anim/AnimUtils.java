package com.tgnet.app.utils.anim;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * author: ZK.
 * date:   On 2017/5/15.
 */
public class AnimUtils {

    public static void showAnimFromTop(View view) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        viewAction(view, true, mShowAction);

    }

    public static void showAnimFromBottom(View view) {
        TranslateAnimation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        viewAction(view, true, mShowAction);

    }

    public static void hideAnimToBottom(View view) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f);
        viewAction(view, false, mHiddenAction);
    }

    public static void hideAnimToTop(View view) {
        TranslateAnimation mHiddenAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f);
        viewAction(view, false, mHiddenAction);
    }

    private static void viewAction(View view, boolean visible, TranslateAnimation animation) {
        animation.setDuration(500);
        view.startAnimation(animation);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);

    }
}
