package com.fiuba.tdd.logger.format.parser;


import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.format.parser.model.AppenderDto;
import com.fiuba.tdd.logger.format.parser.model.ConfigDto;
import com.fiuba.tdd.logger.format.parser.model.FilterDto;
import com.fiuba.tdd.logger.format.parser.model.LoggerProperties;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractPropertiesParserTemplate implements ConfigParser{

    @Override
    public Map<String, LoggerConfig> parseConfigFile(InputStream config) throws InvalidArgumentException, IOException {

        Map<String, LoggerConfig> loggerConfigs = new HashMap<>();

        try {
            LoggerProperties logger = parseLoggerProperties(config);

            for (ConfigDto configDto: logger.configs){
                LoggerConfig parsedConfig = new LoggerConfig(configDto.format,
                        Configurable.Level.valueOf(configDto.level),
                        configDto.separator, configDto.formatter);

                addAppenders(configDto, parsedConfig);
                addFilters(configDto, parsedConfig);

                loggerConfigs.put(configDto.name, parsedConfig);
            }

            return loggerConfigs;

        } catch ( NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InvalidArgumentException("Unable to parse this file, invalid format");
        }
    }

    protected abstract LoggerProperties parseLoggerProperties(InputStream config) throws InvalidArgumentException, IOException;

    private void addFilters(ConfigDto configDto, LoggerConfig parsedConfig) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(FilterDto filter: configDto.filters){
            try {
                parsedConfig.addFilter(Instantiator.instantiate(filter));
            } catch (ClassNotFoundException | InstantiationException | InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void addAppenders(ConfigDto configDto, LoggerConfig parsedConfig) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(AppenderDto appender:  configDto.appenders){
            try {
                parsedConfig.addAppender(Instantiator.instantiate(appender));
            } catch (ClassNotFoundException | InstantiationException | InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
