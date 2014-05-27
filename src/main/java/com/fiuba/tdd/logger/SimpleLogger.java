package com.fiuba.tdd.logger;

import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.internal.MessageFormatterBuilder;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class SimpleLogger implements Logger{

    public enum Level {DEBUG, INFO, WARN, ERROR, FATAL, OFF}

    private Level level;
    private String format;
    private String separator;
    private List<Appendable> outputs = new LinkedList<>();

    public SimpleLogger(){
        LoggerConfig defaultConfig = new LoggerConfig();
        this.level = defaultConfig.level;
        this.format = defaultConfig.format;
        this.separator = defaultConfig.separator;
    }

    public SimpleLogger(final String format, Level level, final String separator, Appendable... outputs)
            throws InvalidArgumentException
    {
        if (format == null || level == null || separator == null)
            throw new InvalidArgumentException("Null value given for required argument. Format, level and separator should not be null");

        this.format = format;
        this.level = level;
        this.separator = separator;

        for (Appendable appender : outputs)
            registerAppender(appender);
    }

    public void setLevel(Level level){
        this.level = level;
    }

    public Level getLevel(){ return Level.valueOf(this.level.name());}

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

    public void debug(String msg){
        log(msg, Level.DEBUG);
    }

    public void info(String msg){
        log(msg, Level.INFO);
    }

    public void warn(String msg){
        log(msg, Level.WARN);
    }

    public void error(String msg){
        log(msg, Level.ERROR);
    }

    public void fatal(String msg){
        log(msg, Level.FATAL);
    }

    private void log(String msg, Level level) {
        if (level.ordinal() < this.level.ordinal())
            return;

        MessageFormatterBuilder builder = new MessageFormatterBuilder();
        builder.withConfig(this.format, level, this.separator).build();

        final String finalMsg = builder.formatMessage(msg);

        for ( Appendable output : outputs ) {
            try {
                output.append(finalMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
