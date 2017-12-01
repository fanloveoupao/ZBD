package com.tgnet.exceptions;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class NetworkException extends Exception {
    public NetworkException(String message) {
        super(message, new TgnetException(ErrorCode.NETWORK, message));
    }
    public NetworkException(String message, Throwable throwable) {
        super(message, new TgnetException(ErrorCode.NETWORK, message, throwable));
    }
}
