package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TextPropertiesParserTestCases extends PropertiesParserTestCases{

    protected String fileName = "loggerAsResource.properties";

    @Before
    public void setup() throws UnsuportedFormatException, IOException, InvalidArgumentException {
        parser = PropertiesParserFactory.getParser(fileName.substring(fileName.lastIndexOf(".")));

        inputFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        assert inputFile != null;
    }

    @Test
    public void testRetrievesAll() throws IOException, InvalidArgumentException {
        Map<String, LoggerConfig> configs = parser.parseConfigFile(inputFile);
        assertEquals(3, configs.values().size());
    }

    @Test
    public void testRetrievesCompleteConfigs() throws IOException, InvalidArgumentException {

        Map<String, LoggerConfig> configs = parser.parseConfigFile(inputFile);
        LoggerConfig rootConfig = configs.get("root");
        LoggerConfig firstConfig = configs.get("first");
        LoggerConfig secondConfig = configs.get("second");

        assertNotNull(rootConfig);
        assertTrue(rootConfig.level.equals(Configurable.Level.valueOf("INFO")));
        assertEquals(1, rootConfig.getAppenders().size());
        assertEquals(0, rootConfig.getFilters().size());

        assertNotNull(firstConfig);
        assertTrue(firstConfig.level.equals(Configurable.Level.valueOf("DEBUG")));
        assertEquals(1, firstConfig.getAppenders().size());
        assertEquals(1, firstConfig.getFilters().size());

        assertNotNull(secondConfig);
        assertTrue(secondConfig.level.equals(Configurable.Level.valueOf("ERROR")));
        assertEquals(2, secondConfig.getAppenders().size());
        assertEquals(0, secondConfig.getFilters().size());
    }
}
