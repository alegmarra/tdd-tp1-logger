package com.fiuba.tdd.logger.format;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.Configurable.Level;

/**
 *  Clase builder que permite un nivel de abstracción extra sobre el StringFormatter.
 *  Encapsula el uso del LoggerInvoker y el LoggerConfig
 * */
public class MessageFormatterBuilder {

    private static final int StackDepthFromLoggerInvokerToLog = 3;

    private LoggerConfig config = new LoggerConfig();
    private LoggerInvoker invoker = null;
    private StringFormatter stringFormatter = null;

    public MessageFormatterBuilder build(){

        stringFormatter = new StringFormatter(config);
        return this;
    }

    public MessageFormatterBuilder withConfig(final String format, final Level level, final String separator)
            throws InvalidArgumentException
    {
        config = new LoggerConfig(format, level, separator);
        return this;
    }

    public String formatMessage(String message, String loggerName){

        if (stringFormatter == null)
            throw new RuntimeException("Builder was not initialized properly. Formatter was null");

        invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[StackDepthFromLoggerInvokerToLog], loggerName);
        return stringFormatter.formatMessage(invoker, message);
    }
}