package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.appenders.Appendable;
import com.fiuba.tdd.logger.appenders.CustomAppendable;
import com.fiuba.tdd.logger.filters.CustomFilter;
import com.fiuba.tdd.logger.filters.Filter;
import com.fiuba.tdd.logger.format.parser.Instantiator;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InstantiatorTestCases {
    private final static String existentAppendable = "com.fiuba.tdd.logger.appenders.CustomAppendable";
    private final static String existentFilter = "com.fiuba.tdd.logger.filters.CustomFilter";
    private Instantiator instantiator;

    @Before
    public void initInstantiator() {
        instantiator = new Instantiator();
    }


    @Test(expected = ClassNotFoundException.class)
    public void instantiateAppendable_ClassNotFound() throws ClassNotFoundException {
        try {
            instantiator.instantiateAppendable("asdfas");
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
            Appendable myFilter = instantiator.instantiateAppendable(existentAppendable);
            assertTrue(myFilter instanceof CustomAppendable);
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to dynamically instantiate an Appendable. Cause: " + e.getCause());
        }
    }


    @Test(expected = ClassNotFoundException.class)
    public void instantiateFilter_ClassNotFound() throws ClassNotFoundException {
        try {
            instantiator.instantiateFilter("asdfas");
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
            Filter myFilter = instantiator.instantiateFilter(existentFilter);
            assertTrue(myFilter instanceof CustomFilter);
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to dynamically instantiate a Filter. Cause: " + e.getCause());
        }
    }

}
