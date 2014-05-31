package com.fiuba.tdd.logger.utils;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.Configurable.Level;

/**
 * Clase cuya función es abstraerse de las internas de los atributos de
 * configuración del Logger.
 */
public class LoggerConfig {

    public enum ConfigKey {LEVEL, FORMAT, SEPARATOR}

    final public Level level;
    final public String format;
    final public String separator;

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

    @Override
    public boolean equals(Object obj){
        return (obj instanceof LoggerConfig &&
               ((LoggerConfig) obj).format.equals(this.format) &&
               ((LoggerConfig) obj).level.equals(this.level) &&
               ((LoggerConfig) obj).separator.equals(this.separator));
    }



}
