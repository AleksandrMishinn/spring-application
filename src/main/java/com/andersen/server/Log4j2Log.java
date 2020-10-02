package com.andersen.server;

import org.apache.juli.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log4j2Log implements Log {

    private final Logger logger;

    public Log4j2Log() {
        logger = LoggerFactory.getLogger(Log4j2Log.class);
    }

    public Log4j2Log(String name) {
        logger = LoggerFactory.getLogger(name);
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isFatalEnabled() {
        return logger.isErrorEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return logger.isInfoEnabled();
    }

    @Override
    public boolean isTraceEnabled() {
        return logger.isTraceEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return logger.isWarnEnabled();
    }

    @Override
    public void trace(Object o) {
        logger.trace(o.toString());
    }

    @Override
    public void trace(Object o, Throwable throwable) {
        logger.trace(o.toString(), throwable);
    }

    @Override
    public void debug(Object o) {
        logger.debug(o.toString());
    }

    @Override
    public void debug(Object o, Throwable throwable) {
        logger.debug(o.toString(), throwable);
    }

    @Override
    public void info(Object o) {
        logger.info(o.toString());
    }

    @Override
    public void info(Object o, Throwable throwable) {
        logger.info(o.toString(), throwable);
    }

    @Override
    public void warn(Object o) {
        logger.warn(o.toString());
    }

    @Override
    public void warn(Object o, Throwable throwable) {
        logger.warn(o.toString(), throwable);
    }

    @Override
    public void error(Object o) {
        logger.error(o.toString());
    }

    @Override
    public void error(Object o, Throwable throwable) {
        logger.error(o.toString(), throwable);
    }

    @Override
    public void fatal(Object o) {
        logger.error(o.toString());
    }

    @Override
    public void fatal(Object o, Throwable throwable) {
        logger.error(o.toString(), throwable);
    }
}
