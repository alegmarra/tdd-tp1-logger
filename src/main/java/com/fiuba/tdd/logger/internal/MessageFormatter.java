package com.fiuba.tdd.logger.internal;

import com.fiuba.tdd.logger.Logger.Level;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Clase de uso interno, cuya responsabilidad es generar el mensaje final a
 * partir de un formato definido
 */
public class MessageFormatter {

    private final String dateFormatDeclarationPattern = ".*?%d\\{([^\\}]+)}.*?";
    private final String dateGroupPattern = "%d\\{([^\\}]+)}";

    private final String separator;
    private final Level level;

    private SimpleDateFormat dateFormatter;
    private String format;

    public MessageFormatter(LoggerConfig loggerConfig){

        this.format = loggerConfig.format;
        this.separator = loggerConfig.separator;
        this.level = loggerConfig.level;

        this.dateFormatter = setDateFormatter();
    }

    private SimpleDateFormat setDateFormatter() {

        Pattern pattern = Pattern.compile(dateFormatDeclarationPattern);
        Matcher matcher = pattern.matcher(format);

        String dateFormat = matcher.matches() ? matcher.group(1) : "";

        return dateFormat.isEmpty() ? new SimpleDateFormat() : new SimpleDateFormat(dateFormat);
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
                     .replaceAll("%%", "%");
    }

}
