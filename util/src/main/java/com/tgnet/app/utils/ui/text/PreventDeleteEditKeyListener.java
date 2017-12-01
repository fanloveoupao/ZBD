package com.tgnet.app.utils.ui.text;

import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

/**
 * Created by fan-gk on 2017/4/27.
 */

public class PreventDeleteEditKeyListener implements View.OnKeyListener {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if(v instanceof EditText) {
            EditText widget = (EditText) v;
            int start = widget.getSelectionStart();
            int end = widget.getSelectionEnd();
            if (start != end || keyCode == KeyEvent.KEYCODE_DEL) { //多选或者按删除的情况
                Editable buffer = widget.getText();
                IPreventDelSpan[] spans = buffer.getSpans(start, end, IPreventDelSpan.class);
                boolean isPreventDel = false;
                for (IPreventDelSpan span : spans) {
                    if (isPreventDel = span.isPreventDel())
                        break;
                }
                if (isPreventDel) {
                    if (start != end || start != buffer.getSpanStart(spans[0]))
                        return true;
                }
            }
        }
        return false;
    }
}
