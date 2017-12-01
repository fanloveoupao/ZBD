package com.tgnet.log;

import com.tgnet.util.StringUtil;

import java.util.HashSet;

/**
 * Created by fan-gk on 2017/4/19.
 */

public class LoggerResolver {
    private static LoggerResolver instance;

    public static LoggerResolver getInstance() {
        return instance;
    }

    static {
        instance = new LoggerResolver();
    }

    public void addLogger(ILogger logger) {
        this.loggers.add(logger);
    }

    public void removeLogger(ILogger logger) {
        this.loggers.remove(logger);
    }

    public void clearLoggers() {
        this.loggers.clear();
    }

    private HashSet<ILogger> loggers;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? StringUtil.EMPTY : appName.trim();
    }

    private String appName;


    public LogLevels getLevel() {
        return level;
    }

    public void setLevel(LogLevels level) {
        this.level = level;
    }

    private LogLevels level;

    private LoggerResolver() {
        this.loggers = new HashSet<>();

        setAppName(StringUtil.EMPTY);
        setLevel(LogLevels.INFO);
        addLogger(new ConsoleLogger());
    }

    public void info(String additional, String content) {
        if (LogLevels.INFO.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.info(getAppName(), additional, content);
            }
        }
    }

    public void info(String additional, Throwable throwable) {
        if (LogLevels.INFO.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.info(getAppName(), additional, throwable);
            }
        }
    }

    public void debug(String additional, String content) {
        if (LogLevels.DEBUG.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.debug(getAppName(), additional, content);
            }
        }
    }

    public void debug(String additional, Throwable throwable) {
        if (LogLevels.DEBUG.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.debug(getAppName(), additional, throwable);
            }
        }
    }

    public void warn(String additional, String content) {
        if (LogLevels.WARN.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.warn(getAppName(), additional, content);
            }
        }
    }

    public void warn(String additional, Throwable throwable) {
        if (LogLevels.WARN.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.warn(getAppName(), additional, throwable);
            }
        }
    }

    public void fail(String additional, String content) {
        if (LogLevels.FAIL.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.fail(getAppName(), additional, content);
            }
        }
    }

    public void fail(String additional, Throwable throwable) {
        if (LogLevels.FAIL.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.fail(getAppName(), additional, throwable);
            }
        }
    }

    public void error(String additional, String content) {
        if (LogLevels.ERROR.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.error(getAppName(), additional, content);
            }
        }
    }

    public void error(String additional, Throwable throwable) {
        if (LogLevels.ERROR.gte(getLevel())) {
            for (ILogger logger : loggers) {
                logger.error(getAppName(), additional, throwable);
            }
        }
    }
}
