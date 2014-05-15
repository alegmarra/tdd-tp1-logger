package com.fiuba.tdd.logger;

import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Esta clase es el Logger en sí mismo, es decir, aquéllo que usaría el
 * usuario final (un programador). Comprende la interfaz pública de la API,
 * que consiste en métodos para configurar, para obtener una instancia y,
 * naturalmente, para loguear en los distintos niveles.
 */
public class Logger {

    public enum Level {DEBUG, INFO, WARN, ERROR, FATAL, OFF}

    private static final int StackDepthFromLoggerInvokerToLog = 2;

    private Level level;
    private String format;
    private String separator;
    private List<Appendable> outputs = new LinkedList<>();

    public Logger(){
        setDefaultConfig();
    }

    public Logger(final String format, Level level, final String separator, Appendable... outputs) throws InvalidArgumentException {

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


    private void setDefaultConfig() {

        LoggerConfig defaultConfig = new LoggerConfig();
        this.level = defaultConfig.level;
        this.format = defaultConfig.format;
        this.separator = defaultConfig.separator;
    }

    private void log(String msg, Level level) {
        if (level.ordinal() < this.level.ordinal())
            return;

        LoggerInvoker invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[StackDepthFromLoggerInvokerToLog]);
        MessageFormatter messageFormatter = new MessageFormatter(new LoggerConfig(this.format, level, this.separator));

        final String finalMsg = messageFormatter.formatMessage(invoker, msg);

        // TODO discuss how to handle IOException
        for ( Appendable output : outputs ) {
            try {
                output.append(finalMsg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
