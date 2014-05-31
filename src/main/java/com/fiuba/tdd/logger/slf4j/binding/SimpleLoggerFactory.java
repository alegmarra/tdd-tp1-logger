package com.fiuba.tdd.logger.slf4j.binding;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

public class SimpleLoggerFactory implements ILoggerFactory {

    @Override
    public Logger getLogger(String s) {
        return new SimpleLoggerAdapter(s);
    }
}
