package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.SimpleLogger.Level;
import com.fiuba.tdd.logger.internal.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.LoggerConfigTool;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class LoggerConfigTestCases {

    private final String fileFormat = "%d{HH:mm:ss} ­ %p ­ %t ­ %m";
    private final String fileSeparator = "::";
    private final Level defaultLevel = Level.INFO;
    private final Level fileLevel = Level.WARN;

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
    public void testPartialConfigFromFile() {

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

    @Test( expected = InvalidArgumentException.class)
    public void testExceptionThrownInvalidFile() throws IOException, InvalidArgumentException {

        new LoggerConfigTool("");
    }
}
