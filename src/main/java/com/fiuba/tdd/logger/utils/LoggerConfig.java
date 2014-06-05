package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.appenders.*;
import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.utils.Configurable.Level;

import java.util.LinkedList;
import java.util.List;

/**
 * Clase cuya función es abstraerse de las internas de los atributos de
 * configuración del Logger.
 */
public class LoggerConfig {

    public enum ConfigKey {LEVEL, FORMAT, SEPARATOR}

    final public Level level;
    final public String format;
    final public String separator;

    private List<Appendable> appenders = new LinkedList<>();
    private List<Filter> filters = new LinkedList<>();

    public LoggerConfig(){

        this.level = Level.INFO;
        this.format = "%d{HH:mm:ss} %n %p %n %t %n %m ";
        this.separator = "-";
    }

    public LoggerConfig(final String format, final Level level, final String separator)
            throws InvalidArgumentException
    {
        if (format == null || level == null || separator == null) {
            throw new InvalidArgumentException("Null value given for required argument. Format, level and separator should not be null");
        }

        this.level = level;
        this.format = format;
        this.separator = separator;
    }

    public List<Appendable> getAppenders(Appendable appender) {
        return appenders;
    }

    public void addAppender(Appendable appender) {
        this.appenders.add(appender);
    }


    public List<Filter> getFilters() {
        return filters;
    }

    public void addFilter(Filter filter) {
        this.filters.add(filter);
    }


    @Override
    public boolean equals(Object obj){
        return (obj instanceof LoggerConfig &&
               ((LoggerConfig) obj).format.equals(this.format) &&
               ((LoggerConfig) obj).level.equals(this.level) &&
               ((LoggerConfig) obj).separator.equals(this.separator));
    }



}
