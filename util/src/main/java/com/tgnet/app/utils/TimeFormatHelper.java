package com.tgnet.app.utils;

import com.tgnet.util.DateUtil;
import com.tgnet.util.StringUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by fan-gk on 2017/4/25.
 */

public class TimeFormatHelper {
    private static final String YESTER_DATE_FORMAT = "昨天";
    private static final SimpleDateFormat CHAT_TIME_DATE_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat CHAT_DAY_DATE_FORMAT = new SimpleDateFormat("MM/dd");
    private static final SimpleDateFormat CHAT_FULL_DAY_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd");
    private static final SimpleDateFormat MESSAGE_TIME_DATE_FORMAT = new SimpleDateFormat("HH:mm");
    private static final SimpleDateFormat MESSAGE_DAY_DATE_FORMAT = new SimpleDateFormat("M月d日 HH:mm");
    private static final SimpleDateFormat MESSAGE_FULL_DAY_DATE_FORMAT = new SimpleDateFormat("yyyy年M月d日 HH:mm");

    public static String toChatTimeString(Date date){
        if(date == null)
            return StringUtil.EMPTY;

        Date nowDate = DateUtil.getDate(DateUtil.now());
        if(date.after(nowDate))
            return CHAT_TIME_DATE_FORMAT.format(date);
        else if(DateUtil.isSameYear(date, nowDate))
            return date.after(DateUtil.addDays(nowDate, -1)) ? YESTER_DATE_FORMAT : CHAT_DAY_DATE_FORMAT.format(date);
        else
            return CHAT_FULL_DAY_DATE_FORMAT.format(date);
    }

    public static String toMessageTimeString(Date date){
        if(date == null)
            return StringUtil.EMPTY;

        Date nowDate = DateUtil.getDate(DateUtil.now());
        if(date.after(nowDate))
            return MESSAGE_TIME_DATE_FORMAT.format(date);
        else if(DateUtil.isSameYear(date, nowDate))
            return MESSAGE_DAY_DATE_FORMAT.format(date);
        else
            return MESSAGE_FULL_DAY_DATE_FORMAT.format(date);
    }
}
