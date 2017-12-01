package com.tgnet.app.utils.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by fan-gk on 2017/4/25.
 */

public class SimplePopupWindow extends android.widget.PopupWindow {
    private boolean mCancelable=true;


    public SimplePopupWindow(Context context, @LayoutRes int resource){
        this(LayoutInflater.from(context).inflate(resource, null),true);
    }
    public SimplePopupWindow(Context context, @LayoutRes int resource,boolean cancelable){
        this(LayoutInflater.from(context).inflate(resource, null),cancelable);
    }
    public SimplePopupWindow(Context context, @LayoutRes int resource, int width, int height){
        this(LayoutInflater.from(context).inflate(resource, null), width, height,true);
    }

    public SimplePopupWindow(View view, int width, int height,boolean cancelable){
        super(view, width, height, true);
        this.mCancelable=cancelable;
        if(cancelable) {
            final ColorDrawable dw = new ColorDrawable(Color.TRANSPARENT);
            setBackgroundDrawable(dw);
            setOutsideTouchable(true);

            view.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismiss();
                        return true;
                    }
                    return false;
                }
            });
        }else{
            setBackgroundDrawable(null);
            setTouchable(true);
            setOutsideTouchable(false);
            setFocusable(false);
        }
    }



    public SimplePopupWindow(View view,boolean cancelable){
        this(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT,cancelable);
    }

    @Override
    public void showAsDropDown(View anchor) {
        showAsDropDown(anchor, anchor.getWidth(), 0);
    }
}

