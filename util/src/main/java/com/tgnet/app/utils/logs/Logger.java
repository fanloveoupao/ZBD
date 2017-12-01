package com.tgnet.app.utils.logs;

import android.util.Log;

import com.tgnet.log.ILogger;
import com.tgnet.util.StringUtil;

/**
 * Created by fan-gk on 2017/4/20.
 */


public class Logger implements ILogger {
    @Override
    public void info(String service, String additional, String content) {
        Log.i(additional, content);
    }

    @Override
    public void info(String service, String additional, Throwable throwable) {
        Log.i(additional, StringUtil.EMPTY, throwable);
    }

    @Override
    public void debug(String service, String additional, String content) {
        Log.d(additional, content);
    }

    @Override
    public void debug(String service, String additional, Throwable throwable) {
        Log.d(additional, StringUtil.EMPTY, throwable);
    }

    @Override
    public void warn(String service, String additional, String content) {
        Log.w(additional, content);
    }

    @Override
    public void warn(String service, String additional, Throwable throwable) {
        Log.w(additional, throwable);
    }

    @Override
    public void fail(String service, String additional, String content) {
        Log.e(additional, content);

    }

    @Override
    public void fail(String service, String additional, Throwable throwable) {
        Log.e(additional, StringUtil.EMPTY, throwable);
    }

    @Override
    public void error(String service, String additional, String content) {
        Log.e(additional, content);
    }

    @Override
    public void error(String service, String additional, Throwable throwable) {
        Log.e(additional, StringUtil.EMPTY, throwable);
    }
}

