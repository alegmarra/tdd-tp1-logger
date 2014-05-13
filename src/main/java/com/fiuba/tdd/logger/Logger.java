package com.fiuba.tdd.logger;

import com.fiuba.tdd.logger.internal.MessageFormatter;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.LinkedList;
import java.util.List;

public class Logger {

    public enum Level { DEBUG, INFO, WARN, ERROR, FATAL, OFF}

    private final String defaultFormat = "%m";
    private final String defaultSeparator = " :: ";

    private List<Appendable> appenders = new LinkedList<Appendable>();

    private Level rootLevel = Level.INFO;
    private MessageFormatter rootFormatter;

    public Logger(){
        this.rootFormatter = new MessageFormatter(defaultFormat, defaultSeparator, rootLevel);
    }

    public Logger(String format){
        this.rootFormatter = new MessageFormatter(format, defaultSeparator, rootLevel);
    }

    public Logger(String format, Level level){
        this.rootFormatter = new MessageFormatter(format, defaultSeparator, level);
    }

    public Logger(String format, Level level, String separator){
        this.rootFormatter = new MessageFormatter(format, separator, level);
    }

    public void setLevel(Level level){}

    public void setRootFormat(final String format){
        //FIXME
    }

    public void registerWriter(Appendable appender) throws InvalidArgumentException {
        if (appender == null)
            throw new InvalidArgumentException(new String[]{"Appenders cannot be null"});

        appenders.add(appender);
    }

    public void debug(String msg){}

    public void info(String msg){}

    public void warn(String msg){}

    public void error(String msg){}

    public void fatal(String msg){}
}
