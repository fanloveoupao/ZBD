package com.tgnet.log;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class ConsoleLogger implements ILogger {

    @Override
    public void info(String service, String additional, String content) {
        System.out.printf(service, additional, content);
    }

    @Override
    public void info(String service, String additional, Throwable throwable) {
        System.out.printf(service, additional, throwable);
    }

    @Override
    public void debug(String service, String additional, String content) {
        System.out.printf(service, additional, content);

    }

    @Override
    public void debug(String service, String additional, Throwable throwable) {
        System.out.printf(service, additional, throwable);
    }

    @Override
    public void warn(String service, String additional, String content) {
        System.out.printf(service, additional, content);

    }

    @Override
    public void warn(String service, String additional, Throwable throwable) {
        System.out.printf(service, additional, throwable);
    }

    @Override
    public void fail(String service, String additional, String content) {
        System.out.printf(service, additional, content);

    }

    @Override
    public void fail(String service, String additional, Throwable throwable) {
        System.out.printf(service, additional, throwable);
    }

    @Override
    public void error(String service, String additional, String content) {
        System.out.printf(service, additional, content);

    }

    @Override
    public void error(String service, String additional, Throwable throwable) {
        System.out.printf(service, additional, throwable);
    }
}
