package com.fiuba.tdd.logger;

import com.fiuba.tdd.logger.internal.LoggerInvoker;
import com.fiuba.tdd.logger.internal.MessageFormatter;

import java.io.IOException;
import java.util.List;

public class Logger {

    private Level level;
    private String format;
    private String separator;
    private List<Appendable> outputs;

    public enum Level {DEBUG, INFO, WARN, ERROR, FATAL, OFF}

    public void setLevel(Level level){
        this.level = level;
    }

    public void setFormat(final String format){
        this.format = format;
    }

    public void setSeparator(final String separator){
        this.separator = separator;
    }

    public void registerAppender(Appendable writer){
        outputs.add(writer);
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

        LoggerInvoker invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[2]);  // Magic number 2!
        MessageFormatter messageFormatter = new MessageFormatter(this.format, this.separator, level);

        String finalMsg = messageFormatter.formatMessage(invoker, msg);

        // TODO discuss how to handle IOException
        for ( Appendable output : outputs ) {
            try {
                output.append(finalMsg);
            } catch (IOException e) {
                System.out.println(e);
            }
        }

    }
}
