package com.fiuba.tdd.logger.testcases;

import com.fiuba.tdd.logger.exceptions.InvalidArgumentException;
import com.fiuba.tdd.logger.exceptions.UnsuportedFormatException;
import com.fiuba.tdd.logger.format.parser.ConfigParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParser;
import com.fiuba.tdd.logger.format.parser.PropertiesParserFactory;
import com.fiuba.tdd.logger.format.parser.XmlPropertiesParser;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class PropertiesFactoryTestCases {

    private ConfigParser parser;

    @Test
    public void testXml() throws UnsuportedFormatException, IOException, InvalidArgumentException {
        parser = PropertiesParserFactory.getParser(".xml");
        assertTrue(parser instanceof XmlPropertiesParser);
    }

    @Test
    public void testProperties()throws UnsuportedFormatException, IOException, InvalidArgumentException {
        parser = PropertiesParserFactory.getParser(".properties");
        assertTrue(parser instanceof PropertiesParser);
    }

    @Test(expected = UnsuportedFormatException.class)
    public void testUnsupportedFormatException()throws UnsuportedFormatException, IOException, InvalidArgumentException {

        PropertiesParserFactory.getParser(".rar");
    }
}
