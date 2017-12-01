package com.tgnet.exceptions;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class UnloginException extends Exception {
    public static final int TYPE_UNLOGIN = 1;
    public static final int TYPE_TOO_LONG = 2;
    public static final int TYPE_OTHER_DEVICE = 3;

    public int getType() {
        return type;
    }

    private final int type;

    public UnloginException(int type){
        super("未登录", new TgnetException(ErrorCode.UNLOGIN, "未登录"));
        this.type = type;
    }
}