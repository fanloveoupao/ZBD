package com.tgnet.app.utils.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.os.Build;
import android.view.ViewTreeObserver;

import com.tgnet.app.utils.ui.widget.statusbar.StatusBarUtil;

/**
 * Created by fan-gk on 2017/3/18.
 */

public class KeyboardChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {
    private Activity mContentView;
    private OnKeyBoardListener mKeyBoardListen;

    public interface OnKeyBoardListener {
        void onKeyboardChange(boolean isShow, int keyboardHeight);
    }

    public void setOnKeyBoardActionListener(OnKeyBoardListener keyBoardListen) {
        this.mKeyBoardListen = keyBoardListen;
    }

    public KeyboardChangeListener(Activity contextObj) {
        mContentView = contextObj;
        if (mContentView == null)
            try {
                throw new Exception("监听的视图不能为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (mContentView != null) {
            addContentTreeObserver();
        }
    }

    private void addContentTreeObserver() {
        mContentView.getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        int currHeight = ScreenUtils.getScreenHeight(mContentView);
        Rect r = new Rect();
        mContentView.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
        int heightDifference = currHeight - r.bottom + StatusBarUtil.getNavBarHeight(mContentView);
        boolean isKeyboardShowing = heightDifference > currHeight / 3;
        if (mKeyBoardListen != null) {
            mKeyBoardListen.onKeyboardChange(isKeyboardShowing, heightDifference);
        }
    }

    public void removeKeyBoardActionListener() {
        if (mContentView == null)
            try {
                throw new Exception("监听的视图不能为空");
            } catch (Exception e) {
                e.printStackTrace();
            }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mContentView.getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
            mContentView.getWindow().getDecorView().getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
