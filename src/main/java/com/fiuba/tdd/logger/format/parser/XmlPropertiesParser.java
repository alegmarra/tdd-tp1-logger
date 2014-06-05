package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.io.InputStream;

public class XmlPropertiesParser implements ConfigParser {
    @Override
    public LoggerConfig parseConfigFile(InputStream config) throws InvalidArgumentException, IOException {
        return null;
    }
}
