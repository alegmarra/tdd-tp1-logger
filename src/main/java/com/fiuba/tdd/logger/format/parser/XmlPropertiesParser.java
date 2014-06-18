package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.format.parser.model.AppenderDto;
import com.fiuba.tdd.logger.format.parser.model.ConfigDto;
import com.fiuba.tdd.logger.format.parser.model.FilterDto;
import com.fiuba.tdd.logger.format.parser.model.LoggerProperties;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlPropertiesParser implements ConfigParser {

    @Override
    public Map<String, LoggerConfig> parseConfigFile(InputStream config) throws InvalidArgumentException, IOException {

        List<LoggerConfig> loggerConfigs = new ArrayList<>();

        try {
            JAXBContext jc = JAXBContext.newInstance(LoggerProperties.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            LoggerProperties logger = (LoggerProperties)unmarshaller.unmarshal(config);

            for (ConfigDto configDto: logger.configs){
                LoggerConfig parsedConfig = new LoggerConfig(configDto.format,
                        Configurable.Level.valueOf(configDto.level),
                        configDto.separator);

                addAppenders(configDto, parsedConfig);
                addFilters(configDto, parsedConfig);

                loggerConfigs.add(parsedConfig);
            }

            return new HashMap<>();

        } catch (JAXBException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            throw new InvalidArgumentException("Unable to parse this file, invalid format");
        }
    }

    private void addFilters(ConfigDto configDto, LoggerConfig parsedConfig) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(FilterDto filter: configDto.filters){
            try {
                parsedConfig.addFilter(Instantiator.instantiateFilter(filter));
            } catch (ClassNotFoundException | InstantiationException | InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }

    private void addAppenders(ConfigDto configDto, LoggerConfig parsedConfig) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        for(AppenderDto appender:  configDto.appenders){
            try {
                parsedConfig.addAppender(Instantiator.instantiateAppendable(appender));
            } catch (ClassNotFoundException | InstantiationException | InvalidArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
