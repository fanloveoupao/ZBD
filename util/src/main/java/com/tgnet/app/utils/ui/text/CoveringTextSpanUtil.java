package com.tgnet.app.utils.ui.text;

import android.text.Spannable;
import android.text.SpannableStringBuilder;

import com.tgnet.app.utils.ui.widget.text.ICoveringTextSpan;
import com.tgnet.util.StringUtil;

/**
 * Created by fan-gk on 2017/4/27.
 */

public class CoveringTextSpanUtil {
    public static String valueOf(Spannable spannable){
        if(spannable == null)
            return StringUtil.EMPTY;

        ICoveringTextSpan[] spans = spannable.getSpans(0, spannable.length(), ICoveringTextSpan.class);
        if(spans == null || spans.length == 0)
            return spannable.toString();

        SpannableStringBuilder ssb = new SpannableStringBuilder(spannable);
        for (ICoveringTextSpan span : spans) {
            ssb = ssb.replace(ssb.getSpanStart(span), ssb.getSpanEnd(span), span.getRealText());
        }
        return ssb.toString();
    }

    public static boolean hasCoveringTextSpans(Spannable spannable){
        if(spannable == null)
            return false;
        else {
            ICoveringTextSpan[] spans = spannable.getSpans(0, spannable.length(), ICoveringTextSpan.class);
            return spans != null && spans.length > 0;
        }
    }
}
