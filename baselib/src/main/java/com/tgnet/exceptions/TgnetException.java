package com.tgnet.exceptions;

/**
 * Created by fan-gk on 2017/4/20.
 */

public class TgnetException extends Exception {

    private ErrorCode errorCode;
    public ErrorCode getErrorCode(){
        return  errorCode;
    }

    public TgnetException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public TgnetException(ErrorCode errorCode, String message, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }
}