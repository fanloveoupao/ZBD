package com.tgnet.app.utils.ui.widget.statusbar;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * Created by fan-gk on 2017/8/31.
 */


public class SoftHideKeyBoardUtil {

    public static void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) scroll.getLayoutParams();
                //1、获取main在窗体的可视区域
                main.getWindowVisibleDisplayFrame(rect);
                //2、获取main在窗体的不可视区域高度，在键盘没有弹起时，main.getRootView().getHeight()调节度应该和rect.bottom高度一样
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                int screenHeight = main.getRootView().getHeight();//屏幕高度
                //3、不可见区域大于屏幕本身高度的1/4：说明键盘弹起了
                if (mainInvisibleHeight > screenHeight / 4) {

                    int srollHeight = rect.bottom;
                    //5､让界面整体上移键盘的高度
                    layoutParams.height = srollHeight;
                } else {
                    //3、不可见区域小于屏幕高度1/4时,说明键盘隐藏了，把界面下移，移回到原有高度
                    layoutParams.height = 0;
                }
                scroll.setLayoutParams(layoutParams);
            }
        });
    }
}