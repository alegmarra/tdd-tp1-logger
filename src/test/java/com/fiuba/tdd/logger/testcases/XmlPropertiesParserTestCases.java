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

import static org.junit.Assert.assertEquals;
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

        LoggerConfig config = parser.parseConfigFile(inputFile);
        assertTrue(config.level.equals(Configurable.Level.valueOf("ERROR")));
        assertEquals(2,config.getAppenders().size());
        assertEquals(1,config.getFilters().size());
    }

}
