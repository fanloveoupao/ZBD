package com.tgnet.app.utils.ui.text;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.tgnet.app.utils.ui.widget.text.IInseparableSpan;

/**
 * Created by fan-gk on 2017/4/27.
 * 和IInseparableSpan组合使用可以将span整体删除
 * @see IInseparableSpan
 */
public class InseparableTextWatcher implements TextWatcher {
    private final EditText editText;

    public InseparableTextWatcher(EditText editText){
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        Editable s = editText.getText();
        IInseparableSpan[] spans = s.getSpans(0, s.length(), IInseparableSpan.class);
        int end = start + before - count;
        for (IInseparableSpan span : spans) {
            int is = s.getSpanStart(span);
            int ie = s.getSpanEnd(span);
            boolean startIn = is <= start && ie >= start;
            boolean endIn = is <= end && ie >= end;
            if(startIn || endIn) {
                if (!s.toString().substring(is, Math.min(ie, s.length())).equals(span.getDisplayText())) {
                    s.removeSpan(span);
                    if(endIn){
                        s.delete(is, ie);
                    }
                    else {
                        if (start + before <= ie) { //有剩余
                            int newEnd = ie - before + count;
                            s.delete(start + count, newEnd + 1);
                        }
                        s.delete(is, start);
                    }
                }
            }
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
