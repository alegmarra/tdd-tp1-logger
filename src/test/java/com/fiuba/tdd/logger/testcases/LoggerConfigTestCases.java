package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.appenders.ConsoleAppender;
import com.fiuba.tdd.logger.filters.PatternFilter;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.Configurable.Level;
import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import com.fiuba.tdd.logger.utils.LoggerConfigBuilder;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoggerConfigTestCases {

    private final String fileFormat = "%d{HH:mm:ss} ­ %p ­ %t ­ %m";
    private final String fileSeparator = "::";
    private final Level defaultLevel = Level.INFO;
    private final Level fileLevel = Level.WARN;

    private final String defaultFormat = "%d{HH:mm:ss} %n %p %n %t %n %m ";
    private final String defaultSeparator = "-";

    private final String loggerXmlAsResource= "loggerXMLconfig.xml";

    private LoggerConfig defaultConfig;



    @Before
    public void initDefault() {
        defaultConfig = LoggerConfigBuilder.getConfig();
    }

    @Test
    public void testDefaultLoggerConfig_defaultFormat() throws IOException, InvalidArgumentException {
        assertEquals(defaultFormat, defaultConfig.format);
    }

    @Test
    public void testDefaultLoggerConfig_defaultSeparatorIsHyphen() throws IOException, InvalidArgumentException {
        assertEquals(defaultSeparator, defaultConfig.separator);
    }

    @Test
    public void testDefaultLoggerConfig_defaultLevelIsInfo() throws IOException, InvalidArgumentException {
        assertEquals(defaultLevel, defaultConfig.level);
    }

    @Test
    public void testDefaultLoggerConfig_defaultAppenderIsOnlyOne() throws IOException, InvalidArgumentException {
        assertEquals(1, defaultConfig.getAppenders().size());
    }

    @Test
    public void testDefaultLoggerConfig_defaultAppenderIsConsoleAppender() throws IOException, InvalidArgumentException {
        assertTrue(defaultConfig.getAppenders().get(0) instanceof ConsoleAppender);
    }

    @Test
    public void testDefaultLoggerConfig_defaultFilterIsOnlyOne() throws IOException, InvalidArgumentException {
        assertEquals(1, defaultConfig.getFilters().size());
    }

    @Test
    public void testDefaultLoggerConfig_defaultFilterIsPatternFilter() throws IOException, InvalidArgumentException {
        assertTrue  (defaultConfig.getFilters().get(0) instanceof PatternFilter);
    }

    // Sort of an integration test
    @Test
    public void testConfigFromPropertiesInClasspath() {


        try {
            LoggerConfigBuilder configTool = new LoggerConfigBuilder(loggerXmlAsResource);

            LoggerConfig complexConfig = configTool.getConfig("complex");
            LoggerConfig simpleConfig = configTool.getConfig("simple");

            assertNotNull(complexConfig);
            Assert.assertTrue(complexConfig.level.equals(Configurable.Level.valueOf("ERROR")));
            assertEquals(2, complexConfig.getAppenders().size());
            assertEquals(2, complexConfig.getFilters().size());

            assertNotNull(simpleConfig);
            Assert.assertTrue(simpleConfig.level.equals(Configurable.Level.valueOf("INFO")));
            assertEquals(1, simpleConfig.getAppenders().size());
            assertEquals(1, simpleConfig.getFilters().size());

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to read a file as resource. Cause: " + e.getCause());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgumentException was thrown when trying to read a valid file. Cause: " + e.getCause());
        }
    }
/*

    @Test
    public void testConfigFromExternalProperties() {

        try {
            LoggerConfigBuilder configTool = new LoggerConfigBuilder();

            LoggerConfig configs = configTool.getConfig();

            assertEquals(fileFormat, configs.format);
            assertEquals(fileSeparator, configs.separator);
            assertEquals(fileLevel, configs.level);

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

            LoggerConfig configs = configTool.getConfig();

            assertEquals(fileFormat, configs.format);

            assertEquals(defaultSeparator, configs.separator);
            assertEquals(defaultLevel, configs.level);

        } catch (IOException e) {
            e.printStackTrace();
            fail("An exception was thrown when trying to read a partial properties file. Cause: " + e.getCause());
        } catch (InvalidArgumentException e) {
            e.printStackTrace();
            fail("An InvalidArgumentException was thrown when trying to read a valid file. Cause: " + e.getCause());

        }
    }
/**/
}
