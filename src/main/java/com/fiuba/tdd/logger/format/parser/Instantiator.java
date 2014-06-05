package com.fiuba.tdd.logger.format.parser;


import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.filters.Filter;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Best name eva!
 */
public class Instantiator {

    public Appendable instantiateAppendable(String className, String... params) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvalidArgumentException {
        return castTo(instantiateObject(className, params), Appendable.class);
    }

    public Filter instantiateFilter(String className, String... params) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, InvalidArgumentException {
        return castTo(instantiateObject(className, params), Filter.class);
    }

    private Object instantiateObject(String className, String... params)
            throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<?> targetClass = Class.forName(className);
        Constructor<?> constructor = targetClass.getConstructor(String.class);
        // TODO: test this params call
        return constructor.newInstance(params);
    }

    private <T> T castTo(Object object, Class<T> classTarget) throws InvalidArgumentException {
        if (classTarget.isInstance(object))
            return classTarget.cast(object);
        else
            throw new InvalidArgumentException("Object is not instance of " + classTarget.getName());
    }
}
