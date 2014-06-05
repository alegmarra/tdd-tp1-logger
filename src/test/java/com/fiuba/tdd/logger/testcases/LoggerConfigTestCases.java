package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.utils.Configurable.Level;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.LoggerConfigBuilder;
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
    public void testDefaultLoggerConfig() throws IOException, InvalidArgumentException {

        LoggerConfigBuilder configTool = new LoggerConfigBuilder();

        LoggerConfig config = configTool.getConfig();

        assertEquals(defaultFormat, config.format);
        assertEquals(defaultSeparator, config.separator);
        assertEquals(defaultLevel, config.level);
    }

    @Test
    public void testConfigFromPropertiesInClasspath() {


        try {
            LoggerConfigBuilder configTool = new LoggerConfigBuilder();

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
            LoggerConfigBuilder configTool = new LoggerConfigBuilder();

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
            LoggerConfigBuilder configTool = new LoggerConfigBuilder();

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
