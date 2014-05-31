package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;

public interface Configurable {

    public enum Level {TRACE, DEBUG, INFO, WARN, ERROR, FATAL, OFF}

    public void setLevel(Level level);
    public void setFormat(final String format);
    public void setSeparator(final String separator);

    public void registerAppender(Appendable appender) throws InvalidArgumentException;

    public void registerFilter(Filter filter) throws InvalidArgumentException;

}
