package com.tgnet.app.utils.ui.widget.text;

/**
 * Created by fan-gk on 2017/4/26.
 */

import android.text.Spannable;

/**
 * Created by fan-gk on 2017/4/26.
 * 显示和实际不一致的span
 * @see  CoveringTextSpanUtil#valueOf(Spannable)
 */
public interface ICoveringTextSpan extends IInseparableSpan {
    String getRealText();
}
