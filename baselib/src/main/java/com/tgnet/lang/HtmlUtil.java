package com.tgnet.lang;

import com.tgnet.util.StringUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fan-gk on 2017/4/21.
 */

public class HtmlUtil {
    private final static Pattern LEFT_TAG = Pattern.compile("<\\w+[^>]*>", Pattern.CASE_INSENSITIVE);
    private final static Pattern RIGTH_TAG = Pattern.compile("</\\w+>", Pattern.CASE_INSENSITIVE);

    public static String clearTags(String html){
        if(html == null)
            return null;

        Matcher matcher = LEFT_TAG.matcher(html);
        html = matcher.replaceAll(StringUtil.EMPTY);
        matcher = RIGTH_TAG.matcher(html);
        html = matcher.replaceAll(StringUtil.EMPTY);
        return html;
    }
}
