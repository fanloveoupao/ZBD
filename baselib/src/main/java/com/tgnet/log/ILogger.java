package com.tgnet.log;

/**
 * Created by fan-gk on 2017/4/19.
 */

public interface ILogger {
    void info(String service, String additional, String content);

    void info(String service, String additional, Throwable throwable);

    void debug(String service, String additional, String content);

    void debug(String service, String additional, Throwable throwable);

    void warn(String service, String additional, String content);

    void warn(String service, String additional, Throwable throwable);

    void fail(String service, String additional, String content);

    void fail(String service, String additional, Throwable throwable);

    void error(String service, String additional, String content);

    void error(String service, String additional, Throwable throwable);
}