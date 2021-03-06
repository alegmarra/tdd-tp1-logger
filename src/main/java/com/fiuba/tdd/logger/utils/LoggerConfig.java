package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.appenders.ConsoleAppender;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.filters.PatternFilter;
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
    final public String formatter;

    private List<Appendable> appenders = new LinkedList<>();
    private List<Filter> filters = new LinkedList<>();

    public LoggerConfig(){
        this.level = Level.INFO;
        this.format = "%d{HH:mm:ss} %n %p %n %t %n %m ";
        this.separator = "-";
        this.formatter = "STRING";
        addAppender(new ConsoleAppender());
        addFilter(new PatternFilter());
    }

    public LoggerConfig(final String format, final Level level, final String separator, String formatter)
            throws InvalidArgumentException
    {
        if (format == null || level == null || separator == null) {
            throw new InvalidArgumentException("Null value given for required argument. Format, level and separator should not be null");
        }

        this.level = level;
        this.format = format;
        this.separator = separator;
        this.formatter = formatter;
    }


    public LoggerConfig(final String format, final Level level, final String separator, String formatter, Appendable... outputs)
            throws InvalidArgumentException
    {
        if (format == null || level == null || separator == null) {
            throw new InvalidArgumentException("Null value given for required argument. Format, level and separator should not be null");
        }

        this.level = level;
        this.format = format;
        this.separator = separator;
        this.formatter = formatter;

        if (outputs != null)
            for (Appendable appender : outputs)
                addAppender(appender);

    }

    public LoggerConfig(final String format, final Level level, final String separator, String formatter, Appendable[] outputs, Filter[] filters)
            throws InvalidArgumentException
    {
        if (format == null || level == null || separator == null) {
            throw new InvalidArgumentException("Null value given for required argument. Format, level and separator should not be null");
        }

        this.level = level;
        this.format = format;
        this.separator = separator;
        this.formatter = formatter;

        if (outputs != null)
            for (Appendable appender : outputs)
                addAppender(appender);

        if (filters != null)
            for (Filter filter : filters)
                addFilter(filter);
    }

    public List<Appendable> getAppenders() {
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
