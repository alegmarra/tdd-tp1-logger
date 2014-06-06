package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.appenders.CustomAppendable;
import com.fiuba.tdd.logger.filters.CustomFilter;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.format.parser.Instantiator;
import com.fiuba.tdd.logger.format.parser.model.AppenderDto;
import com.fiuba.tdd.logger.format.parser.model.FilterDto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InstantiatorTestCases {
    private final static String existentAppendableName = "com.fiuba.tdd.logger.appenders.CustomAppendable";
    private final static String existentFilterName = "com.fiuba.tdd.logger.filters.CustomFilter";
    private AppenderDto existentAppender;
    private AppenderDto nonExistentAppender;
    private FilterDto existentFilter;
    private FilterDto nonExistentFilter;

    @Before
    public void initInstantiator() {
        existentAppender = new AppenderDto();
        existentAppender.implementation = existentAppendableName;
        nonExistentAppender = new AppenderDto();
        nonExistentAppender.implementation = "asdfa";
        existentFilter = new FilterDto();
        nonExistentFilter.implementation = existentFilterName;
        nonExistentFilter = new FilterDto();
        nonExistentFilter.implementation = "asdfa";
    }


    @Test(expected = ClassNotFoundException.class)
    public void instantiateAppendable_ClassNotFound() throws ClassNotFoundException {
        try {
            Instantiator.instantiateAppendable(nonExistentAppender);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception besides ClassNotFound was thrown when trying to instantiate an unexistent class. Cause: " + e.getCause());
        }
    }

    @Test
    public void instantiateAppendable_ConstructorWithoutArguments() {
        try {
            Appendable myFilter = Instantiator.instantiateAppendable(existentAppender);
            assertTrue(myFilter instanceof CustomAppendable);
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to dynamically instantiate an Appendable. Cause: " + e.getCause());
        }
    }


    @Test(expected = ClassNotFoundException.class)
    public void instantiateFilter_ClassNotFound() throws ClassNotFoundException {
        try {
            Instantiator.instantiateFilter(nonExistentFilter);
        } catch (ClassNotFoundException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception besides ClassNotFound was thrown when trying to instantiate an unexistent class. Cause: " + e.getCause());
        }
    }

    @Test
    public void instantiateFilter_ConstructorWithoutArguments() {
        try {
            Filter myFilter = Instantiator.instantiateFilter(existentFilter);
            assertTrue(myFilter instanceof CustomFilter);
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to dynamically instantiate a Filter. Cause: " + e.getCause());
        }
    }

}