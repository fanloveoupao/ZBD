package com.tgnet.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fan-gk on 2017/5/2.
 */


public class VerifyUtils {


    /**
     * 检测是不是手机号
     */
    public static boolean isPhoneNum(String str) {
        Pattern pattern = Pattern.compile("^(1)\\d{10}$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }
    /**
     * 检测是不是数字
     */
    public static boolean isNum(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }
    /**
     * 验证密码是不是符合规则
     */
    public static boolean isPassword(String str) {
        Pattern pattern = Pattern.compile("^[0-9a-zA-Z~`\\!\\@#\\$%\\^&\\*\\(\\)\\-_\\+=\\[\\]\\{\\}:;\\|\\\\\\<\\>\\?\\/\\r\\n]*$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    /**
     * 6~16位数字‘和’字母
     *
     * @param str
     * @return
     */
    public static boolean isNumAlph(String str) {
        boolean enough = false;
        if (str.length() > 5 && str.length() < 17) {
            enough = true;
        }
        Pattern pattern = Pattern.compile("^([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*$");
        Matcher m = pattern.matcher(str);
        return m.matches() && enough;
    }

    /**
     * 检查是否有英文字母和数字
     */
    public static boolean isNoName(String str) {
        Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5]$");
        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            Matcher m = pattern.matcher(c);
            if (!m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否有汉字
     */
    public static int isHanZhi(String str) {
        int count = 0 ;
        Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]$");
        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            Matcher m = pattern.matcher(c);
            if (m.matches()) {
                count++;
            }
        }
        return count;
    }
    /**
     * 验证是否为邮箱格式
     */
    public static boolean isMail(String str){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

    /**
     * 检查是否纯汉字
     */
    public static boolean isAllHanZhi(String str) {
        boolean isTrue = true;
        Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]$");
        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            Matcher m = pattern.matcher(c);
            if (!m.matches()) {
                isTrue = false;
            }
        }
        return isTrue;
    }

    /**
     * 检查是否有不不合法字符
     */
    public static boolean isNoCompanyName(String str) {
        Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5|0-9a-zA-Z]$");
        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            Matcher m = pattern.matcher(c);
            if (!m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否有不不合法字符
     */
    public static boolean isNoCompanyAddress(String str) {
        Pattern pattern = Pattern.compile("^[\u4E00-\u9FA5|0-9a-zA-Z|()（）\\-\\-]$");
        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            Matcher m = pattern.matcher(c);
            if (!m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检查是否有符号
     */
    public static String noSign(String str) {
        String string;
        if (str.endsWith(".") || str.endsWith("*") || str.endsWith("~") || str.endsWith("`") || str.endsWith("!") || str.endsWith("@") || str.endsWith("#") || str.endsWith("$")
                || str.endsWith("%") || str.endsWith("^") || str.endsWith("&") || str.endsWith("*") || str.endsWith("(") || str.endsWith(")") || str.endsWith("-")
                || str.endsWith("_") || str.endsWith("+") || str.endsWith("=") || str.endsWith("[") || str.endsWith("]") || str.endsWith("{")
                || str.endsWith("}") || str.endsWith(":") || str.endsWith(";") || str.endsWith("|") || str.endsWith("\\") || str.endsWith("<") ||
                str.endsWith(">") || str.endsWith("?") || str.endsWith("/") || str.endsWith("\r") || str.endsWith("\n") || str.endsWith("$")) {
            String s = str.substring(0, str.length() - 1);
            string = noSign(s);
        } else {
            string = String.valueOf(str.charAt(str.length() - 1));

        }

        return string;
    }

    /**
     * 检查json格式
     */
    public static boolean isJSON(String str) {
        if (str.startsWith("{") && str.endsWith("}")) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * 判断是否为URL
     * @param str
     * @return
     */
    public static boolean isURL (String str){
        String regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]" ;
        Pattern pattern = Pattern. compile(regex );
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

    public static boolean isNumAndAlphabet(String str) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
        for (int i = 0; i < str.length(); i++) {
            String c = str.charAt(i) + "";
            Matcher m = pattern.matcher(c);
            if (m.matches()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断是否为可提供帮忙的类型
     * @param num
     * @return
     */
    public static boolean isHelpType(int num){
        int [] type = {1,2,4,8,3,5,9,6,10,12,7,11,13,14,15};
        for (int i= 0; i < type.length; i++){
            if (num == type[i]){
                return true;
            }
        }
        return false;
    }
}

