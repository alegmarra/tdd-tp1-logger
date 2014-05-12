package com.fiuba.tdd.logger;

import com.fiuba.tdd.logger.internal.LoggerInvoker;

public class Logger {

    public enum Level {DEBUG, INFO, WARN, ERROR, FATAL, OFF}

    public void setLevel(Level level){}

    public void setFormat(final String format){}

    public void registerAppender(Appendable writer){}

    public void debug(String msg){


        LoggerInvoker invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[1]);
    }

    public void info(String msg){}

    public void warn(String msg){}

    public void error(String msg){}

    public void fatal(String msg){}
}
