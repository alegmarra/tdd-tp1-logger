package com.fiuba.tdd.logger.format.parser;

import com.fiuba.tdd.logger.appenders.*;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;

import java.io.IOException;
import java.io.InputStream;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.format.parser.model.AppenderImp;
import com.fiuba.tdd.logger.format.parser.model.FilterImp;
import com.fiuba.tdd.logger.format.parser.model.LoggerProperties;
import com.fiuba.tdd.logger.format.parser.model.Parameter;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.appenders.Appendable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlPropertiesParser implements ConfigParser {

    public static final String TYPES = "types";
    public static final String VALUES = "values";

    @Override
    public LoggerConfig parseConfigFile(InputStream config) throws InvalidArgumentException, IOException {
        try {
            JAXBContext jc = JAXBContext.newInstance(LoggerProperties.class);
            Unmarshaller unmarshaller = null;
            unmarshaller = jc.createUnmarshaller();
            LoggerProperties logger = (LoggerProperties)unmarshaller.unmarshal(config);

            LoggerConfig parsedConfig = new LoggerConfig(logger.config.format,
                    Configurable.Level.valueOf(logger.config.level),
                    logger.config.separator);

            for(AppenderImp appender:  logger.config.appenders){
                try {
                    Object customAppender = getInstance(appender.implementation, appender.params);
                    if (customAppender instanceof Appendable)
                        parsedConfig.addAppender((Appendable) customAppender);
                    // else TODO

                } catch (ClassNotFoundException | InstantiationException e) {
                    e.printStackTrace();
                }
            }

            for(FilterImp filter: logger.config.filters){
                try {
                    Object customFilter = getInstance(filter.implementation, filter.params);
                    if (customFilter instanceof Filter)
                        parsedConfig.addFilter((Filter) customFilter);
                    // else TODO

                } catch (ClassNotFoundException | InstantiationException e) {
                    e.printStackTrace();
                }
            }

            return parsedConfig;

        } catch (JAXBException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Object getInstance(String className, List<Parameter> paramsList) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Map<String, Object> params = getConstructorParams(paramsList);

        Class<?> clazz = Class.forName(className);
        Constructor<?> construct = clazz.getConstructor((Class<?>[]) params.get(TYPES));
        return construct.newInstance((Object[])params.get(VALUES));
    }

    private Map<String, Object> getConstructorParams(List<Parameter> paramsList) throws ClassNotFoundException {
        Map<String, Object> params = new HashMap<>();
        Class[] paramsImp = null;
        Object[] paramsVal = null;
        if (paramsList != null) {
            paramsImp = new Class[paramsList.size()];
            paramsVal = new Object[paramsList.size()];
            int i=0;
            for (Parameter param : paramsList) {
                paramsImp[i] = Class.forName(param.type);
                paramsVal[i++] = param.value;
            }
        } else {
            paramsImp = new Class[]{};
            paramsVal = new Object[]{};
        }

        params.put(TYPES, paramsImp);
        params.put(VALUES, paramsVal);

        return params;
    }
}
