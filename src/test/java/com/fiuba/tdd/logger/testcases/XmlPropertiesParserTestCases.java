package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class XmlPropertiesParserTestCases extends PropertiesParserTestCases{

    protected String fileName = "loggerXMLconfig.xml";

    @Before
    public void setup() throws UnsuportedFormatException, IOException, InvalidArgumentException {
        parser = PropertiesParserFactory.getParser(fileName.substring(fileName.lastIndexOf(".")));

        inputFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
        assert inputFile != null;
    }

    @Test
    public void test() throws IOException, InvalidArgumentException {

        Map<String, LoggerConfig> configs = parser.parseConfigFile(inputFile);
        LoggerConfig complexConfig = configs.get("complex");
        LoggerConfig simpleConfig = configs.get("simple");

        assertNotNull(complexConfig);
        assertTrue(complexConfig.level.equals(Configurable.Level.valueOf("ERROR")));
        assertEquals(2, complexConfig.getAppenders().size());
        assertEquals(2, complexConfig.getFilters().size());

        assertNotNull(simpleConfig);
        assertTrue(simpleConfig.level.equals(Configurable.Level.valueOf("INFO")));
        assertEquals(1, simpleConfig.getAppenders().size());
        assertEquals(1, simpleConfig.getFilters().size());
    }
}
