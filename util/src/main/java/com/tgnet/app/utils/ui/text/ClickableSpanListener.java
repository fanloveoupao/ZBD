package com.tgnet.app.utils.ui.text;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by lzh on 2017/9/11.
 */

public class ClickableSpanListener extends ClickableSpan implements View.OnClickListener {
    private View.OnClickListener listener;
    private int mColor;


    public ClickableSpanListener(View.OnClickListener listener,int color) {
        this.listener = listener;
        this.mColor=color;
    }

    @Override
    public void onClick(View widget) {
        listener.onClick(widget);
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(mColor);
        ds.setUnderlineText(false);
    }
}
