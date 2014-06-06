package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import com.fiuba.tdd.logger.format.parser.XmlPropertiesParser;
import com.fiuba.tdd.logger.utils.Configurable;
import com.fiuba.tdd.logger.utils.LoggerConfig;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class PropertiesParserTestCases {

    private ConfigParser parser;
    private InputStream inputFile;

    @Before
    public void setup() throws UnsuportedFormatException, IOException, InvalidArgumentException {
        parser = PropertiesParserFactory.getParser(".properties");

        inputFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("loggerAsResource.properties");
        assert inputFile != null;
    }
    @Test
    public void testFromPath() throws UnsuportedFormatException, IOException, InvalidArgumentException {

        List<LoggerConfig> conf = parser.parseConfigFile(inputFile);

        assertTrue(conf.get(0).level.equals(Configurable.Level.valueOf("WARN")));
    }

}
