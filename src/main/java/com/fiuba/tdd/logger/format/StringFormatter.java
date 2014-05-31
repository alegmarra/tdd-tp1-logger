package com.fiuba.tdd.logger.format;

import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.util.GregorianCalendar;

/**
 * Clase de uso interno, cuya responsabilidad es generar el mensaje final a
 * partir de un formato definido
 */
public class StringFormatter extends MessageFormatter{

    public StringFormatter(LoggerConfig loggerConfig){

        super(loggerConfig);
    }

    public String formatMessage(LoggerInvoker invoker, String message){

        return format.replaceAll("%p", level.name())
                     .replaceAll("%m", message)
                     .replaceAll("%n", separator)
                     .replaceAll("%L", invoker.getLine())
                     .replaceAll("%F", invoker.getFilename())
                     .replaceAll("%M", invoker.getMethodName())
                     .replaceAll("%t", invoker.getThread())
                     .replaceAll(dateGroupPattern, dateFormatter.format(new GregorianCalendar().getTime()))
                     .replaceAll("%%", "%")
                     .replaceAll("%g", invoker.getName());
    }

}
