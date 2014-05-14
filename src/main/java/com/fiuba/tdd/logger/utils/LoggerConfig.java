package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.Logger;

public class LoggerConfig {

    final public Logger.Level level;
    final public String format;
    final public String separator;

    public LoggerConfig(){
        this.level = Logger.Level.DEBUG;
        this.format = "%d{HH:mm:ss} %n %p %n %t %n %m ";
        this.separator = "-";
    }

    public LoggerConfig(final String format, final Logger.Level level, final String separator)
    {
        this.level = level;
        this.format = format;
        this.separator = separator;
    }

}
