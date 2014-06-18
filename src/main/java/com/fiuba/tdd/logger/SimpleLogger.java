package com.fiuba.tdd.logger;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.format.MessageFormatterBuilder;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class SimpleLogger implements Configurable{

    private String name;
    private Level level;
    private String format;
    private String separator;

    private List<Appendable> outputs = new LinkedList<>();
    private List<Filter> filters = new LinkedList<>();

    public SimpleLogger(final String name) throws InvalidArgumentException {
        setName(name);
        setConfig(new LoggerConfig());
    }

    public SimpleLogger(final String name, LoggerConfig config) throws InvalidArgumentException {
        setName(name);
        setConfig(config);
    }

    public SimpleLogger(final String name, final String format, Level level, final String separator, Appendable... outputs)
            throws InvalidArgumentException
    {
        setName(name);
        setConfig(new LoggerConfig(format, level, separator, outputs));
    }


    public SimpleLogger(final String name, final String format, Level level, final String separator, Appendable[] outputs, Filter[] filters)
            throws InvalidArgumentException
    {
        setName(name);
        setConfig(new LoggerConfig(format, level, separator, outputs, filters));
    }

    public void trace(String msg){
        log(msg, Level.TRACE);
    }

    public void trace(String msg, Throwable exception){
        log(msg, Level.TRACE, exception);
    }

    public void debug(String msg){
        log(msg, Level.DEBUG);
    }

    public void debug(String msg, Throwable exception){
        log(msg, Level.DEBUG, exception);
    }

    public void info(String msg){
        log(msg, Level.INFO);
    }

    public void info(String msg, Throwable exception){
        log(msg, Level.INFO, exception);
    }

    public void warn(String msg){
        log(msg, Level.WARN);
    }

    public void warn(String msg, Throwable exception){
        log(msg, Level.WARN, exception);
    }

    public void error(String msg){
        log(msg, Level.ERROR);
    }

    public void error(String msg, Throwable exception){
        log(msg, Level.ERROR, exception);
    }

    public void fatal(String msg){
        log(msg, Level.FATAL);
    }

    public void fatal(String msg, Throwable exception){
        log(msg, Level.FATAL, exception);
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public Level getLevel(){ return Level.valueOf(this.level.name());}

    public String getName(){ return name;}

    public void setFormat(final String format){
        this.format = format;
    }

    public void setSeparator(final String separator){
        this.separator = separator;
    }

    public void registerAppender(Appendable appender) throws InvalidArgumentException {
        if (appender == null)
            throw new InvalidArgumentException("Output appenders cannot be null");

        outputs.add(appender);
    }

    public void registerFilter(Filter filter) throws InvalidArgumentException {
        if (filter == null)
            throw new InvalidArgumentException("Filters cannot be null");

        filters.add(filter);
    }

    private void log(String msg, Level level) {
        if (skipMessage(msg, level))
            return;

        try {

            MessageFormatterBuilder builder = new MessageFormatterBuilder();
            builder.withConfig(this.format, level, this.separator).build();

            final String finalMsg = builder.formatMessage(msg, this.name);

            for ( Appendable output : outputs ) {
                try {
                    output.append(finalMsg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    private Boolean skipMessage(final String msg, Level level) {
        boolean skipped = false;

        skipped = level.ordinal() < this.level.ordinal();

        Iterator<Filter> it = filters.iterator();
        while (it.hasNext() && !skipped){
            skipped = !it.next().allows(msg);
        }

        return skipped;
    }

    private void log(String msg, Level level, Throwable exception) {
        if (exception == null)
            log(msg, level);
        else
            log(msg + exception.getMessage(), level);
    }

    private void setConfig(LoggerConfig defaultConfig) throws InvalidArgumentException {
        this.level = defaultConfig.level;
        this.format = defaultConfig.format;
        this.separator = defaultConfig.separator;

        for ( Appendable output : defaultConfig.getAppenders() ) {
            registerAppender(output);
        }

        for ( Filter filter : defaultConfig.getFilters() ) {
            registerFilter(filter);
        }
    }


    private void setName(String name) throws InvalidArgumentException {
        if (name == null || name.equals(""))
            throw new InvalidArgumentException("Invalid name");

        this.name = name;
    }
}
