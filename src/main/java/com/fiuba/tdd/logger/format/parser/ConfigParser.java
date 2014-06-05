package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public interface ConfigParser {

    public java.util.List<LoggerConfig> parseConfigFile(InputStream config) throws InvalidArgumentException, IOException;

}
