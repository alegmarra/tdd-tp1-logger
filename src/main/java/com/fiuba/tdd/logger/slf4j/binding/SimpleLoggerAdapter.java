package com.fiuba.tdd.logger.slf4j.binding;

import com.fiuba.tdd.logger.SimpleLogger;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.utils.Configurable;
import org.slf4j.Logger;
import org.slf4j.Marker;

public class SimpleLoggerAdapter implements Logger, Configurable{

    private SimpleLogger logger;

    public SimpleLoggerAdapter(Class<?> type){
        this(type.getName());

    }
    public SimpleLoggerAdapter(String name){
        try {
            logger = new SimpleLogger(name);
        } catch (InvalidArgumentException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean isTraceEnabled() {
        return false;
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
        return false;
    }

    @Override
    public void trace(Marker marker, String s) {

    }

    @Override
    public void trace(Marker marker, String s, Object o) {

    }

    @Override
    public void trace(Marker marker, String s, Object o, Object o2) {

    }

    @Override
    public void trace(Marker marker, String s, Object... objects) {

    }

    @Override
    public void trace(Marker marker, String s, Throwable throwable) {

    }

    @Override
    public boolean isDebugEnabled() {
        return false;
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
        return false;
    }

    @Override
    public void debug(Marker marker, String s) {

    }

    @Override
    public void debug(Marker marker, String s, Object o) {

    }

    @Override
    public void debug(Marker marker, String s, Object o, Object o2) {

    }

    @Override
    public void debug(Marker marker, String s, Object... objects) {

    }

    @Override
    public void debug(Marker marker, String s, Throwable throwable) {

    }

    @Override
    public boolean isInfoEnabled() {
        return false;
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
        return false;
    }

    @Override
    public void info(Marker marker, String s) {

    }

    @Override
    public void info(Marker marker, String s, Object o) {

    }

    @Override
    public void info(Marker marker, String s, Object o, Object o2) {

    }

    @Override
    public void info(Marker marker, String s, Object... objects) {

    }

    @Override
    public void info(Marker marker, String s, Throwable throwable) {

    }

    @Override
    public boolean isWarnEnabled() {
        return false;
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
        return false;
    }

    @Override
    public void warn(Marker marker, String s) {

    }

    @Override
    public void warn(Marker marker, String s, Object o) {

    }

    @Override
    public void warn(Marker marker, String s, Object o, Object o2) {

    }

    @Override
    public void warn(Marker marker, String s, Object... objects) {

    }

    @Override
    public void warn(Marker marker, String s, Throwable throwable) {

    }

    @Override
    public boolean isErrorEnabled() {
        return false;
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
        return false;
    }

    @Override
    public void error(Marker marker, String s) {

    }

    @Override
    public void error(Marker marker, String s, Object o) {

    }

    @Override
    public void error(Marker marker, String s, Object o, Object o2) {

    }

    @Override
    public void error(Marker marker, String s, Object... objects) {

    }

    @Override
    public void error(Marker marker, String s, Throwable throwable) {

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
