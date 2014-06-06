package com.fiuba.tdd.logger.format.parser;


import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.format.parser.model.AppenderDto;
import com.fiuba.tdd.logger.format.parser.model.FilterDto;
import com.fiuba.tdd.logger.format.parser.model.Parameter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Best name eva!
 */
public abstract class Instantiator {

    private static final String TYPES = "types";
    private static final String VALUES = "values";

    public static Appendable instantiateAppendable(AppenderDto appender) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvalidArgumentException {
        return castTo(getInstance(appender.implementation, appender.params), Appendable.class);
    }

    private Object instantiateObject(String className, String... params)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> targetClass = Class.forName(className);
        Constructor<?> constructor = targetClass.getConstructor(String.class);
        // TODO: test this params call
        return constructor.newInstance(params);
    }

    private static <T> T castTo(Object object, Class<T> classTarget) throws InvalidArgumentException {
        if (classTarget.isInstance(object))
            return classTarget.cast(object);
        else
            throw new InvalidArgumentException("Object is not instance of " + classTarget.getName());
    }

    private static Object getInstance(String className, List<Parameter> paramsList) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        Map<String, Object> params = getConstructorParams(paramsList);

        Class<?> clazz = Class.forName(className);
        Constructor<?> construct = clazz.getConstructor((Class<?>[]) params.get(TYPES));
        return construct.newInstance((Object[])params.get(VALUES));
    }

    private static Map<String, Object> getConstructorParams(List<Parameter> paramsList) throws ClassNotFoundException {
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
