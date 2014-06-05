package com.fiuba.tdd.logger.format;

import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class MessageFormatter {

    protected enum PatternName {level, message, separator, line, fileName, methodName, thread, dateTime, logger}

    protected final Map<String, PatternName> patternKeys = new HashMap<String, PatternName>(){{
                                                                    put("%p", PatternName.level);
                                                                    put("%t", PatternName.thread);
                                                                    put("%m", PatternName.message);
                                                                    put("%n", PatternName.separator);
                                                                    put("%L", PatternName.line);
                                                                    put("%F", PatternName.fileName);
                                                                    put("%M", PatternName.methodName);
                                                                    put("%g", PatternName.logger);
                                                            }};

    protected final String dateFormatDeclarationPattern = ".*?%d\\{([^\\}]+)}.*?";
    protected final String dateGroupPattern = "%d\\{([^\\}]+)}";

    protected final String separator;
    protected final Configurable.Level level;

    protected SimpleDateFormat dateFormatter;
    protected String format;


    public abstract String formatMessage(LoggerInvoker invoker, String message);


    protected MessageFormatter(LoggerConfig loggerConfig){

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

    protected Map<PatternName,String> getValuesByPatternName(final LoggerInvoker invoker, final String msg) {

        return new HashMap<PatternName, String>(){{
            put(PatternName.message, msg);
            put(PatternName.level, level.name());
            put(PatternName.separator, separator);
            put(PatternName.line, invoker.getLine());
            put(PatternName.fileName, invoker.getFilename());
            put(PatternName.methodName, invoker.getMethodName());
            put(PatternName.thread, invoker.getThread());
            put(PatternName.logger, invoker.getName());
            put(PatternName.dateTime, dateFormatter.format(new GregorianCalendar().getTime()));
        }};
    }

}
