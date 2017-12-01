package com.tgnet.app.utils.ui.widget.comment;

import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by fan-gk on 2017/8/29.
 */


public abstract class SpannableClickable extends ClickableSpan implements View.OnClickListener {

    private int textColor;

    public SpannableClickable(int textColor) {
        this.textColor = textColor;
    }

    public SpannableClickable() {
        this.textColor = Color.parseColor("#8192ae");
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);

        ds.setColor(textColor);
        ds.setUnderlineText(false);
        ds.clearShadowLayer();
    }
}
