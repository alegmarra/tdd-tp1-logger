package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.SimpleLogger;
import com.fiuba.tdd.logger.slf4j.binding.SimpleLoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;

public class SimpleLoggerFactoryTestCases {

    private SimpleLoggerFactory simpleLoggerFactory;

    @Before
    public void initLoggerFactory() {
        simpleLoggerFactory = new SimpleLoggerFactory();
    }



    @Test(expected = RuntimeException.class)
    public void getLogger_withNullNameTest() {
        simpleLoggerFactory.getLogger(null);
    }

    @Test(expected = RuntimeException.class)
    public void getLogger_withEmptyStringTest() {
        simpleLoggerFactory.getLogger("");
    }

    @Test
    public void getLogger_shouldGetTheSameLoggerWhenNamesAreEqualTest() {
        Logger expectedLogger = simpleLoggerFactory.getLogger("testLogger");
        Logger actualLogger = simpleLoggerFactory.getLogger("testLogger");

        assertEquals(expectedLogger, actualLogger);
    }

    @Test
    public void getLogger_shouldGetNotNullLoggerTest() {
        Logger actualLogger = simpleLoggerFactory.getLogger("testLogger");

        assertNotNull(actualLogger);
    }

    @Test
    public void getLogger_shouldGetAnotherLoggerWhenNamesAreDifferentTest() {
        Logger expectedLogger = simpleLoggerFactory.getLogger("testLogger-1");
        Logger actualLogger = simpleLoggerFactory.getLogger("testLogger-2");

        assertNotSame(expectedLogger, actualLogger);
    }
}
