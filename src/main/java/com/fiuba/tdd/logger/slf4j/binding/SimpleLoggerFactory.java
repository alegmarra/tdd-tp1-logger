package com.fiuba.tdd.logger.slf4j.binding;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.LoggerConfigBuilder;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SimpleLoggerFactory implements ILoggerFactory {

    static private Map<String, Logger> loggers = new HashMap<>();
    static private LoggerConfigBuilder loggersConfigs;

    public SimpleLoggerFactory() {
        try {
            loggersConfigs = new LoggerConfigBuilder();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Logger getLogger(String name) {
        if (name == null) throw new RuntimeException("Requested Logger with a null name");

        Logger logger = loggers.get(name);
        LoggerConfig defaultConfig = LoggerConfigBuilder.getConfig();
        LoggerConfig loggerConfig = loggersConfigs == null ? defaultConfig : loggersConfigs.getConfig(name);
        if (loggerConfig == null)
            loggerConfig = defaultConfig;

        if (logger == null) {
            logger = new SimpleLoggerAdapter(name, loggerConfig);
            loggers.put(name, logger);
        }

        return logger;
    }
}
