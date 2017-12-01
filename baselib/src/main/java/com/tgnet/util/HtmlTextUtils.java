package com.tgnet.util;

import java.util.List;

/**
 * Created by Administrator on 2015/8/11.
 */
public class HtmlTextUtils {
    public static final String COLOR_ORANGE_ffae13 = "#ffae13";
    public static final String COLOR_YELLOW_FF9D01 = "#ff9d01";
    public static final String COLOR_BLACK_FF333333 = "#ff333333";
    public static final String COLOR_GRAY_FF999999 = "#ff999999";

    public static String color(String colorCode, String str) {
        return "<font color=\"" + colorCode + "\">" + str + "</font>";
    }

    public static String font(String str) {
        return "<font>" + str + "</font>";
    }

    public static String diyTagToFont(String text, String colorString, String tag) {
        String text1 = text.replaceAll("<" + tag + ">", "<font color=\"" + colorString + "\">");
        String text2 = text1.replaceAll("</" + tag + ">", "</font>");
        return text2;
    }

    public static String diyTagToFont(String text, String colorString, String... tag) {

        String text1 = text.replaceAll("<" + tag + ">", "<font color=\"" + colorString + "\">");
        String text2 = text1.replaceAll("</" + tag + ">", "</font>");
        return text2;
    }

    public static String color(String colorCode, String content, List<String> keywords) {
        if (!StringUtil.isNullOrEmpty(content)) {
            if (!CollectionUtil.isNullOrEmpty(keywords)) { //高亮关键字
                for (String keyword : keywords) {
                    content = content.replace(keyword, color(colorCode, keyword));
                }
                return content;
            }
            return content;
        }
        return null;
    }

    public static String color(String colorCode, String content, String keyword) {
        if (StringUtil.isNullOrEmpty(content))
            return null;
        //高亮关键字
        if (StringUtil.isNullOrEmpty(keyword))
            return content;
        content = content.replace(keyword, color(colorCode, keyword));
        return content;

    }

}
