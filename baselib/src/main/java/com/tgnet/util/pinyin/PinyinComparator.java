package com.tgnet.util.pinyin;

import com.tgnet.util.StringUtil;

import java.util.Comparator;

/**
 * Created by fan-gk on 2017/4/24.
 */

public class PinyinComparator implements Comparator<SortModel> {

    public int compare(SortModel o1, SortModel o2) {
        if (!StringUtil.isNullOrEmpty(o1.getSortLetters()) && !StringUtil.isNullOrEmpty(o2.getSortLetters())) {
            if (o1.getSortLetters().equals("@")
                    || o2.getSortLetters().equals("#")) {
                return -1;
            } else if (o1.getSortLetters().equals("#")
                    || o2.getSortLetters().equals("@")) {
                return 1;
            } else {
                return o1.getSortLetters().compareTo(o2.getSortLetters());
            }
        } else {
            return 1;
        }
    }

}
