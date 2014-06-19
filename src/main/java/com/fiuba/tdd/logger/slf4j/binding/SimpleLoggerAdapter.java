package com.fiuba.tdd.logger.slf4j.binding;

import com.fiuba.tdd.logger.SimpleLogger;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import org.slf4j.Logger;
import org.slf4j.Marker;

public class SimpleLoggerAdapter implements Logger, Configurable{

    private SimpleLogger logger;

    public SimpleLoggerAdapter(Class<?> type){
        this(type.getName());
    }

    public SimpleLoggerAdapter(Class<?> type, LoggerConfig config){
        this(type.getName(), config);
    }

    public SimpleLoggerAdapter(String name){
        try {
            logger = new SimpleLogger(name);
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        }
    }
    public SimpleLoggerAdapter(String name, LoggerConfig config){
        try {
            logger = new SimpleLogger(name, config);
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isLevelEnabled(Level level) {
        return logger.getLevel().ordinal() <= level.ordinal() ;
    }

    @Override
    public String getName() {
        return logger.getName();
    }

    @Override
    public boolean isTraceEnabled() {
        return isLevelEnabled(Level.TRACE);
    }

    @Override
    public void trace(String s) {
        logger.trace(s);
    }

    @Override
    public void trace(String s, Object o) {
        trace(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void trace(String s, Object o, Object o2) {
        trace(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void trace(String s, Object... objects) {
        trace(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void trace(String s, Throwable throwable) {
        logger.trace(s, throwable);
    }

    @Override
    public boolean isTraceEnabled(Marker marker) {
        return isLevelEnabled(Level.TRACE);
    }

    @Override
    public void trace(Marker marker, String s) {
        trace(s);
    }

    @Override
    public void trace(Marker marker, String s, Object o) {
        trace(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o2) {
        trace(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {
        trace(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {
        trace(s, throwable);
    }

    @Override
    public boolean isDebugEnabled() {
        return isLevelEnabled(Level.DEBUG);
    }

    @Override
    public void debug(String s) {
        logger.debug(s);
    }

    @Override
    public void debug(String s, Object o) {
        debug(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void debug(String s, Object o, Object o2) {
        debug(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void debug(String s, Object... objects) {
        debug(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void debug(String s, Throwable throwable) {
        logger.debug(s, throwable);
    }

    @Override
    public boolean isDebugEnabled(Marker marker) {
        return isLevelEnabled(Level.DEBUG);
    }

    @Override
    public void debug(Marker marker, String s) {
        debug(s);
    }

    @Override
    public void debug(Marker marker, String s, Object o) {
        debug(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o2) {
        debug(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {
        debug(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {
        debug(s, throwable);
    }

    @Override
    public boolean isInfoEnabled() {
        return isLevelEnabled(Level.INFO);
    }

    @Override
    public void info(String s) {
        logger.info(s);
    }

    @Override
    public void info(String s, Object o) {
        info(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void info(String s, Object o, Object o2) {
        info(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void info(String s, Object... objects) {
        info(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void info(String s, Throwable throwable) {
        logger.info(s, throwable);
    }

    @Override
    public boolean isInfoEnabled(Marker marker) {
        return isLevelEnabled(Level.INFO);
    }

    @Override
    public void info(Marker marker, String s) {
        info(s);
    }

    @Override
    public void info(Marker marker, String s, Object o) {
        info(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void info(Marker marker, String s, Object o, Object o2) {
        info(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void info(Marker marker, String s, Object... objects) {
        info(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {
        info(s, throwable);
    }

    @Override
    public boolean isWarnEnabled() {
        return isLevelEnabled(Level.WARN);
    }

    @Override
    public void warn(String s) {
        logger.warn(s);
    }

    @Override
    public void warn(String s, Object o) {
        warn(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void warn(String s, Object o, Object o2) {
        warn(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void warn(String s, Object... objects) {
        warn(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void warn(String s, Throwable throwable) {
        logger.warn(s, throwable);
    }

    @Override
    public boolean isWarnEnabled(Marker marker) {
        return isLevelEnabled(Level.WARN);
    }

    @Override
    public void warn(Marker marker, String s) {
        warn(s);
    }

    @Override
    public void warn(Marker marker, String s, Object o) {
        warn(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o2) {
        warn(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {
        warn(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {
        warn(s, throwable);
    }

    @Override
    public boolean isErrorEnabled() {
        return isLevelEnabled(Level.ERROR);
    }

    @Override
    public void error(String s) {
        logger.error(s);
    }

    @Override
    public void error(String s, Object o) {
        error(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void error(String s, Object o, Object o2) {
        error(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void error(String s, Object... objects) {
        error(ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void error(String s, Throwable throwable) {
        logger.error(s, throwable);
    }

    @Override
    public boolean isErrorEnabled(Marker marker) {
        return isLevelEnabled(Level.ERROR);
    }

    @Override
    public void error(Marker marker, String s) {
        error(s);
    }

    @Override
    public void error(Marker marker, String s, Object o) {
        error(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o));
    }

    @Override
    public void error(Marker marker, String s, Object o, Object o2) {
        error(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, o, o2));
    }

    @Override
    public void error(Marker marker, String s, Object... objects) {
        error(marker, ParametrizedMessageInterpreter.replaceMessageWithArgs(s, objects));
    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {
        error(s, throwable);
    }

    @Override
    public void setLevel(Level level) {
        logger.setLevel(level);
    }

    @Override
    public void setFormat(String format) {
        logger.setFormat(format);
    }

    @Override
    public void setSeparator(String separator) {
        logger.setSeparator(separator);
    }

    @Override
    public void registerAppender(com.fiuba.tdd.logger.appenders.Appendable appender) throws InvalidArgumentException {
        logger.registerAppender(appender);
    }

    @Override
    public void registerFilter(Filter filter) throws InvalidArgumentException {
        logger.registerFilter(filter);
    }
}
