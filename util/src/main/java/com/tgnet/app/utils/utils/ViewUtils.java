package com.tgnet.app.utils.utils;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class ViewUtils {
    private ViewUtils() {

    }

    public static void clearCompoundDrawable(TextView textView) {
        textView.setCompoundDrawables(null, null, null, null);
    }

    public static void setLeftDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void setRightDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, drawable, null);
    }

    public static void setTopDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, drawable, null, null);
    }

    public static void setBottomDrawable(TextView textView, Drawable drawable) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        textView.setCompoundDrawables(null, null, null, drawable);
    }

    public static int getViewHeightOrWidth(View view, boolean isHeight) {
        int result;
        if (view == null) return 0;
        if (isHeight) {
            int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(0, h);
            result = view.getMeasuredHeight();
        } else {
            int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
            view.measure(w, 0);
            result = view.getMeasuredWidth();
        }
        return result;
    }


    public static int getViewHeight(View view) {
        return getViewHeightOrWidth(view, true);
    }

    public static int getViewWidth(View view) {
        return getViewHeightOrWidth(view, false);
    }


    public static void setViewWidthOrHeight(View view, int value, boolean isSetHeight) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (isSetHeight)
            layoutParams.height = value;
        else
            layoutParams.width = value;
        view.setLayoutParams(layoutParams);
    }

    public static void setViewHeight(View view, int value) {
        setViewWidthOrHeight(view, value, true);
    }

    public static void setViewWidth(View view, int value) {
        setViewWidthOrHeight(view, value, false);
    }


}
