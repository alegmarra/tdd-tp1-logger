package com.fiuba.tdd.logger.slf4j.binding;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class SimpleLoggerFactory implements ILoggerFactory {

    static private Map<String, Logger> loggers = new HashMap<>();

    @Override
    public Logger getLogger(String name) {
        if (name == null) throw new RuntimeException("Requested Logger with a null name");

        Logger logger = loggers.get(name);

        if (logger == null) {
            logger = new SimpleLoggerAdapter(name);
            loggers.put(name, logger);
        }

        return logger;
    }
}
