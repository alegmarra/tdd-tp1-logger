package com.fiuba.tdd.logger.internal;

import com.fiuba.tdd.logger.SimpleLogger.Level;
import com.fiuba.tdd.logger.utils.LoggerConfig;

/**
 *  Clase builder que permite un nivel de abstracción extra sobre el MessageFormatter.
 *  Encapsula el uso del LoggerInvoker y el LoggerConfig
 * */
public class MessageFormatterBuilder {

    private static final int StackDepthFromLoggerInvokerToLog = 3;

    private LoggerConfig config = new LoggerConfig();
    private LoggerInvoker invoker = null;
    private MessageFormatter messageFormatter = null;

    public MessageFormatterBuilder build(){

        messageFormatter = new MessageFormatter(config);
        return this;
    }

    public MessageFormatterBuilder withConfig(final String format, final Level level, final String separator){
        config = new LoggerConfig(format, level, separator);
        return this;
    }

    public String formatMessage(String message){

        if (messageFormatter == null)
            throw new RuntimeException("Builder was not initialized properly");

        invoker = new LoggerInvoker(Thread.currentThread().getStackTrace()[StackDepthFromLoggerInvokerToLog]);
        return messageFormatter.formatMessage(invoker, message);
    }
}