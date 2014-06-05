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
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class XmlPropertiesParserTestCases {

    private ConfigParser parser;
    private InputStream inputFile;

    @Before
    public void setup() throws UnsuportedFormatException, IOException, InvalidArgumentException {
        parser = PropertiesParserFactory.getParser(".xml");

        inputFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("loggerXMLconfig.xml");
        assert inputFile != null;
    }

    @Test
    public void test() throws IOException, InvalidArgumentException {

        List<LoggerConfig> configs = parser.parseConfigFile(inputFile);

        assertNotNull(configs.get(0));
        assertTrue(configs.get(0).level.equals(Configurable.Level.valueOf("ERROR")));
        assertEquals(2, configs.get(0).getAppenders().size());
        assertEquals(1, configs.get(0).getFilters().size());

        assertNotNull(configs.get(1));
        assertTrue(configs.get(1).level.equals(Configurable.Level.valueOf("INFO")));
        assertEquals(1, configs.get(1).getAppenders().size());
        assertEquals(1, configs.get(1).getFilters().size());
    }

}
