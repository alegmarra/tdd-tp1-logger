package com.fiuba.tdd.logger.internal;

import com.fiuba.tdd.logger.Logger.Level;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageFormatter {

    private final String dateFormatDeclarationPattern = ".*?%d\\{([^\\}]+)}.*?";
    private final String dateGroupPattern = "%d\\{([^\\}]+)}";

    private final String separator;
    private final Level level;

    private SimpleDateFormat dateFormatter;

    private String format;

    public MessageFormatter(String format, String separator, Level logLevel){
        this.format = format;
        this.separator = separator;
        this.level = logLevel;

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
                     .replaceAll("%t", Thread.currentThread().getName())
                     .replaceAll(dateGroupPattern, dateFormatter.format(new GregorianCalendar().getTime()))
                     .replaceAll("%%", "%");
    }

}
