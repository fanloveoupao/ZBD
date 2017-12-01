package com.tgnet.app.utils.ui.text;

import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.tgnet.app.utils.ui.widget.text.IInseparableSpan;

/**
 * Created by fan-gk on 2017/4/27.
 */


public class InseparableEditTouchListener implements View.OnTouchListener {
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v instanceof EditText) {
            EditText widget = (EditText) v;
            if (event.getAction() == MotionEvent.ACTION_DOWN
                    || event.getAction() == MotionEvent.ACTION_UP) {
                int x = (int) event.getX();
                int y = (int) event.getY();

                x -= widget.getTotalPaddingLeft();
                y -= widget.getTotalPaddingTop();

                x += widget.getScrollX();
                y += widget.getScrollY();

                Layout layout = widget.getLayout();
                int line = layout.getLineForVertical(y);
                float width = layout.getLineWidth(line);
                if (width >= x) {
                    int off = layout.getOffsetForHorizontal(line, x);
                    Editable buffer = widget.getText();
                    IInseparableSpan[] spans = buffer.getSpans(off, off, IInseparableSpan.class);
                    if (spans.length > 0) {
                        int start = buffer.getSpanStart(spans[0]);
                        int end = buffer.getSpanEnd(spans[0]);
                        boolean gteHalf = (end - start) / 2f <= off - start;
                        Selection.setSelection(buffer, gteHalf ? end : start);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
