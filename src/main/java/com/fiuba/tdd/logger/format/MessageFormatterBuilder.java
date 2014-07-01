package com.fiuba.tdd.logger.format;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.Configurable.Level;

/**
 *  Clase builder que permite un nivel de abstracción extra sobre el StringFormatter.
 *  Encapsula el uso del LoggerInvoker y el LoggerConfig
 * */
public class MessageFormatterBuilder {

    public static enum MessageFormat {STRING, JSON}

    private static final int StackDepthFromLoggerInvokerToLog = 5;

    private LoggerConfig config = new LoggerConfig();
    private MessageFormatter formatter = null;

    public MessageFormatterBuilder build(){
        return build(MessageFormat.STRING);
    }

    public MessageFormatterBuilder build(MessageFormat type){
        switch (type){
            case STRING: formatter = new StringFormatter(config);
                break;
            case JSON: formatter = new JSONFormatter(config);
                break;
        }

        return this;
    }

    public MessageFormatterBuilder withConfig(final String format, final Level level, final String separator)
            throws InvalidArgumentException
    {
        config = new LoggerConfig(format, level, separator, "STRING");
        return this;
    }

    public String formatMessage(String message, String loggerName){

        if (formatter == null)
            throw new RuntimeException("Builder was not initialized properly. Formatter was null");

        LoggerInvoker invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[StackDepthFromLoggerInvokerToLog], loggerName);
        return formatter.formatMessage(invoker, message);
    }
}