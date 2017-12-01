package com.tgnet.exceptions;

import com.tgnet.util.StringUtil;

/**
 * Created by fan-gk on 2017/4/20.
 */

public final class ExceptionUtil {
    public static void throwIfArgumentNullOrWhiteSpace(String value, String message) throws TgnetException {
        if (StringUtil.isNullOrWhiteSpace(value))
            throw new TgnetException(ErrorCode.ARGUMENT, message);
    }
}