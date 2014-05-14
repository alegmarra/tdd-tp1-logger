package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.Logger.Level;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.LoggerConfigTool;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LoggerConfigTestCases {

    private final Level defaultLevel = Level.DEBUG;
    private final String defaultFormat = "%d{HH:mm:ss} %n %p %n %t %n %m ";
    private final String defaultSeparator = "-";

    private final String propertiesInClassPath = "loggerAsResource.properties";
    private final String partialProperties = "partialLoggerConfig.properties";
    private final String propertiesInRoot = "./loggerAsExternal.properties";



    @Test
    public void testDefaultLoggerConfig() {

        LoggerConfigTool configTool = new LoggerConfigTool();

        LoggerConfig config = configTool.getConfig();

        assertEquals(defaultFormat, config.format);
        assertEquals(defaultSeparator, config.separator);
        assertEquals(defaultLevel, config.level);
    }

    @Test
    public void testConfigFromPropertiesInClasspath() {

        Level fileLevel = Level.WARN;
        String fileFormat = "%d{HH:mm:ss} ­ %p ­ %t ­ %m";
        String fileSeparator = "::";

        try {
            LoggerConfigTool configTool = new LoggerConfigTool(propertiesInClassPath);

            LoggerConfig config = configTool.getConfig();

            assertEquals(fileFormat, config.format);
            assertEquals(fileSeparator, config.separator);
            assertEquals(fileLevel, config.level);

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to read a file as resource. Cause: " + e.getCause());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgumentException was thrown when trying to read a valid file. Cause: " + e.getCause());
        }
    }

    @Test
    public void testConfigFromExternalProperties() {

        Level fileLevel = Level.WARN;
        String fileFormat = "%d{HH:mm:ss} ­ %p ­ %t ­ %m";
        String fileSeparator = "::";

        try {
            LoggerConfigTool configTool = new LoggerConfigTool(propertiesInRoot);

            LoggerConfig config = configTool.getConfig();

            assertEquals(fileFormat, config.format);
            assertEquals(fileSeparator, config.separator);
            assertEquals(fileLevel, config.level);

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to read a file from relative path. Cause: " + e.getCause());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgumentException was thrown when trying to read a valid file. Cause: " + e.getCause());
        }
    }


    @Test
    public void testExceptionThrownInvalidFile() {

        try {
            LoggerConfigTool configTool = new LoggerConfigTool("");
            fail("An exception was expected but none was thrown when trying to read an invalid file");

        } catch (IOException e) {
            e.printStackTrace();
            fail("An InvalidArgumentException was expected a different one was thrown");

        } catch (InvalidArgumentException e) {
            assertTrue(true);
        }
    }

    @Test
    public void testPartialConfigFromFile() {

        String fileFormat = "%d{HH:mm:ss} ­ %p ­ %t ­ %m";

        try {
            LoggerConfigTool configTool = new LoggerConfigTool(partialProperties);

            LoggerConfig config = configTool.getConfig();

            assertEquals(fileFormat, config.format);

            assertEquals(defaultSeparator, config.separator);
            assertEquals(defaultLevel, config.level);

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to read a partial properties file. Cause: " + e.getCause());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgumentException was thrown when trying to read a valid file. Cause: " + e.getCause());

        }
    }

}
